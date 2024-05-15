package com.example.groupassignment.activity;
/**
 * @author Wenzhao Zheng u7705888
 * */
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.groupassignment.utility.MyAppGlideModule;

import com.bumptech.glide.Glide;

import com.example.groupassignment.AssignmentDemonstrationActivity;
import com.example.groupassignment.DAO.Profile;
import com.example.groupassignment.MenuPage;
import com.example.groupassignment.R;
import com.example.groupassignment.activity.FriendsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    Profile profile;
    private TextView UsernameProfile;
    private TextView EmailProfile;
    private ListView CourseList;
    private Button ButtonProfile2Menu;
    private Button ButtonProfile2Friends;
    private Button ButtonProfile2Courses;
    private ImageView HeadImage;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> friends;
    private List<String> coursesToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        UsernameProfile = (TextView) findViewById(R.id.UsernameProfile);
        EmailProfile = (TextView) findViewById(R.id.EmailProfile);
        CourseList = (ListView) findViewById(R.id.CoursesList);
        ButtonProfile2Menu = (Button) findViewById(R.id.ButtonProfile2Menu);
        ButtonProfile2Courses = (Button) findViewById(R.id.ButtonProfile2Courses);
        ButtonProfile2Friends = (Button) findViewById(R.id.ButtonProfile2Friends);
        HeadImage = (ImageView) findViewById(R.id.imageView14);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        CourseList.setAdapter(adapter);
        //Get username from sharedPreferences initialised in the login
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME_KEY", "defaultUsername");
        //System.out.println(username);
        String password = sharedPreferences.getString("PASSWORD_KEY", "defaultPassword");
        //Get courses list from favourite list
        coursesToAdd = getIntent().getStringArrayListExtra("courses_list");

        //Download and update the profile
        downloadProfile(username, FirebaseStorage.getInstance());

        //Button to go back to menu
        ButtonProfile2Menu.setOnClickListener(v -> {
            Intent intent0 = new Intent(ProfileActivity.this, MenuPage.class);
            startActivity(intent0);
        });
        //Button to go to the friend list page
        ButtonProfile2Friends.setOnClickListener(v -> {
            Intent intent1 = new Intent(ProfileActivity.this, FriendsActivity.class);
            intent1.putExtra("friendsList", friends);
            String currentUserName = UsernameProfile.getText().toString();
            intent1.putExtra("currentUserName",currentUserName);
            startActivity(intent1);
        });
        //Button to go to the assignment page
        ButtonProfile2Courses.setOnClickListener(v -> {
            Intent intent2 = new Intent(ProfileActivity.this, AssignmentDemonstrationActivity.class);
            startActivity(intent2);
        });
    }

    /**
     * Method for downloading json format profile from storage
     * and update the data to display format of profile
     * Download the profile from storage Profiles/ directory and translate json to Profile
     * Iterate all elements of the profile page, fill the profile page with respective data
     * @param username String username to identify a profile document in storage
     * @param storage Storage initialised when calling the method
     */
    @SuppressLint("SetTextI18n")
    public void downloadProfile(String username, FirebaseStorage storage) {
        StorageReference profileRef = storage.getReference().child("Profiles/" + username + ".json");

        profileRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
            String json = new String(bytes);
            System.out.println("Downloaded Profile json " + json);
            profile = new Gson().fromJson(json, Profile.class);
            if (profile!=null){
                //Update course list from favourite list
                if (coursesToAdd!=null && !coursesToAdd.equals(profile.getCourses())){
                    profile.updateCourses(coursesToAdd);
                }
                //Update all information from data of Profile class for display
                if(profile.getUsername()!=null){
                    System.out.println("The user name is " + profile.getUsername());
                    UsernameProfile.setText(profile.getUsername());
                }
                if(profile.getEmail()!=null){
                    EmailProfile.setText(profile.getEmail());
                    System.out.println("The email is " + profile.getEmail());
                }else {
                    EmailProfile.setText("Fail to load email address");
                }
                if(profile.getCourses()!=null){
                    adapter.clear();
                    adapter.addAll(profile.getCourses());
                    adapter.notifyDataSetChanged();
                }
                if(profile.getFriends()!=null){
                    friends = (ArrayList<String>) profile.getFriends();
                    for (String friend : friends){
                        System.out.println(friend);
                    }
                }
                if (profile.getProfileImageUrl()!=null){
                    getHeadImage();
                }
                //Update the profile of the storage
                profile.uploadProfileJson(profile);
                //Print the identifier of the profile to check if it is downloaded correctly
                System.out.println("Profile downloaded: " + profile.getUsername());
            }
        }).addOnFailureListener(e -> {
            System.err.println("Failed to download profile JSON: " + e.getMessage());
        });
    }

    /**
     * Method for updating head image
     */
    public void getHeadImage(){
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + profile.getProfileImageUrl());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            //Method for getting image and handle error where no image is loaded from storage
            //Reference: https://stackoverflow.com/questions/42868036/android-how-to-load-image-by-name-using-glide
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(HeadImage.getContext())
                        .load(uri.toString())
                        .error(R.drawable.user_default)
                        .into(HeadImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

}
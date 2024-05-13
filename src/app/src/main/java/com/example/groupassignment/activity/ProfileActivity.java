package com.example.groupassignment.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import com.example.groupassignment.AssignmentDemonstrationActivity;
import com.example.groupassignment.DAO.Profile;
import com.example.groupassignment.MenuPage;
import com.example.groupassignment.R;
import com.example.groupassignment.activity.FriendsActivity;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.ArrayList;

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

        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME_KEY", "defaultUsername");
        String password = sharedPreferences.getString("PASSWORD_KEY", "defaultPassword");

        downloadProfile(username, FirebaseStorage.getInstance());

        ButtonProfile2Menu.setOnClickListener(v -> {
            Intent intent0 = new Intent(ProfileActivity.this, MenuPage.class);
            startActivity(intent0);
        });
        //Following button use temporary classes for "go-to", further classes including friends.java and courses.java will be done later
        //Or the button will be replaced by listview or removed
        ButtonProfile2Friends.setOnClickListener(v -> {
            Intent intent1 = new Intent(ProfileActivity.this, FriendsActivity.class);
            intent1.putExtra("friendsList", friends);
            String currentUserName = UsernameProfile.getText().toString();
            intent1.putExtra("currentUserName",currentUserName);
            startActivity(intent1);
        });
        ButtonProfile2Courses.setOnClickListener(v -> {
            Intent intent2 = new Intent(ProfileActivity.this, AssignmentDemonstrationActivity.class);
            startActivity(intent2);
        });
    }


    public void downloadProfile(String username, FirebaseStorage storage) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("Profiles/" + username + ".json");

        profileRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
            String json = new String(bytes);
            System.out.println("Downloaded Profile json " + json);
            profile = new Gson().fromJson(json, Profile.class);
            if (profile!=null){
                if(profile.getUsername()!=null){
                    System.out.println("Profile " + profile.getUsername());
                    UsernameProfile.setText(profile.getUsername());
                }
                if(profile.getEmail()!=null){
                    EmailProfile.setText(profile.getEmail());
                    System.out.println("Email is " + profile.getEmail());
                }else {
                    EmailProfile.setText(profile.getUsername());
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
                    //TODO:For checkpoint 2, please ask why the image load keeps failing
                    //    I have added all required dependencies:
                    //    implementation("com.github.bumptech.glide:glide:4.16.0")
                    //    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
                    //    The url is manually set to be correct
                    //    Error: class com.bumptech.glide.load.engine.GlideException: Failed to load resource
                    Glide.with(HeadImage.getContext())
                            .load(profile.getProfileImageUrl())
                            .override(126, 126)
                            .into(HeadImage);
                }
            }
            System.out.println("Profile downloaded: " + profile.getUsername());
        }).addOnFailureListener(e -> {
            System.err.println("Failed to download profile JSON: " + e.getMessage());
        });
    }

    /*protected void updateProfile(Profile profile){
        //UsernameProfile.setText(profile.getUsername());
        //EmailProfile.setText(profile.getEmail());

        /*adapter.clear();
        adapter.addAll(profile.getCourses());
        adapter.notifyDataSetChanged();*/

        /*RequestOptions options = new RequestOptions().override(128, 128).centerCrop();
        Glide.with(HeadImage.getContext())
                .load(profile.getProfileImageUrl())
                .apply(options)
                .into(HeadImage);
    }*/
}
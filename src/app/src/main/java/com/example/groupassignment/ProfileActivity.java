package com.example.groupassignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        UsernameProfile = (TextView) findViewById(R.id.UsernameProfile);
        EmailProfile = (TextView) findViewById(R.id.EmailProfile);
        CourseList = (ListView) findViewById(R.id.CourseList);
        ButtonProfile2Menu = (Button) findViewById(R.id.ButtonProfile2Menu);
        ButtonProfile2Courses = (Button) findViewById(R.id.ButtonProfile2Courses);
        ButtonProfile2Friends = (Button) findViewById(R.id.ButtonProfile2Friends);
        HeadImage = (ImageView) findViewById(R.id.imageView14);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        CourseList.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME_KEY", "defaultUsername");
        String password = sharedPreferences.getString("PASSWORD_KEY", "defaultPassword");

        fetchUserProfile(username, password);
        updateProfile(profile);

        ButtonProfile2Menu.setOnClickListener(v -> {
            Intent intent0 = new Intent(ProfileActivity.this, MenuPage.class);
            startActivity(intent0);
        });
        //Following button use temporary classes for "go-to", further classes including friends.java and courses.java will be done later
        //Or the button will be replaced by listview or removed
        ButtonProfile2Friends.setOnClickListener(v -> {
            Intent intent1 = new Intent(ProfileActivity.this, showInfoActivity.class);
            startActivity(intent1);
        });
        ButtonProfile2Courses.setOnClickListener(v -> {
            Intent intent2 = new Intent(ProfileActivity.this, AssignmentDemonstrationActivity.class);
            startActivity(intent2);
        });
    }

    private void fetchUserProfile(String username, String password) {
        // get data from firestore
        // use firestore to store profile information, further development to modify login is required later
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    profile = document.toObject(Profile.class);
                } else {
                    Log.d("Firebase", "No such document");
                }
            } else {
                Log.d("Firebase", "get failed with ", task.getException());
            }
        });
    }

    protected void updateProfile(Profile profile){
        UsernameProfile.setText(profile.getUsername());
        EmailProfile.setText(profile.getEmail());

        adapter.clear();
        adapter.addAll(profile.getCourses());
        adapter.notifyDataSetChanged();

        RequestOptions options = new RequestOptions().override(128, 128).centerCrop();
        Glide.with(HeadImage.getContext())
                .load(profile.getProfileImageUrl())
                .apply(options)
                .into(HeadImage);
    }
}
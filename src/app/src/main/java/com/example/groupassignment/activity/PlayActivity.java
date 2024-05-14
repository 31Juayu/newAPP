package com.example.groupassignment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.MediaController;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.groupassignment.DAO.Profile;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;


import java.util.HashMap;
import java.util.Map;


import com.example.groupassignment.R;

public class PlayActivity extends AppCompatActivity {
    private TextView video_name_textView;
    private VideoView current_VideoView;
    MediaController mediaController;

    private Button go_back_play;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);



        video_name_textView = (TextView) findViewById(R.id.current_video_name);
        current_VideoView = (VideoView) findViewById(R.id.current_video_play);
        mediaController = new MediaController(this);
        String toPlayName = getIntent().getStringExtra("toPlayName");
        String toPlayView = getIntent().getStringExtra("toPlayView");
        video_name_textView.setText(toPlayName);
        current_VideoView.setVideoPath(toPlayView);
        current_VideoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(current_VideoView);
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME_KEY", "defaultUsername");
        String password = sharedPreferences.getString("PASSWORD_KEY", "defaultPassword");
        SharedPreferences.Editor editor = sharedPreferences.edit();


        // pzy 新增了play页面的switch按钮用于收藏和返回按钮
        favoriteSwitch = findViewById(R.id.switch_favorite);
        favoriteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addToFavorites(username, toPlayName, toPlayView);
                    editor.putString("COURSE_KEY", toPlayName);
                    editor.apply();
                } else {
                    removeFromFavorites(username, toPlayName, toPlayView);
                    editor.remove("COURSE_KEY");
                    editor.apply();
                }
            }
        });
        checkFavoriteStatus(username, toPlayName, toPlayView);

        Button backButton = findViewById(R.id.backButton11);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // 结束当前Activity，返回到前一个Activity
            }
        });



        current_VideoView.start();

        go_back_play = (Button) findViewById(R.id.go_back_play);

        go_back_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void addToFavorites(String username, String videoName, String videoUrl) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> favorite = new HashMap<>();
        favorite.put("username", username);
        favorite.put("videoName", videoName);
        favorite.put("videoUrl", videoUrl);

        db.collection("favorites").add(favorite)
                .addOnSuccessListener(documentReference -> Toast.makeText(PlayActivity.this, "This video is Favorited", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(PlayActivity.this, "Fail to favorite it: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void removeFromFavorites(String username, String videoName, String videoUrl) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("favorites")
                .whereEqualTo("username", username)
                .whereEqualTo("videoName", videoName)
                .whereEqualTo("videoUrl", videoUrl)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            db.collection("favorites").document(document.getId()).delete();
                        }
                    }
                });
    }

    private void checkFavoriteStatus(String username, String videoName, String videoUrl) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("favorites")
                .whereEqualTo("username", username)
                .whereEqualTo("videoName", videoName)
                .whereEqualTo("videoUrl", videoUrl)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        favoriteSwitch.setChecked(true);
                    } else {
                        favoriteSwitch.setChecked(false);
                    }
                });
    }


}
package com.example.groupassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.MediaController;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;


import java.util.HashMap;
import java.util.Map;


public class PlayActivity extends AppCompatActivity {
    private TextView video_name_textView;
    private VideoView current_VideoView;
    MediaController mediaController;
    private Switch favoriteSwitch;

    private String username; // 用户名

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
        username = getIntent().getStringExtra("username");

        // pzy 新增了play页面的switch按钮用于收藏和返回按钮
        favoriteSwitch = findViewById(R.id.switch_favorite);
        favoriteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addToFavorites(username, toPlayName);
                } else {
                    removeFromFavorites(username, toPlayName);
                }
            }
        });
        checkFavoriteStatus(username, toPlayName);

        Button backButton = findViewById(R.id.backButton11);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // 结束当前Activity，返回到前一个Activity
            }
        });



        current_VideoView.start();
    }

    private void addToFavorites(String username, String videoName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> favorite = new HashMap<>();
        favorite.put("username", username);
        favorite.put("videoName", videoName);

        db.collection("favorites").add(favorite)
                .addOnSuccessListener(documentReference -> Toast.makeText(PlayActivity.this, "This video is Favorited", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(PlayActivity.this, "Fail to favorite it: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void removeFromFavorites(String username, String videoName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("favorites")
                .whereEqualTo("username", username)
                .whereEqualTo("videoName", videoName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            db.collection("favorites").document(document.getId()).delete();
                        }
                    }
                });
    }

    private void checkFavoriteStatus(String username, String videoName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("favorites")
                .whereEqualTo("username", username)
                .whereEqualTo("videoName", videoName)
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
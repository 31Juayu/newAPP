package com.example.groupassignment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class VideoCollection extends AppCompatActivity {
    private ListView favoritesList;
    private Button backButton;
    private ArrayAdapter<String> adapter;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME_KEY", "defaultUsername");
        String password = sharedPreferences.getString("PASSWORD_KEY", "defaultPassword");


        backButton = (Button) findViewById(R.id.backButton12);



        // 假设从SharedPreferences或数据库加载收藏列表
        loadUserFavorites(username);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(VideoCollection.this, MenuPage.class);
            startActivity(intent);
            // 结束当前的Activity，确保用户按返回键时不会返回到PlayActivity
            finish();
        });
    }

    private void loadUserFavorites(String username) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("favorites")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> videoNames = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            videoNames.add(document.getString("videoName"));
                        }
                        // 更新UI或处理视频名称列表
                        updateVideoList(videoNames);
                    } else {
                        Log.e("Firestore", "Error getting documents: ", task.getException());
                    }
                });
    }
    private void updateVideoList(List<String> videoNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.custom_list_item, videoNames);
        ListView listView = findViewById(R.id.favoritesList);

        listView.setAdapter(adapter);

        // 设置点击事件监听器
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedVideoName = videoNames.get(position); // 获取选中的视频名称
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("favorites")
                    .whereEqualTo("videoName", selectedVideoName)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            String videoUrl = task.getResult().getDocuments().get(0).getString("videoUrl");

                            // 跳转到 PlayActivity 并传递视频 URL
                            Intent intent = new Intent(VideoCollection.this,  com.example.groupassignment.activity.PlayActivity.class);
                            intent.putExtra("toPlayName", selectedVideoName); // 传递视频名称
                            intent.putExtra("toPlayView", videoUrl); // 传递视频路径
                            startActivity(intent);
                        } else {
                            Toast.makeText(VideoCollection.this, "Video URL not found", Toast.LENGTH_SHORT).show();
                        }
                    });
        });



    }



}

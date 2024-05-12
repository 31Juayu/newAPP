package com.example.groupassignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class VideoCollection extends AppCompatActivity {
    private ListView favoritesList;
    private Button backButton;
    private ArrayAdapter<String> adapter;
    private String username;
    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);


        backButton = (Button) findViewById(R.id.backButton12);
        username = getIntent().getStringExtra("username");


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
                android.R.layout.simple_list_item_1, videoNames);
        ListView listView = findViewById(R.id.favoritesList);
        listView.setAdapter(adapter);

        // 设置点击事件监听器
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedVideoName = videoNames.get(position); // 获取选中的视频名称

            // 假设我们还需要从数据库或者某处获取选中视频的URI
            fetchVideoUriAndPlay(selectedVideoName);
        });
    }

    private void fetchVideoUriAndPlay(String videoName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Files") // 假设视频信息存储在名为"videos"的集合中
                .whereEqualTo("videoName", videoName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        String videoUri = task.getResult().getDocuments().get(0).getString("videoUri");

                        // 创建Intent并启动PlayActivity播放视频
                        Intent intent = new Intent(VideoCollection.this, PlayActivity.class);
                        intent.putExtra("toPlayName", videoName);
                        intent.putExtra("toPlayView", videoUri);
                        startActivity(intent);
                    } else {
                        Log.e("Firestore", "Video not found or error retrieving video URI", task.getException());
                    }
                });
    }

}

package com.example.groupassignment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.groupassignment.R;

public class PostActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private List<String> postList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        recyclerView = findViewById(R.id.post_lost);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostAdapter(postList);
        recyclerView.setAdapter(adapter);

        downloadPosts();
    }

    private void downloadPosts() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("posts/");
        storageRef.listAll().addOnSuccessListener(listResult -> {
            for (StorageReference item : listResult.getItems()) {
                item.getDownloadUrl().addOnSuccessListener(uri -> {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(uri.toString()).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            runOnUiThread(() -> Toast.makeText(PostActivity.this, "Error downloading file: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            String jsonData = response.body().string();
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<Post>>(){}.getType();
                            List<Post> posts = gson.fromJson(jsonData, type);
                            runOnUiThread(() -> {
                                for (Post post : posts) {
                                    String postText = post.getDisplayContent();
                                    postList.add(postText);
                                }
                                adapter.notifyDataSetChanged();
                            });
                        }
                    });
                }).addOnFailureListener(e -> Toast.makeText(PostActivity.this, "Error getting file URL: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        })
                .addOnFailureListener(e -> Toast.makeText(PostActivity.this, "Error listing files: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
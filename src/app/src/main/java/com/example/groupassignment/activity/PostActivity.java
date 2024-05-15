package com.example.groupassignment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupassignment.DAO.Profile;
import com.example.groupassignment.MenuPage;
import com.example.groupassignment.Post;
import com.example.groupassignment.PostAdapter;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private Button ButtonPost2PostItem;
    private Button ButtonPostRefresh;

    private FirebaseStorage storage;
    private StorageReference storageRef;

    private Button go_back_post;
    private Button go_back_content;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child("postNotices/");

        recyclerView = findViewById(R.id.post_lost);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostAdapter(postList);
        recyclerView.setAdapter(adapter);
        ButtonPost2PostItem = (Button) findViewById(R.id.post_notice);
        ButtonPostRefresh = (Button) findViewById(R.id.button_refresh_posts);

        String[] robotProvider = {"Welcome! ", "This is robot messaging system! ", "Have fun! "};
        String robotMessageRoot = "Robot ";
        for (int i = 0; i < 3; i ++){
            String autoPostItem = robotMessageRoot + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + robotProvider[i];
            postList.add(autoPostItem);
            adapter = new PostAdapter(postList);
            recyclerView.setAdapter(adapter);
        }

        downloadPosts();
        ButtonPost2PostItem.setOnClickListener(v -> {
            System.out.println("pressed");
            showPostDialog();
        });
        ButtonPostRefresh.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PostActivity.class);
            startActivity(intent);
        });

        go_back_post = (Button) findViewById(R.id.go_back_post);
        go_back_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        go_back_content = (Button) findViewById(R.id.go_back_content);
        go_back_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showPostDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.post_content);

        EditText EdixPost = dialog.findViewById(R.id.EditTextPost);
        Button ButtonUploadPost = dialog.findViewById(R.id.ButtonUploadPost);

        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME_KEY", "defaultUsername");
        String time = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

        ButtonUploadPost.setOnClickListener(v -> {
            String postContent = EdixPost.getText().toString();
            if (!postContent.isEmpty()){
                Post post = new Post(username, postContent, time, username + time);
                post.uploadPostJson(post);
            }
        });

        dialog.show();
    }

    private void downloadPosts() {
        storageRef.listAll().addOnSuccessListener(listResult -> {
                    for (StorageReference item : listResult.getItems()){
                        item.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
                            String json = new String(bytes);
                            Post post = new Gson().fromJson(json, Post.class);
                            String postItem = post.getDisplayContent();
                            postList.add(postItem);
                            adapter = new PostAdapter(postList);
                            recyclerView.setAdapter(adapter);
                        }).addOnFailureListener(e -> {
                            System.out.println("Fail to download posts.");
                        });
                    }
                }).addOnFailureListener(e -> {
            System.out.println("Fail to obtain posts from storage.");
        });
    }

}
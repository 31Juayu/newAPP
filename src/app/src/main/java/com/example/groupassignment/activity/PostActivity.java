package com.example.groupassignment.activity;
/**
 * @author Wenzhao Zheng u7705888
 * */
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupassignment.Post;
import com.example.groupassignment.PostAdapter;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


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

        //System automatically generated messages, not loaded to the storage
        String[] robotProvider = {"Welcome! ", "This is robot messaging system! ", "Have fun! "};
        String robotMessageRoot = "Robot ";
        for (int i = 0; i < 3; i ++){
            String autoPostItem = robotMessageRoot + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + robotProvider[i];
            postList.add(autoPostItem);
            adapter = new PostAdapter(postList);
            recyclerView.setAdapter(adapter);
        }

        //Get posts from storage
        downloadPosts();
        //Pop up a window for creating post
        ButtonPost2PostItem.setOnClickListener(v -> {
            showPostDialog();
        });
        ButtonPostRefresh.setOnClickListener(v -> {
            Intent intent = new Intent(PostActivity.this, PostActivity.class);
            startActivity(intent);
        });

        go_back_post = (Button) findViewById(R.id.go_back_post);
        go_back_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * Method for popping up a window for users to create content
     * The dialog will pop up a window depicted by the post_content.
     * Get username from sharedPreferences and concatenate the username with time formatted as date and time
     * Press the upload button to finish posting and upload the post to storage automatically
     */
    private void showPostDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.post_content);

        EditText EdixPost = dialog.findViewById(R.id.EditTextPost);
        Button ButtonUploadPost = (Button) dialog.findViewById(R.id.ButtonUploadPost);

        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME_KEY", "defaultUsername");
        String time = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        //Generate Post for uploading
        ButtonUploadPost.setOnClickListener(v -> {
            String postContent = EdixPost.getText().toString();
            if (!postContent.isEmpty()){
                Post post = new Post(username, postContent, time, username + time);
                post.uploadPostJson(post);
            }
        });

        dialog.show();
    }

    /**
     * Method for automatically fetching and displaying all posted notices
     * For each item in the postNotices/ directory, download the post and translate the json
     * format post to data of Post class. Get the display format of the post and put the string
     * to the post list. Then update the list to the recyclerView.
     * Error handler with different print text for it to be easier to tell which step of download fails
     */
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
            System.out.println("Fail to fetch posts from storage.");
        });
    }

}
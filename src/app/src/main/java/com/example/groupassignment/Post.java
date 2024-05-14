package com.example.groupassignment;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

public class Post {
    private String username;
    private String postContent;
    private String time;
    private String usernameTime;


    public Post(String username, String postContent, String time, String usernameTime) {
        this.username = username;
        this.postContent = postContent;
        this.time = time;
        this.usernameTime = usernameTime;
    }//使用usernameTime作为确定post的指标


    public Post(){}

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void uploadPostJson(Post post) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("postNotices/" + post.getUsernameTime() + ".json");

        String json = post.toJson();
        UploadTask uploadTask = storageRef.putBytes(json.getBytes());
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            System.out.println("Post JSON uploaded");
        }).addOnFailureListener(e -> {
            System.err.println("Failed to upload: " + e.getMessage());
        });
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsernameTime() {
        return usernameTime;
    }

    public void setUsernameTime(String usernameTime) {
        this.usernameTime = usernameTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }
    public String getDisplayContent() {
        return username + " (" + time + "): " + postContent;
    }
}

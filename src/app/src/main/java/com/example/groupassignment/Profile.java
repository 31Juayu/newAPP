package com.example.groupassignment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Profile {
    private String username;
    private String email;
    private String password;
    private String profileImageUrl;
    private List<String> courses;
    private List<String> friends;

    public Profile(String username, String email, String password, String profileImageUrl, List<String> courses, List<String> friends) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.courses = courses;
        this.friends = friends;
    }

    //following method is example data for test only
    public void setExampleData(){
        this.setEmail("example0@gmail.com");
        this.setProfileImageUrl("gs://comp6442project-8a60c.appspot.com/images/user0.jpg");
        List<String> c = Arrays.asList("03-打开CMD", "1715313106408", "gs://comp6442project-8a60c.appspot.com/Files/02-人机交互-图形化界面的小故事.mp4", "gs://comp6442project-8a60c.appspot.com/Files/01-Java学习介绍.mp4");
        this.setCourses(c);
        List<String> f = Arrays.asList("a@gmail.com", "b@outlook.com", "c@hotmail.com");
        this.setFriends(f);
    }

    //save the Profile class to json document
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void uploadProfileJson(Profile profile) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("Profiles/" + profile.getUsername() + ".json");

        String json = profile.toJson();
        UploadTask uploadTask = storageRef.putBytes(json.getBytes());
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            System.out.println("Profile JSON uploaded");
        }).addOnFailureListener(e -> {
            System.err.println("Failed to upload: " + e.getMessage());
        });
    }

    public Profile(){}

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}

package com.example.groupassignment.DAO;
/**
 * @author Wenzhao Zheng u7705888
 * */
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class Profile {
    private String username;
    private String email;
    private String password;
    private String profileImageUrl;
    private List<String> courses;
    private List<String> friends;

    /**
     * Constructor, the profile represent the user's personal information, non-private information
     * will be shown on the profile page.
     * @param username as registered in the signup page
     * @param email as registered in the signup page
     * @param password will be saved in profile but not be disclosed on the profile page
     * @param profileImageUrl the image reference of the head image
     * @param courses courses list, would be updated via favourite list
     * @param friends friends list, would show all friends that the user has interacted with
     */

    public Profile(String username, String email, String password, String profileImageUrl, List<String> courses, List<String> friends) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.courses = courses;
        this.friends = friends;
    }

    //Profile example data for the first version login
    public void setExampleData(){
        this.setEmail("example0@gmail.com");
        this.setProfileImageUrl("gs://comp6442project-8a60c.appspot.com/images/user0.jpg");
        List<String> c = Arrays.asList("03-打开CMD", "1715313106408", "02-人机交互-图形化界面的小故事", "01-Java学习介绍");
        this.setCourses(c);
        List<String> f = Arrays.asList("comp2100@anu.edu.au", "comp6442@anu.edu.au", "c@hotmail.com");
        this.setFriends(f);
    }

    //Save the Profile class to json document
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Method for uploading profile to the firebase storage
     * First, get the reference of Profiles/ directory which is the target directory
     * Second, call the toJson() function to generate the profile to json format
     * Third, create the update variable to upload the json to the storage
     * If the upload is successful, then print the success message; else, print the fail message
     * Reference: https://firebase.google.com/docs/storage/android/upload-files
      * @param profile
     */
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

    //Helper constructor for fetching firebase storage data
    public Profile(){}

    public List<String> getCourses() {
        return courses;
    }
    //Update courses list from favourite list
    public void updateCourses(List<String> courses){
        this.setCourses(courses);
        uploadProfileJson(this);
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

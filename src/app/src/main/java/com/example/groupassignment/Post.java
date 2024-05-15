package com.example.groupassignment;
/*Author: Wenzhao Zheng*/
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

public class Post {
    private String username;
    private String postContent;
    private String time;
    private String usernameTime;


    /**
     * Constructor of Post, usernameTime, as a concatenated string of username
     * and time, is used to locate specific Post in storage
     * @param username
     * @param postContent
     * @param time
     * @param usernameTime
     */
    public Post(String username, String postContent, String time, String usernameTime) {
        this.username = username;
        this.postContent = postContent;
        this.time = time;
        this.usernameTime = usernameTime;
    }

    /**
     * Helper constructor for fetching firebase storage data
     */
    public Post(){}

    /**
     * Method for saving the Post class to json document
     * @return
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Method for uploading json format of post data to firebase storage
     * @param post
     */
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

    /**
     * Getter method for time
     * @return
     */
    public String getTime() {
        return time;
    }

    /**
     * Setter method for time
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Getter method for usernameTime
     * @return
     */
    public String getUsernameTime() {
        return usernameTime;
    }

    /**
     * Setter method for username time
     * @param usernameTime
     */
    public void setUsernameTime(String usernameTime) {
        this.usernameTime = usernameTime;
    }

    /**
     * Getter method for username
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method for username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter method for post content
     * @return
     */
    public String getPostContent() {
        return postContent;
    }

    /**
     * Setter method for post content
     * @param postContent
     */
    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    /**
     * Method for transforming the post from Post to display format
     * @return
     */
    public String getDisplayContent() {
        return username + " (" + time + "): " + postContent;
    }
}

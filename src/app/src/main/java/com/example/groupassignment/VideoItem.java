package com.example.groupassignment;

public class VideoItem {
    String videoName;
    String videoUri;

    public VideoItem(String videoName, String videoUri) {
        this.videoName = videoName;
        this.videoUri = videoUri;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getVideoUri() {
        return videoUri;
    }
}

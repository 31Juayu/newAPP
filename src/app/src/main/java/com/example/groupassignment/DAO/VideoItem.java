package com.example.groupassignment.DAO;

/**
 * @author Tianyi Xu
 * Represents an item for a course which is a video.
 * This class stores information about a video file, including its name and URI location.
 */
public class VideoItem {
    // name of a video
    String videoName;
    // uri of a video
    String videoUri;

    /**
     * The constructor of the video item
     * This constructor initializes the videoName and videoUri fields of the object.
     * @param videoName
     * @param videoUri
     */
    public VideoItem(String videoName, String videoUri) {
        this.videoName = videoName;
        this.videoUri = videoUri;
    }

    /**
     * get the video name
     * @return
     */
    public String getVideoName() {
        return videoName;
    }

    /**
     * get the video uri
     * @return
     */
    public String getVideoUri() {
        return videoUri;
    }
}

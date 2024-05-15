package com.example.groupassignment.DAO;

/**
 * @author Tianyi Xu
 * Defines methods necessary for uploading video files and reading their associated links.
 * This interface is intended to be implemented by classes that handles the upload and download of the courses video files
 */
public interface VideoUploadInterface {
    public void uploadVideo();

    public void readVideoLinks();
}

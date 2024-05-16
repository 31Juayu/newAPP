package com.example.groupassignment.DAO;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * @author Zhengyu Peng u7727795
 * ProfileService class for uploading registered user information.
 *
 * This class provides a method to upload the profile JSON of a registered user to Firebase Storage.
 * It also includes an interface for handling upload success and failure events.
 */
public class ProfileService {
    /**
     * Uploads the profile JSON of a registered user to Firebase Storage.
     *
     * This method uploads the profile JSON to a storage reference based on the username.
     * It invokes the listener's onUploadSuccess() method if the upload is successful,
     * or onUploadFailure(Exception e) method if the upload fails.
     *
     * @param profile  The Profile object containing the user's information.
     * @param listener The listener for handling upload success and failure events.
     */
    public static void uploadProfileJson(Profile profile, OnProfileUploadListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("Profiles/" + profile.getUsername() + ".json");
        String json = profile.toJson();
        UploadTask uploadTask = storageRef.putBytes(json.getBytes());
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            System.out.println("Profile JSON uploaded");
            listener.onUploadSuccess();
        }).addOnFailureListener(e -> {
            System.err.println("Failed to upload: " + e.getMessage());
            listener.onUploadFailure(e);
        });
    }
    // Interface for handling profile upload success and failure events.
    public interface OnProfileUploadListener {
        void onUploadSuccess();
        void onUploadFailure(Exception e);
    }
}


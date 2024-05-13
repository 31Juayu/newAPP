package com.example.groupassignment.DAO;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfileService {


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
    public interface OnProfileUploadListener {
        void onUploadSuccess();
        void onUploadFailure(Exception e);
    }
}


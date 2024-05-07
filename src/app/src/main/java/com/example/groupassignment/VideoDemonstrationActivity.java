package com.example.groupassignment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VideoDemonstrationActivity extends AppCompatActivity {
    private Button uploadv;
    private Uri videouri;
    private ProgressBar progressBar;
    private TextView progressText;

    private RecyclerView videoList;

    private List<Uri> videoUris;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_demonstration);

        // Initialize layout components
        uploadv = findViewById(R.id.upload_video);
        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);
        videoList = findViewById(R.id.video_list);
        videoUris = new ArrayList<>();
        uploadv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseVideo();
            }
        });
    }

    // Choose a video from phone storage
    private void chooseVideo() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(intent, 5);
    }

    // Receive the result of the selected video
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videouri = data.getData();
            progressBar.setVisibility(View.VISIBLE);
            progressText.setVisibility(View.VISIBLE);
            uploadVideo();
        } else {
            Toast.makeText(this, "No video selected or selection cancelled.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileType(Uri videouri) {
        if (videouri == null) return "";
        ContentResolver r = getContentResolver();
        // Get the file type, in this case it's mp4
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(r.getType(videouri));
    }

    private void uploadVideo() {
        if (videouri != null) {
            // Save the selected video in Firebase storage (后续修改存储位置，名字)
            final StorageReference reference = FirebaseStorage.getInstance()
                    .getReference("Files/" + System.currentTimeMillis() + "." + getFileType(videouri));

            reference.putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadUri) {
                            // Get the link of the video
                            String uriString = downloadUri.toString();
                            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Video");
                            HashMap<String, String> map = new HashMap<>();
                            map.put("videolink", uriString);
                            reference1.child("" + System.currentTimeMillis()).setValue(map);

                            // Video uploaded successfully
                            progressBar.setVisibility(View.GONE);
                            progressText.setVisibility(View.GONE);
                            Toast.makeText(VideoDemonstrationActivity.this, "Video Uploaded!!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            progressText.setVisibility(View.GONE);
                            Toast.makeText(VideoDemonstrationActivity.this, "Failed to get download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    progressBar.setVisibility(View.GONE);
                    progressText.setVisibility(View.GONE);
                    Toast.makeText(VideoDemonstrationActivity.this, "Failed to upload video: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    // Show the progress bar
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int) progress);
                    progressText.setText("Uploaded " + (int) progress + "%");
                }
            });
        } else {
            Toast.makeText(this, "No video selected for upload.", Toast.LENGTH_SHORT).show();
        }
    }

}
package com.example.groupassignment;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.groupassignment.DAO.VideoItem;
import com.example.groupassignment.DAO.VideoUploadInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class VideoDemonstrationActivity extends AppCompatActivity implements VideoUploadInterface {
    private Button uploadv;
    private Uri videouri;
    private ProgressBar progressBar;
    private TextView progressText;

    private RecyclerView videoList;

    private List<Uri> videoUris;

    private Button go_back_demo;


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
        videoList.setLayoutManager(new LinearLayoutManager(this));
        readVideoLinks();
        go_back_demo = (Button) findViewById(R.id.go_back_demo);

        uploadv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseVideo();
            }
        });

        go_back_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    private String getUploadFileName(Uri videouri){
        String result = null;
        if (Objects.equals(videouri.getScheme(), "content")){
            try (Cursor cursor = getContentResolver().query(videouri, null, null, null, null)){
                if(cursor != null && cursor.moveToFirst()){
                    result = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null){
            result = videouri.getPath();
            int cut = result.lastIndexOf("/");
            if (cut!= -1){
                result = result.substring(cut + 1);
            }
        }
        if (result != null && result.contains(".")) {
            result = result.substring(0, result.lastIndexOf("."));
        }
        return result;
    }

    public void uploadVideo() {
        if (videouri != null) {
            // Save the selected video in Firebase storage
            String fileName = getUploadFileName(videouri);
            final StorageReference reference = FirebaseStorage.getInstance()
                    .getReference("Files/" + fileName + "." + getFileType(videouri));

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
    public void readVideoLinks() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef.child("Files").listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                List<VideoItem> videoItemList = new ArrayList<>();
                int itemCount = listResult.getItems().size();
                AtomicInteger count = new AtomicInteger(0);
                for (StorageReference item : listResult.getItems()) {
                    String fileName = item.getName();
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadUri = uri.toString();
                            videoItemList.add(new VideoItem(fileName,downloadUri));
                            // Check if this is the last item
                            updateVideoList(videoItemList);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(VideoDemonstrationActivity.this, "Failed to get download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VideoDemonstrationActivity.this, "Failed to list files: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateVideoList(List<VideoItem> videoItemList) {
        VideoAdapter adapter = new VideoAdapter(videoItemList,this);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        videoList.setLayoutManager(layoutManager);
        videoList.setAdapter(adapter);
    }
}
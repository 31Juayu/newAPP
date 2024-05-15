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

/**
 * @author Tianyi Xu u7780366
 * @author Ruize Luo u7776709 (deal with the getUploadFileName, updateVideoList(testing and solve with bug), uploadVideo(modified))
 * because we did this offline together and most of the function is push by Tianyi Xu
 * Activity class for demonstrating videos, managing the upload and display of video files.
 * Implements VideoUploadInterface for managing uploads
 */
public class VideoDemonstrationActivity extends AppCompatActivity implements VideoUploadInterface {
    // The declaration of the UI components
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
        // Finding all the views in the layout
        uploadv = findViewById(R.id.upload_video);
        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);
        videoList = findViewById(R.id.video_list);
        videoUris = new ArrayList<>();
        // setting the recyclerView layout manager
        videoList.setLayoutManager(new LinearLayoutManager(this));
        // reading existing videos from storage
        readVideoLinks();
        go_back_demo = (Button) findViewById(R.id.go_back_demo);
        // setting up the upload function for the upload button
        uploadv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseVideo();
            }
        });
        // setting up for the return button
        go_back_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * Start the file chooser page in android for the user to choose the files
     */
    private void chooseVideo() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(intent, 5);
    }

    /**
     * Handles the results from the file chooser
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     *
     */
    // the reference to this method is https://verytoolz.com/blog/f632174b21/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if the results are ok, store the uri of the pdf and show the progressBar
        if (requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videouri = data.getData();
            progressBar.setVisibility(View.VISIBLE);
            progressText.setVisibility(View.VISIBLE);
            // Start the upload process
            uploadVideo();
        } else {
            Toast.makeText(this, "No video selected or selection cancelled.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * return the file type of the video
     * @param videouri the input uri
     * @return the file type of the video files
     */
    // the reference to this method is https://verytoolz.com/blog/f632174b21/
    private String getFileType(Uri videouri) {
        if (videouri == null) return "";
        ContentResolver r = getContentResolver();
        // Get the file type, in this case it's mp4
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(r.getType(videouri));
    }

    /**
     * written by Ruize Luo u7776709
     * Get the file name from the Uri
     * @param videouri The input uri
     * @return the name of the file with the input uri
     */
    private String getUploadFileName(Uri videouri){
        String result = null;
        if (Objects.equals(videouri.getScheme(), "content")){
            try (Cursor cursor = getContentResolver().query(videouri, null, null, null, null)){
                if(cursor != null && cursor.moveToFirst()){
                    result = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        // Fallback for file path extraction
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

    /**
     * modified by Ruize Luo
     * Upload the selected video file to the firebase storage
     */
    // the reference to this method is https://verytoolz.com/blog/f632174b21/
    public void uploadVideo() {
        if (videouri != null) {
            // Save the selected video in Firebase storage
            String fileName = getUploadFileName(videouri);
            final StorageReference reference = FirebaseStorage.getInstance()
                    .getReference("Files/" + fileName + "." + getFileType(videouri));

            reference.putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // If the upload successful, get the download url
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadUri) {
                            // store the download url in firebase
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
                        // handle the failure
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
                // update the progress bar and the text to show the upload process
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

    /**
     * Reads videos from the firebase database storage
     */
    public void readVideoLinks() {
        // get All the files in the Files directory
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef.child("Files").listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                // use a list to store all the video items to show in the recyclerView
                List<VideoItem> videoItemList = new ArrayList<>();
                int itemCount = listResult.getItems().size();
                AtomicInteger count = new AtomicInteger(0);
                for (StorageReference item : listResult.getItems()) {
                    // get the name of the videos in the storage
                    String fileName = item.getName();
                    // if get success, get the uri and make them into video items to store
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadUri = uri.toString();
                            videoItemList.add(new VideoItem(fileName,downloadUri));
                            // Check if this is the last item
                            updateVideoList(videoItemList);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        // handle failure
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(VideoDemonstrationActivity.this, "Failed to get download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            // handle failure
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VideoDemonstrationActivity.this, "Failed to list files: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * modified by Ruize Luo
     * The video items is used to update the recyclerView
     * @param videoItemList The list to store all the video items
     */
    private void updateVideoList(List<VideoItem> videoItemList) {
        // create an adapter using the VideoAdapter
        VideoAdapter adapter = new VideoAdapter(videoItemList,this);
        // set the layout of the adapter
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        videoList.setLayoutManager(layoutManager);
        // set the list to the adapter
        videoList.setAdapter(adapter);
    }
}

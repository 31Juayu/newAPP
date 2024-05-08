package com.example.groupassignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AssignmentDemonstrationActivity extends AppCompatActivity {
    private Button uploadPDF;
    private Uri PDFUri;
    private ProgressBar progressBarPDF;
    private TextView progressTextViewPDF;
    private RecyclerView PDFList;
    private List<Uri> PDFUris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_demonstration);
        uploadPDF = (Button) findViewById(R.id.upload_Assignment);
        progressBarPDF = (ProgressBar) findViewById(R.id.progress_bar_PDF);
        progressTextViewPDF = (TextView) findViewById(R.id.progress_text_PDF);
        PDFList = (RecyclerView) findViewById(R.id.PDF_list);
        PDFUris = new ArrayList<>();
        PDFList.setLayoutManager(new LinearLayoutManager(this));
        //TODO: 后续增加下载
        uploadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAssignment();
            }
        });
    }
    private void chooseAssignment(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent,5);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            PDFUri = data.getData();
            progressBarPDF.setVisibility(View.VISIBLE);
            progressTextViewPDF.setVisibility(View.VISIBLE);
            uploadPDFAssign();
        } else {
            Toast.makeText(this, "No video selected or selection cancelled.", Toast.LENGTH_SHORT).show();
        }
    }
    private String getUploadFileType(Uri AssignUri){
        if (AssignUri != null){
            ContentResolver r = getContentResolver();
            // Get the file type, in this case it's PDF
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType(r.getType(AssignUri));
        } else{
            return "";
        }
    }
    private String getUploadFileName(Uri pdfUri){
        String result = null;
        if (Objects.equals(pdfUri.getScheme(), "content")){
            try (Cursor cursor = getContentResolver().query(pdfUri, null, null, null, null)){
                if(cursor != null && cursor.moveToFirst()){
                    result = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null){
            result = pdfUri.getPath();
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
    private void uploadPDFAssign(){
        if (PDFUri != null){
            String fileName = getUploadFileName(PDFUri);
            final StorageReference reference = FirebaseStorage.getInstance()
                    .getReference("Files/Assignments" + fileName + "." + getUploadFileType(PDFUri));
            reference.putFile(PDFUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uriString = uri.toString();
                            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Assignments");
                            HashMap<String, String> map = new HashMap<>();
                            map.put("PDFLink", uriString);
                            reference1.child(fileName).setValue(map);

                            // Video uploaded successfully
                            progressBarPDF.setVisibility(View.GONE);
                            progressTextViewPDF.setVisibility(View.GONE);
                            Toast.makeText(AssignmentDemonstrationActivity.this, "Assignment Uploaded!!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBarPDF.setVisibility(View.GONE);
                            progressTextViewPDF.setVisibility(View.GONE);
                            Toast.makeText(AssignmentDemonstrationActivity.this, "Failed to get download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBarPDF.setVisibility(View.GONE);
                    progressTextViewPDF.setVisibility(View.GONE);
                    Toast.makeText(AssignmentDemonstrationActivity.this, "Failed to upload Assignment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    // Show the progress bar
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressBarPDF.setProgress((int) progress);
                    progressTextViewPDF.setText("Uploaded " + (int) progress + "%");
                }
            });
        } else {
            Toast.makeText(this, "No Assignment selected for upload.", Toast.LENGTH_SHORT).show();
        }
    }
}
package com.example.groupassignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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

import com.example.groupassignment.DAO.AssignmentItem;
import com.example.groupassignment.DAO.AssignmentUploadInterface;
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

/**
 * @author Tianyi Xu u7780366
 * This file reference from online resources, I used some basic methods provide by it
 * The references will be shown on the methods
 * Activity class for demonstrating assignments, managing the upload and display of Assignment PDFs.
 * Implements AssignmentUploadInterface for managing uploads.
 */
public class AssignmentDemonstrationActivity extends AppCompatActivity implements AssignmentUploadInterface {
    // The declaration of the UI components
    private Button uploadPDF;
    private Uri PDFUri;
    private ProgressBar progressBarPDF;
    private TextView progressTextViewPDF;
    private RecyclerView PDFList;
    private List<Uri> PDFUris;

    private Button go_back_assign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_demonstration);
        // Finding all the views in the layout
        uploadPDF = (Button) findViewById(R.id.upload_Assignment);
        progressBarPDF = (ProgressBar) findViewById(R.id.progress_bar_PDF);
        progressTextViewPDF = (TextView) findViewById(R.id.progress_text_PDF);
        PDFList = (RecyclerView) findViewById(R.id.PDF_list);
        PDFUris = new ArrayList<>();
        // setting the recyclerView layout manager
        PDFList.setLayoutManager(new LinearLayoutManager(this));
        // reading existing PDFs from storage
        readPDFLinks();
        // setting up the upload function for the upload button
        uploadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAssignment();
            }
        });
        // setting up for the return button
        go_back_assign = (Button) findViewById(R.id.go_back_assign);
        go_back_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * Start the file chooser page in android for the user to choose the files
     */
    private void chooseAssignment(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent,5);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if the results are ok, store the uri of the pdf and show the progressBar
        if (requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            PDFUri = data.getData();
            progressBarPDF.setVisibility(View.VISIBLE);
            progressTextViewPDF.setVisibility(View.VISIBLE);
            // Start the upload process
            uploadPDFAssign();
        } else {
            Toast.makeText(this, "No Assignment selected or selection cancelled.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * return the file type of the PDF
     * @param AssignUri the input uri
     * @return the file type of the PDF files
     */
    // the reference to this method is https://verytoolz.com/blog/f632174b21/
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

    /**
     * Get the file name from the Uri
     * @param pdfUri The input uri
     * @return the name of the file with the input uri
     */
    private String getUploadFileName(Uri pdfUri){
        String result = null;
        if (Objects.equals(pdfUri.getScheme(), "content")){
            try (Cursor cursor = getContentResolver().query(pdfUri, null, null, null, null)){
                if(cursor != null && cursor.moveToFirst()){
                    result = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        // Fallback for file path extraction
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

    /**
     * Upload the selected pdf file to the firebase storage
     */
    // the reference to this method is https://verytoolz.com/blog/f632174b21/
    public void uploadPDFAssign(){
        if (PDFUri != null){
            String fileName = getUploadFileName(PDFUri);
            final StorageReference reference = FirebaseStorage.getInstance()
                    .getReference("Assignments/" + fileName + "." + getUploadFileType(PDFUri));
            reference.putFile(PDFUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // If the upload successful, get the download url
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // store the download url in firebase
                            String uriString = uri.toString();
                            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Assignments");
                            HashMap<String, String> map = new HashMap<>();
                            map.put("PDFLink", uriString);
                            reference1.child(fileName).setValue(map);

                            // Assignment uploaded successfully
                            progressBarPDF.setVisibility(View.GONE);
                            progressTextViewPDF.setVisibility(View.GONE);
                            Toast.makeText(AssignmentDemonstrationActivity.this, "Assignment Uploaded!!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        // handle the failure
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
                // update the progress bar and the text to show the upload process
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

    /**
     * Reads PDF files from the firebase database storage
     */
    public void readPDFLinks() {
        // get All the files in the Assignments directory
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef.child("Assignments").listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                // use a list to store all the Assignment items to show in the recyclerView
                List<AssignmentItem> assignmentItemList = new ArrayList<>();
                for (StorageReference item : listResult.getItems()) {
                    // get the name of the files in the storage
                    String fileName = item.getName();
                    // if get success, get the uri and make them into assignment items to store
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadUriPDF = uri.toString();
                            assignmentItemList.add(new AssignmentItem(fileName,downloadUriPDF));
                            updatePDFList(assignmentItemList);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        // handle failure
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AssignmentDemonstrationActivity.this, "Failed to get download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            // handle failure
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AssignmentDemonstrationActivity.this, "Failed to list files: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * The assignment items is used to update the recyclerView
     * @param assignmentItemList The list to store all the assignment items
     */
    private void updatePDFList(List<AssignmentItem> assignmentItemList){
        // create an adapter using the AssignmentAdapter
        AssignmentAdapter adapter = new AssignmentAdapter(assignmentItemList,this);
        // set the layout of the adapter
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        PDFList.setLayoutManager(layoutManager);
        // set the list to the adapter
        PDFList.setAdapter(adapter);
    }
}
package com.example.groupassignment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.groupassignment.activity.ProfileActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
// author : Zhengyu Peng
public class VideoCollection extends AppCompatActivity {
    private ListView favoritesList;
    private Button backButton;
    private Button ButtonUpdate2Profile;
    private ArrayAdapter<String> adapter;
    private Intent intent;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME_KEY", "defaultUsername");
        String password = sharedPreferences.getString("PASSWORD_KEY", "defaultPassword");
        intent = new Intent(this, ProfileActivity.class);

        backButton = (Button) findViewById(R.id.backButton12);
        ButtonUpdate2Profile = (Button) findViewById(R.id.buttonUploadToProfile);

        loadUserFavorites(username);

        /**
         * @author Wenzhao Zheng u7705888
         * Get the favourite list from the current class and deliver the intent to the profile activity
         * Pressing the button, the favourite list would be updated to the profile courses list
          */
        ButtonUpdate2Profile.setOnClickListener(v -> {
            startActivity(intent);
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(VideoCollection.this, MenuPage.class);
            startActivity(intent);
            // End the Activity, making sure the user doesn't return to the PlayActivity when they press the Back key
            finish();
        });
    }

    /**
     * author: Zhengyu Peng
     * Loads the list of favorite videos for a user from the Firestore database.
     *
     * This method retrieves documents from the "favorites" collection in Firestore where the "username" field
     * matches the provided username. It extracts the video names from the retrieved documents and updates
     * the UI or processes the list of video names accordingly. If the task fails, an error message is logged.
     *
     * @param username The username of the user whose favorite videos are to be loaded.
     */
    private void loadUserFavorites(String username) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("favorites")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> videoNames = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            videoNames.add(document.getString("videoName"));
                        }
                        // Update UI or process the list of video names
                        updateVideoList(videoNames);
                        //Authored by Wenzhao Zheng: Update video lists to intent, prepare for updating to profile
                        intent.putStringArrayListExtra("courses_list", new ArrayList<>(videoNames));

                    } else {
                        Log.e("Firestore", "Error getting documents: ", task.getException());
                    }
                });
    }
    /**
     * author： Zhengyu Peng
     * Updates the video list UI with the provided video names and sets up item click handling.
     *
     * This method creates an ArrayAdapter using the provided list of video names and sets it to the ListView.
     * When an item in the ListView is clicked, it retrieves the corresponding video URL from the Firestore database
     * and starts the PlayActivity, passing the video name and URL as extras.
     *
     * @param videoNames The list of video names to display in the ListView.
     */
    private void updateVideoList(List<String> videoNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.custom_list_item, videoNames);
        ListView listView = findViewById(R.id.favoritesList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedVideoName = videoNames.get(position); // 获取选中的视频名称
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("favorites")
                    .whereEqualTo("videoName", selectedVideoName)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            String videoUrl = task.getResult().getDocuments().get(0).getString("videoUrl");

                            // Go to PlayActivity and pass the video URL
                            Intent intent = new Intent(VideoCollection.this,  com.example.groupassignment.activity.PlayActivity.class);
                            intent.putExtra("toPlayName", selectedVideoName);
                            intent.putExtra("toPlayView", videoUrl);
                            startActivity(intent);
                        } else {
                            Toast.makeText(VideoCollection.this, "Video URL not found", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }



}

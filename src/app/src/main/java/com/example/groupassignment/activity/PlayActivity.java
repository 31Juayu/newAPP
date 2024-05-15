package com.example.groupassignment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.MediaController;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.groupassignment.DAO.Profile;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;


import java.util.HashMap;
import java.util.Map;


import com.example.groupassignment.R;

public class PlayActivity extends AppCompatActivity {
    private TextView video_name_textView;
    private VideoView current_VideoView;
    MediaController mediaController;
    private Switch favoriteSwitch;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);



        video_name_textView = (TextView) findViewById(R.id.current_video_name);
        current_VideoView = (VideoView) findViewById(R.id.current_video_play);
        mediaController = new MediaController(this);
        String toPlayName = getIntent().getStringExtra("toPlayName");
        String toPlayView = getIntent().getStringExtra("toPlayView");
        video_name_textView.setText(toPlayName);
        current_VideoView.setVideoPath(toPlayView);
        current_VideoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(current_VideoView);
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME_KEY", "defaultUsername");
        String password = sharedPreferences.getString("PASSWORD_KEY", "defaultPassword");
        //SharedPreferences.Editor editor = sharedPreferences.edit();


        /**
         * author: Zhengyu Peng
         * Sets up the favorite switch and its change listener to add or remove a video from favorites.
         *
         * This method initializes the favorite switch and sets an OnCheckedChangeListener on it.
         * When the switch is checked or unchecked, it adds or removes the video from the favorites list,
         * respectively. It also checks the current favorite status of the video for the given user.
         *
         * @param username  The username of the current user.
         * @param toPlayName The name of the video to be added or removed from favorites.
         * @param toPlayView The URL of the video to be added or removed from favorites.
         */
        favoriteSwitch = findViewById(R.id.switch_favorite);
        favoriteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addToFavorites(username, toPlayName, toPlayView);
                    //editor.putString("COURSE_KEY", toPlayName);
                    //editor.apply();
                } else {
                    removeFromFavorites(username, toPlayName, toPlayView);
                    //editor.remove("COURSE_KEY");
                    //editor.apply();
                }
            }
        });
        checkFavoriteStatus(username, toPlayName, toPlayView);

        Button backButton = findViewById(R.id.backButton11);
        /**
         * Sets up the back button to finish the current activity and return to the previous one.
         *
         * This method sets an OnClickListener on the back button. When the button is clicked,
         * the current activity is finished, effectively returning the user to the previous activity
         * in the activity stack.
         */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        current_VideoView.start();
    }

    /**
     * author: Zhengyu Peng
     * Adds a video to the user's favorites in the Firestore database.
     *
     * This method creates a map with the user's username, video name, and video URL.
     * It then adds this map to the "favorites" collection in Firestore.
     * If the operation is successful, a toast message is displayed indicating the video has been favorited.
     * If the operation fails, a toast message is displayed indicating the failure and the error message.
     *
     * @param username  The username of the user.
     * @param videoName The name of the video to be added to favorites.
     * @param videoUrl  The URL of the video to be added to favorites.
     */
    private void addToFavorites(String username, String videoName, String videoUrl) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> favorite = new HashMap<>();
        favorite.put("username", username);
        favorite.put("videoName", videoName);
        favorite.put("videoUrl", videoUrl);

        db.collection("favorites").add(favorite)
                .addOnSuccessListener(documentReference -> Toast.makeText(PlayActivity.this, "This video is Favorited", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(PlayActivity.this, "Fail to favorite it: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    /**
     * Author： Zhengyu Peng
     * Removes a video from the user's favorites in the Firestore database.
     *
     * This method queries the "favorites" collection in Firestore for documents matching the provided
     * username, video name, and video URL. If matching documents are found, they are deleted from the collection.
     *
     * @param username  The username of the user.
     * @param videoName The name of the video to be removed from favorites.
     * @param videoUrl  The URL of the video to be removed from favorites.
     */
    private void removeFromFavorites(String username, String videoName, String videoUrl) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("favorites")
                .whereEqualTo("username", username)
                .whereEqualTo("videoName", videoName)
                .whereEqualTo("videoUrl", videoUrl)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            db.collection("favorites").document(document.getId()).delete();
                        }
                    }
                });
    }

    /**
     * Author: Zhengyu Peng
     * Checks if a video is in the user's favorites in the Firestore database and updates the favorite switch accordingly.
     *
     * This method queries the "favorites" collection in Firestore for documents matching the provided
     * username, video name, and video URL. If a matching document is found, the favorite switch is set to checked.
     * If no matching document is found, the favorite switch is set to unchecked.
     *
     * @param username  The username of the user.
     * @param videoName The name of the video to check.
     * @param videoUrl  The URL of the video to check.
     */
    private void checkFavoriteStatus(String username, String videoName, String videoUrl) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("favorites")
                .whereEqualTo("username", username)
                .whereEqualTo("videoName", videoName)
                .whereEqualTo("videoUrl", videoUrl)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        favoriteSwitch.setChecked(true);
                    } else {
                        favoriteSwitch.setChecked(false);
                    }
                });
    }


}
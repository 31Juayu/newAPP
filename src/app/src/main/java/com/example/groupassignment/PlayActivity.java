package com.example.groupassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class PlayActivity extends AppCompatActivity {
    private TextView video_name_textView;
    private VideoView current_VideoView;
    MediaController mediaController;
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
        current_VideoView.start();
    }
}
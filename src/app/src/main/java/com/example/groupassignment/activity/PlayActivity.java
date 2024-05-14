package com.example.groupassignment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.groupassignment.R;

public class PlayActivity extends AppCompatActivity {
    private TextView video_name_textView;
    private VideoView current_VideoView;
    MediaController mediaController;

    private Button go_back_play;
    @SuppressLint("MissingInflatedId")
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

        go_back_play = (Button) findViewById(R.id.go_back_play);

        go_back_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
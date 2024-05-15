package com.example.groupassignment.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groupassignment.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.net.Uri;
import android.widget.Toast;
import android.widget.VideoView;

import android.widget.MediaController;

/**
 * @author jiayu jian u7731262
 */
public class playVedioOfcourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_vedio_ofcourse);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        Intent intent = getIntent();
        String videoName = intent.getStringExtra("name");
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference videoRef = storageReference.child("Files/" + videoName + ".mp4");

        // 获取下载URL
        videoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // 当成功获取URL后，使用URL播放视频
                playVideo(uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(playVedioOfcourse.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void playVideo(String videoUrl) {
        VideoView videoView = (VideoView) findViewById(R.id.video_view);
        videoView.setVideoURI(Uri.parse(videoUrl));
        videoView.setMediaController(new MediaController(this)); // 添加媒体控制器
        videoView.requestFocus();
        videoView.start();
    }

}
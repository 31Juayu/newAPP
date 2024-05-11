package com.example.groupassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ChatIndividual extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_individual);
        TextView chatHeader = (TextView) findViewById(R.id.header_chat);
        String friendName = getIntent().getStringExtra("friendName");
        if (friendName != null){
            chatHeader.setText(friendName);
        }
    }
}
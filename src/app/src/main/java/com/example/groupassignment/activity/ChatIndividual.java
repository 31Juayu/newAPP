package com.example.groupassignment.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupassignment.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChatIndividual extends AppCompatActivity {
    private LinearLayout show_message_layout;
    private ScrollView scrollView;
    private Button send_button;
    private EditText message_area;
    DatabaseReference reference1;
    DatabaseReference reference2;
    private Button go_back_chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_individual);
        TextView chatHeader = (TextView) findViewById(R.id.header_chat);
        String friendName = getIntent().getStringExtra("friendName");
        String currentUserName = getIntent().getStringExtra("currentUserName");
        if (friendName != null){
            chatHeader.setText(currentUserName + "chat with" + friendName);
        }
        show_message_layout = (LinearLayout) findViewById(R.id.show_message_layout);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        send_button = (Button) findViewById(R.id.send_button);
        message_area = (EditText) findViewById(R.id.message_area);
        // use the singleton pattern
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String toUseUserName = currentUserName.replace(".","-");
        String toUseFriendName = friendName.replace(".","-");
        reference1 = FirebaseDatabase.getInstance().getReference("messages/" + toUseUserName + "_" + toUseFriendName);
        reference2 = FirebaseDatabase.getInstance().getReference("messages/" + toUseFriendName + "_" + toUseUserName);
        go_back_chat = (Button) findViewById(R.id.go_back_chat);


        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = message_area.getText().toString();
                if (!message.equals("")){
                    Toast.makeText(ChatIndividual.this, "Sending message: " + message, Toast.LENGTH_SHORT).show();
                    Map<String,String> map = new HashMap<>();
                    map.put("current_message",message);
                    map.put("current_user",currentUserName);
                    reference1.push().setValue(map)
                            .addOnSuccessListener(aVoid -> Toast.makeText(ChatIndividual.this, "Message sent to reference1 successfully!", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(ChatIndividual.this, "Failed to send message to reference1: " + e.getMessage(), Toast.LENGTH_LONG).show());
                    reference2.push().setValue(map)
                            .addOnSuccessListener(aVoid -> Toast.makeText(ChatIndividual.this, "Message sent to reference2 successfully!", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(ChatIndividual.this, "Failed to send message to reference2: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }
        });
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Map current_map = snapshot.getValue(Map.class);
                assert current_map != null;
                String current_message = Objects.requireNonNull(current_map.get("current_message")).toString();
                String current_user = Objects.requireNonNull(current_map.get("current_user")).toString();
                if (current_user.equals(currentUserName)){
                    messageViewer("You:\n"+current_message,1);
                } else {
                    messageViewer(friendName + ":\n" + current_message,0);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        go_back_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void messageViewer(String message,int userType){
        TextView textView = new TextView(ChatIndividual.this);
        textView.setText(message);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0,10);
        textView.setLayoutParams(layoutParams);
        if (userType == 1){
            textView.setBackgroundResource(R.drawable.message1);
        } else if (userType == 0) {
            textView.setBackgroundResource(R.drawable.message0);
        }
        show_message_layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}
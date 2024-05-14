package com.example.groupassignment.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groupassignment.R;

import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity {

    private ListView friendList;
    private ArrayAdapter<String> adapter;

    private Button go_back_friend;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friends);
        ArrayList<String> friends = getIntent().getStringArrayListExtra("friendsList");
        String currentUserName = getIntent().getStringExtra("currentUserName");
        friendList = (ListView) findViewById(R.id.FriendsList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        friendList.setAdapter(adapter);

        go_back_friend = (Button) findViewById(R.id.go_back_friend);

        adapter.clear();
        if (friends!=null){
            adapter.addAll(friends);
            adapter.notifyDataSetChanged();
        }
        friendList.setOnItemClickListener((parent,view,position,id) -> {
            String friendName = adapter.getItem(position);
            Intent intent = new Intent(getApplicationContext(), ChatIndividual.class);
            intent.putExtra("friendName", friendName);
            intent.putExtra("currentUserName",currentUserName);
            startActivity(intent);
        });

        go_back_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
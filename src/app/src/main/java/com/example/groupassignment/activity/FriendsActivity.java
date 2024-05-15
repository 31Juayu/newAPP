package com.example.groupassignment.activity;
//Author: Wenzhao Zheng
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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

        adapter.clear();
        if (friends!=null){
            adapter.addAll(friends);
            adapter.notifyDataSetChanged();
        }
        //Author: Tianyi Xu
        friendList.setOnItemClickListener((parent,view,position,id) -> {
            String friendName = adapter.getItem(position);
            Intent intent = new Intent(getApplicationContext(), ChatIndividual.class);
            intent.putExtra("friendName", friendName);
            intent.putExtra("currentUserName",currentUserName);
            startActivity(intent);
        });
    }
}
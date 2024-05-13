package com.example.groupassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.groupassignment.activity.ProfileActivity;
import com.example.groupassignment.activity.locationActivity;
import com.example.groupassignment.activity.searchActivity;

public class MenuPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        Button searchButton = (Button) findViewById(R.id.button10);

        Button newButton = (Button) findViewById(R.id.button1);
        Button videoButton = (Button) findViewById(R.id.button2);
        Button assignmentButton = (Button) findViewById(R.id.button3);
        Button profileButton = (Button) findViewById(R.id.button4);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), searchActivity.class);
                startActivity(intent);
            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), locationActivity.class);
                startActivity(intent);
            }
        });
        videoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),VideoDemonstrationActivity.class);
                startActivity(intent);
            }
        });
        assignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AssignmentDemonstrationActivity.class);
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
package com.example.groupassignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.groupassignment.R;
import com.example.groupassignment.activity.dealSearchActivity;

public class searchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
/*        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        Button infoButton = (Button) findViewById(R.id.seeInfo);
        Button countryButton = (Button) findViewById(R.id.Country);
        Button startSearch = (Button) findViewById(R.id.startSearch);

        EditText text = (EditText) findViewById(R.id.informationEditText);

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), showInfoActivity.class);
                startActivity(intent);

            }
        });

        countryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), showCountryActivity.class);
                startActivity(intent);

            }
        });

        startSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = text.getText().toString();
                if (!inputText.isEmpty() && !inputText.equals("*")) {
                    Intent intent = new Intent(getApplicationContext(), dealSearchActivity.class);
                    intent.putExtra("userInput",inputText);
                    startActivity(intent);

                }else{
                    Toast.makeText(searchActivity.this, "input may invalid", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
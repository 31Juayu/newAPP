package com.example.groupassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginButtonMain = (Button) findViewById(R.id.Login_button_main);
        Button signUpButtonMain = (Button) findViewById(R.id.SignUp_button_main);
        CheckBox privacyCheckbox = (CheckBox) findViewById(R.id.privacyCheckbox);

        loginButtonMain.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        });
        signUpButtonMain.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        });
        privacyCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            loginButtonMain.setEnabled(isChecked);
                signUpButtonMain.setEnabled(isChecked);
        });
    }

}

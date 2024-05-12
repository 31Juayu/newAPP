package com.example.groupassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    private EditText emailName;
    private EditText passWord;
    private EditText confirmPassWord;
    private EditText userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userName = (EditText) findViewById(R.id.editTextText);
        emailName = (EditText) findViewById(R.id.editTextTextEmailAddress);
        passWord = (EditText) findViewById(R.id.editTextTextPassword);
        confirmPassWord = (EditText) findViewById(R.id.editTextTextPassword3);
        Button registerButton = (Button) findViewById(R.id.button_register);
        Button returnButton = (Button) findViewById(R.id.button_return_login);





    }








}


package com.example.groupassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText userNameText;
    private EditText passWordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // find all elements
        userNameText = (EditText) findViewById(R.id.UserNameEdit);
        passWordText = (EditText) findViewById(R.id.PassWordEdit);
        Button loginButton = (Button) findViewById(R.id.Login_button);
        Button signUpButton = (Button) findViewById(R.id.Sign_up_button);
        // add the listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSignUp();
            }
        });
    }
    private void performLogin(){
        String userName = userNameText.getText().toString().trim();
        String passWord = passWordText.getText().toString().trim();
        if(userName.isEmpty() || passWord.isEmpty()){

        }
    }
    private void performSignUp(){

    }
}
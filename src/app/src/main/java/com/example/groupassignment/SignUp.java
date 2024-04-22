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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailName = (EditText) findViewById(R.id.emailInput);
        passWord = (EditText) findViewById(R.id.setPassword);
        confirmPassWord = (EditText) findViewById(R.id.confirmPassWord);
        Button registerButton = (Button) findViewById(R.id.button_register);
        Button returnButton = (Button) findViewById(R.id.button_return_login);

        //跳转
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRegister();
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnAct();
            }
        });

    }

    private void performRegister(){
        String userEmail = emailName.getText().toString();
        String strPassW = passWord.getText().toString();
        String confirmPass = confirmPassWord.getText().toString();
        if(!strPassW.equals(confirmPass)){
            Toast.makeText(this, "two password are different", Toast.LENGTH_SHORT).show();
            return;
        }

        signup(userEmail,strPassW);

    }

    private void signup(String userEmail,String strPassW){

    };

    private void returnAct(){
        Intent intent = new Intent(getApplicationContext(),Login.class);
        startActivity(intent);

    }


}


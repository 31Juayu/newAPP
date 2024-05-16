package com.example.groupassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groupassignment.DAO.Profile;
import com.example.groupassignment.DAO.ProfileService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Zhengyu Peng u7727795
 */

public class SignUp extends AppCompatActivity {
    private EditText emailName;
    private EditText passWord;
    private EditText confirmPassWord;
    private EditText userName;
    private ProfileService signupService;
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

        // Clicking the button jumps from the SignUp class to the Login class and stops the SignUp class from running.
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        // author : Zhengyu Peng
        registerButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles the click event for the sign-up button, performing validation checks on the user input.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                // Retrieve and trim the input values
                String PW = passWord.getText().toString().trim();
                String PW2 = confirmPassWord.getText().toString().trim();
                String email = emailName.getText().toString().trim();
                String username = userName.getText().toString().trim();
                int pwLength = PW.length();
                // Check if the password and confirm password fields match
                if (!PW.equals(PW2)) {
                    new AlertDialog.Builder(SignUp.this)
                            .setTitle("Comparison Result")
                            .setMessage("The two Passwords are not equal.\nPlease modify. ")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                } else if (pwLength < 8) {
                    Toast.makeText(SignUp.this, "String is too short, Please more than 7", Toast.LENGTH_SHORT).show();
                    return;
                } else if (pwLength > 16) {
                    Toast.makeText(SignUp.this, "String is too long, Please less than 17", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(SignUp.this, "Password is set.", Toast.LENGTH_SHORT).show();
                }
                if (username.length() < 3 || isNumeric(username)|| username.length() > 12 ) {
                    new AlertDialog.Builder(SignUp.this)
                            .setTitle("Error Result")
                            .setMessage("\"Username must be at least 3(max 12) characters long and cannot be purely numeric\"")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }
                if (!isValidEmail(email)) {
                    new AlertDialog.Builder(SignUp.this)
                            .setTitle("Error Result")
                            .setMessage("\"Invalid email format\"")
                            .setPositiveButton("OK", null)
                            .show();
                    return;

                }
                registerUser(username, PW, email);
            }
            private boolean isNumeric(String str) {
                return str.matches("\\d+");
            }
            private boolean isValidEmail(String email) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }
            /**
             * author : Zhengyu Peng
             * Registers a new user with the provided username, password, and email.
             *
             * This method generates a random profile image URL, initializes empty lists for courses and friends,
             * and creates a Profile object for the new user. It then uploads the profile JSON using the signup service.
             * If the upload is successful, a success message is displayed and the user is redirected to the login page.
             * If the upload fails, an error message is displayed.
             *
             * @param username The username of the new user.
             * @param password The password of the new user.
             * @param email    The email address of the new user.
             */
            private void registerUser(String username, String password, String email) {
//                version 1, use my method to upload the data
//                初始化用户信息
//                Map<String, Object> userInfo = new HashMap<>();
//                userInfo.put("username", username);
//                userInfo.put("email", email);
//                userInfo.put("courses", new ArrayList<>()); // 初始为空列表
//                userInfo.put("friends", new ArrayList<>()); // 初始为空列表
//                userInfo.put("profileImageUrl", "gs://comp6442project-8a60c.appspot.com/images/user0.jpg");
//                // 将用户信息存储在 Firebase Storage 中
//                FirebaseStorage storage = FirebaseStorage.getInstance();
//                StorageReference storageRef = storage.getReference();
//                StorageReference userRef = storageRef.child("Profiles/" + email + ".json");
//
//                // 将用户信息转换为 JSON 格式
//                Gson gson = new Gson();
//                String jsonString = gson.toJson(userInfo);
//                byte[] data = jsonString.getBytes();
//
//                // 上传 JSON 数据到 Firebase Storage
//                UploadTask uploadTask = userRef.putBytes(data);
//                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        // 注册成功
//                        Toast.makeText(SignUp.this, "Registration successful", Toast.LENGTH_SHORT).show();
//                        // 注册成功后可以跳转到其他活动
//                        Intent intent = new Intent(SignUp.this, Login.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // 注册失败
//                        Toast.makeText(SignUp.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
                //By Wenzhao Zheng: generate random head images saved in the storage
                Random r = new Random();
                int randomUserImage = r.nextInt(6);
                String profileImageUrl = "user" + randomUserImage + ".jpg";
                List<String> courses = new ArrayList<>();
                List<String> friends = new ArrayList<>();
                Profile signUpProfile = new Profile(username, email, password, profileImageUrl, courses, friends);
                signupService.uploadProfileJson(signUpProfile, new ProfileService.OnProfileUploadListener() {
                    @Override
                    public void onUploadSuccess() {
                        // success
                        Toast.makeText(SignUp.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        // After registration, you can jump to other activities
                        Intent intent = new Intent(SignUp.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void onUploadFailure(Exception e) {
                        Toast.makeText(SignUp.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }

        });







    }








}


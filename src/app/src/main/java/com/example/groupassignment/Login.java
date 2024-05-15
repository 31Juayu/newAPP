package com.example.groupassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groupassignment.DAO.Customer;
import com.example.groupassignment.DAO.Profile;
import com.example.groupassignment.activity.findCourseActivity;
import com.example.groupassignment.utility.smallParserToSearch;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * @author jiayu jian u7731262
 */
public class Login extends AppCompatActivity {
    private EditText userNameText;
    private EditText passWordText;

    private String jsonRead;
    private List<String> courseListInJson;

    private String usernameInJson;

    private String passwordInJson;

    private String emailInJson;

    private String profileImageUrlInJson;

    private List<String> friendsInJson;

    private ArrayList<String> videoNamesFound;

    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // open the fire base
        FirebaseApp.initializeApp(getBaseContext());
        // find all elements
        userNameText = (EditText) findViewById(R.id.UserNameEdit);
        passWordText = (EditText) findViewById(R.id.PassWordEdit);
        Button loginButton = (Button) findViewById(R.id.Login_button);
        Button signUpButton = (Button) findViewById(R.id.Sign_up_button);
        videoNamesFound = new ArrayList<>();
        // add the listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSignUp();
            }
        });
    }
    // Login function version 2.0 base on firebase dataset.
    private void checkLogin(){
        String username = userNameText.getText().toString().trim();
        String password = passWordText.getText().toString().trim();
        checkUserJson(username, password, FirebaseStorage.getInstance());

    }
    // 实际上不建议在客户端直接处理密码验证。这种验证应该在服务器端进行，客户端只发送请求并处理响应，可以写进报告里。
    /**
     * author : Zhengyu Peng
     * Checks the user's JSON profile stored in Firebase Storage to authenticate the user.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param storage  The FirebaseStorage instance.
     */
    private void checkUserJson(String username, String password, FirebaseStorage storage){
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("Profiles/" + username + ".json");

        // Set the maximum file size to 1MB
        long ONE_MEGABYTE = 1024 * 1024;
        profileRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
            String json = new String(bytes);
            Profile userProfile = new Gson().fromJson(json, Profile.class);
            if(!userProfile.getPassword().equals(password)){
                // show wrong password message
                Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_LONG).show();

                return;
            }else {
                Toast.makeText(getApplicationContext(), "User authenticated successfully", Toast.LENGTH_LONG).show();
                // Combined with the previous login code
                SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USERNAME_KEY", username);
                editor.putString("PASSWORD_KEY", password);
                editor.apply();
                //StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                //StorageReference profileRef = storageReference.child("Profiles/" + username + ".json");
                readJSONFromStorage(profileRef);
                Intent intent = new Intent(getApplicationContext(),MenuPage.class);
                startActivity(intent);
                finish();

            }

        }).addOnFailureListener(e -> {
            Toast.makeText(getApplicationContext(), "Username error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        });

    }

    public void readJSONFromStorage(StorageReference storageRef) {
        storageRef.getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(bytes -> {
                    // 成功获取文件内容，将字节数组转换为字符串
                    jsonRead = new String(bytes);
                    // 将 JSON 内容显示在 TextView 中
                    //jsonTextView.setText(json);

                    Gson gson = new Gson();
                    Profile people = gson.fromJson(jsonRead, Profile.class);
                    courseListInJson = people.getCourses();
                    usernameInJson = people.getUsername();
                    passwordInJson = people.getPassword();
                    emailInJson = people.getEmail();
                    profileImageUrlInJson = people.getProfileImageUrl();
                    friendsInJson = people.getFriends();
                    StorageReference path = storageReference.child("Files/");
                    //getVideoNames(path);
                    path.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                        @Override
                        public void onSuccess(ListResult listResult) {
                            for (StorageReference item : listResult.getItems()) {
                                String name = item.getName();
                                if (name.endsWith(".mp4")) {
                                    name = name.substring(0, name.length() - 4); // 去掉.mp4后缀
                                }
                                videoNamesFound.add(name);
                            }
                            //Toast.makeText(Login.this, "first is " + videoNamesFound.get(0), Toast.LENGTH_SHORT).show();
                            //public Profile(String username, String email, String password, String profileImageUrl, List<String> courses, List<String> friends) {
                            Profile upDateP = new Profile(usernameInJson,emailInJson,passwordInJson,profileImageUrlInJson,videoNamesFound,friendsInJson);
                            upDateP.uploadProfileJson(upDateP);
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this, "fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //Toast.makeText(findCourseActivity.this,"succeed" , Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(exception -> {
                    //Toast.makeText(findCourseActivity.this, "fail", Toast.LENGTH_SHORT).show();
                });
    }

    // Login function version 1.0 base on local storage files.
    private void performLogin(){
        String userName = userNameText.getText().toString().trim();
        String passWord = passWordText.getText().toString().trim();
        List<Customer> list = parseXML();
        boolean ifIn = false;
        for (Customer e : list) {
            if (e.getUsername().equals(userName) && e.getPassword().equals(passWord)) {
                ifIn = true;
                //Wenzhao Zheng: following is temporary method for profile activity
                SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USERNAME_KEY", userName);
                editor.putString("PASSWORD_KEY", passWord);
                editor.apply();
                //Wenzhao Zheng: following is test method to upload files to firebase, no check will apply for the test method
                Profile profile = new Profile(userName, null, passWord, null, null, null);
                profile.setExampleData();
                profile.uploadProfileJson(profile);
                break;
            }
        }
        if(ifIn){
            //List<String> urlList = readURLs();
            Intent intent = new Intent(getApplicationContext(), MenuPage.class);
            //intent.putStringArrayListExtra("key_name", (ArrayList<String>) urlList);
            startActivity(intent);

        }

    }

    private void performSignUp(){
        Intent intent = new Intent(getApplicationContext(),SignUp.class);
        startActivity(intent);
        finish(); // 关闭当前的 Activity
    }
    public ArrayList<Customer> parseXML() {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
/*            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));*/

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream inputStream = getAssets().open("people4.xml");
            Document document = builder.parse(inputStream);

            NodeList nodeList = document.getElementsByTagName("ID");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String id = element.getAttribute("id");
                    String email = element.getElementsByTagName("Email").item(0).getTextContent();
                    String password = element.getElementsByTagName("PassWord").item(0).getTextContent();

                    customers.add(new Customer(id, email, password));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return customers;
    }
}
package com.example.groupassignment.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.groupassignment.DAO.Profile;
import com.example.groupassignment.R;
import com.example.groupassignment.utility.smallParserToSearch;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.protobuf.Internal;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListAdapter;


public class findCourseActivity extends AppCompatActivity {
    //private TextView jsonTextView;
    String json;
    List<String> list;

    String usernameInJson;

    String passwordInJson;

    String emailInJson;

    String username;

    String password;

    String email;

    String userInput;

    ListView lv;
    ArrayList<String> ress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_find_course);
/*        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("USERNAME_KEY", "defaultUsername");
        password = sharedPreferences.getString("PASSWORD_KEY", "defaultPassword");
        email = password;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("Profiles/" + username + ".json");

        Button seeTest = (Button) findViewById(R.id.seeInfo);
        lv = (ListView) findViewById(R.id.listView);
        //Button sortCourse = (Button) findViewById(R.id.DirectlySort);


        Intent intent = getIntent();
        userInput = intent.getStringExtra("input");

        seeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                readJSONFromStorage(profileRef);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String inputText = ress.get(position);
                String originalName = null;
                for (String e:
                        list) {
                    if(e.toLowerCase().equals(inputText)){
                        originalName = e;
                        break;
                    }
                }
                Intent intent = new Intent(getApplicationContext(), playVedioOfcourse.class);
                intent.putExtra("name",originalName);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.directly){
            ArrayList<String> newList = new ArrayList<>(ress);
            Collections.sort(newList);
            ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newList);
            lv.setAdapter(ad);
        }
        return super.onOptionsItemSelected(item);
    }


    public void readJSONFromStorage(StorageReference storageRef) {
        storageRef.getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(bytes -> {
                    // 成功获取文件内容，将字节数组转换为字符串
                    json = new String(bytes);
                    // 将 JSON 内容显示在 TextView 中
                    //jsonTextView.setText(json);

                    Gson gson = new Gson();
                    Profile people = gson.fromJson(json, Profile.class);
                    list = people.getCourses();
                    usernameInJson = people.getUsername();
                    passwordInJson = people.getPassword();
                    emailInJson = people.getEmail();

                    ArrayList<String> lowerCase = new ArrayList<>();
                    for (String str : list) {
                        lowerCase.add(str.toLowerCase());
                    }


                    ress = smallParserToSearch.findRessInSmall(userInput,lowerCase);
                    //去重
                    LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(ress);
                    ress = new ArrayList<>(linkedHashSet);

                    ArrayAdapter<String> ad = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ress);
                    lv.setAdapter(ad);


                    Toast.makeText(findCourseActivity.this,"succeed" , Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(exception -> {
                    Toast.makeText(findCourseActivity.this, "fail", Toast.LENGTH_SHORT).show();
                });
    }
}
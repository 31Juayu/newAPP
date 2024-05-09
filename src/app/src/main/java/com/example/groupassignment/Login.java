package com.example.groupassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Login extends AppCompatActivity {
    private EditText userNameText;
    private EditText passWordText;

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
        List<Customer> list = parseXML();
        boolean ifIn = false;
        for (Customer e : list) {
            if (e.getUsername().equals(userName) && e.getPassword().equals(passWord)) {
                ifIn = true;
                //following is temporary method for profile activity
                SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USERNAME_KEY", userName);
                editor.putString("PASSWORD_KEY", passWord);
                editor.apply();
                //following is test method to upload files to firebase, no check will apply for the test method
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
package com.example.groupassignment.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groupassignment.R;
import com.example.groupassignment.utility.parserToSearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.TreeMap;

/**
 * @author jiayu jian u7731262
 */
public class dealSearchActivity extends AppCompatActivity {
    ArrayAdapter<String> ad;
    ArrayList<String> resLine;

    private Button go_back_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deal_search);
/*        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        Button btu = (Button) findViewById(R.id.showRes);
        ListView lv = (ListView) findViewById(R.id.listView);
        TextView textView = findViewById(R.id.rightTextView);
        Intent intent = getIntent();
        String input = intent.getStringExtra("userInput");


        btu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resLine = parserToSearch.findRess(input,dealSearchActivity.this);
                //remove duplicate item
                LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(resLine);
                resLine = new ArrayList<>(linkedHashSet);
                textView.setText("result were found");
                androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                ad = new ArrayAdapter<String>(dealSearchActivity.this,android.R.layout.simple_list_item_1,resLine);
                lv.setAdapter(ad);

            }
        });

        go_back_search = (Button) findViewById(R.id.go_back_search);
        go_back_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.sort){
            TreeMap<Integer, String> map = new TreeMap<>();
            for (String e:
                    resLine) {
                map.put(getNum(e),e);
            }
            ArrayList<String> newList = new ArrayList<>();
            for (Integer key : map.keySet()) {
                newList.add(map.get(key));
            }
            ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newList);
            ListView lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(ad);
        } else if (itemId == R.id.directly) {
            ArrayList<String> newList = new ArrayList<>(resLine);
            Collections.sort(newList);
            ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newList);
            ListView lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(ad);
        } else if (itemId == R.id.maximum) {
            TreeMap<Integer, String> map = new TreeMap<>();
            for (String e:
                    resLine) {
                map.put(getNum(e),e);
            }
            ArrayList<String> newList = new ArrayList<>();
            for (Integer key : map.keySet()) {
                newList.add(map.get(key));
            }
            ArrayList<String> lastInfo = new ArrayList<>();
            lastInfo.add(newList.get(newList.size()-1));
            ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lastInfo);
            ListView lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(ad);

        } else if(itemId == R.id.minimum){
            TreeMap<Integer, String> map = new TreeMap<>();
            for (String e:
                    resLine) {
                map.put(getNum(e),e);
            }
            ArrayList<String> newList = new ArrayList<>();
            for (Integer key : map.keySet()) {
                newList.add(map.get(key));
            }
            ArrayList<String> firstInfo = new ArrayList<>();
            firstInfo.add(newList.get(0));
            ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, firstInfo);
            ListView lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(ad);
        }
        return super.onOptionsItemSelected(item);
    }

    public static int getNum(String str){

        int index = str.indexOf("number is:");

        if (index != -1) {
            String numberPart = str.substring(index + "number is:".length()).trim();

            int endIndex = numberPart.length();
            for (int i = 0; i < numberPart.length(); i++) {
                if (!Character.isDigit(numberPart.charAt(i))) {
                    endIndex = i;
                    break;
                }
            }

            String numberStr = numberPart.substring(0, endIndex);
            int number = 0;
            //
            //System.out.println(numberStr);
            try {
                number = Integer.parseInt(numberStr);
            }catch (Exception e){
                number = 0;
            }

            return number;

        } else {
            return 0;
        }
    }
}
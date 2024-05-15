package com.example.groupassignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.groupassignment.R;
import com.example.groupassignment.activity.GPSActivity;
import com.example.groupassignment.activity.GPSMapsActivity;

/**
 * @author jiayu jian u7731262
 * @author Tianyi Xu u7780366
 * @author Ruize Luo u7776709
 */

public class locationActivity extends AppCompatActivity {

    private Button go_back_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_location);
/*        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        Button GpsButton = (Button) findViewById(R.id.seeInfo);

        Button mapButton = (Button) findViewById(R.id.Country);

        GpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GPSActivity.class);
                startActivity(intent);
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GPSMapsActivity.class);
                startActivity(intent);
            }
        });

        go_back_location = (Button) findViewById(R.id.go_back_location);
        go_back_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
}
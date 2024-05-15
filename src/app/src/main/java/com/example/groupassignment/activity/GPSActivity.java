package com.example.groupassignment.activity;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupassignment.R;

public class GPSActivity extends AppCompatActivity {
    private LocationManager lm;
    private TextView position_show;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private Button go_back_gps;
    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsactivity);
        // get the related element
        position_show = (TextView) findViewById(R.id.position_show);
        // create the location manager
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // if the gps service is closed, switch on
        if (!isGPSable(lm)) {
            Toast.makeText(GPSActivity.this, "please open GPS", Toast.LENGTH_SHORT).show();
            openGPS();
        }
        // get the current nearest location,check if the permission is allowed in manifest
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else {
            Location current_location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            updatePage(current_location);
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 8, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                updatePage(location);
            }
            // 根据模板还有可加的，但是似乎有问题
        });

        go_back_gps = (Button) findViewById(R.id.go_back_gps);
        go_back_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    /**
     * method to check if GPS is available
     */
    private boolean isGPSable(LocationManager lm){
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    /**
     * open the GPS setting page to the users
     */
    private void openGPS(){
        Intent gpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(gpsIntent,0);
    }

    /**
     * update the page according to  the position get
     * @param location
     */
    private void updatePage(Location location){
        if (location != null){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Current location information:\n");
            stringBuilder.append("Longitude: ").append(location.getLongitude()).append("\n");
            stringBuilder.append("Latitude: ").append(location.getLatitude()).append("\n");
            stringBuilder.append("Altitude: ").append(location.getAltitude()).append("\n");
            stringBuilder.append("Speed: ").append(location.getSpeed()).append("\n");
            stringBuilder.append("Bearing: ").append(location.getBearing()).append("\n");
            stringBuilder.append("Accuracy: ").append(location.getAccuracy()).append("\n");
            position_show.setText(stringBuilder.toString());
        } else{
            position_show.setText("");
        }
    }
}
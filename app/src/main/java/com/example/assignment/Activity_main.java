package com.example.assignment;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.assignment.Model.Gps;
import com.example.assignment.PrefManager.SharedPrefManager;
import com.example.assignment.Service.LocationService;

import java.util.List;
import java.util.Locale;

public class Activity_main extends AppCompatActivity {
    private TextView txt_location;
    private Gps gps;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ///login check
        if(SharedPrefManager.getInstance(Activity_main.this).isLoggedIn()){
            setContentView(R.layout.activity_main);
            //init
            txt_location = findViewById(R.id.txt_location);
            try {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            startService(new Intent(Activity_main.this, LocationService.class));
            getLocation();
        }else{
            Intent intent = new Intent(Activity_main.this,Activity_login.class);
            startActivity(intent);
            finish();
        }
    }
    public void getLocation(){
        double latitude = 0, longitude = 0;
        Geocoder geocoder;
        List<Address> addresses;
        String address = "";
        gps = new Gps(Activity_main.this);
        if(gps.canGetLocation()){
             latitude = gps.getLatitude();
             longitude = gps.getLongitude();
        }else{
            gps.showSettingsAlert();
        }
        try {
            geocoder = new Geocoder(this, Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            address = addresses.get(0).getAddressLine(0);
            txt_location.setText("Current Location: \n"+address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grantResults);
        if (permsRequestCode == 101) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
                startService(new Intent(Activity_main.this,LocationService.class));
            }
        }
    }
}

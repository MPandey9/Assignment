package com.example.assignment.Service;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.assignment.Model.Gps;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class LocationService extends Service {

    private boolean isRunning  = false;
    private Handler handler = null;
    Timer timer;
    TimerTask task;
    Gps gps;
    String address;
    @Override
    public void onCreate() {
        handler = new Handler();
        isRunning = true;
        if (Build.VERSION.SDK_INT > 21) {
            startTimerTask();
        }
    }
    public void startTimerTask() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        writetofile(getApplicationContext());
                    }
                });
            }
        };
        timer.schedule(task, 0, 1000 * 60 * 5);
    }
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override
    public void onDestroy() {
        isRunning = false;
    }
    public void writetofile(Context mContext){
        double latitude = 0, longitude = 0;
        Geocoder geocoder;
        List<Address> addresses;
        gps = new Gps(getApplicationContext());
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        File file = new File(mContext.getFilesDir(),"mydir");
        if(!file.exists()){
            file.mkdir();
        }
        try{
            File gpxfile = new File(file, "location");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(address);
            writer.flush();
            writer.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.amandjaja.statistic.demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.amandjaja.statistic.ContentProvider;
import com.amandjaja.statistic.StatisticManager;
import com.amandjaja.statistic.data.ContentData;

public class MainActivity extends AppCompatActivity {
    ContentProvider contentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= 23){
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },10);
        }

        StatisticManager.startSession();

        StatisticManager.createEvent()
                .setType("application")
                .setCategory("action")
                .setName("open")
                .setId(getPackageName())
                .setOk()
                .send();

        contentProvider = StatisticManager.createContent().withTimeout();
    }

    @Override
    protected void onStart() {
        super.onStart();
        StatisticManager.createEvent()
                .setCategory("account")
                .setName("login")
                .setType("google")
                .setId("silveriusdodi@gmail.com")
                .setOk(0)
                .setStatus("00")
                .setStatusCode("XX")
                .setStatusMessage("Fail")
                .send();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatisticManager.setScreenType("android");
        StatisticManager.setScreenView("MainActivity");
        contentProvider.addData(
                StatisticManager.createContent()
                        .setId(1).setType("test").setCategory("test").setAction("test")
                        .putCustom("custom","1")
                        .build()
        );
        contentProvider.addData(
                StatisticManager.createContent()
                        .setId(2).setType("test").setCategory("test").setAction("test")
                        .putCustom("custom","2")
                        .build()
        );
        contentProvider.addData(
                StatisticManager.createContent()
                        .setId(3).setType("test").setCategory("test").setAction("test")
                        .putCustom("custom","3")
                        .build()
        );
        contentProvider.send();

        ContentData data = StatisticManager.createContent()
                .setType("content")
                .setCategory("animation")
                .setAction("play")
                .setId(1)
                .setUserId(1) // IF Login
                .build();
        StatisticManager.triggerStart(data); // Video Start
        StatisticManager.triggerEnd(data); // Video End
        StatisticManager.getInstance().send(data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==10){
            if(checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= 23){
                StatisticManager.createEvent()
                        .setType("application")
                        .setCategory("permission")
                        .setName("location_granted")
                        .setId(getPackageName())
                        .setOk()
                        .send();
            }
        }
    }
}

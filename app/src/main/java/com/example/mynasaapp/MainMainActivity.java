package com.example.mynasaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMainActivity extends AppCompatActivity {
    Intent toEarth, toCredits, toVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);

        toEarth = new Intent(this, TodayActivity.class);
        toVideo = new Intent(this, MainActivity.class);
        // toCredits = new Intent(this, InfoActivity.class);

    }

    public void onVideoClick(View view) {
         startActivity(toVideo);
    }

    public void onEarthClick(View view) {
        startActivity(toEarth);
    }

    public void onCreditsClick(View view) {

    }
}
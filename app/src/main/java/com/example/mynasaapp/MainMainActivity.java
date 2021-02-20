package com.example.mynasaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mynasaapp.dao.ApodDao;
import com.example.mynasaapp.jsonParser.TickerGetter;

public class MainMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);

        BackgroundLoading backgroundLoading = new BackgroundLoading();
        backgroundLoading.execute("Param1", "Param2", "etc");

    }
    private class BackgroundLoading extends AsyncTask<String, Double, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            TickerGetter tickerGetter = new TickerGetter();
            tickerGetter.getNameByTicker();

            return null;
        }

        @Override
        protected void onProgressUpdate(Double... values) {

        }
    }

}
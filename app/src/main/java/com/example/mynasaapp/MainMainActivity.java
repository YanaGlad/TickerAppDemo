package com.example.mynasaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mynasaapp.jsonParser.TickerGetter;

import org.json.JSONException;

public class MainMainActivity extends AppCompatActivity {
    TextView t1;
    TextView t2;
    TextView t3;
    static String name, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);

        t1 = findViewById(R.id.textView1);
        t2 = findViewById(R.id.textView2);
        t3 = findViewById(R.id.textView3);

        BackgroundLoading backgroundLoading = new BackgroundLoading();

        backgroundLoading.execute("Param1", "Param2", "etc");

        while (name == null || price == null)
            System.out.println("wait...");

        t1.setText("MGNI");
        t2.setText(name);
        t3.setText(price);
    }

    private static class BackgroundLoading extends AsyncTask<String, Double, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            TickerGetter tickerGetter = new TickerGetter();
            tickerGetter.loadData("MGNI");

            try {
                name = tickerGetter.getNameByTicker();
                price = tickerGetter.getPriceByTicker();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Double... values) {

        }
    }

}
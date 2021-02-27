package com.example.mynasaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mynasaapp.Support.Data;
import com.example.mynasaapp.jsonParser.TickerGetter;

import org.json.JSONException;

public class MainMainActivity extends AppCompatActivity {
    private String name, price;
    private String currentTicker = Data.tickers[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);

        TextView t1 = findViewById(R.id.textView1);
        TextView t2 = findViewById(R.id.textView2);
        TextView t3 = findViewById(R.id.textView3);

        BackgroundLoading backgroundLoading = new BackgroundLoading();

        backgroundLoading.execute("Param1", "Param2", "etc");

        while (name == null || price == null)
            System.out.println("wait...");

        t1.setText(currentTicker);
        t2.setText(name);
        t3.setText(price);
    }

    private class BackgroundLoading extends AsyncTask<String, Double, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            TickerGetter tickerGetter = new TickerGetter();
            tickerGetter.loadData(currentTicker);

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

//
//public class MainMainActivity extends AppCompatActivity {
//    private TextView t1, t2, t3;
//    private String name, price;
//    private String currentTicker = Data.tickers[0];
//    private Button change;
//    private EditText newNumber;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_main);
//
//        t1 = findViewById(R.id.textView1);
//        t2 = findViewById(R.id.textView2);
//        t3 = findViewById(R.id.textView3);
//        newNumber = findViewById(R.id.editTxt);
//        change = findViewById(R.id.changeBtn);
//
//        BackgroundLoading backgroundLoading = new BackgroundLoading();
//        new Thread(backgroundLoading).start();
//
//        t1.setText(currentTicker);
//        t2.setText(name);
//        t3.setText(price);
//    }
//
//    synchronized void changeNames(TickerGetter tickerGetter) throws InterruptedException {
//        tickerGetter.loadData(currentTicker);
//        wait();
//        System.out.println("Waiting...");
//        try {
//            name = tickerGetter.getNameByTicker();
//            price = tickerGetter.getPriceByTicker();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Notifying");
//        notify();
//    }
//
//    private class BackgroundLoading implements Runnable {
//
//        @Override
//        public void run() {
//
//            TickerGetter tickerGetter = new TickerGetter();
//            try {
//                changeNames(tickerGetter);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//}
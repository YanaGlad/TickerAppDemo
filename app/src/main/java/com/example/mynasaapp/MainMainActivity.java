package com.example.mynasaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mynasaapp.Support.Data;
import com.example.mynasaapp.Support.TickerInfo;
import com.example.mynasaapp.jsonParser.TickerGetter;

import org.json.JSONException;

import java.util.ArrayList;

public class MainMainActivity extends AppCompatActivity {
    public static SQLiteDatabase featureDB;
    public static Cursor cursor;
    public static String DB_PATH;
    public static ArrayList<TickerInfo> tickerInfos;
    public static int countFavourites = 0;

    private String name, price, priceChange;
    private String currentTicker = Data.tickers[1];


    private Button button;
    private TextView t1, t2, t3, t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);

        t1 = findViewById(R.id.textView1);
        t2 = findViewById(R.id.textView2);
        t3 = findViewById(R.id.textView3);
        t4 = findViewById(R.id.textView4);

        button = findViewById(R.id.changeBtn);

        tickerInfos = new ArrayList<>();

        LoadingOneTicker backgroundLoading = new LoadingOneTicker();
        new Thread(backgroundLoading).start();

        while (name == null || price == null || priceChange == null)
            System.out.println("wait...");

        t1.setText(currentTicker);
        t2.setText(name);
        t3.setText(price);
        t4.setText(priceChange);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingAllTickers loadingAllTickers = new LoadingAllTickers();
                new Thread(loadingAllTickers).start();

                System.out.println("Done");
                for (int i = 0; i < tickerInfos.size(); i++) {
                    System.out.println(tickerInfos.get(i).getNameTicker() + " " + tickerInfos.get(i).getNameCompany()
                            + " " + tickerInfos.get(i).getPrice() + tickerInfos.get(i).getPriceChange());
                }
            }
        });
    }

    private static class LoadingAllTickers implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < Data.tickers.length; i++) {
                String ticker = Data.tickers[i];

                TickerGetter tickerGetter = new TickerGetter();
                tickerGetter.loadData(ticker);

                try {
                    System.out.println("Adding " + ticker + " " + tickerGetter.getNameByTicker() + " " + tickerGetter.getChangePercent());
                    tickerInfos.add(new TickerInfo(ticker, tickerGetter.getNameByTicker(),
                            tickerGetter.getPriceByTicker(), tickerGetter.getChangePercent()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }


    private class LoadingOneTicker implements Runnable {

        @Override
        public void run() {
            TickerGetter tickerGetter = new TickerGetter();
            tickerGetter.loadData(currentTicker);

            try {
                name = tickerGetter.getNameByTicker();
                price = tickerGetter.getPriceByTicker();
                priceChange = tickerGetter.getChangePercent();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    public static void addTickerToDB(String tickerName) {
        countFavourites++;
        featureDB.execSQL("INSERT into feature (_id, ticker) VALUES ( " + countFavourites + "," + tickerName + ")");
    }

    public static void removeTickerFromDB(String tickerName) {
        featureDB.execSQL("DELETE * from feature WHERE ticker = " + tickerName);
        countFavourites--;

        ContentValues cv = new ContentValues();
        //обновить id чтобы было по порядку после удаления
        for (int i = 0; i < countFavourites; i++) {
            cv.put("_id", i);
            featureDB.update("feature", cv, "_id = " + i, null);
        }

    }

    public void takeInfoFromDB() {
        for (int i = 0; i < countFavourites; i++) {
            cursor = featureDB.rawQuery("SELECT * from feature WHERE _id = " + (i + 1), null);
            if (cursor != null && cursor.moveToFirst()) {
                currentTicker = Data.searchTicker(cursor.getString(1));
                System.out.println("Current ticker is " + currentTicker);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tickerInfos = new ArrayList<>();
        DB_PATH = this.getFilesDir().getPath() + "feature.db";
        featureDB = getBaseContext().openOrCreateDatabase("feature.db", MODE_PRIVATE, null);
        featureDB.execSQL("DROP TABLE IF EXISTS feature");
        featureDB.execSQL("CREATE TABLE IF NOT EXISTS feature (_id INTEGER, ticker TEXT)");
        featureDB.execSQL("INSERT into feature (_id, ticker) VALUES (1, 'AAPL')");
        countFavourites++;

    }
}

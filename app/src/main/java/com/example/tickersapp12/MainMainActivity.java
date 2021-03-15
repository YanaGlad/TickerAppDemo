package com.example.tickersapp12;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.mynasaapp.R;
import com.example.tickersapp12.Support.Data;
import com.example.tickersapp12.Support.TickerInfo;
import com.example.tickersapp12.jsonParser.TickerGetter;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);

        TextView t1 = findViewById(R.id.textView1);
        TextView t2 = findViewById(R.id.textView2);
        TextView t3 = findViewById(R.id.textView3);
        TextView t4 = findViewById(R.id.textView4);

        tickerInfos = new ArrayList<>();

        LoadingOneTicker backgroundLoading = new LoadingOneTicker();
        new Thread(backgroundLoading).start();

        while (name == null || price == null || priceChange == null)
            System.out.println("wait...");

        t1.setText(currentTicker);
        t2.setText(name);
        t3.setText(price);
        t4.setText(priceChange);

    }
    public void onPrintClick(View view){
        for (int i = 0; i < tickerInfos.size(); i++) {
            System.out.println(tickerInfos.get(i).getNameTicker() + " " + tickerInfos.get(i).getNameCompany()
                    + " " + tickerInfos.get(i).getPrice() + tickerInfos.get(i).getPriceChange());
            System.out.println();
        }
    }

    public void onLoadClick(View view){
        LoadingAllTickers loadingAllTickers = new LoadingAllTickers();
        Thread loader = new Thread(loadingAllTickers);
        loader.start();
    }

    private static class LoadingAllTickers implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < Data.tickers.length; i++) {
                String ticker = Data.tickers[i];

                TickerGetter tickerGetter = new TickerGetter();
                tickerGetter.loadData(ticker);

                try {
                  //  System.out.println("Adding " + ticker + " " + tickerGetter.getNameByTicker() + " " + tickerGetter.getChangePercent());
                    MainMainActivity.tickerInfos.add(new TickerInfo(ticker, tickerGetter.getNameByTicker(),
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

    public void printInfoFromDB() {
        for (int i = 0; i < countFavourites; i++) {
            cursor = featureDB.rawQuery("SELECT * from feature WHERE _id = " + (i + 1), null);
            if (cursor != null && cursor.moveToFirst()) {
                System.out.println("Current ticker is " +  cursor.getString(1));
            }
        }
    }


    public void takeInfoFromDB() {
        for (int i = 0; i < countFavourites; i++) {
            cursor = featureDB.rawQuery("SELECT * from feature WHERE _id = " + (i + 1), null);
            if (cursor != null && cursor.moveToFirst()) {
                currentTicker = cursor.getString(1);
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

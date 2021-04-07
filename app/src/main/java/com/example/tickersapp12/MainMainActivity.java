package com.example.tickersapp12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mynasaapp.R;
import com.example.tickersapp12.Support.Data;
import com.example.tickersapp12.Support.TickerInfo;
import com.example.tickersapp12.Support.TradeInfo;
import com.example.tickersapp12.jsonParser.TickerGetter;

import org.json.JSONException;

import java.util.ArrayList;

public class MainMainActivity extends AppCompatActivity {
    public static SQLiteDatabase featureDB, tradeDB;
    public static Cursor cursor, cursorCheck;
    public static String DB_PATH, DB_PATH_TRADE;
    public static ArrayList<TickerInfo> tickerInfos, favTickers;
    public static ArrayList<TradeInfo> tradeInfos;

    public static int threadCount = 0;
    public static int countFavourites = 0;

    private RecyclerView recyclerView;
    private TickerAdapter.OnTickerClickListener onTickerClickListener;

    public void onFavsClick(View view) {
        updateFavTickers();

        while (favTickers.size() < countFavourites) {
            System.out.println("CHECK " + favTickers.size() + " " + countFavourites);
            continue;
        }

        recyclerView.setAdapter(new TickerAdapter(favTickers, getApplicationContext(), onTickerClickListener));

    }

    public void onStockClick(View view) {
        tickerInfos.clear();

        for (int i = 0; i < Data.tickers.length; i++) {
            LoadingOneTicker loadingOneTicker = new LoadingOneTicker(i);
            new Thread(loadingOneTicker).start();
        }

        while (tickerInfos.size() != Data.tickers.length) {
            System.out.println("Wait... " + tickerInfos.size() + " " + Data.tickers.length);
        }


        recyclerView.setAdapter(new TickerAdapter(tickerInfos, getApplicationContext(), onTickerClickListener));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recycle);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(false);

        tickerInfos = new ArrayList<>();
        favTickers = new ArrayList<>();


        onTickerClickListener = new TickerAdapter.OnTickerClickListener() {
            @Override
            public void onTickerClick(TickerInfo tickerInfo, int position) {
                Intent intent = new Intent(MainMainActivity.this, ListActivity.class);
                intent.putExtra("ticker", tickerInfo.getNameTicker());
                intent.putExtra("company", tickerInfo.getNameCompany());
                intent.putExtra("price", tickerInfo.getPrice());
                intent.putExtra("change", tickerInfo.getPriceChange());
                startActivity(intent);

            }
        };


        for (int i = 0; i < Data.tickers.length; i++) {
            LoadingOneTicker loadingOneTicker = new LoadingOneTicker(i);
            new Thread(loadingOneTicker).start();
            System.out.println("Active threads: " + threadCount);
        }

        while (tickerInfos.size() != Data.tickers.length);

        recyclerView.setAdapter(new TickerAdapter(tickerInfos, getApplicationContext(), onTickerClickListener));


    }

    private class LoadingOneTicker implements Runnable {
        private int index;

        public LoadingOneTicker(int index) {
            this.index = index;
            threadCount++;
            System.out.println("Thread " + index + " started ");
        }

        @Override
        public void run() {
            String ticker = Data.tickers[index];
            TickerGetter tickerGetter = new TickerGetter();
            tickerGetter.loadData(ticker);

            try {
            //    System.out.println("Adding " + ticker + " " + tickerGetter.getNameByTicker() + " " + tickerGetter.getChangePercent());
                MainMainActivity.tickerInfos.add(new TickerInfo(ticker, tickerGetter.getNameByTicker(),
                        tickerGetter.getPriceByTicker(), tickerGetter.getChangePercent()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
          //  System.out.println("Finishing thread " + index);
         }
    }


    private static class LoadingFavTicker implements Runnable {
        private String ticker;

        public LoadingFavTicker(String ticker) {
            this.ticker = ticker;
        }

        @Override
        public void run() {

            TickerGetter tickerGetter = new TickerGetter();
            tickerGetter.loadData(ticker);

            boolean check = true;

            for (int i = 0; i < favTickers.size(); i++)
                if (favTickers.get(i).getNameTicker().equals(ticker))
                    check = false;


            if (check) {
                try {
                    favTickers.add(new TickerInfo(ticker, tickerGetter.getNameByTicker(), tickerGetter.getPriceByTicker(), tickerGetter.getChangePercent()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void addTickerToDB(String tickerName) {
        countFavourites++;
        featureDB.execSQL("INSERT into feature (_id, ticker) VALUES (" + countFavourites + "," + "'" + tickerName + "'" + ")");
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

    public static void updateFavTickers() {
        favTickers.clear();

        for (int i = 0; i < countFavourites; i++) {
            cursor = featureDB.rawQuery("SELECT * from feature WHERE _id = " + (i + 1), null);

            if (cursor != null && cursor.moveToFirst()) {
                LoadingFavTicker loadingFavTicker = new LoadingFavTicker(cursor.getString(1));
                Thread loader = new Thread(loadingFavTicker);
                loader.start();
                try {
                    loader.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void updateTradeInfo() {
        tradeInfos.clear();

        for (int i = 0; i < countFavourites; i++) {
            cursor = featureDB.rawQuery("SELECT * from trade WHERE _id = " + (i + 1), null);
            if (cursor != null && cursor.moveToFirst()) {
                System.out.println("Buy price: " + cursor.getDouble(2) + " ticker: " + cursor.getString(1));
            }
        }
    }


    public void printInfoFromDB() {
        for (int i = 0; i < countFavourites; i++) {
            cursor = featureDB.rawQuery("SELECT * from feature WHERE _id = " + (i + 1), null);
            if (cursor != null && cursor.moveToFirst()) {
                System.out.println("DB: " + cursor.getString(1));
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

        DB_PATH = this.getFilesDir().getPath() + "trade.db";
        tradeDB = getBaseContext().openOrCreateDatabase("trade.db", MODE_PRIVATE, null);
        tradeDB.execSQL("DROP TABLE IF EXISTS trade");
        tradeDB.execSQL("CREATE TABLE IF NOT EXISTS trade (_id INTEGER, ticker TEXT, buyPrice REAL, sellPrice REAL, lot INTEGER)");

//
//        int ind = 0;
//        while ((featureDB.rawQuery("SELECT * from feature WHERE _id = " + (ind + 1), null)) != null) {
//            cursor = featureDB.rawQuery("SELECT * from feature WHERE _id = " + (ind + 1), null);
//            if (cursor != null && cursor.moveToFirst()) {
//                System.out.println("DB: " + cursor.getString(1));
//            }
//        }

        printInfoFromDB();
    }
}

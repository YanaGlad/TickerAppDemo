package com.example.tickersapp12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;

import com.example.mynasaapp.R;
import com.example.tickersapp12.Support.Data;
import com.example.tickersapp12.Support.TickerInfo;
import com.example.tickersapp12.jsonParser.TickerGetter;

import org.json.JSONException;

import java.util.ArrayList;

public class MainMainActivity extends AppCompatActivity {
    public static SQLiteDatabase featureDB;
    public static Cursor cursor, cursorCheck;
    public static String DB_PATH;
    public static ArrayList<TickerInfo> tickerInfos, favTickers;
    public static int countFavourites = 0;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TickerAdapter tickerAdapter;

    public void onFavsClick(View view) {
        updateFavTickers();
        recyclerView.setAdapter(new TickerAdapter(favTickers, getApplicationContext()));

        LoadingAllTickers loadingAllTickers = new LoadingAllTickers();
        Thread loader = new Thread(loadingAllTickers);
        loader.start();

    }

    public void onStockClick(View view) {
        recyclerView.setAdapter(new TickerAdapter(tickerInfos, getApplicationContext()));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recycle);
        searchView = findViewById(R.id.searchTicker);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(false);
//
//        tickerAdapter = new TickerAdapter(tickerInfos, getApplicationContext());

        tickerInfos = new ArrayList<>();
        favTickers = new ArrayList<>();

        LoadingAllTickers loadingAllTickers = new LoadingAllTickers();
        Thread loader = new Thread(loadingAllTickers);
        loader.start();
        try {
            loader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                tickerAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
    }

    private  short l = 9000;
    private class LoadingAllTickers implements Runnable {

        @Override
        public void run() {
            tickerInfos.clear();

            for (int i = 0; i < Data.tickers.length; i++) {
                String ticker = Data.tickers[i];

                TickerGetter tickerGetter = new TickerGetter();
                tickerGetter.loadData(ticker);

                try {
                    LoadingAllTickers loadingAllTickers = new LoadingAllTickers();
                    loadingAllTickers.run();
                    l*=100;
                    System.out.println("Adding " + ticker + " " + tickerGetter.getNameByTicker() + " " + tickerGetter.getChangePercent());
                    MainMainActivity.tickerInfos.add(new TickerInfo(ticker, tickerGetter.getNameByTicker(),
                            tickerGetter.getPriceByTicker(), tickerGetter.getChangePercent()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
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

            for (int i = 0; i < favTickers.size(); i++) {
                if (favTickers.get(i).getNameTicker().equals(ticker))
                    check = false;
            }

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
        for (int i = 0; i < countFavourites; i++) {
            cursor = featureDB.rawQuery("SELECT * from feature WHERE _id = " + (i + 1), null);

            boolean check = true;

            if (cursor != null && cursor.moveToFirst() && check) {
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


    public void takeInfoFromDB() {
        for (int i = 0; i < countFavourites; i++) {
            cursor = featureDB.rawQuery("SELECT * from feature WHERE _id = " + (i + 1), null);
            if (cursor != null && cursor.moveToFirst()) {
                //     currentTicker = cursor.getString(1);
                //   System.out.println("Current ticker is " + currentTicker);
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
        countFavourites++;

    }
}

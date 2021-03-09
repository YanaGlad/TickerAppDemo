package com.example.mynasaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mynasaapp.Support.Data;
import com.example.mynasaapp.Support.TickerInfo;
import com.example.mynasaapp.jsonParser.TickerGetter;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends MainRunActivity {
    public static SQLiteDatabase timeDB, catsDB, strategyDB, mathsDB;
    public static Cursor cursor, catCursor, strategyCursor, mathsCursor;
    public static String DB_PATH1, DB_PATH2, DB_PATH3;
     public static ArrayList<Cat> listOfCats;
    public static ArrayList<CatPet> listOfPets;

    Context context;

    public MenuView getNewView() {
        context = this;
        try {
            BitmapLoader bitmapLoader = new BitmapLoader(this, this.getGamePaint());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MenuView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        timeLevels = new ArrayList<>();
        DB_PATH1 = this.getFilesDir().getPath() + "time.db";
        timeDB = getBaseContext().openOrCreateDatabase("time.db", MODE_PRIVATE, null);
        //     timeDB.execSQL("DROP TABLE IF EXISTS time");
        timeDB.execSQL("CREATE TABLE IF NOT EXISTS time (_id INTEGER, stars INTEGER)");
        timeDB.execSQL("INSERT into time (_id, stars) VALUES (1,0)");
        timeDB.execSQL("INSERT into time (_id, stars) VALUES (2,0)");
        timeDB.execSQL("INSERT into time (_id, stars) VALUES (3,0)");
        timeDB.execSQL("INSERT into time (_id, stars) VALUES (4,0)");
        timeDB.execSQL("INSERT into time (_id, stars) VALUES (5,0)");
        timeDB.execSQL("INSERT into time (_id, stars) VALUES (6,0)");
        timeDB.execSQL("INSERT into time (_id, stars) VALUES (7,0)");
        timeDB.execSQL("INSERT into time (_id, stars) VALUES (8,0)");
        timeDB.execSQL("INSERT into time (_id, stars) VALUES (9,0)");

        for (int i = 0; i < BasicGameSupport.levelsCount; i++) {
            cursor = timeDB.rawQuery("SELECT * from time WHERE _id = " + (i + 1), null);
            if (cursor != null && cursor.moveToFirst()) {
                timeLevels.add(new Level(cursor.getInt(0), cursor.getInt(1)));
            }
        }

        DB_PATH2 = this.getFilesDir().getPath() + "cats.db";
        catsDB = getBaseContext().openOrCreateDatabase("cats.db", MODE_PRIVATE, null);
        //  catsDB.execSQL("DROP TABLE IF EXISTS cats");
        catsDB.execSQL("CREATE TABLE IF NOT EXISTS cats (_id INTEGER, name TEXT, imageSet TEXT, power INTEGER, speed INTEGER, delay INTEGER,  chosen INTEGER, unlocked INTEGER, room INTEGER, price INTEGER, health INTEGER)"); ///PRICE
        catsDB.execSQL("INSERT into cats (_id, name, imageSet, power, speed , delay , chosen, unlocked, room, price, health) VALUES (1, 'Gray', 'gray', 25, 5, 3, 1, 1, 1, 25, 30)");
        catsDB.execSQL("INSERT into cats (_id, name, imageSet, power, speed , delay , chosen, unlocked, room, price, health) VALUES (2, 'Oragne', 'orange', 35 , 5, 3, 0, 0, -1, 30, 35)");
        catsDB.execSQL("INSERT into cats (_id, name, imageSet, power, speed , delay , chosen, unlocked, room, price, health) VALUES (3, 'Alien', 'greenAlien', 50 , 5, 4, 0, 0, -1, 50, 70)");
        catsDB.execSQL("INSERT into cats (_id, name, imageSet, power, speed , delay , chosen, unlocked, room, price, health) VALUES (4, 'Shadow', 'shadow', 40 , 5, 3, 0, 0, -1, 45, 40)");
        catsDB.execSQL("INSERT into cats (_id, name, imageSet, power, speed , delay , chosen, unlocked, room, price, health) VALUES (5, 'MainCoon', 'mainCoon', 60 , 5, 2, 0, 0, -1, 60, 100)");


        listOfCats = new ArrayList<>();
        listOfPets = new ArrayList<>();

        for (int i = 0; i < BasicGameSupport.catsCount; i++) {
            catCursor = catsDB.rawQuery("SELECT * from cats WHERE _id = " + (i + 1), null);
            if (catCursor != null && catCursor.moveToFirst()) {
                listOfCats.add(new Cat(catCursor.getInt(0), catCursor.getString(1), catCursor.getString(2),
                        catCursor.getInt(3), catCursor.getInt(4), catCursor.getInt(5), catCursor.getInt(6),
                        catCursor.getInt(7), catCursor.getInt(8), catCursor.getInt(9), catCursor.getInt(10)));

                listOfPets.add(new CatPet(listOfCats.get(i), catCursor.getInt(0), catCursor.getInt(8)));
            }
        }

        strategyLevels = new ArrayList<>();

        DB_PATH3 = this.getFilesDir().getPath() + "strategy.db";
        strategyDB = getBaseContext().openOrCreateDatabase("strategy.db", MODE_PRIVATE, null);
        // strategyDB.execSQL("DROP TABLE IF EXISTS strategy");
        strategyDB.execSQL("CREATE TABLE IF NOT EXISTS strategy (_id INTEGER, stars INTEGER)");
        strategyDB.execSQL("INSERT into strategy (_id, stars) VALUES (1,0)");
        strategyDB.execSQL("INSERT into strategy (_id, stars) VALUES (2,0)");
        strategyDB.execSQL("INSERT into strategy (_id, stars) VALUES (3,0)");
        strategyDB.execSQL("INSERT into strategy (_id, stars) VALUES (4,0)");
        strategyDB.execSQL("INSERT into strategy (_id, stars) VALUES (5,0)");
        strategyDB.execSQL("INSERT into strategy (_id, stars) VALUES (6,0)");
        strategyDB.execSQL("INSERT into strategy (_id, stars) VALUES (7,0)");
        strategyDB.execSQL("INSERT into strategy (_id, stars) VALUES (8,0)");
        strategyDB.execSQL("INSERT into strategy (_id, stars) VALUES (9,0)");


        for (int i = 0; i < BasicGameSupport.levelsCount; i++) {
            strategyCursor = strategyDB.rawQuery("SELECT * from strategy WHERE _id = " + (i + 1), null);
            if (strategyCursor != null && strategyCursor.moveToFirst()) {
                strategyLevels.add(new Level(strategyCursor.getInt(0), strategyCursor.getInt(1)));
            }
        }

        mathsLevels = new ArrayList<>();

        DB_PATH3 = this.getFilesDir().getPath() + "maths.db";
        mathsDB = getBaseContext().openOrCreateDatabase("maths.db", MODE_PRIVATE, null);
        // mathsDB.execSQL("DROP TABLE IF EXISTS maths");
        mathsDB.execSQL("CREATE TABLE IF NOT EXISTS maths (_id INTEGER, stars INTEGER)");
        mathsDB.execSQL("INSERT into maths (_id, stars) VALUES (1,0)");
        mathsDB.execSQL("INSERT into maths (_id, stars) VALUES (2,0)");
        mathsDB.execSQL("INSERT into maths (_id, stars) VALUES (3,0)");
        mathsDB.execSQL("INSERT into maths (_id, stars) VALUES (4,0)");
        mathsDB.execSQL("INSERT into maths (_id, stars) VALUES (5,0)");
        mathsDB.execSQL("INSERT into maths (_id, stars) VALUES (6,0)");
        mathsDB.execSQL("INSERT into maths (_id, stars) VALUES (7,0)");
        mathsDB.execSQL("INSERT into maths (_id, stars) VALUES (8,0)");
        mathsDB.execSQL("INSERT into maths (_id, stars) VALUES (9,0)");
        for (int i = 0; i < BasicGameSupport.levelsCount; i++) {
            mathsCursor = mathsDB.rawQuery("SELECT * from maths WHERE _id = " + (i + 1), null);
            if (mathsCursor != null && mathsCursor.moveToFirst()) {
                mathsLevels.add(new Level(mathsCursor.getInt(0), mathsCursor.getInt(1)));
            }
        }
    }
}





public class MainMainActivity extends AppCompatActivity {
    public static SQLiteDatabase featureDB;
    public static Cursor cursor ;
    public static String DB_PATH ;
    public static ArrayList<TickerInfo>tickerInfos;

    private String name, price;
    private String currentTicker = Data.tickers[0];

    @Override
    protected void onResume() {
        super.onResume();
        tickerInfos = new ArrayList<>();
        DB_PATH= this.getFilesDir().getPath() + "feature.db";
        featureDB = getBaseContext().openOrCreateDatabase("feature.db", MODE_PRIVATE, null);
        // featureDB.execSQL("DROP TABLE IF EXISTS feature");
        featureDB.execSQL("CREATE TABLE IF NOT EXISTS time (_id INTEGER, price INTEGER, percent REAL, NAME TEXT)");
        featureDB.execSQL("INSERT into time (_id, stars) VALUES (1, 1200, 0.05, 'MonsterGlad Corp')");



        for (int i = 0; i < 1; i++) {
            cursor = featureDB.rawQuery("SELECT * from time WHERE _id = " + (i + 1), null);
            if (cursor != null && cursor.moveToFirst()) {
                tickerInfos.add(new TickerInfo( ));
            }
        }


    }

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
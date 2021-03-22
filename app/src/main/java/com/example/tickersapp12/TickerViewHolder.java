package com.example.tickersapp12;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynasaapp.R;
import com.example.tickersapp12.Support.TickerInfo;

import java.util.ArrayList;

import static com.example.tickersapp12.MainMainActivity.countFavourites;

public class TickerViewHolder extends RecyclerView.ViewHolder {

    private TextView ticker, company, price, priceChange;
    public ImageButton fav;
    boolean favourite = false;

    public TickerViewHolder(@NonNull View itemView) {
        super(itemView);

        ticker = itemView.findViewById(R.id.nameTicker);
        company = itemView.findViewById(R.id.companyTicker);
        price = itemView.findViewById(R.id.priceTicker);
        priceChange = itemView.findViewById(R.id.changeTicker);

        fav = itemView.findViewById(R.id.fav);

//        System.out.println("KONSTRUKTOR");
//
//        System.out.println("Count favs " + countFavourites);

//        for (int i = 0; i < countFavourites; i++) {
//
//            cursor = MainMainActivity.featureDB.rawQuery("SELECT * from feature WHERE _id = " + (i + 1), null);
//            if(cursor == null)
//                System.out.println("N U L L ");
//
//
//            if (cursor != null && cursor.moveToFirst()) {
//                System.out.println("TEST TEST ET SGGERGTFV " +  cursor.getString(1).toLowerCase() + "  " + ticker.getText().toString());
//                if (cursor.getString(1).equals(ticker.getText().toString())) {
//                    System.out.println("Searching db " + cursor.getString(1).toLowerCase() + " and got: " + ticker.getText().toString().toLowerCase());
//                    favourite = true;
//                }
//            }
//        }

//        System.out.println("Ticker is " + ticker.getText().toString() + "  " + favourite);

        //  if(favourite)
        //   fav.setImageResource(R.drawable.star);
        fav.setImageResource(R.drawable.nasa);

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favourite = true;
                fav.setImageResource(R.drawable.star);
                MainMainActivity.addTickerToDB(ticker.getText().toString());
                MainMainActivity.updateFavTickers();
            }
        });

    }

    void sell(View view) {

    }

    void buy(View view) {

    }

    void setStar(Context context, String ticker, ImageButton imageButton) {
        boolean added = false;

        for (int i = 0; i < countFavourites; i++) {
            Cursor cursor = MainMainActivity.featureDB.rawQuery("SELECT * from feature WHERE _id = " + (i + 1), null);
            if (cursor != null && cursor.moveToFirst()) {
                if (cursor.getString(1).equals(ticker)) {
                    System.out.println("Searching db " + cursor.getString(1) + " and got: " + ticker);
                    added = true;
                }
            }
        }
        if (added)
            imageButton.setImageResource(R.drawable.star);
    }

    void setDescription(Context context, String ticker, String company, String price, String priceChange) {
        this.ticker.setText(ticker);
        this.company.setText(company);
        this.price.setText(price + " USD");
        this.priceChange.setText(priceChange);

        if (Double.parseDouble(priceChange.substring(0, priceChange.length() - 1)) > 0)
            this.priceChange.setTextColor(Color.GREEN);
        else this.priceChange.setTextColor(Color.RED);
    }


}

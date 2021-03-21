package com.example.tickersapp12;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynasaapp.R;

public class TickerViewHolder extends RecyclerView.ViewHolder {

    private TextView ticker, company, price, priceChange;
    private Button fav;

    public TickerViewHolder(@NonNull View itemView) {
        super(itemView);

        ticker = itemView.findViewById(R.id.nameTicker);
        company = itemView.findViewById(R.id.companyTicker);
        price = itemView.findViewById(R.id.priceTicker);
        priceChange = itemView.findViewById(R.id.changeTicker);

        fav = itemView.findViewById(R.id.fav);

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMainActivity.addTickerToDB(ticker.getText().toString());
                MainMainActivity.updateFavTickers();
               // System.out.println(ticker.getText() + " " + company.getText() + " FAVS");
            }
        });

    }

    void sell(View view) {

    }

    void buy(View view) {

    }

    void setDescription(Context context, String ticker, String company, String price, String priceChange) {
        this.ticker.setText(ticker);
        this.company.setText(company);
        this.price.setText(price + " USD");
        this.priceChange.setText(priceChange);

        if (Double.parseDouble(priceChange.substring(0, priceChange.length()-1)) > 0)
            this.priceChange.setTextColor(Color.GREEN);
        else this.priceChange.setTextColor(Color.RED);
    }
}

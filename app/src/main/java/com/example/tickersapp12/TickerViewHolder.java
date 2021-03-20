package com.example.tickersapp12;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynasaapp.R;

public class TickerViewHolder extends RecyclerView.ViewHolder {

    private TextView ticker, company, price, priceChange;
    private Button fav ;

    public TickerViewHolder(@NonNull View itemView) {
        super(itemView);

        ticker = itemView.findViewById(R.id.nameTicker);
        company = itemView.findViewById(R.id.companyTicker);
        price = itemView.findViewById(R.id.priceTicker);
        priceChange = itemView.findViewById(R.id.changeTicker);

        fav = itemView.findViewById(R.id.fav);

    }
    void sell(View view){

    }

    void buy(View view){

    }

    void addToFavs(View view){

    }

    void setDescription(Context context, String ticker, String company, String price, String priceChange) {
        this.ticker.setText(ticker);
        this.company.setText(company);
        this.price.setText(price);
        this.priceChange.setText(priceChange);
    }
}

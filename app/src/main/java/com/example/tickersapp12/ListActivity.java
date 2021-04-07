package com.example.tickersapp12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mynasaapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListActivity extends AppCompatActivity {

    private TextView ticker, company, price, change, owning, lotCount;
    private FloatingActionButton exit;
    private Button plus, minus;
    private static int countLots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_ticker_info);

        exit = findViewById(R.id.floatingActionButton);
        ticker = findViewById(R.id.nameTickerMore);
        company = findViewById(R.id.nameCompanyMore);
        price = findViewById(R.id.priceTickerMore);
        change = findViewById(R.id.priceChangeMore);
        owning = findViewById(R.id.owningMore);
        plus = findViewById(R.id.plusMore);
        minus = findViewById(R.id.minusMore);
        lotCount = findViewById(R.id.lotCount);

        countLots = Integer.parseInt(lotCount.getText().toString());


        Bundle arguments = getIntent().getExtras();

        String tickerS = "", companyS = "", priceS = "", changeS = "";

        if (arguments != null) {
            tickerS = arguments.getString("ticker");
            companyS = arguments.getString("company");
            priceS = arguments.getString("price");
            changeS = arguments.getString("change");

        }


        this.ticker.setText(tickerS);
        this.company.setText(companyS);
        this.price.setText(price.getText().toString() + " " + priceS);
        this.change.setText(changeS);

//
//        for (int i = 0; i < MainMainActivity.tradeInfos.size(); i++) {
//
//        }


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countLots++;
                lotCount.setText(String.valueOf(countLots));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countLots--;
                lotCount.setText(String.valueOf(countLots));
            }
        });
    }
}
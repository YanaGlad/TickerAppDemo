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
    private Button plus, minus, buy, sell;
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
        buy = findViewById(R.id.buyMore);
        sell = findViewById(R.id.sellMore);

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
        this.company.setText(company.getText().toString() + " " + companyS);
        this.price.setText(price.getText().toString() + " " + priceS);
        this.change.setText(changeS);


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

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("BOUGHT");
                MainMainActivity.addTradeToDB(String.valueOf(ticker.getText().toString()), Double.parseDouble(price.getText().toString().substring(21)), 0.0, countLots);
            }
        });
    }
}
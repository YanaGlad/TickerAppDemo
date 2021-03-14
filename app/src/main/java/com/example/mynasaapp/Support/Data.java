package com.example.mynasaapp.Support;

//Вспомогательные функции и переменные для работы с данными
public class Data {

    public static String[] tickers = {
            "AAPL",
            "TSLA",
            "AMZN",
            "BA",
            "CSCO",
            "KO",
            "EBAY",
            "INTC",
            "MSFT",
            "MCD",
            "GOOG",
            "ORCL",
            "BAC"
    };

    public static String searchTicker(String tick) {
        for (String ticker : tickers) {
            if (tick.equals(ticker))
                return ticker;
        }
        return null;
    }
}


package com.example.mynasaapp.Support;

//Вспомогательные функции и переменные для работы с данными
public class Data {

    public static String[] tickers = {
            "AAPL",
            "TSLA",
            "PINS_SPB",
            "TWTR_SPB",
            "DLB_SPB",
            "ROSN",
            "LKOH",
            "GAZP",
            "SBER",
            "RTKMP",
            "AFLT",
            "MTSS",
            "CHMF",
///////////////////////////////////////
    };

    public static String searchTicker(String tick) {
        for (String ticker : tickers) {
            if (tick.equals(ticker))
                return ticker;
        }
        return null;
    }
}


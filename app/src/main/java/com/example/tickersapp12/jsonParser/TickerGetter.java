package com.example.tickersapp12.jsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//https://mboum.com/api/v1/qu/quote/?symbol=AAPL,F&apikey=OnTzYmu6BOQAsducd9Qvx4qLvnf9XJtKJ6xv1wa4VtS3e9wLDykN0HCyzvJk
// Загрузка данных из апи
public class TickerGetter {
    private String TICKER_URL;
    private JSONObject jsonObject;
    private String apiKey = "Js3NevXUW27CP1xP0T99e7d7sriJCLxrg9fgQGbS1rpSBE85VKsp2A6VcRs4";

    public TickerGetter() {

    }

    public void loadData(String tickerName) {
        try {
           System.out.println("Loading data");

            changeUrl(tickerName);
            java.net.URL url = new URL(TICKER_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;

            StringBuilder builder = new StringBuilder();

            while ((line = br.readLine()) != null) builder.append(line);
            String jsonContent = builder.toString();

            JSONArray jsonArray = new JSONArray(jsonContent);
            jsonObject = new JSONObject(jsonArray.getString(0));
            System.out.println("Loaded");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void changeUrl(String tickerName) {
        TICKER_URL = "https://mboum.com/api/v1/qu/quote/?symbol=" + tickerName + ",F&apikey=" + apiKey;
    }

    public String getNameByTicker() throws JSONException {
         return jsonObject.getString("longName");
    }

    public String getPriceByTicker() throws JSONException {
        return jsonObject.getString("regularMarketPrice");
    }

    public String getChangePercent() throws JSONException {
        return jsonObject.getString("regularMarketChangePercent") + "%";
    }
}

package com.example.mynasaapp.jsonParser;

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
//https://mboum.com/api/v1/qu/quote/?symbol=AAPL,F&apikey=T4sGPqNq58PHGgh92K0gpjGylaQgjA0aBXonwb01v3TfodoC5nnk9hHgCJuy

public class TickerGetter {
    private String TICKER_URL;
    private JSONObject jsonObject;

    public TickerGetter() {

    }

    public void loadData(String tickerName) {
        try {

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

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void changeUrl(String tickerName) {
        TICKER_URL = "https://mboum.com/api/v1/qu/quote/?symbol=" + tickerName + ",F&apikey=YFAcFuRKFHBuGIDQjTDTMEvZsk6OElZVWxxB6tN2cQUBOsXjj8MGcCgeK5A5";
    }

    public String getNameByTicker() throws JSONException {
        return jsonObject.getString("longName");
    }

    public String getPriceByTicker() throws JSONException {
        return jsonObject.getString("regularMarketPrice");
    }
}

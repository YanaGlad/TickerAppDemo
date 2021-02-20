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

public class TickerGetter {

    public void getNameByTicker() {
        try {

            //https://mboum.com/api/v1/qu/quote/?symbol=AAPL,F&apikey=T4sGPqNq58PHGgh92K0gpjGylaQgjA0aBXonwb01v3TfodoC5nnk9hHgCJuy
            String TICKER_URL = "https://mboum.com/api/v1/qu/quote/?symbol=MGNI,F&apikey=T4sGPqNq58PHGgh92K0gpjGylaQgjA0aBXonwb01v3TfodoC5nnk9hHgCJuy";
            java.net.URL apod = new URL(TICKER_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) apod.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;

            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
                System.out.println(line);
            }
            String jsonContent = builder.toString();

            JSONArray jsonArray = new JSONArray(jsonContent);

            System.out.println("LOOOK");
            JSONObject jsonObject = new JSONObject(jsonArray.getString(0));
            System.out.println(jsonObject);
            System.out.println("NAME:");
            System.out.println(jsonObject.getString("longName"));
//            JSONArray values = new JSONArray(jsonObject.getString("quotes")); //Все тикеры
//
//            System.out.println("This!!!!!!!!!!!!");
//            int i = 0;
//            System.out.println(values.get(i));
//            while (i<158){
//                System.out.print(values.get(i) + " ");
//                i++;
//            }

//------------------------------------------------------------------------------------------------------

//            String TICKER_URL = "https://mboum.com/api/v1/tr/trending?apikey=T4sGPqNq58PHGgh92K0gpjGylaQgjA0aBXonwb01v3TfodoC5nnk9hHgCJuy";
//            java.net.URL apod = new URL(TICKER_URL);
//            HttpURLConnection urlConnection = (HttpURLConnection) apod.openConnection();
//
//            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//            BufferedReader br = new BufferedReader(new InputStreamReader(in));
//
//            String line;
//
//            StringBuilder builder = new StringBuilder();
//            while ((line = br.readLine()) != null) {
//                builder.append(line);
//                System.out.println(line);
//            }
//            String jsonContent = builder.toString();
//
//            JSONArray jsonArray = new JSONArray(jsonContent);
//
//            String quotes = "count";
//
//            System.out.println("LOOOK");
//            JSONObject jsonObject = new JSONObject(jsonArray.getString(0));
//            JSONArray values = new JSONArray(jsonObject.getString("quotes")); //Все тикеры
//
//            System.out.println("This!!!!!!!!!!!!");
//            int i = 0;
//            System.out.println(values.get(i));
//            while (i<158){
//                System.out.print(values.get(i) + " ");
//                i++;
//            }
//
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

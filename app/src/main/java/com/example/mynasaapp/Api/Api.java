package com.example.mynasaapp.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
// / ?
    @GET("/api/v1/qu/quote/")
    Call<ResponseData> getDataCountry(@Query("symbol") String symbol);

}

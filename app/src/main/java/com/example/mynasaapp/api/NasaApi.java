package com.example.mynasaapp.api;

import com.example.mynasaapp.models.Date;
import com.example.mynasaapp.models.Photo;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NasaApi {
    @GET("natural/all")
    Single<List<Date>> getDatesWithPhoto();

    @GET("natural/date/{date}")
    Single<List<Photo>> getPhotosForDate(@Path("date") String date);
}

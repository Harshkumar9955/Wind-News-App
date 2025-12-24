package com.projectinteract.wind;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {
    @GET("news")
    Call<JsonObject> getNews(
            @Query("apikey") String apiKey,
            @Query("country") String country,
            @Query("q") String query
    );

    @GET("news")
    Call<JsonObject> getNewsByCategory(
            @Query("apikey") String apiKey,
            @Query("category") String category,
            @Query("language") String language



    );
    @GET("news")
    Call<JsonObject> searchNews(
            @Query("apikey") String apiKey,
            @Query("q") String query,
            @Query("language") String language
    );


}


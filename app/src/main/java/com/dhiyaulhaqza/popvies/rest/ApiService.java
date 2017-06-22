package com.dhiyaulhaqza.popvies.rest;

import com.dhiyaulhaqza.popvies.model.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dhiyaulhaqza on 6/19/17.
 */

public interface ApiService {

    @GET("movie/{sort_by}")
    Call<Movie> getMovies(@Path("sort_by") String sortBy, @Query("api_key") String apiKey);
}

package com.dhiyaulhaqza.popvies.rest;

import com.dhiyaulhaqza.popvies.features.home.model.Movie;
import com.dhiyaulhaqza.popvies.features.detail.model.Trailer;
import com.dhiyaulhaqza.popvies.features.review.model.Review;

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

    @GET("movie/{movie_id}/videos")
    Call<Trailer> getMovieTrailer(@Path("movie_id") String movieId,
                                  @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<Review> getMovieReviews(@Path("movie_id") String movieId,
                                 @Query("api_key") String apiKey);

    // COMPLETED: TASK(1) Buat trailer recyclerview dan custom itemnya
    // COMPLETED: TASK(2) Buat model trailer
    // COMPLETED: TASK(3) Buat service movie
    // TODO: TASK(4) Buat recyclerview adapter trailer
    // TODO: TASK(5) Set recyclerview di detailActivity

    // TODO: TASK(1) Buat reviews Activityrecyclerview dan custom itemnya
    // TODO: TASK(2) Buat model reviews
    // TODO: TASK(3) Buat service reviews
    // TODO: TASK(4) Buat Tombol view reviews di Detail
    // TODO: TASK(5) Buat recyclerview adapter reviews
    // TODO: TASK(6) Set recyclerview di detailActivity
}

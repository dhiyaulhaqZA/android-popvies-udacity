package com.dhiyaulhaqza.popvies.features.review.presenter;

import com.dhiyaulhaqza.popvies.config.ApiCfg;
import com.dhiyaulhaqza.popvies.features.home.model.Movie;
import com.dhiyaulhaqza.popvies.features.review.model.Review;
import com.dhiyaulhaqza.popvies.rest.ApiClient;
import com.dhiyaulhaqza.popvies.rest.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dhiyaulhaqza on 8/7/17.
 */

public class ReviewPresenter {
    private ReviewView reviewView;

    public ReviewPresenter(ReviewView reviewView) {
        this.reviewView = reviewView;
    }

    public void fetchReviews(String movieId) {
        reviewView.onLoading(true);
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<Review> call = service.getMovieReviews(movieId, ApiCfg.API_KEY);
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                Review review = response.body();
                if (review != null && review.getResults() != null) {
                    reviewView.onLoading(false);
                    reviewView.onResponse(review);
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                reviewView.onLoading(false);
                reviewView.onFailure(t.toString());
            }
        });

    }
}

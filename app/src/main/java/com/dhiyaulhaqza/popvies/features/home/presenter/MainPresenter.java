package com.dhiyaulhaqza.popvies.features.home.presenter;

import android.util.Log;

import com.dhiyaulhaqza.popvies.config.ApiCfg;
import com.dhiyaulhaqza.popvies.features.home.model.Movie;
import com.dhiyaulhaqza.popvies.rest.ApiClient;
import com.dhiyaulhaqza.popvies.rest.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dhiyaulhaqza on 6/19/17.
 */

public class MainPresenter {

    private final MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    public void fetchMovies(String sortBy) {
        mainView.onLoading(true);
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<Movie> call = service.getMovies(sortBy, ApiCfg.API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                if (movie != null && movie.getResults() != null) {
                    mainView.onLoading(false);
                    mainView.onResponse(movie);
                    Log.d("DEBUG", response.raw().request().toString());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                mainView.onLoading(false);
                mainView.onFailure(t.toString());
            }
        });
    }
}

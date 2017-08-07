package com.dhiyaulhaqza.popvies.features.detail.presenter;

import com.dhiyaulhaqza.popvies.config.ApiCfg;
import com.dhiyaulhaqza.popvies.features.detail.model.Trailer;
import com.dhiyaulhaqza.popvies.rest.ApiClient;
import com.dhiyaulhaqza.popvies.rest.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dhiyaulhaqza on 8/7/17.
 */

public class DetailPresenter {
    private DetailView mDetailView;

    public DetailPresenter(DetailView mDetailView) {
        this.mDetailView = mDetailView;
    }

    public void fetchTrailers(String movieId) {
        mDetailView.onLoading(true);
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<Trailer> call = service.getMovieTrailer(movieId, ApiCfg.API_KEY);
        call.enqueue(new Callback<Trailer>() {
            @Override
            public void onResponse(Call<Trailer> call, Response<Trailer> response) {
                Trailer trailer = response.body();
                if (trailer != null && trailer.getResults() != null) {
                    mDetailView.onLoading(false);
                    mDetailView.onResponse(trailer);
                }
            }

            @Override
            public void onFailure(Call<Trailer> call, Throwable t) {
                mDetailView.onLoading(false);
                mDetailView.onFailure(t.toString());
            }
        });
    }
}

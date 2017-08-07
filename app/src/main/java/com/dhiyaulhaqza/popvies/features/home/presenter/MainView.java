package com.dhiyaulhaqza.popvies.features.home.presenter;

import com.dhiyaulhaqza.popvies.features.home.model.Movie;

/**
 * Created by dhiyaulhaqza on 6/19/17.
 */

public interface MainView {

    void onResponse(Movie movie);

    void onFailure(String errMsg);

    void onLoading(boolean isLoading);
}

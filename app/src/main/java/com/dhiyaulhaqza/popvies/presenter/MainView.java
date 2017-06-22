package com.dhiyaulhaqza.popvies.presenter;

import com.dhiyaulhaqza.popvies.model.Results;

import java.util.List;

/**
 * Created by dhiyaulhaqza on 6/19/17.
 */

public interface MainView {
    void onResponse(List<Results> resultses);

    void onFailure(String errMsg);

    void onLoading(boolean isLoading);
}

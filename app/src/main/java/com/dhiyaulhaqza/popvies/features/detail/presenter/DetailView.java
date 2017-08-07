package com.dhiyaulhaqza.popvies.features.detail.presenter;
import com.dhiyaulhaqza.popvies.features.detail.model.Trailer;

/**
 * Created by dhiyaulhaqza on 8/7/17.
 */

public interface DetailView {
    void onResponse(Trailer trailer);

    void onFailure(String errMsg);

    void onLoading(boolean isLoading);
}

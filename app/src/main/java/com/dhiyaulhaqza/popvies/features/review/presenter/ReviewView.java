package com.dhiyaulhaqza.popvies.features.review.presenter;
import com.dhiyaulhaqza.popvies.features.review.model.Review;

/**
 * Created by dhiyaulhaqza on 8/7/17.
 */

public interface ReviewView {
    void onResponse(Review review);

    void onFailure(String errMsg);

    void onLoading(boolean isLoading);
}

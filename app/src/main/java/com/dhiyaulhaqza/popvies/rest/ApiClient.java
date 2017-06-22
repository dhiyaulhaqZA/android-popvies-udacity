package com.dhiyaulhaqza.popvies.rest;

import com.dhiyaulhaqza.popvies.config.ApiCfg;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dhiyaulhaqza on 6/19/17.
 */

public final class ApiClient {

    private static Retrofit retrofit;

    private ApiClient() {}

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiCfg.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } return retrofit;
    }
}

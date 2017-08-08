package com.dhiyaulhaqza.popvies.config;

import com.dhiyaulhaqza.popvies.BuildConfig;

/**
 * Created by dhiyaulhaqza on 6/19/17.
 */

public final class ApiCfg {
    private ApiCfg()    {}

    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/w154/";
    public static final String BASE_BACKDROP_URL = "https://image.tmdb.org/t/p/w500/";
    public static final String BASE_YOUTUBE_WATCH = "https://youtube.com/watch?v=";

    public static final String FAVORITE = "favorite";
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";
}

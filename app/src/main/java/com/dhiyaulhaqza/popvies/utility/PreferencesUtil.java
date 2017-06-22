package com.dhiyaulhaqza.popvies.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dhiyaulhaqza on 6/21/17.
 */

public final class PreferencesUtil {
    private static SharedPreferences sharedPref;
    private static final String PREF_NAME = "movie_pref";

    private static final String KEY_SORT = "sort_by";

    private PreferencesUtil() {
    }

    public static void writeSortMovie(Context context, String value) {
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_SORT, value);
        editor.apply();
    }

    public static String readSortMovie(Context context, String defValue) {
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(KEY_SORT, defValue);
    }
}

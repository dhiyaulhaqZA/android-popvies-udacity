package com.dhiyaulhaqza.popvies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.dhiyaulhaqza.popvies.features.home.model.MovieResults;
import com.dhiyaulhaqza.popvies.data.MovieContract.MovieEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhiyaulhaqza on 8/7/17.
 */

public class MovieDbManager {

    public static void insertFavoriteMovie(Context context, MovieResults movieResults) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movieResults.getId());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, movieResults.getTitle());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, movieResults.getOverview());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE, movieResults.getVote_average());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, movieResults.getRelease_date());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, movieResults.getPoster_path());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_BACKDROP_PATH, movieResults.getBackdrop_path());

        Uri uri = context.getContentResolver().insert(
                MovieContract.MovieEntry.CONTENT_URI, contentValues);

        if (uri != null) {
            Log.d("INSERT", uri.toString());
        }
    }

    public static List<MovieResults> readFavoriteMovies(Cursor cursor) {
        List<MovieResults> resultses = new ArrayList<>();
        long id = -1;
        try {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(MovieEntry._ID));
                    String movieId = cursor.getString(
                            cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_MOVIE_ID));
                    String title = cursor.getString(
                            cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_MOVIE_TITLE));
                    String overview = cursor.getString(
                            cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_MOVIE_OVERVIEW));
                    String voteAverage = cursor.getString(
                            cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE));
                    String backdropPath = cursor.getString(
                            cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_MOVIE_BACKDROP_PATH));
                    String posterPath = cursor.getString(
                            cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_MOVIE_POSTER_PATH));
                    String releaseDate = cursor.getString(
                            cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_MOVIE_RELEASE_DATE));

                    MovieResults movieResults =
                            new MovieResults();

                    movieResults.setId(movieId);
                    movieResults.setTitle(title);
                    movieResults.setOverview(overview);
                    movieResults.setVote_average(voteAverage);
                    movieResults.setBackdrop_path(backdropPath);
                    movieResults.setPoster_path(posterPath);
                    movieResults.setRelease_date(releaseDate);

                    resultses.add(movieResults);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DBManager Error : ", e.toString());
        }
        cursor.close();
        Log.d("READ", "Size : " + resultses.size());
        Log.d("READ", "id : " + id);
        return resultses;
    }

    public static void deleteFavoritedMovie(Context context, String id) {
        Uri uri = MovieEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(id).build();
        context.getContentResolver().delete(uri, null, null);
    }
}

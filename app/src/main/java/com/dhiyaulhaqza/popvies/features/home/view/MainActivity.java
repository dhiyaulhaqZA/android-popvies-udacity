package com.dhiyaulhaqza.popvies.features.home.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.dhiyaulhaqza.popvies.R;
import com.dhiyaulhaqza.popvies.config.ApiCfg;
import com.dhiyaulhaqza.popvies.config.Const;
import com.dhiyaulhaqza.popvies.data.MovieContract;
import com.dhiyaulhaqza.popvies.data.MovieDbManager;
import com.dhiyaulhaqza.popvies.databinding.ActivityMainBinding;
import com.dhiyaulhaqza.popvies.features.home.model.Movie;
import com.dhiyaulhaqza.popvies.features.home.model.MovieResults;
import com.dhiyaulhaqza.popvies.features.home.presenter.MainPresenter;
import com.dhiyaulhaqza.popvies.features.home.presenter.MainView;
import com.dhiyaulhaqza.popvies.utility.PreferencesUtil;
import com.dhiyaulhaqza.popvies.features.detail.view.DetailActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MOVIE_LOADER_ID = 0;
    private Uri mUri;

    private ActivityMainBinding binding;
    private MainPresenter presenter;
    private MovieResults mMovieResults;
    private MovieAdapter adapter;
    private final MovieAdapterClickHandler clickHandler = new MovieAdapterClickHandler() {
        @Override
        public void onAdapterClickHandler(MovieResults results) {
            if (currentSortBy.equals(ApiCfg.FAVORITE)) {
                openDetail(true, results);
            } else {
                mMovieResults = results;
                mUri = MovieContract.MovieEntry.CONTENT_URI;
                mUri = mUri.buildUpon().appendPath(results.getId()).build();
                getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, MainActivity.this);
                Log.d(TAG, "Uri : " + mUri.toString());
            }
        }
    };

    private String currentSortBy;
    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(binding.toolbar);
        setTitle(R.string.app_name);
        setupRv();
        setupSwipeRefresh();
        presenter = new MainPresenter(this);
        currentSortBy = PreferencesUtil.readSortMovie(getApplicationContext(), ApiCfg.POPULAR);

        mUri = MovieContract.MovieEntry.CONTENT_URI;
        if (savedInstanceState == null) {
            if (currentSortBy.equals(ApiCfg.FAVORITE)) { //sis null && favorite
                getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);
            } else { //sis null && !favorite
                presenter.fetchMovies(currentSortBy);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentSortBy.equals(ApiCfg.FAVORITE)) {
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem menuItem;
        if (currentSortBy.equals(ApiCfg.POPULAR)) {
            menuItem = menu.findItem(R.id.action_popular);
        } else if (currentSortBy.equals(ApiCfg.TOP_RATED)){
            menuItem = menu.findItem(R.id.action_top_rated);
        } else {
            menuItem = menu.findItem(R.id.action_favorite);
        }
        menuItem.setChecked(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        item.setChecked(true);
        binding.tvErrorMsg.setVisibility(View.GONE);
        switch (id) {
            case R.id.action_popular:
                editSortPref(ApiCfg.POPULAR);
                return true;
            case R.id.action_top_rated:
                editSortPref(ApiCfg.TOP_RATED);
                return true;
            case R.id.action_favorite:
                editSortPref(ApiCfg.FAVORITE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Const.SAVE_INSTANCE_HOME, mMovie);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Movie movie = savedInstanceState.getParcelable(Const.SAVE_INSTANCE_HOME);
        if (movie != null) {
            addMovies(movie);
        }
    }

    private void editSortPref(String sortBy) {
        Context context = getApplicationContext();
        PreferencesUtil.writeSortMovie(context, sortBy);
        currentSortBy = PreferencesUtil.readSortMovie(context, sortBy);
        if (!currentSortBy.equals(ApiCfg.FAVORITE)) {
            getSupportLoaderManager().destroyLoader(MOVIE_LOADER_ID);
            presenter.fetchMovies(currentSortBy);
        } else {
            mUri = MovieContract.MovieEntry.CONTENT_URI;
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
        }
    }

    private void openDetail(boolean isFavorite, MovieResults results) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Const.DATA, results);
        intent.putExtra(Const.IS_FAVORITE, isFavorite);
        startActivity(intent);
        getSupportLoaderManager().destroyLoader(MOVIE_LOADER_ID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(MOVIE_LOADER_ID);
    }

    private void setupRv() {
        binding.rvMovies.setHasFixedSize(true);
        GridLayoutManager layoutManager;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = new GridLayoutManager(this, 3);
        }
        else{
            layoutManager = new GridLayoutManager(this, 5);
        }
        binding.rvMovies.setLayoutManager(layoutManager);

        adapter = new MovieAdapter(clickHandler);
        binding.rvMovies.setAdapter(adapter);
    }

    private void setupSwipeRefresh() {
        binding.swipeRefreshMovie.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!currentSortBy.equals(ApiCfg.FAVORITE)) {
                    presenter.fetchMovies(currentSortBy);
                }
            }
        });
    }

    private void addMovies(Movie movie) {
        mMovie = movie;
        binding.tvErrorMsg.setVisibility(View.GONE);
        adapter.addMovies(movie.getResults());
    }

    @Override
    public void onResponse(Movie movie) {
        addMovies(movie);
    }

    @Override
    public void onFailure(String errMsg) {
        Log.e(TAG, errMsg);
        binding.rvMovies.setVisibility(View.GONE);
        binding.tvErrorMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoading(boolean isLoading) {
        if (isLoading) {
            // prevent double loading (swiperefersh && pbLoading)
            if (binding.swipeRefreshMovie.isRefreshing()) {
                binding.pbLoading.setVisibility(View.GONE);
            } else {
                binding.pbLoading.setVisibility(View.VISIBLE);
            }

            binding.rvMovies.setVisibility(View.INVISIBLE);
            
            } else {
                binding.swipeRefreshMovie.setRefreshing(false);
                binding.pbLoading.setVisibility(View.GONE);
                binding.rvMovies.setVisibility(View.VISIBLE);
            }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mMovieData = null;

            @Override
            protected void onStartLoading() {
                if (mMovieData != null) {
                    deliverResult(mMovieData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(mUri,
                            null,
                            null,
                            null,
                            null);
                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(Cursor data) {
                mMovieData = data;
                super.deliverResult(data);

            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        List<MovieResults> movieResults = MovieDbManager.readFavoriteMovies(data);
        boolean isSortedByFavorite = currentSortBy.equals(ApiCfg.FAVORITE);
        if (isSortedByFavorite) {
            adapter.addMovies(movieResults);
            mMovie = new Movie();
            mMovie.setResults(movieResults);
            if (movieResults.size() < 1) {
                binding.tvErrorMsg.setVisibility(View.VISIBLE);
            } else {
                binding.tvErrorMsg.setVisibility(View.GONE);
            }
        } else {
            boolean size = movieResults.size() == 1;
            openDetail(size, mMovieResults);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}

package com.dhiyaulhaqza.popvies.features.home.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
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
import com.dhiyaulhaqza.popvies.databinding.ActivityMainBinding;
import com.dhiyaulhaqza.popvies.features.home.model.Movie;
import com.dhiyaulhaqza.popvies.features.home.model.MovieResults;
import com.dhiyaulhaqza.popvies.features.home.presenter.MainPresenter;
import com.dhiyaulhaqza.popvies.features.home.presenter.MainView;
import com.dhiyaulhaqza.popvies.utility.PreferencesUtil;
import com.dhiyaulhaqza.popvies.features.detail.view.DetailActivity;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;
    private MainPresenter presenter;

    private MovieAdapter adapter;
    private final MovieAdapterClickHandler clickHandler = new MovieAdapterClickHandler() {
        @Override
        public void onAdapterClickHandler(MovieResults results) {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(Const.DATA, results);
            startActivity(intent);
        }
    };

    private String currentSortBy;
    private Movie movie;

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

        if (savedInstanceState == null) {
            presenter.fetchMovies(currentSortBy);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem menuItem;
        if (currentSortBy.equals(ApiCfg.POPULAR)) {
            menuItem = menu.findItem(R.id.action_popular);
        } else {
            menuItem = menu.findItem(R.id.action_top_rated);
        }
        menuItem.setChecked(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        item.setChecked(true);
        switch (id) {
            case R.id.action_popular:
                editSortPref(ApiCfg.POPULAR);
                return true;
            case R.id.action_top_rated:
                editSortPref(ApiCfg.TOP_RATED);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Const.SAVE_INSTANCE_HOME, movie);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        addMovies((Movie) savedInstanceState.getParcelable(Const.SAVE_INSTANCE_HOME));
    }

    private void editSortPref(String sortBy) {
        Context context = getApplicationContext();
        PreferencesUtil.writeSortMovie(context, sortBy);
        currentSortBy = PreferencesUtil.readSortMovie(context, sortBy);
        presenter.fetchMovies(currentSortBy);
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
                presenter.fetchMovies(currentSortBy);
            }
        });
    }

    private void addMovies(Movie movie) {
        this.movie = movie;
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
}

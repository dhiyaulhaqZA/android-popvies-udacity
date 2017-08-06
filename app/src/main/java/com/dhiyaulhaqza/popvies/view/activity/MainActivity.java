package com.dhiyaulhaqza.popvies.view.activity;

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
import com.dhiyaulhaqza.popvies.model.Results;
import com.dhiyaulhaqza.popvies.presenter.MainPresenter;
import com.dhiyaulhaqza.popvies.presenter.MainView;
import com.dhiyaulhaqza.popvies.utility.PreferencesUtil;
import com.dhiyaulhaqza.popvies.view.adapter.AdapterClickHandler;
import com.dhiyaulhaqza.popvies.view.adapter.MovieAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;
    private String currentSortBy;
    private MainPresenter presenter;
    private MovieAdapter adapter;
    private final AdapterClickHandler clickHandler = new AdapterClickHandler() {
        @Override
        public void onAdapterClickHandler(Results results) {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Const.RESULT, results);
            intent.putExtra(Const.DATA, bundle);
            startActivity(intent);
        }
    };

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
        presenter.fetchMovies(currentSortBy);
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

    @Override
    public void onResponse(List<Results> resultses) {
        binding.tvErrorMsg.setVisibility(View.GONE);
        adapter.addMovies(resultses);
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

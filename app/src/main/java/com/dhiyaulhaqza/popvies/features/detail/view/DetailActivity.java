package com.dhiyaulhaqza.popvies.features.detail.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.dhiyaulhaqza.popvies.R;
import com.dhiyaulhaqza.popvies.config.ApiCfg;
import com.dhiyaulhaqza.popvies.config.Const;
import com.dhiyaulhaqza.popvies.databinding.ActivityDetailBinding;
import com.dhiyaulhaqza.popvies.features.detail.model.Trailer;
import com.dhiyaulhaqza.popvies.features.detail.model.TrailerResults;
import com.dhiyaulhaqza.popvies.features.detail.presenter.DetailPresenter;
import com.dhiyaulhaqza.popvies.features.detail.presenter.DetailView;
import com.dhiyaulhaqza.popvies.features.home.model.MovieResults;
import com.dhiyaulhaqza.popvies.utility.PicassoImg;

public class DetailActivity extends AppCompatActivity implements DetailView{

    private static final String TAG = DetailActivity.class.getSimpleName();
    private ActivityDetailBinding binding;
    private MovieResults results;
    private DetailPresenter detailPresenter;
    private TrailerAdapter mAdapter;
    private final TrailerAdapterClickHandler clickHandler = new TrailerAdapterClickHandler() {
        @Override
        public void onAdapterClickHandler(TrailerResults results) {
            Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse(
                    ApiCfg.BASE_YOUTUBE_WATCH + results.getKey()));
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        detailPresenter = new DetailPresenter(this);
        setupRv();

        binding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (binding.collapsingToolbar.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(binding.collapsingToolbar)) {
                    //on closed
                    binding.collapsingToolbar.setTitle(getString(R.string.app_name));
                } else {
                    //on opened
                    binding.collapsingToolbar.setTitle("");
                }
            }
        });

        if (!isHasExtra()) return;

        writeUi();
        detailPresenter.fetchTrailers(results.getId());
        Log.d(TAG, results.getId());
    }

    private void setupRv() {
        binding.rvDetailTrailer.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvDetailTrailer.setLayoutManager(layoutManager);
        mAdapter = new TrailerAdapter(clickHandler);
        binding.rvDetailTrailer.setAdapter(mAdapter);
    }

    private void writeUi() {
        binding.tvTitle.setText(results.getTitle());
        binding.tvDate.setText(results.getRelease_date());
        binding.tvRating.setText(results.getVote_average());
        binding.tvSynopsis.setText(results.getOverview());

        PicassoImg.setImage(binding.imgPoster, ApiCfg.BASE_IMG_URL + results.getPoster_path());
        PicassoImg.setImage(binding.imgBackdrop, ApiCfg.BASE_BACKDROP_URL + results.getBackdrop_path());
    }

    private boolean isHasExtra() {
        Intent intent = getIntent();
        if (intent.hasExtra(Const.DATA)) {
            results = intent.getParcelableExtra(Const.DATA);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Trailer trailer) {
        mAdapter.addReviews(trailer.getResults());
    }

    @Override
    public void onFailure(String errMsg) {
        Log.e(TAG, errMsg);
    }

    @Override
    public void onLoading(boolean isLoading) {
        int visibility;
        if (isLoading) {
            visibility = View.GONE;
        } else {
            visibility = View.VISIBLE;
        }

        binding.tvTrailerLabel.setVisibility(visibility);
        binding.rvDetailTrailer.setVisibility(visibility);
    }
}

package com.dhiyaulhaqza.popvies.features.review.view;

import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.dhiyaulhaqza.popvies.R;
import com.dhiyaulhaqza.popvies.config.Const;
import com.dhiyaulhaqza.popvies.databinding.ActivityReviewBinding;
import com.dhiyaulhaqza.popvies.features.home.model.MovieResults;
import com.dhiyaulhaqza.popvies.features.review.model.Review;
import com.dhiyaulhaqza.popvies.features.review.presenter.ReviewPresenter;
import com.dhiyaulhaqza.popvies.features.review.presenter.ReviewView;

public class ReviewActivity extends AppCompatActivity implements ReviewView {

    private static final String TAG = ReviewActivity.class.getSimpleName();
    private ActivityReviewBinding mBinding;
    private ReviewAdapter mAdapter;
    private ReviewPresenter reviewPresenter;
    private MovieResults movieResults;
    private Review review;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_review);
        setSupportActionBar(mBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setupRv();
        reviewPresenter = new ReviewPresenter(this);

        if (!getIntent().hasExtra(Const.MOVIE_EXTRA)) return;

        movieResults = getIntent().getParcelableExtra(Const.MOVIE_EXTRA);
        setTitle(movieResults.getTitle());

        if (savedInstanceState == null) {
            reviewPresenter.fetchReviews(movieResults.getId());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Const.SAVE_INSTANCE_REVIEW, review);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        addReviews((Review) savedInstanceState.getParcelable(Const.SAVE_INSTANCE_REVIEW));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRv() {
        mBinding.rvReview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.rvReview.setLayoutManager(layoutManager);
        mAdapter = new ReviewAdapter();
        mBinding.rvReview.setAdapter(mAdapter);
    }

    private void addReviews(Review review) {
        this.review = review;
        mAdapter.addReviews(review.getResults());
    }

    @Override
    public void onResponse(Review review) {
        addReviews(review);
        if (review.getResults().size() > 0) {
            mBinding.tvEmptyReview.setVisibility(View.GONE);
        } else {
            mBinding.tvEmptyReview.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailure(String errMsg) {
        Log.e(TAG, errMsg);
    }

    @Override
    public void onLoading(boolean isLoading) {
        int visibility;
        if (isLoading) {
            visibility = View.VISIBLE;
        } else {
            visibility = View.GONE;
        }

        mBinding.pbReviewLoading.setVisibility(visibility);
    }
}

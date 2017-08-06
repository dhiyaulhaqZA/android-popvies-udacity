package com.dhiyaulhaqza.popvies.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.dhiyaulhaqza.popvies.R;
import com.dhiyaulhaqza.popvies.config.ApiCfg;
import com.dhiyaulhaqza.popvies.config.Const;
import com.dhiyaulhaqza.popvies.databinding.ActivityDetailBinding;
import com.dhiyaulhaqza.popvies.model.Results;
import com.dhiyaulhaqza.popvies.utility.PicassoImg;

import java.io.Serializable;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private Results results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (isHasExtra())   {
            writeUi();
        }

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
}

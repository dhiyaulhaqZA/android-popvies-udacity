package com.dhiyaulhaqza.popvies.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dhiyaulhaqza.popvies.R;
import com.dhiyaulhaqza.popvies.config.ApiCfg;
import com.dhiyaulhaqza.popvies.databinding.ItemMovieBinding;
import com.dhiyaulhaqza.popvies.model.Results;
import com.dhiyaulhaqza.popvies.utility.PicassoImg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhiyaulhaqza on 6/19/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private ItemMovieBinding binding;
    private final List<Results> resultsList = new ArrayList<>();
    private final AdapterClickHandler callback;

    public MovieAdapter(AdapterClickHandler callback) {
        this.callback = callback;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_movie, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    public void addMovies(List<Results> results) {
        resultsList.clear();
        resultsList.addAll(results);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            Results results = resultsList.get(position);
            if (results != null) {
                binding.tvTitle.setText(results.getTitle());
                binding.tvRating.setText(results.getVote_average());
                PicassoImg.setImage(binding.imgPoster, ApiCfg.BASE_IMG_URL + results.getPoster_path());
            }
        }

        @Override
        public void onClick(View v) {
            callback.onAdapterClickHandler(resultsList.get(getAdapterPosition()));
        }
    }
}

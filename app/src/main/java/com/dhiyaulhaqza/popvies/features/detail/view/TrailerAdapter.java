package com.dhiyaulhaqza.popvies.features.detail.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dhiyaulhaqza.popvies.R;
import com.dhiyaulhaqza.popvies.features.detail.model.TrailerResults;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhiyaulhaqza on 8/7/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private Context mContext;
    private List<TrailerResults> trailerResults = new ArrayList<>();
    private TrailerAdapterClickHandler mCallback;

    public TrailerAdapter(TrailerAdapterClickHandler mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_trailer, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return trailerResults.size();
    }

    public void addReviews(List<TrailerResults> results) {
        trailerResults.clear();
        trailerResults.addAll(results);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvTrailerPosition;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTrailerPosition = (TextView) itemView.findViewById(R.id.tv_item_trailer_title);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            tvTrailerPosition.setText(mContext.getString(R.string.str_trailer)
                    + " " + String.valueOf(position+1)); //Trailer 1 ...
        }

        @Override
        public void onClick(View v) {
            mCallback.onAdapterClickHandler(trailerResults.get(getAdapterPosition()));
        }
    }
}

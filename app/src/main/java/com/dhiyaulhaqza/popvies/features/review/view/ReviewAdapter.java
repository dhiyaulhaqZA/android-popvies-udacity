package com.dhiyaulhaqza.popvies.features.review.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dhiyaulhaqza.popvies.R;
import com.dhiyaulhaqza.popvies.features.review.model.ReviewResults;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhiyaulhaqza on 8/7/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private Context mContext;
    private List<ReviewResults> reviewResultses = new ArrayList<>();

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_review, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return reviewResultses.size();
    }

    public void addReviews(List<ReviewResults> reviews) {
        reviewResultses.clear();
        reviewResultses.addAll(reviews);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuthor;
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_item_review_author);
            tvContent = (TextView) itemView.findViewById(R.id.tv_item_review_content);
        }

        public void bind(int position) {
            ReviewResults results = reviewResultses.get(position);
            if (results != null) {
                tvAuthor.setText(results.getAuthor());
                tvContent.setText(results.getContent());
            }
        }
    }
}

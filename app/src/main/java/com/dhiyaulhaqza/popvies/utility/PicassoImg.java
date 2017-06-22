package com.dhiyaulhaqza.popvies.utility;

import android.widget.ImageView;

import com.dhiyaulhaqza.popvies.R;
import com.squareup.picasso.Picasso;

/**
 * Created by dhiyaulhaqza on 6/19/17.
 */

public final class PicassoImg {
    private PicassoImg()    {}

    public static void setImage(ImageView imageView, String url) {
        Picasso.with(imageView.getContext().getApplicationContext())
                .load(url)
                .placeholder(R.color.colorNoImage)
                .error(R.color.colorNoImage)
                .into(imageView);
    }
}

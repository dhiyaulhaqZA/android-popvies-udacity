package com.dhiyaulhaqza.popvies.features.review.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dhiyaulhaqza on 8/7/17.
 */

public class Review implements Parcelable{
    private String id;

    private List<ReviewResults> results;

    private String page;

    private String total_pages;

    private String total_results;

    protected Review(Parcel in) {
        id = in.readString();
        page = in.readString();
        total_pages = in.readString();
        total_results = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public List<ReviewResults> getResults ()
    {
        return results;
    }

    public void setResults (List<ReviewResults> results)
    {
        this.results = results;
    }

    public String getPage ()
    {
        return page;
    }

    public void setPage (String page)
    {
        this.page = page;
    }

    public String getTotal_pages ()
    {
        return total_pages;
    }

    public void setTotal_pages (String total_pages)
    {
        this.total_pages = total_pages;
    }

    public String getTotal_results ()
    {
        return total_results;
    }

    public void setTotal_results (String total_results)
    {
        this.total_results = total_results;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", results = "+results+", page = "+page+", total_pages = "+total_pages+", total_results = "+total_results+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(page);
        dest.writeString(total_pages);
        dest.writeString(total_results);
    }
}

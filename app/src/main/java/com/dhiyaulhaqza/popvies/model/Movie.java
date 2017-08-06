package com.dhiyaulhaqza.popvies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dhiyaulhaqza on 6/19/17.
 */

public class Movie implements Parcelable
{
    private List<Results> results;

    private String page;

    private String total_pages;

    private String total_results;

    protected Movie(Parcel in) {
        results = in.createTypedArrayList(Results.CREATOR);
        page = in.readString();
        total_pages = in.readString();
        total_results = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public List<Results> getResults ()
    {
        return results;
    }

    public void setResults (List<Results> results)
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
        return "ClassPojo [results = "+results+", page = "+page+", total_pages = "+total_pages+", total_results = "+total_results+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
        dest.writeString(page);
        dest.writeString(total_pages);
        dest.writeString(total_results);
    }
}
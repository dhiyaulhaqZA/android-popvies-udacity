package com.dhiyaulhaqza.popvies.features.detail.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dhiyaulhaqza on 8/7/17.
 */

public class Trailer implements Parcelable
{
    private String id;

    private List<TrailerResults> results;

    protected Trailer(Parcel in) {
        id = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
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

    public List<TrailerResults> getResults ()
    {
        return results;
    }

    public void setResults (List<TrailerResults> results)
    {
        this.results = results;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", results = "+results+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }
}

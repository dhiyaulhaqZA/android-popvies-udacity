package com.dhiyaulhaqza.popvies.features.detail.model;

import java.util.List;

/**
 * Created by dhiyaulhaqza on 8/7/17.
 */

public class Trailer
{
    private String id;

    private List<TrailerResults> results;

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
}

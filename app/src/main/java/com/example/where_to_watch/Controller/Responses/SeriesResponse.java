package com.example.where_to_watch.Controller.Responses;

import com.example.where_to_watch.Models.Movie;
import com.example.where_to_watch.Models.Serie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeriesResponse {
    @SerializedName("results")
    private List<Serie> series;

    public List<Serie> getPopularSeries() {
        return series;
    }
}

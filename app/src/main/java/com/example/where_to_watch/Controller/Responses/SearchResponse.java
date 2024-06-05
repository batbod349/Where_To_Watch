package com.example.where_to_watch.Controller.Responses;

import com.example.where_to_watch.Models.People;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {
    @SerializedName("results")
    private List<Object> results;

    public List<Object> getSearchResult() { return results; }
}

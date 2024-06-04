package com.example.where_to_watch.Models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class WatchProviderResponse {
    @SerializedName("id")
    private Integer id;

    @SerializedName("results")
    private Map<String, CountryWatchProviders> results;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<String, CountryWatchProviders> getResults() {
        return results;
    }

    public void setResults(Map<String, CountryWatchProviders> results) {
        this.results = results;
    }
}


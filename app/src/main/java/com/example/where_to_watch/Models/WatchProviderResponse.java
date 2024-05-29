package com.example.where_to_watch.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WatchProviderResponse {
    @SerializedName("results")
    private List<CountryWatchProviders> countryWatchProviders;
    public List<CountryWatchProviders> getWatchProviders() { return countryWatchProviders; }
}
class CountryWatchProviders {
    @SerializedName("link")
    private String link;

    @SerializedName("flatrate")
    private List<WatchProviders> flatrate;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<WatchProviders> getFlatrate() {
        return flatrate;
    }

    public void setFlatrate(List<WatchProviders> flatrate) {
        this.flatrate = flatrate;
    }
}

package com.example.where_to_watch.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WatchProviders {
    @SerializedName("id")
    private Integer id;
    @SerializedName("provider_name")
    private List<String> providers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getProviders() {
        return providers;
    }

    public void setProviders(List<String> providers) {
        this.providers = providers;
    }
}

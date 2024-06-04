package com.example.where_to_watch.Models;

import com.google.gson.annotations.SerializedName;

public class WatchProviders {
    @SerializedName("provider_id")
    private Integer id;

    @SerializedName("provider_name")
    private String name;

    @SerializedName("logo_path")
    private String logoPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
}

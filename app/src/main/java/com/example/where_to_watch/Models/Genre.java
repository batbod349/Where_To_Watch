package com.example.where_to_watch.Models;

import com.google.gson.annotations.SerializedName;

public class Genre {
    @SerializedName("id")
    Integer idMovie;
    @SerializedName("name")
    String name;

    public Integer getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(Integer idMovie) {
        this.idMovie = idMovie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.example.where_to_watch.Models;

import android.provider.MediaStore;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Genre {
    @SerializedName("id")
    Integer id;
    @SerializedName("name")
    String name;
    @SerializedName("genres")
    private List<Genre> Genres;
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

    public List<Genre> getGenres() {
        return Genres;
    }
}

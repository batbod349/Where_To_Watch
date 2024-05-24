package com.example.where_to_watch.Models;

import com.google.gson.annotations.SerializedName;

public class Genre {
    @SerializedName("id")
    Integer id;
    @SerializedName("name")
    String name;

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
}

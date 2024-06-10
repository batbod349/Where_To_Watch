package com.example.where_to_watch.Controller.Responses;

import com.example.where_to_watch.Models.Movie;
import com.example.where_to_watch.Models.People;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonResponse {
    @SerializedName("results")
    private List<People> people;
    @SerializedName("cast")
    private List<People> acteurs;
    @SerializedName("crew")
    private List<People> ekip;

    public List<People> getSearchPeople() {
        return people;
    }
    public List<People> getMovieCrew() { return ekip; }
    public List<People> getMovieActeurs() { return acteurs; }
}

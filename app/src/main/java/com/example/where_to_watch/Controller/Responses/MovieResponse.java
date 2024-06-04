package com.example.where_to_watch.Controller.Responses;

import com.example.where_to_watch.Models.Movie;
import com.example.where_to_watch.Models.People;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieResponse extends Movie {
    //Récupère la réponse de l'API à partir de la balise 'results'
    @SerializedName("results")
    private List<Movie> movies;
    @SerializedName("cast")
    private List<People> acteurs;
    @SerializedName("crew")
    private List<People> ekip;
    public List<People> getMovieCrew() { return ekip; }
    public List<People> getMovieActeurs() { return acteurs; }
    public List<Movie> getPopularMovies() {
        return movies;
    }
}

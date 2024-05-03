package com.example.where_to_watch.Responses;

import com.example.where_to_watch.Models.Movie;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieResponse extends Movie {
    //Récupère la réponse de l'API à partir de la balise 'results'
    @SerializedName("results")
    private List<Movie> movies;
    public List<Movie> getPopularMovies() {
        return movies;
    }
}

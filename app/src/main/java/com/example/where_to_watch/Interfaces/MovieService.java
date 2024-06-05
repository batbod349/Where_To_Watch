package com.example.where_to_watch.Interfaces;

import com.example.where_to_watch.Controller.Responses.SearchResponse;
import com.example.where_to_watch.Models.Movie;
import com.example.where_to_watch.Controller.Responses.WatchProviderResponse;
import com.example.where_to_watch.Controller.Responses.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
    //Appel à l'API pour renvoyer les films populaires
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key")String apiKey,@Query("language")String language);
    @GET("movie/{movieID}")
    Call<Movie> getMoviesDetails(@Path("movieID")String movieID, @Query("api_key")String apiKey, @Query("language")String language);
    @GET("movie/{movieID}/credits")
    Call<MovieResponse> getMoviesCredits(@Path("movieID")String movieID, @Query("api_key")String apiKey, @Query("language")String language);
    @GET("movie/{movieID}/watch/providers")
    Call<WatchProviderResponse> getWatchProviders(@Path("movieID")String movieID, @Query("api_key")String apiKey);
    @GET("search/multi")
    Call<SearchResponse> getSearchResults(@Query("query")String sQuery, @Query("api_key")String apiKey, @Query("language")String language);
}

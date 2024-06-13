package com.example.where_to_watch.Interfaces;

import com.example.where_to_watch.Controller.Responses.SeriesResponse;
import com.example.where_to_watch.Models.Genre;
import com.example.where_to_watch.Controller.Responses.PersonResponse;
import com.example.where_to_watch.Models.Movie;
import com.example.where_to_watch.Controller.Responses.WatchProviderResponse;
import com.example.where_to_watch.Controller.Responses.MovieResponse;
import com.example.where_to_watch.Models.People;

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
    Call<PersonResponse> getMoviesCredits(@Path("movieID")String movieID, @Query("api_key")String apiKey, @Query("language")String language);
    @GET("movie/{movieID}/watch/providers")
    Call<WatchProviderResponse> getWatchProviders(@Path("movieID")String movieID, @Query("api_key")String apiKey);
    @GET("genre/movie/list")
    Call<Genre> getGenre(@Query("api_key")String apiKey, @Query("language")String language);
    @GET("search/movie")
    Call<MovieResponse> getSearchMovieResults(@Query("query")String sQuery, @Query("api_key")String apiKey, @Query("language")String language);
    @GET("search/person")
    Call<PersonResponse> getSearchPersonResults(@Query("query")String sQuery, @Query("api_key")String apiKey, @Query("language")String language);
    @GET("person/{personID}")
    Call<People> getPeopleDetails(@Path("personID")String personID, @Query("api_key")String apiKey, @Query("language")String language);
    @GET("person/popular")
    Call<PersonResponse> getPopularPeople(@Query("api_key")String apiKey,@Query("lanquage")String language);
    @GET("person/{personID}/movie_credits")
    Call<MovieResponse> getCinematographie(@Path("personID")String movieID, @Query("api_key")String apiKey, @Query("language")String language);
    @GET("tv/popular")
    Call<SeriesResponse> getPopularSeries(@Query("api_key")String apiKey, @Query("language")String language);
}

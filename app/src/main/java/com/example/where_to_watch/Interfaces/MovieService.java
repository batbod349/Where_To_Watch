package com.example.where_to_watch.Interfaces;

import com.example.where_to_watch.Responses.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
    //Appel à l'API pour renvoyer les films populaires
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key")String apiKey,@Query("language")String language);
    @GET("movie/{movieID}")
    Call<MovieResponse> getMoviesDetails(@Path("movieID")Integer movieID, @Query("api_key")String apiKey, @Query("language")String language);
}

package com.example.where_to_watch.Vue;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.where_to_watch.Controller.Adapteur.GenreMoviesAdapter;
import com.example.where_to_watch.Controller.Adapteur.GridSpacingItemDecoration;
import com.example.where_to_watch.Controller.Adapteur.MovieAdapter;
import com.example.where_to_watch.Controller.Responses.MovieResponse;
import com.example.where_to_watch.Controller.RetrofitClient;
import com.example.where_to_watch.Interfaces.MovieService;
import com.example.where_to_watch.Models.Genre;
import com.example.where_to_watch.R;
import com.example.where_to_watch.Models.Movie; // Adjust the import based on your package structure

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GenreMoviesView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GenreMoviesAdapter movieAdapter; // Adjust the adapter based on your implementation
    private List<Movie> movieList; // This should be populated with movies of the selected genre

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_movie_view);

        // Retrieve the genre from the intent
        Intent intent = getIntent();
        int selectedGenreId = intent.getIntExtra("genre_id", -1);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Ajouter un LayoutManager
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spacingInPixels));

        // Initialize movie list and adapter
        movieList = new ArrayList<>();
        movieAdapter = new GenreMoviesAdapter(this, movieList); // Adjust constructor based on your implementation
        recyclerView.setAdapter(movieAdapter);

        // Load movies of the selected genre (implement your own method to fetch movies)
        loadMoviesByGenre(selectedGenreId);
    }

    private void loadMoviesByGenre(int genreId) {

        Retrofit retrofit = RetrofitClient.getClient();
        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieResponse> call = movieService.getMoviesByGenre("d85ec7da27477ca0d57dfd8ffd9fd94d", genreId) ;

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    List<Movie> movies = response.body().getPopularMovies();
                    movieList.addAll(movies);
                    movieAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(GenreMoviesView.this, "Failed to fetch movies", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(GenreMoviesView.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


package com.example.where_to_watch.Models;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.where_to_watch.Interfaces.MovieService;
import com.example.where_to_watch.R;
import com.example.where_to_watch.Responses.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PopularMovieView extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieService movieService;

    int counter = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_popular_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recyclerView);

        // Créer une instance de MovieService en utilisant Retrofit
        Retrofit retrofit = RetrofitClient.getClient();
        movieService = retrofit.create(MovieService.class);

        // Appeler la méthode pour récupérer les films populaires
        Call<MovieResponse> call = movieService.getPopularMovies("d85ec7da27477ca0d57dfd8ffd9fd94d","fr-FR");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    //Récupère le contenu de la réponse
                    MovieResponse movieResponse = response.body();
                    //Récupère les différernts films contenu dans la réponse pour les ranger dans une List
                    List<Movie> movies = movieResponse.getPopularMovies();
                    //Créer et initialise notre adapteur avec les films
                    MovieAdapter adapter = new MovieAdapter(movies);
                    //Relie le recyclerView à l'adapter
                    recyclerView.setAdapter(adapter);
                    //Notifie l'adapter qu'il y a eu des ajouts
                    adapter.notifyItemInserted(movies.size()-1);

                } else {
                    // Gérer les erreurs
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                System.out.println("Erreur lors de la récupération des films : " + t.getMessage());
            }
        });
    }
}
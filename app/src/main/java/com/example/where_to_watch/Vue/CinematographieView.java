package com.example.where_to_watch.Vue;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.where_to_watch.Controller.Adapteur.CinematographieAdapter;
import com.example.where_to_watch.Controller.Adapteur.GridSpacingItemDecoration;
import com.example.where_to_watch.Controller.Adapteur.MovieAdapter;
import com.example.where_to_watch.Controller.Responses.MovieResponse;
import com.example.where_to_watch.Controller.RetrofitClient;
import com.example.where_to_watch.Interfaces.MovieService;
import com.example.where_to_watch.Models.Movie;
import com.example.where_to_watch.Models.People;
import com.example.where_to_watch.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CinematographieView extends AppCompatActivity {
    private RecyclerView recyclerView;
    TextView peopleName;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cinematographie_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        peopleName = findViewById(R.id.cinematoNom);
        recyclerView = findViewById(R.id.cinematoRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Ajouter un LayoutManager
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spacingInPixels)); // Ajouter l'ItemDecoration

        // Créer une instance de MovieService en utilisant Retrofit
        Retrofit retrofit = RetrofitClient.getClient();
        MovieService movieService = retrofit.create(MovieService.class);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedPerson")) {
            People person = intent.getParcelableExtra("selectedPerson");

            Call<MovieResponse> call = movieService.getCinematographie(String.valueOf(person.getId()),"d85ec7da27477ca0d57dfd8ffd9fd94d","fr-FR");
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    //Récupère le contenu de la réponse
                    MovieResponse movieResponse = response.body();
                    //Récupère les différents films contenu dans la réponse pour les ranger dans une List
                    List<Movie> movies = movieResponse.getCinematographie();
                    //Créer et initialise notre adapteur avec les films
                    CinematographieAdapter adapter = new CinematographieAdapter(movies);
                    //Relie le recyclerView à l'adapter
                    recyclerView.setAdapter(adapter);
                    //Notifie l'adapter qu'il y a eu des ajouts
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
        }
    }
}
package com.example.where_to_watch.Vue;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.where_to_watch.Controller.MovieAdapter;
import com.example.where_to_watch.Controller.Responses.MovieResponse;
import com.example.where_to_watch.Controller.RetrofitClient;
import com.example.where_to_watch.Interfaces.MovieService;
import com.example.where_to_watch.Models.Movie;
import com.example.where_to_watch.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button searchBut;
    private EditText searchBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        searchBut = findViewById(R.id.searchBut);
        searchBar = findViewById(R.id.searchBar);
        recyclerView = findViewById(R.id.resultsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Créer une instance de MovieService en utilisant Retrofit
        Retrofit retrofit = RetrofitClient.getClient();
        MovieService movieService = retrofit.create(MovieService.class);

        searchBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<MovieResponse> call = movieService.getSearchResults(String.valueOf(searchBar.getText()),"d85ec7da27477ca0d57dfd8ffd9fd94d","fr-FR");
                call.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            //Récupère le contenu de la réponse
                            MovieResponse movieResponse = response.body();
                            //Récupère les différents films contenu dans la réponse pour les ranger dans une List
                            List<Movie> movies = movieResponse.getSearchResult();
                            //Créer et initialise notre adapteur avec les films
                            MovieAdapter adapter = new MovieAdapter(movies);
                            //Relie le recyclerView à l'adapter
                            recyclerView.setAdapter(adapter);
                            //Notifie l'adapter qu'il y a eu des ajouts
                            adapter.notifyItemInserted(movies.size()-1);
                        }
                        else {
                            // Gérer les erreurs
                        }
                    }
                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}
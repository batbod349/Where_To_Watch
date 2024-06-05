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
import com.example.where_to_watch.Controller.Responses.SearchResponse;
import com.example.where_to_watch.Controller.RetrofitClient;
import com.example.where_to_watch.Controller.SearchAdapter;
import com.example.where_to_watch.Interfaces.MovieService;
import com.example.where_to_watch.Models.Movie;
import com.example.where_to_watch.Models.People;
import com.example.where_to_watch.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchView extends AppCompatActivity {

    private RecyclerView movieRecyclerView;
    private RecyclerView peopleRecyclerView;
    private Button searchBut;
    private EditText searchBar;
    private Button movieToggleBut;
    private Button peopleToggleBut;
    private List<String> movieList = new ArrayList<>();
    private List<String> personList = new ArrayList<>();

    private SearchAdapter movieAdapter;
    private SearchAdapter personAdapter;
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
        movieRecyclerView = findViewById(R.id.movieRecyclerView);
        peopleRecyclerView = findViewById(R.id.peopleRecyclerView);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        peopleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieToggleBut = findViewById(R.id.movieToggleButton);
        peopleToggleBut = findViewById(R.id.peopleToggleButton);

        // Créer une instance de MovieService en utilisant Retrofit
        Retrofit retrofit = RetrofitClient.getClient();
        MovieService movieService = retrofit.create(MovieService.class);

        searchBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<SearchResponse> call = movieService.getSearchResults(String.valueOf(searchBar.getText()),"d85ec7da27477ca0d57dfd8ffd9fd94d","fr-FR");
                call.enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        if (response.isSuccessful()) {
                            //Récupère le contenu de la réponse
                            SearchResponse searchResponse = response.body();
                            List<Object> results = searchResponse.getSearchResult();
                            for (Object result : results) {
                                if (result instanceof Movie) {
                                    Movie movie = (Movie) result;
                                    // Traitez le film ici
                                } else if (result instanceof People) {
                                    People people = (People) result;
                                    // Traitez la personne ici
                                }
                            }
                            //Créer et initialise notre adapteur avec les films
                           // MovieAdapter adapter = new MovieAdapter(movies);
                            //Relie le recyclerView à l'adapter
                           // recyclerView.setAdapter(adapter);
                            //Notifie l'adapter qu'il y a eu des ajouts
                           // adapter.notifyItemInserted(movies.size()-1);
                        }
                        else {
                            // Gérer les erreurs
                        }
                    }
                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}
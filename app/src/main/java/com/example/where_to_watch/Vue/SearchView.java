package com.example.where_to_watch.Vue;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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

import com.example.where_to_watch.Controller.Responses.MovieResponse;
import com.example.where_to_watch.Controller.Responses.PersonResponse;
import com.example.where_to_watch.Controller.RetrofitClient;
import com.example.where_to_watch.Controller.SearchMovieAdapter;
import com.example.where_to_watch.Controller.SearchPersonAdapter;
import com.example.where_to_watch.Interfaces.MovieService;
import com.example.where_to_watch.Models.Movie;
import com.example.where_to_watch.Models.People;
import com.example.where_to_watch.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchView extends AppCompatActivity {

    private RecyclerView recyclerViewMovie;
    private RecyclerView recyclerViewPerson;
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
        recyclerViewMovie = findViewById(R.id.movieRecyclerView);
        recyclerViewMovie.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPerson = findViewById(R.id.peopleRecyclerView);
        recyclerViewPerson.setLayoutManager(new LinearLayoutManager(this));

        // Créer une instance de MovieService en utilisant Retrofit
        Retrofit retrofit = RetrofitClient.getClient();
        MovieService movieService = retrofit.create(MovieService.class);

        searchBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<MovieResponse> call = movieService.getSearchMovieResults(String.valueOf(searchBar.getText()),"d85ec7da27477ca0d57dfd8ffd9fd94d","fr-FR");
                call.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            //Récupère le contenu de la réponse
                            MovieResponse movieResponse = response.body();
                            //Récupère les différents films contenu dans la réponse pour les ranger dans une List
                            List<Movie> movies = movieResponse.getSearchMovieResult();
                            //Créer et initialise notre adapteur avec les films
                            SearchMovieAdapter adapter = new SearchMovieAdapter(movies);
                            //Relie le recyclerView à l'adapter
                            recyclerViewMovie.setAdapter(adapter);
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
                Call<PersonResponse> callPerson = movieService.getSearchPersonResults(String.valueOf(searchBar.getText()),"d85ec7da27477ca0d57dfd8ffd9fd94d","fr-FR");
                callPerson.enqueue(new Callback<PersonResponse>() {
                    @Override
                    public void onResponse(Call<PersonResponse> callPerson, Response<PersonResponse> responsePerson) {
                        if (responsePerson.isSuccessful()) {
                            PersonResponse personResponse = responsePerson.body();
                            if (personResponse != null) {
                                List<People> peoples = personResponse.getSearchPeople();
                                SearchPersonAdapter personAdapter = new SearchPersonAdapter(peoples);
                                recyclerViewPerson.setAdapter(personAdapter);
                                personAdapter.notifyDataSetChanged();
                                Log.d(TAG, "People loaded: " + peoples.size());
                            } else {
                                Log.e(TAG, "PersonResponse is null");
                            }
                        } else {
                            Log.e(TAG, "Response unsuccessful: " + responsePerson.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<PersonResponse> callPerson, Throwable t) {

                    }
                });
            }
        });
    }
}
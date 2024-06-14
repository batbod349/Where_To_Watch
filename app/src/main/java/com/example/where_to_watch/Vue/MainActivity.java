package com.example.where_to_watch.Vue;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.where_to_watch.Controller.Adapteur.PopularPeopleAdapter;
import com.example.where_to_watch.Controller.Adapteur.PopularSeriesAdapter;
import com.example.where_to_watch.Controller.Adapteur.TopRatedMovieAdapter;
import com.example.where_to_watch.Controller.Responses.MovieResponse;
import com.example.where_to_watch.Controller.Responses.PersonResponse;
import com.example.where_to_watch.Controller.Responses.SeriesResponse;
import com.example.where_to_watch.Database.MyDatabaseHelper;
import com.example.where_to_watch.Interfaces.MovieService;
import com.example.where_to_watch.Models.Genre;
import com.example.where_to_watch.Controller.RetrofitClient;
import com.example.where_to_watch.Models.Movie;
import com.example.where_to_watch.Models.People;
import com.example.where_to_watch.Models.Serie;
import com.example.where_to_watch.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewFilms;
    private RecyclerView recyclerViewActeurs;
    private RecyclerView recyclerViewSeries;
    Button getPopularMovieButt;
    Button getPopularPersonButt;
    Button getSearch;
    private DrawerLayout drawerLayout;
    private ListView listView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewFilms = findViewById(R.id.recyclerViewFilms);
        recyclerViewActeurs = findViewById(R.id.recyclerViewActeurs);
        recyclerViewSeries = findViewById(R.id.recyclerViewSeries);

        // Configure le RecyclerView avec un LinearLayoutManager horizontal pour les films
        LinearLayoutManager layoutManagerFilms = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFilms.setLayoutManager(layoutManagerFilms);

        // Configure le RecyclerView avec un LinearLayoutManager horizontal pour les acteurs
        LinearLayoutManager layoutManagerActeurs = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewActeurs.setLayoutManager(layoutManagerActeurs);

        // Configure le RecyclerView avec un LinearLayoutManager horizontal pour les acteurs
        LinearLayoutManager layoutManagerSeries = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSeries.setLayoutManager(layoutManagerSeries);

        // Créer une instance de MovieService en utilisant Retrofit
        Retrofit retrofit = RetrofitClient.getClient();
        MovieService movieService = retrofit.create(MovieService.class);

        // Appeler la méthode pour récupérer les films populaires
        Call<MovieResponse> callFilms = movieService.getPopularMovies("d85ec7da27477ca0d57dfd8ffd9fd94d", "fr-FR");
        callFilms.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    // Récupère le contenu de la réponse
                    MovieResponse movieResponse = response.body();
                    // Récupère les différents films contenus dans la réponse pour les ranger dans une liste
                    List<Movie> movies = movieResponse.getPopularMovies();
                    // Créer et initialise notre adaptateur avec les films
                    TopRatedMovieAdapter adapterFilms = new TopRatedMovieAdapter(movies);
                    // Relie le recyclerView à l'adaptateur
                    recyclerViewFilms.setAdapter(adapterFilms);
                } else {
                    // Gérer les erreurs
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                System.out.println("Erreur lors de la récupération des films : " + t.getMessage());
            }
        });

        // Appeler la méthode pour récupérer les acteurs populaires (si applicable)
        Call<PersonResponse> callActeurs = movieService.getPopularPeople("d85ec7da27477ca0d57dfd8ffd9fd94d", "fr-FR");
        callActeurs.enqueue(new Callback<PersonResponse>() {
            @Override
            public void onResponse(Call<PersonResponse> call, Response<PersonResponse> response) {
                if (response.isSuccessful()) {
                    // Récupère le contenu de la réponse
                    PersonResponse personResponse = response.body();
                    // Récupère les différents acteurs contenus dans la réponse pour les ranger dans une liste
                    List<People> actors = personResponse.getSearchPeople();
                    // Créer et initialise notre adaptateur avec les acteurs
                    PopularPeopleAdapter adapterActeurs = new PopularPeopleAdapter(actors);
                    // Relie le recyclerView à l'adaptateur
                    recyclerViewActeurs.setAdapter(adapterActeurs);
                } else {
                    // Gérer les erreurs
                }
            }

            @Override
            public void onFailure(Call<PersonResponse> call, Throwable t) {
                System.out.println("Erreur lors de la récupération des acteurs : " + t.getMessage());
            }
        });

        Call<SeriesResponse> callSeries = movieService.getPopularSeries("d85ec7da27477ca0d57dfd8ffd9fd94d", "fr-FR");
        callSeries.enqueue(new Callback<SeriesResponse>() {
            @Override
            public void onResponse(Call<SeriesResponse> call, Response<SeriesResponse> response) {
                // Récupère le contenu de la réponse
                SeriesResponse seriesResponse = response.body();
                // Récupère les différents acteurs contenus dans la réponse pour les ranger dans une liste
                List<Serie> series = seriesResponse.getPopularSeries();
                // Créer et initialise notre adaptateur avec les acteurs
                PopularSeriesAdapter adapterActeurs = new PopularSeriesAdapter(series);
                // Relie le recyclerView à l'adaptateur
                recyclerViewSeries.setAdapter(adapterActeurs);
            }

            @Override
            public void onFailure(Call<SeriesResponse> call, Throwable t) {

            }
        });

        drawerLayout = findViewById(R.id.drawerlayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        listView = navigationView.findViewById(R.id.listGenre);

        Call<Genre> call = movieService.getGenre("d85ec7da27477ca0d57dfd8ffd9fd94d", "fr-FR");
        call.enqueue(new Callback<Genre>() {

            @Override
            public void onResponse(Call<Genre> call, Response<Genre> response) {
                if (response.isSuccessful()) {
                    List<Genre> genreList = new ArrayList<>();
                    List<Genre> genres = response.body().getGenres();

                    for (Genre genre : genres) {
                        genreList.add(genre);
                    }

                    ArrayAdapter<Genre> adapter = new ArrayAdapter<>(
                            MainActivity.this,
                            android.R.layout.simple_list_item_1,
                            genreList
                    );
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Genre selectedGenre = (Genre) parent.getItemAtPosition(position);
                            drawerLayout.closeDrawer(GravityCompat.START); // Fermer le tiroir après la sélection

                            Intent intent = new Intent(MainActivity.this, GenreMoviesView.class);
                            intent.putExtra("genre_id", selectedGenre.getId());
                            startActivity(intent);
                        }
                    });

                } else {
                    Log.e("MainActivity", "Response not successful: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Genre> call, Throwable t) {
                Log.e("MainActivity", "Network call failed", t);
            }
        });

        getPopularPersonButt =findViewById(R.id.getPopularPeople);
        getPopularMovieButt = findViewById(R.id.getPopularMovie);
        getSearch = findViewById(R.id.getSearch);
        getPopularMovieButt.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v) {
                // Créer un Intent pour ouvrir AutreActivity
                Intent intent = new Intent(MainActivity.this, PopularMovieView.class);
                startActivity(intent);
            }
        });
        getSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer un Intent pour ouvrir AutreActivity
                Intent intent = new Intent(MainActivity.this, SearchView.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.burgermenu) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

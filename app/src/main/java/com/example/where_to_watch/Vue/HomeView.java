    package com.example.where_to_watch.Vue;

    import android.annotation.SuppressLint;
    import android.content.Intent;
    import android.os.Bundle;
    import android.widget.ImageButton;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.where_to_watch.Controller.Adapteur.MovieAdapter;
    import com.example.where_to_watch.Controller.Adapteur.PopularPeopleAdapter;
    import com.example.where_to_watch.Controller.Adapteur.TopRatedMovieAdapter;
    import com.example.where_to_watch.Controller.Responses.MovieResponse;
    import com.example.where_to_watch.Controller.Responses.PersonResponse;
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

    public class HomeView extends AppCompatActivity {

        private RecyclerView recyclerViewFilms;
        private RecyclerView recyclerViewActeurs;
        private ImageButton homeButton;
        private ImageButton starButton;
        private ImageButton favoriteButtonBottom;
        private ImageButton profileButton;

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_home_view);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            recyclerViewFilms = findViewById(R.id.recyclerViewFilms);
            recyclerViewActeurs = findViewById(R.id.recyclerViewActeurs);
            homeButton = findViewById(R.id.homeButton);
            starButton = findViewById(R.id.starButton);
            favoriteButtonBottom = findViewById(R.id.favoriteButtonBottom);
            profileButton = findViewById(R.id.profileButton);

            // Configure le RecyclerView avec un LinearLayoutManager horizontal pour les films
            LinearLayoutManager layoutManagerFilms = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewFilms.setLayoutManager(layoutManagerFilms);

            // Configure le RecyclerView avec un LinearLayoutManager horizontal pour les acteurs
            LinearLayoutManager layoutManagerActeurs = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewActeurs.setLayoutManager(layoutManagerActeurs);

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

            // Configure les clics sur les boutons de navigation en bas
            homeButton.setOnClickListener(v -> {
                // Ajoutez l'action pour le bouton home si nécessaire
            });

            starButton.setOnClickListener(v -> {
                // Ajoutez l'action pour le bouton star
            });

            favoriteButtonBottom.setOnClickListener(v -> {
                //Intent intent = new Intent(HomeView.this, FavorisView.class);
                //startActivity(intent);
            });

            profileButton.setOnClickListener(v -> {
                // Ajoutez l'action pour le bouton de profil
            });
        }
    }
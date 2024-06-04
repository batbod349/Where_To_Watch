package com.example.where_to_watch.Models;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.where_to_watch.Interfaces.MovieService;
import com.example.where_to_watch.MainActivity;
import com.example.where_to_watch.R;
import com.example.where_to_watch.Responses.MovieResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDetailsView extends AppCompatActivity {

    TextView titleTV;
    TextView dureeTV;
    TextView dateSortieTV;
    TextView genreTV;
    TextView acteursTV;
    TextView realsTV;
    TextView synospisTV;
    ImageView poster;
    ListView listProviders;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_details_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        titleTV = findViewById(R.id.TitleTV);
        dureeTV = findViewById(R.id.movieDureeTV);
        dateSortieTV = findViewById(R.id.dateSortieTV);
        genreTV = findViewById(R.id.genreTV);
        acteursTV = findViewById(R.id.acteursTV);
        realsTV = findViewById(R.id.realsTV);
        synospisTV = findViewById(R.id.movieSynopsisTV);
        poster = findViewById(R.id.poster);
        listProviders = findViewById(R.id.listProviders);

        Intent intent = getIntent();

        // Vérifier si des données supplémentaires sont attachées à l'intent
        if (intent != null) {
            // Récupérer le paramètre "movieID"
            String movieID = intent.getStringExtra("movieID");

            // Créer une instance de MovieService en utilisant Retrofit
            Retrofit retrofit = RetrofitClient.getClient();
            MovieService movieService = retrofit.create(MovieService.class);

            // Appeler la méthode pour récupérer les films populaires
            Call<Movie> call = movieService.getMoviesDetails(movieID,"d85ec7da27477ca0d57dfd8ffd9fd94d","fr-FR");
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if (response.isSuccessful()) {
                        String genre = "";
                        Movie movie = response.body();
                        titleTV.setText(movie.getTitle());
                        dureeTV.setText(String.valueOf(movie.getDuree()));
                        dateSortieTV.setText(movie.getDateSortie());
                        synospisTV.setText(movie.getSynopsis());
                        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).into(poster);
                        List<Genre> genres;
                        genres = movie.getGenres();
                        for (int i = 0 ; i < genres.size() ; i++) {
                            genre += genres.get(i).getName() + " ";
                        }
                        genreTV.setText(genre);

                        // Récupération des crédits du film //
                        Call<MovieResponse> callCredits = movieService.getMoviesCredits(String.valueOf(movie.getId()),"d85ec7da27477ca0d57dfd8ffd9fd94d","fr-FR");
                        callCredits.enqueue(new Callback<MovieResponse>() {
                            @Override
                            public void onResponse(Call<MovieResponse> callCredits, Response<MovieResponse> responseCredit) {
                                if (responseCredit.isSuccessful()) {
                                   MovieResponse movieResponse = responseCredit.body();
                                   List<People> listActeurs = movieResponse.getMovieActeurs();
                                   List<People> listCrew = movieResponse.getMovieCrew();

                                   //Récupère la list des acteurs
                                   Integer nbActeur = listActeurs.size();
                                   Integer topNb = nbActeur >= 3 ? 3 : nbActeur; //Ternaire qui retourne 3 si 3 ou plus de people sinon le nombre de people (c'est un if)
                                   String acteurs = "";
                                   for (int i = 0 ; i < topNb ; i++){
                                       acteurs += listActeurs.get(i).getName() + "    ";
                                   }
                                   acteursTV.setText(acteurs);

                                   // Récupère la list des realisateurs
                                   List<People> listReal = listCrew.stream().filter(people -> people.getWork().equals("Directing")).collect(Collectors.toList());
                                   realsTV.setText(listReal.get(0).getName());

                                } else {
                                    Log.e("MovieDetailsView", "Erreur dans la réponse des crédits : " + responseCredit.errorBody());
                                    Toast.makeText(MovieDetailsView.this, "Erreur dans la réponse des crédits : " + responseCredit.message(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<MovieResponse> callCredits, Throwable t) {
                                System.out.println("Erreur lors de la récupération du film : " + t.getMessage());
                            }
                        });

                        Call<WatchProviderResponse> callProviders = movieService.getWatchProviders(String.valueOf(movie.getId()),"d85ec7da27477ca0d57dfd8ffd9fd94d");
                        callProviders.enqueue(new Callback<WatchProviderResponse>() {
                            @Override
                            public void onResponse(Call<WatchProviderResponse> callProviders, Response<WatchProviderResponse> responseProviders) {
                                if (responseProviders.isSuccessful()) {
                                    WatchProviderResponse response = responseProviders.body();
                                    if (response != null) {
                                        // Supposons que vous voulez les providers pour le pays "FR"
                                        CountryWatchProviders countryProviders = response.getResults().get("FR");
                                        //if (countryProviders != null && countryProviders.getFlatrate() != null) {
                                            //List<WatchProviders> providers = countryProviders.getFlatrate();
                                            List<WatchProviders> providers = countryProviders.getBuy();
                                            List<String> providersName = providers.stream().map(WatchProviders::getName).collect(Collectors.toList());

                                            ArrayAdapter<String> adapter = new ArrayAdapter<>(MovieDetailsView.this, android.R.layout.simple_list_item_1, providersName);
                                            listProviders.setAdapter(adapter);
                                        //}
                                    }
                                } else {
                                    Log.e("MovieDetailsView", "Erreur dans la réponse des providers : " + responseProviders.errorBody());
                                    Toast.makeText(MovieDetailsView.this, "Erreur dans la réponse des providers : " + responseProviders.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<WatchProviderResponse> callProviders, Throwable t) {
                                Log.e("MovieDetailsView", "Erreur lors de la récupération des providers : " + t.getMessage());
                            }
                        });
                }
            }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    System.out.println("Erreur lors de la récupération du film : " + t.getMessage());
                    }
                });
        }
    }
}
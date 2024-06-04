package com.example.where_to_watch.Models;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    Button aboBut;
    Button rentBut;
    Button buyBut;

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
        aboBut = findViewById(R.id.aboBut);
        rentBut = findViewById(R.id.rentBut);
        buyBut = findViewById(R.id.buyBut);

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
                        Call<MovieResponse> callCredits = movieService.getMoviesCredits(String.valueOf(movie.getId()), "d85ec7da27477ca0d57dfd8ffd9fd94d", "fr-FR");
                        callCredits.enqueue(new Callback<MovieResponse>() {
                            @Override
                            public void onResponse(Call<MovieResponse> callCredits, Response<MovieResponse> responseCredit) {
                                if (responseCredit.isSuccessful()) {
                                    MovieResponse movieResponse = responseCredit.body();
                                    List<People> listActeurs = movieResponse.getMovieActeurs();
                                    List<People> listCrew = movieResponse.getMovieCrew();

                                    // Récupère la liste des acteurs
                                    Integer nbActeur = listActeurs.size();
                                    Integer topNb = nbActeur >= 3 ? 3 : nbActeur;
                                    String acteurs = "";
                                    for (int i = 0 ; i < topNb ; i++) {
                                        acteurs += listActeurs.get(i).getName() + "    ";
                                    }
                                    acteursTV.setText(acteurs);

                                    // Récupère la liste des réalisateurs
                                    List<People> listReal = listCrew.stream()
                                            .filter(people -> people.getWork().equals("Directing"))
                                            .collect(Collectors.toList());

                                    if (listReal.isEmpty()) {
                                        realsTV.setText("Aucun réalisateur trouvé");
                                    } else {
                                        realsTV.setText(listReal.get(0).getName());
                                    }

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

                        Call<WatchProviderResponse> callProviders = movieService.getWatchProviders(String.valueOf(movie.getId()), "d85ec7da27477ca0d57dfd8ffd9fd94d");
                        callProviders.enqueue(new Callback<WatchProviderResponse>() {
                            @Override
                            public void onResponse(Call<WatchProviderResponse> callProviders, Response<WatchProviderResponse> responseProviders) {
                                if (responseProviders.isSuccessful()) {
                                    WatchProviderResponse response = responseProviders.body();
                                    if (response != null) {
                                        CountryWatchProviders countryProviders = response.getResults().get("FR");
                                        if (countryProviders == null) {
                                            buyBut.setVisibility(View.INVISIBLE);
                                            rentBut.setVisibility(View.INVISIBLE);
                                            aboBut.setVisibility(View.INVISIBLE);
                                            List<String> providersName = new ArrayList<>();
                                            providersName.add("\nAucune plateforme ne possède ce film en France.");
                                            ArrayAdapter<String> adapter = new ArrayAdapter<>(MovieDetailsView.this, android.R.layout.simple_list_item_1, providersName);
                                            listProviders.setAdapter(adapter);
                                        } else {
                                            buyBut.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    updateListView(countryProviders.getBuy(), "\nCe film n'est pas disponible à l'achat");
                                                }
                                            });

                                            rentBut.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    updateListView(countryProviders.getRent(), "\nCe film n'est pas disponible à la location");
                                                }
                                            });

                                            aboBut.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    updateListView(countryProviders.getFlatrate(), "\nCe film n'est pas disponible via un abonnement");
                                                }
                                            });
                                        }
                                    } else {
                                        Log.e("MovieDetailsView", "La réponse des providers est nulle");
                                        Toast.makeText(MovieDetailsView.this, "Impossible de récupérer les données des providers.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.e("MovieDetailsView", "Erreur dans la réponse des providers : " + responseProviders.errorBody());
                                    Toast.makeText(MovieDetailsView.this, "Erreur dans la réponse des providers : " + responseProviders.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<WatchProviderResponse> callProviders, Throwable t) {
                                Log.e("MovieDetailsView", "Erreur lors de la récupération des providers : " + t.getMessage());
                                Toast.makeText(MovieDetailsView.this, "Erreur lors de la récupération des providers : " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
    private void updateListView(List<WatchProviders> providers, String defaultMessage) {
        List<String> providersName = new ArrayList<>();
        if (providers == null || providers.isEmpty()) {
            providersName.add(defaultMessage);
        } else {
            providersName = providers.stream().map(WatchProviders::getName).collect(Collectors.toList());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MovieDetailsView.this, android.R.layout.simple_list_item_1, providersName);
        listProviders.setAdapter(adapter);

        buyBut.setVisibility(View.INVISIBLE);
        rentBut.setVisibility(View.INVISIBLE);
        aboBut.setVisibility(View.INVISIBLE);
    }
}
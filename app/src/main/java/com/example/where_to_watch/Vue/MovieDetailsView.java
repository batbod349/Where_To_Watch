package com.example.where_to_watch.Vue;

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
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.where_to_watch.Controller.Responses.PersonResponse;
import com.example.where_to_watch.Database.MyDatabaseHelper;
import com.example.where_to_watch.Interfaces.MovieService;
import com.example.where_to_watch.Models.CountryWatchProviders;
import com.example.where_to_watch.Models.Genre;
import com.example.where_to_watch.Models.Movie;
import com.example.where_to_watch.Models.People;
import com.example.where_to_watch.Controller.RetrofitClient;
import com.example.where_to_watch.Controller.Responses.WatchProviderResponse;
import com.example.where_to_watch.Models.WatchProviders;
import com.example.where_to_watch.R;
import com.example.where_to_watch.Controller.Responses.MovieResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDetailsView extends AppCompatActivity {

    // Déclaration des variables globales
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
    Button backproviderButt;

    // Déclaration de la liste et de l'adaptateur globalement
    private List<String> providersName = new ArrayList<>();
    private ArrayAdapter<String> adapter;
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
        backproviderButt = findViewById(R.id.backProvidersButt);

        // Initialisation de l'adaptateur
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, providersName);
        listProviders.setAdapter(adapter);

        backproviderButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listProviders.setVisibility(View.INVISIBLE);
                buyBut.setVisibility(View.VISIBLE);
                aboBut.setVisibility(View.VISIBLE);
                rentBut.setVisibility(View.VISIBLE);
                updateListView(null, "", true);
            }
        });

        Intent intent = getIntent();

        // Vérifier si des données supplémentaires sont attachées à l'intent
        if (intent != null) {
            // Récupérer le paramètre "movieID"
            String movieID = intent.getStringExtra("movieID");

            // Exemple dans votre activité ou fragment
            MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
            boolean isFavorite = dbHelper.isFav(Integer.parseInt(movieID), "movie");

            ImageView starIcon = findViewById(R.id.starIcon);
            if (isFavorite) {
                starIcon.setColorFilter(ContextCompat.getColor(this, R.color.star_icon_favorite_color));
            } else {
                starIcon.setColorFilter(ContextCompat.getColor(this, R.color.star_icon_default_color));
            }

            starIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isFavorite = dbHelper.isFav(Integer.parseInt(movieID), "movie");
                    if (isFavorite) {
                        dbHelper.removeFav(Integer.parseInt(movieID), "favoris");
                    } else {
                        dbHelper.newFav(Integer.parseInt(movieID), "movie");
                    }

                    // Mettre à jour la couleur de l'étoile après le changement de favori
                    if (dbHelper.isFav(Integer.parseInt(movieID), "movie")) {
                        starIcon.setColorFilter(ContextCompat.getColor(MovieDetailsView.this, R.color.star_icon_favorite_color));
                    } else {
                        starIcon.setColorFilter(ContextCompat.getColor(MovieDetailsView.this, R.color.star_icon_default_color));
                    }
                }
            });

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
                        Call<PersonResponse> callCredits = movieService.getMoviesCredits(String.valueOf(movie.getId()), "d85ec7da27477ca0d57dfd8ffd9fd94d", "fr-FR");
                        callCredits.enqueue(new Callback<PersonResponse>() {
                            @Override
                            public void onResponse(Call<PersonResponse> callCredits, Response<PersonResponse> responseCredit) {
                                if (responseCredit.isSuccessful()) {
                                    PersonResponse peopleResponse = responseCredit.body();
                                    List<People> listActeurs = peopleResponse.getMovieActeurs();
                                    List<People> listCrew = peopleResponse.getMovieCrew();

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
                            public void onFailure(Call<PersonResponse> callCredits, Throwable t) {
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
                                                    updateListView(countryProviders.getBuy(), "\nCe film n'est pas disponible à l'achat",false);
                                                    backproviderButt.setVisibility(View.VISIBLE);
                                                }
                                            });

                                            rentBut.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    updateListView(countryProviders.getRent(), "\nCe film n'est pas disponible à la location",false);
                                                    backproviderButt.setVisibility(View.VISIBLE);
                                                }
                                            });

                                            aboBut.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    updateListView(countryProviders.getFlatrate(), "\nCe film n'est pas disponible via un abonnement",false);
                                                    backproviderButt.setVisibility(View.VISIBLE);
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

    private void updateListView(List<WatchProviders> providers, String defaultMessage, boolean clearAll) {
        providersName.clear(); // Vider la liste existante

        if (!clearAll) {
            if (providers == null || providers.isEmpty()) {
                providersName.add(defaultMessage);
            } else {
                Set<String> prefixes = new HashSet<>();
                for (WatchProviders provider : providers) {
                    String name = provider.getName();
                    String prefix = name.length() >= 5 ? name.substring(0, 5) : name;
                    if (!prefixes.contains(prefix)) {
                        prefixes.add(prefix);
                        providersName.add(name);
                    }
                }
            }
            buyBut.setVisibility(View.INVISIBLE);
            rentBut.setVisibility(View.INVISIBLE);
            aboBut.setVisibility(View.INVISIBLE);
            listProviders.setVisibility(View.VISIBLE); // Assurez-vous que la ListView est visible
        } else {
            listProviders.setVisibility(View.INVISIBLE);
        }
        adapter.notifyDataSetChanged(); // Notifier l'adaptateur des changements
    }
}

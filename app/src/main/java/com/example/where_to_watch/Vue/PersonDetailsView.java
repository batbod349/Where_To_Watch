package com.example.where_to_watch.Vue;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.where_to_watch.Controller.RetrofitClient;
import com.example.where_to_watch.Interfaces.MovieService;
import com.example.where_to_watch.Models.People;
import com.example.where_to_watch.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PersonDetailsView extends AppCompatActivity {
    TextView personName;
    TextView personBio;
    ImageView personImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_person_details_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        personName = findViewById(R.id.personName);
        personBio = findViewById(R.id.personBio);
        personImage = findViewById(R.id.personImage);

        Intent intent = getIntent();
        if (intent != null) {
            // Récupérer le paramètre "personID"
            String personID = intent.getStringExtra("personID");

            // Créer une instance de MovieService en utilisant Retrofit
            Retrofit retrofit = RetrofitClient.getClient();
            MovieService movieService = retrofit.create(MovieService.class);

            Call<People> call = movieService.getPeopleDetails(personID,"d85ec7da27477ca0d57dfd8ffd9fd94d","fr-FR");
            call.enqueue(new Callback<People>() {
                @Override
                public void onResponse(Call<People> call, Response<People> response) {
                    People person = response.body();
                    personName.setText(person.getName());
                    personBio.setText(person.getBiography());
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + person.getProfileImg()).into(personImage);
                }

                @Override
                public void onFailure(Call<People> call, Throwable t) {

                }
            });

        }


    }

}
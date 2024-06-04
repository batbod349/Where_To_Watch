package com.example.where_to_watch.Models;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.where_to_watch.Interfaces.MovieService;
import com.example.where_to_watch.MainActivity;
import com.example.where_to_watch.R;
import com.example.where_to_watch.Responses.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GenreView extends AppCompatActivity  {

    private ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listGenre);


        Retrofit retrofit = RetrofitClient.getClient();
        MovieService movieService = retrofit.create(MovieService.class);

        Call<Genre> call = movieService.getGenre("d85ec7da27477ca0d57dfd8ffd9fd94d", "fr-FR");

        call.enqueue(new Callback<Genre>() {
            @Override
            public void onResponse(Call<Genre> call, Response<Genre> response) {
                if (response.isSuccessful()) {
                    List<String> genreList = new ArrayList<>();
                    List<Genre> genres = response.body().getGenres();

                    for (int i = 0; i < genres.size(); i++) {
                        genreList.add(genres.get(i).getName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            GenreView.this,
                            android.R.layout.simple_list_item_1,
                            genreList
                    );
                    listView.setAdapter(adapter);
                } else {

                }
            }

            @Override
            public void onFailure(Call<Genre> call, Throwable t) {

            }
        });



    }

}

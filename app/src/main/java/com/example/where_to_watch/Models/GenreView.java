package com.example.where_to_watch.Models;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.where_to_watch.Interfaces.MovieService;
import com.example.where_to_watch.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GenreView extends AppCompatActivity {

    private ListView listView;
    private List<String> genreList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_view);

        listView = findViewById(R.id.listGenre);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Retrofit retrofit = RetrofitClient.getClient();
        MovieService movieService = retrofit.create(MovieService.class);

        Call<Genre> call = movieService.getGenre("d85ec7da27477ca0d57dfd8ffd9fd94d", "en");

        call.enqueue(new Callback<Genre>() {
            @Override
            public void onResponse(Call<Genre> call, Response<Genre> response) {
                if (response.isSuccessful()) {
                    List<Genre> genres = response.body().getGenres();

                    for (Genre genre : genres) {
                        genreList.add(genre.getName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            GenreView.this,
                            android.R.layout.simple_list_item_1,
                            genreList
                    );
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String genre = genreList.get(position);
                            Toast.makeText(GenreView.this, "Genre selected: " + genre, Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Log.e("GenreView", "Response not successful: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Genre> call, Throwable t) {
                Log.e("GenreView", "Network call failed", t);
            }
        });
    }
}

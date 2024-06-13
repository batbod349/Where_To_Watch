package com.example.where_to_watch.Vue;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.where_to_watch.Controller.Adapteur.GridSpacingItemDecoration;
import com.example.where_to_watch.Controller.Adapteur.PopularPeopleAdapter;
import com.example.where_to_watch.Controller.Responses.PersonResponse;
import com.example.where_to_watch.Controller.RetrofitClient;
import com.example.where_to_watch.Interfaces.MovieService;
import com.example.where_to_watch.Models.People;
import com.example.where_to_watch.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PopularPeopleView extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_popular_people_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.popularPeopleRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Ajouter un LayoutManager
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spacingInPixels)); // Ajouter l'ItemDecoration

        // Créer une instance de MovieService en utilisant Retrofit
        Retrofit retrofit = RetrofitClient.getClient();
        MovieService movieService = retrofit.create(MovieService.class);

        Call<PersonResponse> call = movieService.getPopularPeople("d85ec7da27477ca0d57dfd8ffd9fd94d","fr-FR");
        call.enqueue(new Callback<PersonResponse>() {
            @Override
            public void onResponse(Call<PersonResponse> call, Response<PersonResponse> response) {
                PersonResponse personResponse = response.body();
                List<People> peoples = personResponse.getSearchPeople();
                PopularPeopleAdapter adapter = new PopularPeopleAdapter(peoples);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PersonResponse> call, Throwable t) {
                Log.e("Erreur lors de la récupération des personnes : ",t.getMessage());
                System.out.println("Erreur lors de la récupération des personnes : " + t.getMessage());
            }
        });
    }
}
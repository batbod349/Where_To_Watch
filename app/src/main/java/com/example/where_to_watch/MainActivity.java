package com.example.where_to_watch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.where_to_watch.Interfaces.MovieService;
import com.example.where_to_watch.Models.PopularMovieView;

public class MainActivity extends AppCompatActivity {
    Button getPopularMovieButt;
    public MovieService movieService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getPopularMovieButt = findViewById(R.id.getPopularMovie);
        getPopularMovieButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer un Intent pour ouvrir AutreActivity
                Intent intent = new Intent(MainActivity.this, PopularMovieView.class);
                startActivity(intent);
            }
        });
    }
}
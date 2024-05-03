package com.example.where_to_watch.Models;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.where_to_watch.R;

public class MovieDetailsView extends AppCompatActivity {

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

        Intent intent = getIntent();

        // Vérifier si des données supplémentaires sont attachées à l'intent
        if (intent != null) {
            // Récupérer le paramètre "movieID"
            String movieID = intent.getStringExtra("movieID");
            // Faites quelque chose avec movieID, par exemple, affichez-le dans un TextView
            TextView textView = findViewById(R.id.textView_movie_id);
            textView.setText(String.valueOf(movieID));
        }
    }
}
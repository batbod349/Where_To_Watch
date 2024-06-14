package com.example.where_to_watch.Controller.Adapteur;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.where_to_watch.R;
import com.example.where_to_watch.Models.Movie;
import com.example.where_to_watch.Vue.MovieDetailsView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GenreMoviesAdapter extends RecyclerView.Adapter<MovieGenreVH> {


    private Context context;
    private List<Movie> movieList;

    public GenreMoviesAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieGenreVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_people, parent, false);
        return new MovieGenreVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieGenreVH holder, int position) {
        Movie movie = movieList.get(position);
        holder.titleTextView.setText(movie.getTitle());
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), MovieDetailsView.class);
                intent.putExtra("movieID",String.valueOf(movie.getId()));
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}

class MovieGenreVH extends RecyclerView.ViewHolder {
    TextView titleTextView;
    ImageView imageView;

    public MovieGenreVH(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.popularPeopleName);
        imageView = itemView.findViewById(R.id.popularPeopleIV);
    }
}


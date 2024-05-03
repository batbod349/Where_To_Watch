package com.example.where_to_watch.Models;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.where_to_watch.R;
import com.squareup.picasso.Picasso;
import java.util.List;

class MovieVH extends RecyclerView.ViewHolder{

    TextView movieTitle;
    ImageView imageView;
    TextView duree;
    TextView dateSortie;
    TextView synopsis;
    TextView adult;
    private LinearLayout movieLayout;
    private AdapterView.OnItemClickListener listener; // Ajout de l'interface
    private MovieAdapter adapter;

    //Prend une vue (itemView) en paramètre et initialise le ViewHolder avec cette vue. Il initialise également les vues (comme TextView) à l'intérieur de l'élément de vue.
    public MovieVH(@NonNull View itemView) {
        super(itemView);
        movieTitle = itemView.findViewById(R.id.movieTitleTV);
        imageView = itemView.findViewById(R.id.imageView);
        duree = itemView.findViewById(R.id.dureeTV);
        dateSortie = itemView.findViewById(R.id.dateSortieTV);
        synopsis = itemView.findViewById(R.id.synopsisTV);
        adult = itemView.findViewById(R.id.adultTV);
        movieLayout = itemView.findViewById(R.id.movieLayout);
    }

    //Lie l'adaptateur au ViewHolder. Permet d'obtenir une référence à l'adaptateur dans le ViewHolder, ce qui peut être utile pour effectuer des actions sur l'adaptateur depuis le ViewHolder,
    public MovieVH linkAdapter(MovieAdapter adapter){
        this.adapter = adapter;
        return this;
    }

}

public class MovieAdapter extends RecyclerView.Adapter<MovieVH>{

    List<Movie> movies;
    //Prend une liste de films en paramètre et initialise l'adaptateur avec cette liste.
    public MovieAdapter(List<Movie> items) {
        this.movies = items;
    }

    //Crée et retourne une instance de MovieVH qui représente un élément de vue individuel.
    @NonNull
    @Override
    public MovieVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieVH(view).linkAdapter(this);
    }

    //Remplit les données de l'élément de vue représenté par le ViewHolder à la position spécifiée.
    @Override
    public void onBindViewHolder(@NonNull MovieVH holder, int position) {

        holder.movieTitle.setText(movies.get(position).getTitle());
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movies.get(position).getPosterPath()).into(holder.imageView);
        holder.dateSortie.setText(movies.get(position).getDateSortie());
        holder.duree.setText(String.valueOf(movies.get(position).getDuree()));
        holder.synopsis.setText(movies.get(position).getSynopsis());
        holder.adult.setText(String.valueOf(movies.get(position).getAdult()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), MovieDetailsView.class);
                intent.putExtra("movieID",movies.get(v.getId()).getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    //Retourne le nombre total d'éléments dans la liste de films.
    @Override
    public int getItemCount() {
        return movies.size();
    }
}
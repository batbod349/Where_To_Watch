package com.example.where_to_watch.Controller.Adapteur;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.where_to_watch.Models.Movie;
import com.example.where_to_watch.R;
import com.example.where_to_watch.Vue.MovieDetailsView;
import com.example.where_to_watch.Vue.PersonDetailsView;
import com.squareup.picasso.Picasso;
import java.util.List;

class CinematoVH extends RecyclerView.ViewHolder{

    TextView movieName;
    ImageView movieImage;
    private LinearLayout peopleLayout;
    private CinematographieAdapter adapter;

    //Prend une vue (itemView) en paramètre et initialise le ViewHolder avec cette vue. Il initialise également les vues (comme TextView) à l'intérieur de l'élément de vue.
    public CinematoVH(@NonNull View itemView) {
        super(itemView);
        movieName = itemView.findViewById(R.id.popularPeopleName);
        movieImage = itemView.findViewById(R.id.popularPeopleIV);
        peopleLayout = itemView.findViewById(R.id.cardLayoutPopPeople);
    }

    //Lie l'adaptateur au ViewHolder. Permet d'obtenir une référence à l'adaptateur dans le ViewHolder, ce qui peut être utile pour effectuer des actions sur l'adaptateur depuis le ViewHolder,
    public CinematoVH linkAdapter(CinematographieAdapter adapter){
        this.adapter = adapter;
        return this;
    }

}

public class CinematographieAdapter extends RecyclerView.Adapter<CinematoVH>{

    List<Movie> movies;
    //Prend une liste de films en paramètre et initialise l'adaptateur avec cette liste.
    public CinematographieAdapter(List<Movie> items) {
        this.movies = items;
    }

    //Crée et retourne une instance de CinematoVH qui représente un élément de vue individuel.
    @NonNull
    @Override
    public CinematoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_people, parent, false);
        return new CinematoVH(view).linkAdapter(this);
    }

    //Remplit les données de l'élément de vue représenté par le ViewHolder à la position spécifiée.
    @Override
    public void onBindViewHolder(@NonNull CinematoVH holder, int position) {

        if (movies.get(position).getTitle() != null){
            holder.movieName.setText(movies.get(position).getTitle());
        } else {
            holder.movieName.setText("null");
        }
        if (movies.get(position).getPosterPath() != null){
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + movies.get(position).getPosterPath()).into(holder.movieImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                int movieposition = holder.getAdapterPosition(); // Obtenez la position actuelle
                if (movieposition != RecyclerView.NO_POSITION) { // Vérifiez si l'élément existe toujours
                    Intent intent = new Intent(v.getContext(), MovieDetailsView.class);
                    intent.putExtra("movieID",String.valueOf(movies.get(movieposition).getId()));
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    //Retourne le nombre total d'éléments dans la liste de films.
    @Override
    public int getItemCount() {
        return movies.size();
    }
}

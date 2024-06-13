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

import com.example.where_to_watch.Models.Serie;
import com.example.where_to_watch.R;
import com.example.where_to_watch.Vue.MovieDetailsView;
import com.squareup.picasso.Picasso;
import java.util.List;

class SeriesVH extends RecyclerView.ViewHolder{

    TextView seriesTitle;
    ImageView imageView;
    private LinearLayout movieLayout;
    private PopularSeriesAdapter adapter;

    //Prend une vue (itemView) en paramètre et initialise le ViewHolder avec cette vue. Il initialise également les vues (comme TextView) à l'intérieur de l'élément de vue.
    public SeriesVH(@NonNull View itemView) {
        super(itemView);
        seriesTitle = itemView.findViewById(R.id.popularPeopleName);
        imageView = itemView.findViewById(R.id.popularPeopleIV);
    }

    //Lie l'adaptateur au ViewHolder. Permet d'obtenir une référence à l'adaptateur dans le ViewHolder, ce qui peut être utile pour effectuer des actions sur l'adaptateur depuis le ViewHolder,
    public SeriesVH linkAdapter(PopularSeriesAdapter adapter){
        this.adapter = adapter;
        return this;
    }

}

public class PopularSeriesAdapter extends RecyclerView.Adapter<SeriesVH>{

    List<Serie> series;
    //Prend une liste de films en paramètre et initialise l'adaptateur avec cette liste.
    public PopularSeriesAdapter(List<Serie> items) {
        this.series = items;
    }

    //Crée et retourne une instance de SeriesVH qui représente un élément de vue individuel.
    @NonNull
    @Override
    public SeriesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_people, parent, false);
        return new SeriesVH(view).linkAdapter(this);
    }

    //Remplit les données de l'élément de vue représenté par le ViewHolder à la position spécifiée.
    @Override
    public void onBindViewHolder(@NonNull SeriesVH holder, int position) {

        holder.seriesTitle.setText(series.get(position).getName());
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + series.get(position).getPoster_path()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                int seriesposition = holder.getAdapterPosition(); // Obtenez la position actuelle
                if (seriesposition != RecyclerView.NO_POSITION) { // Vérifiez si l'élément existe toujours
                    Intent intent = new Intent(v.getContext(), MovieDetailsView.class);
                    intent.putExtra("seriesID",String.valueOf(series.get(seriesposition).getId()));
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    //Retourne le nombre total d'éléments dans la liste de films.
    @Override
    public int getItemCount() {
        return series.size();
    }
}
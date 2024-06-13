package com.example.where_to_watch.Controller.Adapteur;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.where_to_watch.Models.People;
import com.example.where_to_watch.R;
import com.example.where_to_watch.Vue.PersonDetailsView;
import com.squareup.picasso.Picasso;
import java.util.List;

class SearchPersonVH extends RecyclerView.ViewHolder{

    TextView personName;
    ImageView personImage;
    TextView personWork;
    TextView lastMovie;
    private SearchPersonAdapter adapter;

    //Prend une vue (itemView) en paramètre et initialise le ViewHolder avec cette vue. Il initialise également les vues (comme TextView) à l'intérieur de l'élément de vue.
    public SearchPersonVH(@NonNull View itemView) {
        super(itemView);
        personName = itemView.findViewById(R.id.searchNameActeur);
        personImage = itemView.findViewById(R.id.searchPpPeople);
        personWork = itemView.findViewById(R.id.searchTypePeople);
        lastMovie = itemView.findViewById(R.id.searchKnowFor);
    }

    //Lie l'adaptateur au ViewHolder. Permet d'obtenir une référence à l'adaptateur dans le ViewHolder, ce qui peut être utile pour effectuer des actions sur l'adaptateur depuis le ViewHolder,
    public SearchPersonVH linkAdapter(SearchPersonAdapter adapter){
        this.adapter = adapter;
        return this;
    }

}

public class SearchPersonAdapter extends RecyclerView.Adapter<SearchPersonVH>{

    List<People> peoples;
    //Prend une liste de films en paramètre et initialise l'adaptateur avec cette liste.
    public SearchPersonAdapter(List<People> items) {
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).getProfileImg() == null || items.get(i).getBiography() != null){
                items.remove(i);
            }
        }
        this.peoples = items;
    }

    //Crée et retourne une instance de SearchVH qui représente un élément de vue individuel.
    @NonNull
    @Override
    public SearchPersonVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_people_result, parent, false);
        return new SearchPersonVH(view).linkAdapter(this);
    }

    //Remplit les données de l'élément de vue représenté par le ViewHolder à la position spécifiée.
    @Override
    public void onBindViewHolder(@NonNull SearchPersonVH holder, int position) {
        if (peoples != null && !peoples.isEmpty()) {
            People person = peoples.get(position);
            holder.personName.setText(person.getName());
            holder.personWork.setText(person.getType());
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + person.getProfileImg()).into(holder.personImage);

            if (person.getKnownFor() != null && !person.getKnownFor().isEmpty()) {
                holder.lastMovie.setText(person.getKnownFor().get(0).getTitle());
            } else {
                holder.lastMovie.setText("N/A"); // Valeur par défaut si 'KnownFor' est vide ou null
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    int personPosition = holder.getAdapterPosition(); // Obtenez la position actuelle
                    if (personPosition != RecyclerView.NO_POSITION) { // Vérifiez si l'élément existe toujours
                        Intent intent = new Intent(v.getContext(), PersonDetailsView.class);
                        People selectedPerson = peoples.get(personPosition);
                        intent.putExtra("selectedPerson", selectedPerson);
                        v.getContext().startActivity(intent);
                    }
                }
            });
        } else {
            Log.e("SearchPersonAdapter", "La liste 'peoples' est vide ou null.");
        }
    }


    //Retourne le nombre total d'éléments dans la liste de films.
    @Override
    public int getItemCount() {
        return peoples.size();
    }
}
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
import com.example.where_to_watch.Models.People;
import com.example.where_to_watch.R;
import com.example.where_to_watch.Vue.PersonDetailsView;
import com.squareup.picasso.Picasso;
import java.util.List;

class PeopleVH extends RecyclerView.ViewHolder{

    TextView peopleName;
    ImageView peopleImage;
    private LinearLayout peopleLayout;
    private PopularPeopleAdapter adapter;

    //Prend une vue (itemView) en paramètre et initialise le ViewHolder avec cette vue. Il initialise également les vues (comme TextView) à l'intérieur de l'élément de vue.
    public PeopleVH(@NonNull View itemView) {
        super(itemView);
        peopleName = itemView.findViewById(R.id.popularPeopleName);
        peopleImage = itemView.findViewById(R.id.popularPeopleIV);
        peopleLayout = itemView.findViewById(R.id.cardLayoutPopPeople);
    }

    //Lie l'adaptateur au ViewHolder. Permet d'obtenir une référence à l'adaptateur dans le ViewHolder, ce qui peut être utile pour effectuer des actions sur l'adaptateur depuis le ViewHolder,
    public PeopleVH linkAdapter(PopularPeopleAdapter adapter){
        this.adapter = adapter;
        return this;
    }

}

public class PopularPeopleAdapter extends RecyclerView.Adapter<PeopleVH>{

    List<People> peoples;
    //Prend une liste de films en paramètre et initialise l'adaptateur avec cette liste.
    public PopularPeopleAdapter(List<People> items) {
        this.peoples = items;
    }

    //Crée et retourne une instance de PeopleVH qui représente un élément de vue individuel.
    @NonNull
    @Override
    public PeopleVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_people, parent, false);
        return new PeopleVH(view).linkAdapter(this);
    }

    //Remplit les données de l'élément de vue représenté par le ViewHolder à la position spécifiée.
    @Override
    public void onBindViewHolder(@NonNull PeopleVH holder, int position) {

        if (peoples.get(position).getName() != null){
            holder.peopleName.setText(peoples.get(position).getName());
        } else {
            holder.peopleName.setText("null");
        }
        if (peoples.get(position).getProfileImg() != null){
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + peoples.get(position).getProfileImg()).into(holder.peopleImage);
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
    }

    //Retourne le nombre total d'éléments dans la liste de films.
    @Override
    public int getItemCount() {
        return peoples.size();
    }
}
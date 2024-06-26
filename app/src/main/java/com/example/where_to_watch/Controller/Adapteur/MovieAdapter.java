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
    import com.squareup.picasso.Picasso;
    import java.util.List;

    class MovieVH extends RecyclerView.ViewHolder{

        TextView movieTitle;
        ImageView imageView;
        TextView dateSortie;
        TextView synopsis;
        private LinearLayout movieLayout;
        private MovieAdapter adapter;

        //Prend une vue (itemView) en paramètre et initialise le ViewHolder avec cette vue. Il initialise également les vues (comme TextView) à l'intérieur de l'élément de vue.
        public MovieVH(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movieTitleTV);
            imageView = itemView.findViewById(R.id.imageView);
            dateSortie = itemView.findViewById(R.id.dateSortieTV);
            synopsis = itemView.findViewById(R.id.synopsisTV);
            movieLayout = itemView.findViewById(R.id.cardLayout);
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
            holder.synopsis.setText(movies.get(position).getSynopsis());
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
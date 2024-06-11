    package com.example.where_to_watch.Controller;

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

    class SearchMovieVH extends RecyclerView.ViewHolder{

        TextView movieTitle;
        ImageView imageView;
        TextView dateSortie;
        TextView movieType;
        private SearchMovieAdapter adapter;

        //Prend une vue (itemView) en paramètre et initialise le ViewHolder avec cette vue. Il initialise également les vues (comme TextView) à l'intérieur de l'élément de vue.
        public SearchMovieVH(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.searchTitleMovie);
            imageView = itemView.findViewById(R.id.searchPosterMovie);
            dateSortie = itemView.findViewById(R.id.searchYearMovie);
            movieType = itemView.findViewById(R.id.searchTypeMovie);
        }

        //Lie l'adaptateur au ViewHolder. Permet d'obtenir une référence à l'adaptateur dans le ViewHolder, ce qui peut être utile pour effectuer des actions sur l'adaptateur depuis le ViewHolder,
        public SearchMovieVH linkAdapter(SearchMovieAdapter adapter){
            this.adapter = adapter;
            return this;
        }

    }

    public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieVH>{

        List<Movie> movies;
        //Prend une liste de films en paramètre et initialise l'adaptateur avec cette liste.
        public SearchMovieAdapter(List<Movie> items) {
            this.movies = items;
        }

        //Crée et retourne une instance de SearchVH qui représente un élément de vue individuel.
        @NonNull
        @Override
        public SearchMovieVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_movie_result, parent, false);
            return new SearchMovieVH(view).linkAdapter(this);
        }

        //Remplit les données de l'élément de vue représenté par le ViewHolder à la position spécifiée.
        @Override
        public void onBindViewHolder(@NonNull SearchMovieVH holder, int position) {

            holder.movieTitle.setText(movies.get(position).getTitle());
            holder.dateSortie.setText(movies.get(position).getDateSortie());
            holder.movieType.setText(movies.get(position).getType());
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + movies.get(position).getPosterPath()).into(holder.imageView);
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
package com.example.where_to_watch.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.where_to_watch.Models.Movie;
import com.example.where_to_watch.Models.People;
import com.example.where_to_watch.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class SearchVH extends RecyclerView.ViewHolder{

    TextView movieTitle;
    TextView movieYear;
    TextView movieType;
    TextView peopleName;
    TextView peopleType;
    TextView peopleKnowFor;
    ImageView posterPath;
    ImageView ppPeople;
    private LinearLayout peopleLayout;
    private LinearLayout movieLayout;
    private SearchAdapter adapter;

    public SearchVH(@NonNull View itemView) {
        super(itemView);
        movieTitle = itemView.findViewById(R.id.searchTitleMovie);
        movieYear = itemView.findViewById(R.id.searchYearMovie);
        movieType = itemView.findViewById(R.id.searchTypeMovie);
        peopleName = itemView.findViewById(R.id.searchNameActeur);
        peopleType = itemView.findViewById(R.id.searchTypePeople);
        peopleKnowFor = itemView.findViewById(R.id.searchKnowFor);
        posterPath = itemView.findViewById(R.id.searchPosterMovie);
        ppPeople = itemView.findViewById(R.id.searchPpPeople);
        peopleLayout = itemView.findViewById(R.id.searchPeopleLayout);
        movieLayout = itemView.findViewById(R.id.searchMovieLayout);
    }

    public SearchVH linkAdapter(SearchAdapter adapter){
        this.adapter = adapter;
        return this;
    }

}

public class SearchAdapter extends RecyclerView.Adapter<SearchVH>{

    private static final int VIEW_TYPE_MOVIE = 0;
    private static final int VIEW_TYPE_PEOPLE = 1;

    List<Movie> movies;
    List<People> people;

    public SearchAdapter(List<Movie> movieItems, List<People> peopleItems) {
        this.movies = movieItems;
        this.people = peopleItems;
    }

    @Override
    public int getItemViewType(int position) {
        return position < movies.size() ? VIEW_TYPE_MOVIE : VIEW_TYPE_PEOPLE;
    }

    @NonNull
    @Override
    public SearchVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MOVIE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_movie_result, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_people_result, parent, false);
        }
        return new SearchVH(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchVH holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_MOVIE) {
            Movie movie = movies.get(position);
            holder.movieTitle.setText(movie.getTitle());
            //holder.movieYear.setText(movie.getYear());
            holder.movieType.setText(movie.getType());
            // Additional binding for movie items
        } else {
            People person = people.get(position - movies.size());
            holder.peopleName.setText(person.getName());
            holder.peopleType.setText(person.getType());
            //holder.peopleKnowFor.setText(person.getKnownFor());
            // Additional binding for people items
        }
    }

    @Override
    public int getItemCount() {
        return movies.size() + people.size();
    }

}

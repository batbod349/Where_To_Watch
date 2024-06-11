package com.example.where_to_watch.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie implements Parcelable {
    @SerializedName("title")
    private String title;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private int id;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("runtime")
    private String duree;
    @SerializedName("release_date")
    private String dateSortie;
    @SerializedName("overview")
    private String synopsis;
    @SerializedName("adult")
    private Boolean adult;
    @SerializedName("genres")
    private List<Genre> genres;
    @SerializedName("provider_name")
    private List<String> providers;
    @SerializedName("media_type")
    private String type;

    // Constructeur par défaut
    public Movie() {}

    // Constructeur utilisé par Parcelable
    protected Movie(Parcel in) {
        title = in.readString();
        name = in.readString();
        id = in.readInt();
        posterPath = in.readString();
        duree = in.readString();
        dateSortie = in.readString();
        synopsis = in.readString();
        byte tmpAdult = in.readByte();
        adult = tmpAdult == 0 ? null : tmpAdult == 1;
        type = in.readString();
        providers = in.createStringArrayList();
        genres = in.createTypedArrayList(Genre.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(posterPath);
        dest.writeString(duree);
        dest.writeString(dateSortie);
        dest.writeString(synopsis);
        dest.writeByte((byte) (adult == null ? 0 : adult ? 1 : 2));
        dest.writeString(type);
        dest.writeStringList(providers);
        dest.writeTypedList(genres);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    // Méthodes getter et setter
    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getProviders() {
        return providers;
    }

    public void setProviders(List<String> providers) {
        this.providers = providers;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(String dateSortie) {
        this.dateSortie = dateSortie;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

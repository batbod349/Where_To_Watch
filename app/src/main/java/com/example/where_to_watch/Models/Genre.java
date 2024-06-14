package com.example.where_to_watch.Models;

import android.provider.MediaStore;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Genre implements Parcelable {
    @SerializedName("id")
    private Integer idMovie;
    @SerializedName("name")
    String name;
    @SerializedName("genres")
    private List<Genre> Genres;
    public Integer getId() {
        return idMovie;
    }

    // Constructor for Parcelable
    protected Genre(Parcel in) {
        if (in.readByte() == 0) {
            idMovie = null;
        } else {
            idMovie = in.readInt();
        }
        name = in.readString();
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idMovie == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(idMovie);
        }
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Integer getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(Integer idMovie) {
        this.idMovie = idMovie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Genre> getGenres() {
        return Genres;
    }

    @NonNull
    @Override
    public String toString() {
        return name; // This will display the genre name in the ListView
    }
}

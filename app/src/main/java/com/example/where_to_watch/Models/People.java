package com.example.where_to_watch.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class People {
    @SerializedName("id")
    private Integer idMovie;
    @SerializedName("name")
    private String name;
    @SerializedName("gender")
    private Integer gender;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("deathday")
    private String deathday;
    @SerializedName("profile_path")
    private String profileImg;
    @SerializedName("known_for_department")
    private String work;
    @SerializedName("biography")
    private String biography;
    @SerializedName("media_type")
    private String type;
    @SerializedName("know_for")
    private List<Movie> knowFor;

    public List<Movie> getKnowFor() {
        return knowFor;
    }

    public void setKnowFor(List<Movie> knowFor) {
        this.knowFor = knowFor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}

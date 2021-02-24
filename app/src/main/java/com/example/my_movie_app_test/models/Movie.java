package com.example.my_movie_app_test.models;

public class Movie {

    private boolean isLiked;

    private final Show show;

    //Constructor
    public Movie(Show show) {
        this.show = show;
    }

    public boolean isLiked() {
        return isLiked;
    }

    //Getters&Setters
    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public Show getShow() {
        return show;
    }
}

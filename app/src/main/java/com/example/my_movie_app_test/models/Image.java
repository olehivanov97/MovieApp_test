package com.example.my_movie_app_test.models;

public class Image {

    private String medium;
    private String original;

    //Constructor
    public Image(String medium, String original) {
        this.medium = medium;
        this.original = original;
    }

    //Getters
    public String getMedium() {
        return medium;
    }

    public String getOriginal() {
        return original;
    }
}

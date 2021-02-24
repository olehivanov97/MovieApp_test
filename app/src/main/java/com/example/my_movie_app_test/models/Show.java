package com.example.my_movie_app_test.models;

public class Show {

    private String type;
    private String name;
    private Image image;
    private Rating rating;
    private int id;

    //Constructor
    public Show(String type, String name, Image image, Rating rating, int id) {

        this.type = type;
        this.name = name;
        this.image = image;
        this.rating = rating;
        this.id = id;

    }

    //Getters
    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public Rating getRating() {
        return rating;
    }

    public int getId() {
        return id;
    }
}

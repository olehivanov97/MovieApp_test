package com.example.my_movie_app_test.ui.favourites;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.my_movie_app_test.database.MovieDB;
import com.example.my_movie_app_test.models.Movie;

import java.util.List;

public class FavouritesViewModel extends ViewModel {

    public MutableLiveData<List<Movie>> filteredMovieList;
    private MovieDB movieDB;

    public FavouritesViewModel() {
        filteredMovieList = new MutableLiveData<>();
    }

    public void setMovieDB(MovieDB movieDB) {
        this.movieDB = movieDB;
    }

    public void favouritesList() {
        filteredMovieList.postValue(movieDB.read_all_data());


    }
}
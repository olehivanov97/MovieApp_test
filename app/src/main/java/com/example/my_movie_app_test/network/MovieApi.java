package com.example.my_movie_app_test.network;

import com.example.my_movie_app_test.models.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {
    @GET("search/shows")
    Call<List<Movie>> getMovies(@Query("q") String query);
}

package com.example.my_movie_app_test.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.my_movie_app_test.models.Movie;
import com.example.my_movie_app_test.network.MovieApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeViewModel extends ViewModel {

    //Retrofit builder
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.tvmaze.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public MutableLiveData<List<Movie>> moviesList = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();

    public void searchMovies(String title) {

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<List<Movie>> call = movieApi.getMovies(title);
        call.enqueue(new Callback<List<Movie>>() {

            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                List<Movie> movies = response.body();
                moviesList.postValue(movies);
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                error.postValue("error");
            }

        });

    }

}
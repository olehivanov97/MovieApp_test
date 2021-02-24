package com.example.my_movie_app_test.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_movie_app_test.R;
import com.example.my_movie_app_test.adapters.MoviesAdapter;
import com.example.my_movie_app_test.models.Movie;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    SearchView searchView;
    RecyclerView recyclerView;
    List<Movie> movieList;
    MoviesAdapter moviesAdapter;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel.moviesList.observe(getViewLifecycleOwner(), this::putDataIntoRecyclerView);
        homeViewModel.error.observe(getViewLifecycleOwner(), error -> Snackbar.make(recyclerView, error, 1000));
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.searchbar);
        movieList = new ArrayList<>();
        addChangeListener();
        createAdapter();
    }

    private void createAdapter() {
        moviesAdapter = new MoviesAdapter(requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(moviesAdapter);
    }

    private void putDataIntoRecyclerView(List<Movie> movieList) {
        moviesAdapter.setMoviesList(movieList);
    }

    //Search
    private void addChangeListener() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return onQueryTextChange(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() > 1) {
                    homeViewModel.searchMovies(newText);
                }
                return true;
            }
        });

    }

}
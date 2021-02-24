package com.example.my_movie_app_test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.my_movie_app_test.R;
import com.example.my_movie_app_test.database.MovieDB;
import com.example.my_movie_app_test.models.Movie;
import com.example.my_movie_app_test.models.Show;

import java.util.Collections;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private final Context mContext;
    private List<Movie> moviesList = Collections.emptyList();

    MovieDB movieDB;

    public MoviesAdapter(Context mContext) {
        this.mContext = mContext;
        movieDB = new MovieDB(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        v = layoutInflater.inflate(R.layout.movie_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //Nested objects
        holder.type.setText(moviesList.get(position).getShow().getType());
        holder.title.setText(moviesList.get(position).getShow().getName());
//        holder.ratingBar.setRating(moviesList.get(position).getShow().getRating().getAverage()/2.0f);
        holder.ratingScore.setText(String.valueOf(moviesList.get(position).getShow().getRating().getAverage()));
        if (moviesList.get(position).getShow().getImage() != null) {
            Glide.with(mContext)
                    .load(moviesList.get(position).getShow().getImage().getOriginal())
                    .circleCrop()
                    .into(holder.img);
        }
        holder.isFavourite = isFavourite(position);
        if (holder.isFavourite) {
            holder.likeButton.setImageResource(R.drawable.like_star);
        } else {
            holder.likeButton.setImageResource(R.drawable.unlike_star);
        }

        holder.likeButton.setOnClickListener(v -> {
            if (holder.isFavourite) {
//                holder.likeButton.setImageResource(R.drawable.unlike_star);
                holder.isFavourite = false;
                Show show = moviesList.get(position).getShow();
                movieDB.removeFromTheDatabase(show.getId());
                notifyItemChanged(position);
            } else {
//                holder.likeButton.setImageResource(R.drawable.like_star);
                holder.isFavourite = true;
                Show show = moviesList.get(position).getShow();
                movieDB.insertIntoTheDatabase(show.getName(), show.getImage(), show.getId(), show.getType(), show.getRating().getAverage());
                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void setMoviesList(List<Movie> list) {
        moviesList = list;
        notifyDataSetChanged();
    }

    private boolean isFavourite(int position) {
        List<Movie> dbData = movieDB.read_all_data();
        Movie currentItem = moviesList.get(position);
        for (Movie movie : dbData) {
            if (movie.getShow().getId() == currentItem.getShow().getId()) {
                return true;
            }
        }
        return false;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView type;
        TextView title;
        ImageView img;
        //        RatingBar ratingBar;
        TextView ratingScore;
        ImageButton likeButton;
        boolean isFavourite;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.textView4);
            title = itemView.findViewById(R.id.textView2);
            img = itemView.findViewById(R.id.imageView);
//            ratingBar =  itemView.findViewById(R.id.ratingBar);
            ratingScore = itemView.findViewById(R.id.textView_id);
            likeButton = itemView.findViewById(R.id.likeButton);


        }
    }


}


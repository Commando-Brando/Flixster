package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;
    int POPULAR = 1;
    int BORING = 0;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // tells the adapter which type of view to inflate
    @Override
    public int getItemViewType(int position) {
        System.out.println(movies.get(position).toString());
        if(movies.get(position).getVote_average() > 8.8)
            return POPULAR;
        else
            return BORING;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        MovieAdapter.ViewHolder viewHolder;

        if(viewType == POPULAR){
            View fiveStar = LayoutInflater.from(context).inflate(R.layout.full_backdrop, parent, false);
            return new FiveStarViewHolder(fiveStar);
        } else {
            View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
            return new ViewHolder(movieView);
        }
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder + " + position);

        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the VH
        holder.bind(movie);
    }

    // Return the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    /*

    View Holders below here

     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            // If phone is in landscape
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                // then imageUrl = back drop image
                imageUrl = movie.getBackdropPath();
            } else {
                // else imageUrl = poster image
                imageUrl = movie.getPosterPath();
            }
            Glide.with(context).load(imageUrl).placeholder(R.drawable.loading).into(ivPoster);
        }
    }

    // this view holder is used if a movie is 5 or more stars
    public class FiveStarViewHolder extends MovieAdapter.ViewHolder {

        ImageView ivBackDrop;

        public FiveStarViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBackDrop = itemView.findViewById(R.id.ivBackDrop);
        }

        public void bind(Movie movie) {
            String imageUrl = movie.getBackdropPath();
            Glide.with(context).load(imageUrl).placeholder(R.drawable.loading).into(ivBackDrop);
        }
    }

}

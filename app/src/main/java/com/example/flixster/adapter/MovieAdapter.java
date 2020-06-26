package com.example.flixster.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.activities.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    //Usually involves inflating a layout from XML and returning the ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }


    //Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        //Get the movie at the passed position
        Movie movie = movies.get(position);

        //Bind the movie data into the view holder
        holder.bind(movie);
    }

    //returns the number of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imgUrl;
            int plcholder;
            //if phone is in landscape
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                //imgUrl = backdropPath
                imgUrl = movie.getBackDropPath();
                plcholder = R.drawable.flicks_movie_placeholder;

            } else {
                //imgUrl = poster path
                imgUrl = movie.getPosterPath();
                plcholder = R.drawable.flicks_backdrop_placeholder;
            }

            int radius = 30; //corner radius
            int margin = 10; //crop margin
            Glide.with(context).load(imgUrl).placeholder(plcholder).transform(new RoundedCornersTransformation(radius, margin)).into(ivPoster);

        }

        @Override
        public void onClick(View view) {
            //get the position that was clicked
            int position = getAdapterPosition();
            //ensure that the position actually exists in the view
            if(position != RecyclerView.NO_POSITION) {
                //get the movie at that position in the list
                Movie movie = movies.get(position);
                //Create an intent to display MovieDetailsActivity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                //Pass the movie as an extra serialized
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                //show the activity
                context.startActivity(intent);
            }
        }
    }
}

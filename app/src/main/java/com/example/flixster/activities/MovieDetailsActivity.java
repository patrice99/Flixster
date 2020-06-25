package com.example.flixster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    //the movie details we want to display
    Movie movie;

    //the view Objects
    TextView tvTitle;
    TextView tvOverView;
    RatingBar rbVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //unwrap the movie passed via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", "Showing details for " + movie.getTitle());

        //resolve the view objects
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverView = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);

        //set the text for title and overview
        tvTitle.setText(movie.getTitle());
        tvOverView.setText(movie.getOverview());

        //set the Rating bar
        float voteavg = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteavg > 0 ? voteavg / 2.0f : voteavg);

    }
}
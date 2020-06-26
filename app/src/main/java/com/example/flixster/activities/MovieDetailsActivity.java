package com.example.flixster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.R;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {

    public final String VIDEOS_API_URL = "https://api.themoviedb.org/3/movie/%d/videos" + "?api_key=6d1de0b93ec9c02249d4812fcce98720";

    //the movie details we want to display
    Movie movie;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //unwrap the movie passed via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", "Showing details for " + movie.getTitle());

        //resolve the view objects
        ActivityMovieDetailsBinding bindDetails = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View view = bindDetails.getRoot();
        setContentView(view);

        //set the text for title and overview
        bindDetails.tvTitle.setText(movie.getTitle());
        bindDetails.tvOverview.setText(movie.getOverview());
        bindDetails.tvPopularity.setText("Popularity : " + movie.getPopularity().toString());

        //set the Rating bar
        float voteavg = movie.getVoteAverage().floatValue();
        bindDetails.rbVoteAverage.setRating(voteavg > 0 ? voteavg / 2.0f : voteavg);

        //set the imageView
        String imgUrl;
        int plcholder;
        //if phone is in landscape
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
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
        Glide.with(this).load(imgUrl).placeholder(plcholder).transform(new RoundedCornersTransformation(radius, margin)).into(bindDetails.ivPoster);


        String fullAPIKey = String.format(VIDEOS_API_URL, movie.getId());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(VIDEOS_API_URL, new JsonHttpResponseHandler() {
            String youtubeKey;
            String TAG = "MovieDetailsActivity";

            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject= json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    JSONObject idJsonObj = (JSONObject) results.get(0);
                    youtubeKey = idJsonObj.getString("key");

                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON Exception", e);
                    e.printStackTrace();
                }


            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");

            }
        });

    }
}
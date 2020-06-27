package com.example.flixster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.R;
import com.example.flixster.adapter.MovieAdapter;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Headers;

import static java.util.Collections.sort;

public class MainActivity extends AppCompatActivity {

    MovieAdapter movieAdapter;

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=6d1de0b93ec9c02249d4812fcce98720";
    public static final String TAG = "MainActivity";
    List<Movie> movies;

    public void onSortAction(MenuItem mi) {
        Collections.sort(movies, new Comparator<Movie>() {
            public int compare(Movie v1, Movie v2) {
                return v1.getTitle().compareTo(v2.getTitle());
            }
        });
        movieAdapter.notifyDataSetChanged();

    }


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //using ViewBinding to get ride of boilerplate code
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        movies = new ArrayList<>();

        //Create an Adapter
        movieAdapter = new MovieAdapter(this, movies);

        //Set the adapter on the Recycler View
        binding.rvMovies.setAdapter(movieAdapter);

        //Set a Layout Manager on the recycle view
        binding.rvMovies.setLayoutManager(new LinearLayoutManager(this));


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "results" + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies" + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON Exception", e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "JsonHttpResponseHandler failed, onFailure");
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void onSearchAction(MenuItem mi){

    }

}
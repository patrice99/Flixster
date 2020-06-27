package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    String posterPath;
    String title;
    String overview;
    String backDropPath;
    Double voteAverage;
    Double popularity;
    Integer id;
    String genreField;

    //default, no args constructor for parceler
    public Movie() {}

    //constructor
    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        backDropPath = jsonObject.getString("backdrop_path");
        voteAverage = jsonObject.getDouble("vote_average");
        popularity = jsonObject.getDouble("popularity");
        id = jsonObject.getInt("id");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public static String forGenre(JSONArray genreArray) throws JSONException {
        ArrayList<String> genres = new ArrayList<>();
        for(int i = 0; i < genreArray.length(); i++){
            JSONObject each = (JSONObject) genreArray.get(i);
            genres.add(each.getString("name"));
        }
        String conCatGenres = "";
        for (int i = 0; i < genres.size(); i++) {
            conCatGenres += genres.get(i);
            if (i < genres.size() - 1) conCatGenres += ", ";
        }
        return conCatGenres;
    }




    public String  getPosterPath() {
        //hardcoding the width to be 342, this makes the posterpath full URL
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String  getTitle() {
        return title;
    }

    public String  getOverview() {
        return overview;
    }

    public String getBackDropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backDropPath);
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Double getPopularity() {
        return popularity;
    }

    public Integer getId() {
        return id;
    }


}

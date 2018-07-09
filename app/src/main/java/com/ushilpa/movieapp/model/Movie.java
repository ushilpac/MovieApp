package com.ushilpa.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movie implements Parcelable{

    private static final String JSON_POSTER_KEY="poster_path";
    private static final String JSON_TITLE_KEY="original_title";
    private static final String JSON_OVERVIEW_KEY="overview";
    private static final String JSON_BACKDROP_IMAGE_KEY="backdrop_path";
    private static final String JSON_VOTE_KEY="vote_average";
    private static final String JSON_RELEASE_DATE_KEY="release_date";
    private static final String JSON_ID_KEY="id";
    private static final String IMAGE_BASE_URL="https://image.tmdb.org/t/p/w185/";


    private String mPosterPath;
    private String mOriginalTitle;
    private String mOverview;
    private String mBackdropImgPath;
    private double mRating;
    private String mReleaseDate;
    private int mMovieId;

    private Movie(JSONObject object){

        try{
            this.mPosterPath = object.getString(JSON_POSTER_KEY);
            this.mOriginalTitle = object.getString(JSON_TITLE_KEY);
            this.mOverview = object.getString(JSON_OVERVIEW_KEY);
            this.mBackdropImgPath = object.getString(JSON_BACKDROP_IMAGE_KEY);
            this.mRating = object.getDouble(JSON_VOTE_KEY);
            this.mReleaseDate = object.getString(JSON_RELEASE_DATE_KEY);
            this.mMovieId = object.getInt(JSON_ID_KEY);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static ArrayList<Movie> fromJsonArray(JSONArray jsonObjects){
        ArrayList<Movie> movies = new ArrayList<>();
        for(int i=0; i<jsonObjects.length(); i++){
            try{
                movies.add(new Movie(jsonObjects.getJSONObject(i)));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return movies;
    }

    private Movie(Parcel in){
        mBackdropImgPath = in.readString();
        mOriginalTitle = in.readString();
        mOverview = in.readString();
        mPosterPath = in.readString();
        mReleaseDate = in.readString();
        mRating = in.readDouble();
        mMovieId = in.readInt();

    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeString(mBackdropImgPath);
        out.writeString(mOriginalTitle);
        out.writeString(mOverview);
        out.writeString(mPosterPath);
        out.writeString(mReleaseDate);
        out.writeDouble(mRating);
        out.writeInt(mMovieId);

    }

    @Override
    public int describeContents(){
        return 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel in)
        {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPosterPath(){
        return  IMAGE_BASE_URL+mPosterPath;
    }

    public String getOriginalTitle(){
        return mOriginalTitle;
    }

    public String getOverview(){
        return mOverview;
    }

    /*public String getBackdropImage(){
        return IMAGE_BASE_URL+mBackdropImgPath;
    }*/

    public String getReleaseDate(){
        return mReleaseDate;
    }

    public double getRating(){
        return mRating;
    }

    /*public int getMovieId(){
        return mMovieId;
    }*/

}

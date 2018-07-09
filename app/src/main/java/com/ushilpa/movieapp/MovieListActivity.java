package com.ushilpa.movieapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ushilpa.movieapp.Adapter.MovieAdapter;
import com.ushilpa.movieapp.model.Movie;
import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MovieListActivity extends AppCompatActivity implements MovieAdapter.RecyclerViewItemClickListener,SortDialogFragment.SortOptionDialogListener {

    public static final String MOVIE_INFO_PARCEL ="movie_info_parcel";
    private static final String POPULAR_MOVIE_URL="http://api.themoviedb.org/3/movie/popular";
    private static final String TOP_RATED_MOVIE_URL="https://api.themoviedb.org/3/movie/top_rated";
    private static final String MOVIE_RESULT="results";
    public static final String POPULAR_MOVIE="popular_movie";
    public static final String TOP_RATED_MOVIE="top_rated_movie";
    private static final String SORT_FRAGMENT="sort_fragment";
    private static final String API_KEY="api_key";


    private static final String TAG = MovieListActivity.class.getSimpleName();
    private ArrayList<Movie> mMovies;
    private MovieAdapter mMovieAdapter;
    private String mMovieOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        mMovieOption = POPULAR_MOVIE;

        RecyclerView mRecyclerView =  findViewById(R.id.movie_rv);
        mMovies = new ArrayList<>();
        mMovieAdapter = new MovieAdapter(getApplicationContext(),mMovies,this);
        mRecyclerView.setAdapter(mMovieAdapter);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        fetchMovies();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.id_sort_by:
            {
                FragmentManager fm = getSupportFragmentManager();
                SortDialogFragment sortDialog = SortDialogFragment.newInstance(mMovieOption);
                sortDialog.show(fm,SORT_FRAGMENT);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void fetchMovies(){

        String key =  getString(R.string.movie_db_api_key);
        String url;

        if(mMovieOption.equals(TOP_RATED_MOVIE)){
            url=TOP_RATED_MOVIE_URL;
        }else{
            url=POPULAR_MOVIE_URL;
        }

        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put(API_KEY, key);
        httpClient.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResponse){
                super.onSuccess(statusCode,headers,jsonResponse);


                JSONArray jsonMovieList;
                try{
                    jsonMovieList = jsonResponse.getJSONArray(MOVIE_RESULT);
                    mMovies.addAll(Movie.fromJsonArray(jsonMovieList));
                    mMovieAdapter.notifyDataSetChanged();

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d(TAG, "http request failed");
            }

        });

    }

    @Override
    public void onItemClick(int clickedPosition){

        Movie movie = mMovieAdapter.getItem(clickedPosition);

        Intent  intent = new Intent(this, MovieDetails.class);
        intent.putExtra(MOVIE_INFO_PARCEL, movie);
        startActivity(intent);

    }

    @Override
    public void onFinishSortDialog(int checkedOption){

        if(checkedOption == R.id.popular_movie_option){
            mMovieOption = POPULAR_MOVIE;
            mMovies.clear();
            fetchMovies();
        }else if(checkedOption == R.id.top_rated_movie_option){
            mMovieOption = TOP_RATED_MOVIE;
            mMovies.clear();
            fetchMovies();
        }
    }


}

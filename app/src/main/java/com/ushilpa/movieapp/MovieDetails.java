package com.ushilpa.movieapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ushilpa.movieapp.model.Movie;

import static com.ushilpa.movieapp.MovieListActivity.MOVIE_INFO_PARCEL;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Movie movieParcel = getIntent().getParcelableExtra(MOVIE_INFO_PARCEL);

        populateMovieInfo(movieParcel);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void populateMovieInfo(Movie movie){

        if(movie != null){

            String imageUri = movie.getPosterPath();
            ImageView posterIv = findViewById(R.id.poster_iv);
            Picasso.with(this)
                    .load(imageUri)
                    .fit().centerCrop()
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(posterIv);

            TextView title =  findViewById(R.id.movie_title_tv);
            title.setText(movie.getOriginalTitle());

            TextView releaseDate = findViewById(R.id.release_date_tv);
            String date = "<b>"+"Release Date: "+"</b>"+movie.getReleaseDate();
            releaseDate.setText(Html.fromHtml(date));

            TextView synopsis =  findViewById(R.id.synopsis_tv);
            String overview = "<b>"+ "Synopsis: "+"</b>"+movie.getOverview();
            synopsis.append(Html.fromHtml(overview));

            TextView rating_tv =  findViewById(R.id.rating_tv);
            String rating = String.valueOf(movie.getRating());
            rating_tv.setText(rating);


        }

    }
}

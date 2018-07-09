package com.ushilpa.movieapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.ushilpa.movieapp.R;
import com.ushilpa.movieapp.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private List<Movie> mMovieList;
    private final Context mContext;
    final private RecyclerViewItemClickListener mOnClickListener;


    public interface RecyclerViewItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    public MovieAdapter(Context context, List<Movie> movies,RecyclerViewItemClickListener listener){
        mMovieList = movies;
        mContext = context;
        mOnClickListener = listener;
    }

    public Movie getItem(int position){
        return mMovieList.get(position);
    }

    @Override
    public int getItemCount(){
        return mMovieList.size();
    }

    @Override
    @NonNull
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View movieView = inflater.inflate(R.layout.movie_list_item, viewGroup, false);

        // Return a new holder instance
        return  new MovieViewHolder(movieView);


    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder viewHolder, int position){
        Movie movie = mMovieList.get(position);
        String imageUri = movie.getPosterPath();

        Picasso.with(mContext)
                .load(imageUri)
                .fit().centerCrop()
                .error(R.drawable.placeholder_image)
                .into(viewHolder.mPosterView);


    }



    class MovieViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        private final ImageView mPosterView;

        private MovieViewHolder(View itemView){
            super(itemView);
            mPosterView =  itemView.findViewById(R.id.movie_poster_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view ){
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onItemClick(clickedPosition);
        }


    }
}

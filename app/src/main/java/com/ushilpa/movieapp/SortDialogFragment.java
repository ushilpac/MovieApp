package com.ushilpa.movieapp;

import android.content.Context;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static com.ushilpa.movieapp.MovieListActivity.POPULAR_MOVIE;
import static com.ushilpa.movieapp.MovieListActivity.TOP_RATED_MOVIE;

public class SortDialogFragment extends DialogFragment {

    private static final String MOVIE_OPTION="movie_option";

    private SortOptionDialogListener mListener;
    private String mMovieOption;


    public SortDialogFragment(){

    }

    public static SortDialogFragment newInstance(String movieOption) {
        SortDialogFragment frag = new SortDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_OPTION,movieOption);
        frag.setArguments(bundle);
        return frag;
    }

    // 1. Defines the listener interface with a method passing back data result.
    public interface SortOptionDialogListener  {
        void onFinishSortDialog(int checkedOption);

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mListener = (SortOptionDialogListener)context;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null){
            mMovieOption = bundle.getString(MOVIE_OPTION);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sort_menu_options, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RadioButton mPopularMovie =  view.findViewById(R.id.popular_movie_option);
        RadioButton mTopRatedMovie =  view.findViewById(R.id.top_rated_movie_option);

        if(mMovieOption.equals(POPULAR_MOVIE)){
            mPopularMovie.setChecked(true);
        }else if(mMovieOption.equals(TOP_RATED_MOVIE)){
            mTopRatedMovie.setChecked(true);
        }

        Button cancel_button =  view.findViewById(R.id.cancel_sort_option);
        cancel_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                getDialog().dismiss();
            }
        });

        RadioGroup movieOption =  view.findViewById(R.id.movie_option_rg);
        movieOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                mListener.onFinishSortDialog(checkedId);
                getDialog().dismiss();
            }
        });

    }



}

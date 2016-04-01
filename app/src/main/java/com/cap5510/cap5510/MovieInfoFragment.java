package com.cap5510.cap5510;

import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cap5510.cap5510.api.GetMovieInfo;
import com.cap5510.cap5510.api.GetPeopleMovie;
import com.cap5510.cap5510.api.objects.AsyncTaskInput;
import com.cap5510.cap5510.api.objects.standard_media_objects.Movie;

/**
 * Created by Vega on 3/25/2016.
 */
public class MovieInfoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.content_movie_info, null);

            int traktID = this.getArguments().getInt("traktID");
            Log.d("id?", Integer.toString(traktID));


            new GetMovieInfo().execute(new AsyncTaskInput(this.getActivity(), new Movie(traktID)));
            new GetPeopleMovie().execute(new AsyncTaskInput(this.getActivity(), new Movie(traktID)));

        return root;
    }
}

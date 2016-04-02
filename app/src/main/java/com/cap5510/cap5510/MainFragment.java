package com.cap5510.cap5510;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.cap5510.cap5510.api.GetWatchedTvShows;
import com.cap5510.cap5510.api.objects.AsyncTaskInput;
import com.cap5510.cap5510.api.objects.standard_media_objects.Episode;

/**
 * Created by Vega on 3/22/2016.
 */
public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.content_main, null);

        getActivity().setTitle("Queue");
        TabHost tabHost = (TabHost)root.findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tvEps");
        tabSpec.setContent(R.id.tabEpisodes);
        tabSpec.setIndicator("Episodes");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("movies");
        tabSpec.setContent(R.id.tabMovies);
        tabSpec.setIndicator("movies");
        tabHost.addTab(tabSpec);
        // generateCode();
        AsyncTaskInput input = new AsyncTaskInput(this.getActivity(), this, "");
        new GetWatchedTvShows().execute(input);
        return root;
    }

    public void openEpisode(Episode ep) {
        //Log.d("poster clicked?", id);
        FragmentTransaction tx = this.getActivity().getSupportFragmentManager().beginTransaction();

        Bundle idInfo = new Bundle();
        idInfo.putInt("showID", ep.getID());
        idInfo.putInt("season", ep.getSeason());
        idInfo.putInt("number", ep.getNumber());
        idInfo.putString("poster", ep.getPosterURL());

        EpisodeInfoFragment epInfoFrag = new EpisodeInfoFragment();
        epInfoFrag.setArguments(idInfo);
        tx.remove(this.getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_container))
                .replace(R.id.frame_container, epInfoFrag, "EpisodeInfoFragment")
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

}

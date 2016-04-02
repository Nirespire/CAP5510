package com.cap5510.cap5510;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cap5510.cap5510.api.GetCommentsEpisode;
import com.cap5510.cap5510.api.GetEpisodeImage;
import com.cap5510.cap5510.api.GetEpisodeInfo;
import com.cap5510.cap5510.api.GetPeopleEpisode;
import com.cap5510.cap5510.api.objects.AsyncTaskInput;
import com.cap5510.cap5510.api.objects.standard_media_objects.Episode;
import com.cap5510.cap5510.api.objects.standard_media_objects.Movie;

/**
 * Created by Vega on 3/25/2016.
 */
public class EpisodeInfoFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.content_episode_info, null);

        Episode ep = new Episode(this.getArguments().getInt("showID"));
        ep.setSeason(this.getArguments().getInt("season"));
        ep.setNumber(this.getArguments().getInt("number"));

        AsyncTaskInput async = new AsyncTaskInput(this.getActivity(), ep);

        new GetEpisodeInfo().execute(async);
        new GetEpisodeImage().execute(async);
        new GetPeopleEpisode().execute(async);
        new GetCommentsEpisode().execute(async);

        return root;
    }
}

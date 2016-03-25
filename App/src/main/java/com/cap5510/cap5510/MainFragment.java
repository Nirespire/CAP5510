package com.cap5510.cap5510;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

/**
 * Created by Vega on 3/22/2016.
 */
public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.content_main, null);

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
        return root;
    }

}

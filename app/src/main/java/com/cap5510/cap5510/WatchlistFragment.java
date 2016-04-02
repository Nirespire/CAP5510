package com.cap5510.cap5510;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Vega on 3/25/2016.
 */
public class WatchlistFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.content_watchlist,null);
        getActivity().setTitle("Watchlist");

        Spinner dropdown = (Spinner)root.findViewById(R.id.spinner1);
        String[] items = new String[]{"Watchlist", "Favorites", "DVDs I Own"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        return root;
    }
}

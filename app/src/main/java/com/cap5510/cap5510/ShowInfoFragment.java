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
public class ShowInfoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.content_show_info, null);
        getActivity().setTitle("Show Information");

        Spinner dropdown = (Spinner)root.findViewById(R.id.spinner1);
        String[] items = new String[]{"Season One", "Season Two", "Season Three"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        return root;
    }
}

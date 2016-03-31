package com.cap5510.cap5510;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cap5510.cap5510.api.AddToWatchlistTask;
import com.cap5510.cap5510.api.DeleteRecommendationTask;
import com.cap5510.cap5510.api.DownloadImageTask;
import com.cap5510.cap5510.api.GetRecommendationsTask;
import com.cap5510.cap5510.api.Type;
import com.cap5510.cap5510.api.objects.AsyncTaskInput;
import com.cap5510.cap5510.api.objects.WatchlistItem;

import java.util.ArrayList;

public class RecommendationFragment extends Fragment
{
    private ArrayList<WatchlistItem> currentItems;
    ViewGroup root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = (ViewGroup)inflater.inflate(R.layout.content_recommendation,null);
        currentItems = new ArrayList<>();

        loadRecommendations();
        setOnClickListeners();
        return root;
    }

    private void loadRecommendations(){
        new GetRecommendationsTask().execute(new AsyncTaskInput(this.getActivity(), this, Type.Movie));
        new GetRecommendationsTask().execute(new AsyncTaskInput(this.getActivity(), this, Type.Show));
    }

    private void setOnClickListeners(){
        ImageButton watchlistButton = (ImageButton) root.findViewById(R.id.add_to_watchlist_button);
        ImageButton hideButton = (ImageButton) root.findViewById(R.id.hide_button);
        ImageButton nextButton = (ImageButton) root.findViewById(R.id.next_button);

        watchlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!currentItems.isEmpty()) {
                    Log.e("sanjay", "Add it!");

                    ArrayList<WatchlistItem> w = new ArrayList<>();
                    w.add(currentItems.get(0));

                    new AddToWatchlistTask().execute(new AsyncTaskInput(getActivity(), RecommendationFragment.this, w));

                    currentItems.remove(0);
                    displayFirstItem();
                }
            }
        });

        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentItems.isEmpty()) {
                    Log.e("sanjay", "Hide it!");

                    new DeleteRecommendationTask().execute(new AsyncTaskInput(getActivity(), RecommendationFragment.this, currentItems.get(0)));

                    currentItems.remove(0);
                    displayFirstItem();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentItems.isEmpty()) {
                    Log.e("sanjay", "Skip it!");

                    WatchlistItem temp = currentItems.remove(0);
                    currentItems.add(temp);
                    displayFirstItem();
                }
            }
        });
    }

    public ArrayList<WatchlistItem> getCurrentItems() {
        return currentItems;
    }

    public void addToCurrentItems(ArrayList<WatchlistItem> currentItems) {
        for(WatchlistItem i : currentItems){
            this.currentItems.add(i);
        }
    }

    public void displayFirstItem (){
        if(!currentItems.isEmpty()) {
            ImageView image = (ImageView) root.findViewById(R.id.recommendation_image);
            image.setImageResource(R.drawable.loading);
            WatchlistItem first = currentItems.get(0);
            TextView title = (TextView) root.findViewById(R.id.recommendation_title);
            new DownloadImageTask(image).execute(first.getPosterURL());
            title.setText(first.getTitle());
        }
    }

}

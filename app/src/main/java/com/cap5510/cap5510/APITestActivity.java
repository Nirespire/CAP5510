package com.cap5510.cap5510;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cap5510.cap5510.api.AddToWatchlistTask;
import com.cap5510.cap5510.api.GetWatchlistTask;
import com.cap5510.cap5510.api.Type;
import com.cap5510.cap5510.api.objects.AsyncTaskInput;
import com.cap5510.cap5510.api.objects.WatchlistItem;

import java.util.ArrayList;

public class APITestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apitest);
    }

    public void testGetWatchList(View v){
        new GetWatchlistTask().execute(this);
    }

    public void testPostWatchList(View v){
        ArrayList<WatchlistItem> watchlistItems = new ArrayList<>();
        WatchlistItem movie = new WatchlistItem(Type.Movie);
        WatchlistItem show = new WatchlistItem(Type.Show);

        movie.setTraktID(228);
        movie.setImdbID("tt0372784");
        movie.setTitle("Batman Begins");
        movie.setSlug("batman-begins-2005");
        movie.setYear(2005);

        watchlistItems.add(movie);

        new AddToWatchlistTask().execute(new AsyncTaskInput(this, watchlistItems));


    }
}

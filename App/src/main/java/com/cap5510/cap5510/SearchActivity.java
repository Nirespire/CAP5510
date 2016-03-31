package com.cap5510.cap5510;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;

import com.cap5510.cap5510.api.SearchTask;
import com.cap5510.cap5510.api.objects.AsyncTaskInput;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        //appData.putBoolean(SearchActivity.JARGON, true);
        startSearch(null, false, appData, false);
        return true;
    }

    // Get the intent, verify the action and get the query
    private void handleIntent(Intent intent) {
        String query = intent.getStringExtra(SearchManager.QUERY);

        Log.e("sanjay", query);

        new SearchTask().execute(new AsyncTaskInput(this, "query=" + query + "&type=show,movie&limit=100"));

    }
}

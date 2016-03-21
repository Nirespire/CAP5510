package com.cap5510.cap5510.api;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cap5510.cap5510.api.objects.AsyncTaskInput;
import com.cap5510.cap5510.api.objects.WatchlistItem;
import static com.cap5510.cap5510.api.Type.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import okhttp3.Response;


public class AddToWatchlistTask extends AsyncTask<AsyncTaskInput, Integer, Response> {

    private final String url = "https://api-v2launch.trakt.tv/sync/watchlist";

    private Activity a = null;
    private Context c = null;

    @Override
    protected Response doInBackground(AsyncTaskInput... params) {

        AsyncTaskInput input = params[0];
        a = input.getActivity();
        c = a.getApplicationContext();

        ArrayList<WatchlistItem> items = (ArrayList<WatchlistItem>)input.getPayload();

        JSONObject j = new JSONObject();

        JSONArray movies = new JSONArray();
        JSONArray shows = new JSONArray();
        JSONArray episodes = new JSONArray();


        try {
            for (WatchlistItem i : items) {
                JSONObject current = new JSONObject();
                JSONObject currentIDs = new JSONObject();
                JSONArray seasons = new JSONArray();
                switch (i.getType()) {
                    case Movie:
                        currentIDs.put("slug", i.getSlug());
                        currentIDs.put("imdb", i.getImdbID());
                        currentIDs.put("trakt", i.getTraktID());

                        current.put("title", i.getTitle());
                        current.put("year", i.getYear());
                        current.put("ids", currentIDs);

                        movies.put(current);
                        break;
                    case Show:
                        currentIDs.put("slug", i.getSlug());
                        currentIDs.put("imdb", i.getImdbID());
                        currentIDs.put("trakt", i.getTraktID());

                        current.put("title", i.getTitle());
                        current.put("year", i.getYear());
                        current.put("ids", currentIDs);

                        shows.put(current);
                        break;
                    case Season:

                        currentIDs.put("slug", i.getSlug());
                        currentIDs.put("imdb", i.getImdbID());
                        currentIDs.put("trakt", i.getTraktID());

                        current.put("title", i.getTitle());
                        current.put("year", i.getYear());
                        current.put("ids", currentIDs);

                        JSONObject season = new JSONObject();


                        break;
                    case Episode:
                        break;
                }
            }
        }
        catch(JSONException e){
            Log.e("sanjay", "JSON Exception when creating watchlist post object");
            Log.e("sanjay", e.getMessage());
        }



        return null;
    }

}

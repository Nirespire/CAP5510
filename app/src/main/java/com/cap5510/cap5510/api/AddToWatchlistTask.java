package com.cap5510.cap5510.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cap5510.cap5510.R;
import com.cap5510.cap5510.api.objects.AsyncTaskInput;
import com.cap5510.cap5510.api.objects.WatchlistItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AddToWatchlistTask extends AsyncTask<AsyncTaskInput, Integer, Response> {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private Activity a = null;
    private Context c = null;
    private SharedPreferences sharedPref;


    @Override
    protected Response doInBackground(AsyncTaskInput... params) {

        AsyncTaskInput input = params[0];
        a = input.getActivity();
        c = a.getApplicationContext();

        ArrayList<WatchlistItem> items = (ArrayList<WatchlistItem>)input.getPayload();

        JSONObject j = new JSONObject();

        JSONArray movies = new JSONArray();
        JSONArray shows = new JSONArray();

        try {
            for (WatchlistItem i : items) {
                JSONObject current = new JSONObject();
                JSONObject currentIDs = new JSONObject();
                JSONArray seasons = new JSONArray();
                JSONObject season = new JSONObject();
                JSONArray episodes = new JSONArray();

                currentIDs.put("slug", i.getSlug());
                currentIDs.put("imdb", i.getImdbID());
                currentIDs.put("trakt", i.getTraktID());

                current.put("title", i.getTitle());
                current.put("year", i.getYear());
                current.put("ids", currentIDs);


                switch (i.getType()) {
                    case Movie:
                        movies.put(current);
                        break;
                    case Show:
                        shows.put(current);
                        break;
                    case Season:

                        season.put("number", i.getSeason());
                        seasons.put(season);

                        current.put("seasons", seasons);
                        shows.put(current);
                        break;
                    case Episode:
                        season.put("number", i.getSeason());
                        for(Integer episodeNum : i.getEpisodes()) {
                            JSONObject episode = new JSONObject();
                            episode.put("number", episodeNum);
                        }
                        season.put("episodes", episodes);
                        seasons.put(season);
                        current.put("seasons", seasons);
                        break;
                }
            }

            if(movies.length() != 0) {
                j.put("movies", movies);
            }

            if(shows.length() != 0) {
                j.put("shows", shows);
            }

            Log.e("sanjay", j.toString());

            OkHttpClient client = new OkHttpClient();
            String url = c.getString(R.string.url_post_watchlist);
            RequestBody rb = RequestBody.create(JSON, j.toString());
            Response response;

            sharedPref = c.getSharedPreferences("api", c.MODE_PRIVATE);

            String accessToken = sharedPref.getString(c.getString(R.string.json_access_token), null);

            if(accessToken == null){
                Toast toast = Toast.makeText(c, "Please sign in to access this feature", Toast.LENGTH_SHORT);
                toast.show();
                return null;
            }

            String apiKey = c.getString(R.string.api_key);

            Log.e("sanjay", accessToken);
            Log.e("sanjay", apiKey);

            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("trakt-api-version", "2")
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .addHeader("trakt-api-key", apiKey)
                    .url(url)
                    .post(rb)
                    .build();

            response = client.newCall(request).execute();

            Log.e("sanjay", response.toString());

            return response;

        }
        catch(IOException i){
            Log.e("sanjay", "IO Exception when creating watchlist post object");
            Log.e("sanjay", i.getMessage());
        }
        catch(JSONException e){
            Log.e("sanjay", "JSON Exception when creating watchlist post object");
            Log.e("sanjay", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        Log.e("sanjay", progress.toString());
    }

    @Override
    protected void onPostExecute(Response result) {

        Log.e("sanjay", result.toString());

        if(result == null){
            Log.e("sanjay", "Response was null");
            return;
        }

        try {
            String response = result.body().string();

            Log.e("sanjay", response);

        }
        catch(IOException e){
            Log.e("sanjay", "IO Exception when postExecute creating watchlist post object");
            Log.e("sanjay", e.getMessage());
        }



    }

}

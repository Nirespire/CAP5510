package com.cap5510.cap5510.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cap5510.cap5510.R;
import com.cap5510.cap5510.api.objects.WatchlistItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * http://docs.trakt.apiary.io/#reference/sync/add-ratings/get-watchlist
 */
public class GetWatchlistTask extends AsyncTask<Activity, Integer, Response> {

    private Activity a = null;
    private Context c = null;
    SharedPreferences sharedPref = null;

    @Override
    protected Response doInBackground(Activity... params) {

        a = params[0];
        c = a.getApplicationContext();

        OkHttpClient client = new OkHttpClient();
        String url = c.getString(R.string.url_get_watchlist);
        Response response = null;

        sharedPref = c.getSharedPreferences("api", c.MODE_PRIVATE);
        String accessToken = sharedPref.getString(c.getString(R.string.json_access_token), null);

        if(accessToken == null){
            Toast toast = Toast.makeText(c, "Please sign in to access this feature", Toast.LENGTH_SHORT);
            toast.show();
            return null;
        }

        try{

            Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("trakt-api-version", "2")
                .addHeader("trakt-api-key", c.getString(R.string.api_key))
                .url(url)
                .build();

            response = client.newCall(request).execute();

            return response;
        } catch (IOException e) {

        }

        return response;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {

    }

    @Override
    protected void onPostExecute(Response result) {
        try {

            String response = result.body().string();

            Log.e("sanjay", response);

            JSONArray json = new JSONArray(response);

            ArrayList<WatchlistItem> items = new ArrayList<WatchlistItem>();

            if(json.length() > 0){

                for(int i = 0; i < json.length(); i++){

                    WatchlistItem item = new WatchlistItem();

                    JSONObject currentItem = json.getJSONObject(i);

                    switch(currentItem.getString("type")){
                        case "movie":
                            item.setType(Type.Movie);
                            JSONObject movie = currentItem.getJSONObject("movie");

                            JSONObject ids = movie.getJSONObject("ids");
                            JSONObject images = movie.getJSONObject("images");

                            item.setTitle(movie.getString("title"));
                            item.setYear(movie.getInt("year"));

                            item.setTraktID(ids.getInt("trakt"));
                            item.setSlug(ids.getString("slug"));
                            item.setImdbID(ids.getString("imdb"));

                            // This could be set to null
                            item.setPosterURL(images.getJSONObject("poster").getString("full"));

                            break;
                        case "show":
                            item.setType(Type.Show);

                            JSONObject show = currentItem.getJSONObject("show");

                            ids = show.getJSONObject("ids");
                            images = show.getJSONObject("images");

                            item.setTitle(show.getString("title"));
                            item.setYear(show.getInt("year"));


                            item.setTraktID(ids.getInt("trakt"));
                            item.setSlug(ids.getString("slug"));
                            item.setImdbID(ids.getString("imdb"));

                            // This could be set to null
                            item.setPosterURL(images.getJSONObject("poster").getString("full"));

                            break;
                        case "season":
                            item.setType(Type.Season);

                            JSONObject season = currentItem.getJSONObject("season");

                            ids = season.getJSONObject("ids");

                            item.setSeason(season.getInt("number"));

                            item.setTraktID(ids.getInt("trakt"));
                            //item.setImdbID(ids.getString("imdb"));

                            show = currentItem.getJSONObject("show");
                            item.setTitle(show.getString("title"));
                            item.setYear(show.getInt("year"));

                            // This could be set to null
                            item.setPosterURL(show.getJSONObject("images").getJSONObject("poster").getString("full"));

                            break;
                        case "episode":
                            item.setType(Type.Episode);

                            JSONObject episode = currentItem.getJSONObject("episode");

                            ids = episode.getJSONObject("ids");

                            item.setTitle(episode.getString("title"));
                            ArrayList<Integer> episodes = new ArrayList<>();
                            episodes.add(episode.getInt("number"));
                            item.setEpisodes(episodes);
                            item.setSeason(episode.getInt("season"));

                            item.setTraktID(ids.getInt("trakt"));
                            item.setImdbID(ids.getString("imdb"));

                            show = currentItem.getJSONObject("show");
                            item.setTitle(show.getString("title"));
                            item.setYear(show.getInt("year"));

                            // This could be set to null
                            item.setPosterURL(show.getJSONObject("images").getJSONObject("poster").getString("full"));
                    }

                    Log.e("sanjay", item.toString());

                    items.add(item);
                }
            }

            Log.e("sanjay", items.toString());

        }
        catch(IOException e){
            Log.e("sanjay", "IO Exception");
            Log.e("sanjay", e.getMessage());
        }
        catch(JSONException j){
            Log.e("sanjay", "JSON Exception");
            Log.e("sanjay", j.getMessage());
        }
    }
}

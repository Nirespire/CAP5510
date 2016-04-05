package com.cap5510.cap5510.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.cap5510.cap5510.R;
import com.cap5510.cap5510.api.objects.AsyncTaskInput;
import com.cap5510.cap5510.api.objects.WatchlistItem;
import com.cap5510.cap5510.api.objects.standard_media_objects.Episode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetWatchedTvShows extends AsyncTask<AsyncTaskInput, Integer, Response> {

    Activity a = null;
    Context c = null;
    Fragment f = null;

    private SharedPreferences sharedPref;

    @Override
    protected Response doInBackground(AsyncTaskInput... params) {
        a = params[0].getActivity();
        c = a.getApplicationContext();
        f = params[0].getFragment();

        OkHttpClient client = new OkHttpClient();
        String url = c.getString(R.string.url_get_watched_tv_shows);
        String apiKey = c.getString(R.string.api_key);

        sharedPref = c.getSharedPreferences("api", c.MODE_PRIVATE);
        String accessToken = sharedPref.getString(c.getString(R.string.json_access_token), null);
        Response response = null;

        try {
            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .addHeader("trakt-api-version", "2")
                    .addHeader("trakt-api-key", apiKey)
                    .url(url)
                    .build();

            response = client.newCall(request).execute();
        } catch (Exception e) {
        }
        return response;
    }

    @Override
    protected void onPostExecute(Response result) {
        //Log.d("FinishedTV", "Find watched tv shows has completed");

        if(result == null){
            Log.e("result", "is null");
        }

        try {


            String response = result.body().string();

            JSONArray json = new JSONArray(response);

            ArrayList<WatchlistItem> items = new ArrayList<WatchlistItem>();
            ArrayList<String> showNames = new ArrayList<String>();

            String showPosterImage;

            if(json.length() > 0){

                for(int i = 0; i < json.length(); i++){

                    WatchlistItem item = new WatchlistItem();

                    JSONObject currentItem = json.getJSONObject(i);

                    item.setType(Type.Episode);

                    JSONObject episode = currentItem.getJSONObject("episode");

                    JSONObject ids = episode.getJSONObject("ids");


                    item.setSeason(episode.getInt("season"));


                    item.setImdbID(ids.getString("imdb"));

                    JSONObject show = currentItem.getJSONObject("show");

                    item.setTitle(show.getString("title"));

                    item.setTraktID(show.getJSONObject("ids").getInt("trakt"));
                    item.setYear(show.getInt("year"));

                    item.setPosterURL(show.getJSONObject("images").getJSONObject("banner").getString("full"));


                    // This could be set to null
                    //item.setPosterURL(show.getJSONObject("images").getJSONObject("poster").getString("full"));

                    if(!showNames.contains(item.getTitle())) {
                        //Log.d("show name", item.getTitle());
                        showNames.add(item.getTitle());
                        items.add(item);
                    }
                }
            }

            //Log.e("stevie", items.toString());
            AsyncTaskInput temp;
            Episode ep;
            for (WatchlistItem it : items) {
                ep = new Episode(it.getTraktID(), it.getPosterURL());
                temp = new AsyncTaskInput(a, f, ep);
                new GetTvProgress().execute(temp);
            }

        }catch(Exception e){
            Log.e("error", e.toString());
        }
//        catch(IOException e){
//            Log.e("stevie", "IO Exception");
//            Log.e("stevie", e.getMessage());
//        }
//        catch(JSONException j){
//            Log.e("stevie", "JSON Exception");
//            Log.e("stevie", j.getMessage());
//        }

    }
}


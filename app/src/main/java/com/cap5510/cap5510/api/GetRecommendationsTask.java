package com.cap5510.cap5510.api;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cap5510.cap5510.APITestActivity;
import com.cap5510.cap5510.R;
import com.cap5510.cap5510.RecommendationActivity;
import com.cap5510.cap5510.RecommendationFragment;
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
import okhttp3.Response;

/*
    Payload is Type of recommendations to get (Movie or Show)
 */
public class GetRecommendationsTask extends AsyncTask<AsyncTaskInput, Integer, Response> {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private Activity a;
    private Context c;
    Fragment f;
    private SharedPreferences sharedPref;

    @Override
    protected Response doInBackground(AsyncTaskInput... params) {

        a = params[0].getActivity();
        c = a.getApplicationContext();
        f = params[0].getFragment();

        Type t = (Type)params[0].getPayload();

        OkHttpClient client = new OkHttpClient();

        String url;

        if(t == Type.Movie){
            url = c.getString(R.string.url_get_movie_recommendations);
        }
        else if(t == Type.Show){
            url = c.getString(R.string.url_get_show_recommendations);
        }
        else{
            return null;
        }
        Response response = null;

        sharedPref = c.getSharedPreferences("api", c.MODE_PRIVATE);
        String accessToken = sharedPref.getString(c.getString(R.string.json_access_token), null);

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

        Log.e("sanjay", result.toString());

        if(result == null){
            Log.e("sanjay", "Response was null");
            return;
        }

        try {
            String response = result.body().string();

            Log.e("sanjay", response);

            if(a instanceof APITestActivity){
                Log.e("sanjay", "called from API Test");
            }
            else if(f != null && f instanceof RecommendationFragment){
                Log.e("sanjay", "called from Recommendations");

                JSONArray results = new JSONArray(response);
                ArrayList<WatchlistItem> resultItems = new ArrayList<>();

                for(int i = 0; i < results.length(); i++){
                    JSONObject current = results.getJSONObject(i);
                    WatchlistItem currentItem = new WatchlistItem();

                    currentItem.setType(Type.Movie);

                    if(current.has("title")){
                        currentItem.setTitle(current.getString("title"));
                    }

                    if(current.has("year")){
                        currentItem.setYear(current.getInt("year"));
                    }

                    if(current.has("ids")){
                        JSONObject ids = current.getJSONObject("ids");
                        if(ids.has("trakt")){
                            currentItem.setTraktID(ids.getInt("trakt"));
                        }

                        if(ids.has("imdb")){
                            currentItem.setImdbID(ids.getString("imdb"));
                        }

                        if(ids.has("slug")){
                            currentItem.setSlug(ids.getString("slug"));
                        }
                    }

                    if(current.has("images")){
                        if(current.getJSONObject("images").has("poster")){
                            if(current.getJSONObject("images").getJSONObject("poster").has("medium")){
                                currentItem.setPosterURL(current.getJSONObject("images").getJSONObject("poster").getString("medium"));
                            }
                        }
                    }

                    resultItems.add(currentItem);
                }

                ((RecommendationFragment) f).addToCurrentItems(resultItems);

                if(!resultItems.isEmpty()){
                    WatchlistItem first = resultItems.get(0);

                    Log.e("sanjay", first.toString());

                    ((RecommendationFragment) f).displayFirstItem();
                }

            }

        }
        catch(IOException e){
            Log.e("sanjay", "IO Exception when postExecute ... ");
            Log.e("sanjay", e.getMessage());
        }
        catch(JSONException j){
            Log.e("sanjay", "JSON Exception when postExecute ... ");
            Log.e("sanjay", j.getMessage());
        }



    }
}

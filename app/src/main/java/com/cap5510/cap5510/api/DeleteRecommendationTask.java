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
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class DeleteRecommendationTask extends AsyncTask<AsyncTaskInput, Integer, Response> {

    private Activity a;
    private Fragment f;
    private Context c;
    private SharedPreferences sharedPref;

    @Override
    protected Response doInBackground(AsyncTaskInput... params) {

        a = params[0].getActivity();
        f = params[0].getFragment();
        c = a.getApplicationContext();

        WatchlistItem w = (WatchlistItem) params[0].getPayload();

        OkHttpClient client = new OkHttpClient();
        String url;

        if(w.getType() == Type.Movie){
            url = c.getString(R.string.url_hide_movie_recommendation)+w.getTraktID();
        }
        else if(w.getType() == Type.Show){
            url = c.getString(R.string.url_hide_show_recommendation)+w.getTraktID();
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
                    .delete()
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

        Log.e("sanjay", String.valueOf(result.code()));
    }
}

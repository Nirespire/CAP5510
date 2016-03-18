package com.cap5510.cap5510.api;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import com.cap5510.cap5510.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetWatchedTvShows extends AsyncTask<Activity, Integer, Response> {

    Activity a = null;
    Context c = null;

    @Override
    protected Response doInBackground(Activity... params) {
        a = params[0];
        c = a.getApplicationContext();

        OkHttpClient client = new OkHttpClient();
        String url = c.getString(R.string.url_get_watched_tv_shows);
        String apiKey = c.getString(R.string.api_key);
        Response response = null;

         try {
            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("trakt-api-version", "2")
                    .addHeader("trakt-api-key", apiKey)
                    .url(url)
                    .build();

            response = client.newCall(request).execute();
            Log.e("Response", response.body().string());
        } catch (IOException e) {
             Log.e("no response", e.toString());
        }

        return response;
    }

    @Override
    protected void onPostExecute(Response result) {
        Log.d("FinishedTV", "Find watched tv shows has completed");
    }
}



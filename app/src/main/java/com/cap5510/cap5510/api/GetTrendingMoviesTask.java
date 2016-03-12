package com.cap5510.cap5510.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cap5510.cap5510.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetTrendingMoviesTask extends AsyncTask<Context, Integer, Response> {

    @Override
    protected Response doInBackground(Context... params) {
        OkHttpClient client = new OkHttpClient();
        String url = params[0].getString(R.string.url_get_trending_movies);
        String apiKey = params[0].getString(R.string.api_key);
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

        }
        return response;
    }
}



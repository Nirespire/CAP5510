package com.cap5510.cap5510.api;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

public class GetTvProgress extends AsyncTask<AsyncTaskInput, Integer, Response> {

    Activity a = null;
    Context c = null;
    Episode e = null;

    @Override
    protected Response doInBackground(AsyncTaskInput... params) {
        a = params[0].getActivity();
        c = a.getApplicationContext();
        e = (Episode) params[0].getPayload();


        OkHttpClient client = new OkHttpClient();
        String url = c.getString(R.string.url_shows_partial) + Integer.toString(e.getID()) + c.getString(R.string.url_show_progress_partial);
        String apiKey = c.getString(R.string.api_key);
        String accessToken = c.getString(R.string.access_token);
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
        //Log.d("finished", "Find tv show progress has completed");

        try {

            String response = result.body().string();
            //Log.e("stevie", response);

            JSONObject showInfo = new JSONObject(response);
            JSONObject nextEp = showInfo.getJSONObject("next_episode");
            Log.d("next ep", nextEp.toString());

            LinearLayout episodeQueue = (LinearLayout)a.findViewById(R.id.episodesQueue);


            LinearLayout episodeLayout = new LinearLayout(c);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            episodeLayout.setLayoutParams(lp);
            episodeLayout.setOrientation(LinearLayout.VERTICAL);
            episodeLayout.setPadding(10, 0, 0, 0);

            ImageView showPoster = new ImageView(c);
            new DownloadImageTask(showPoster).execute(e.getPosterURL());
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 330, c.getResources().getDisplayMetrics());
            int width = (int)  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, c.getResources().getDisplayMetrics());
            lp = new LinearLayout.LayoutParams(width, height);
            showPoster.setLayoutParams(lp);

            Button seasonTitle = new Button(c);
            seasonTitle.setText(nextEp.getInt("season") + "x" + nextEp.getInt("number") + " " + nextEp.getString("title"));
            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            //seasonTitle.setWidth(45);
            seasonTitle.setLayoutParams(lp);

            episodeLayout.addView(showPoster);
            episodeLayout.addView(seasonTitle);
            episodeQueue.addView(episodeLayout);



        }
        catch(IOException e){
            Log.e("stevie", "IO Exception");
            Log.e("stevie", e.getMessage());
        }
        catch(JSONException j){
            Log.e("stevie", "JSON Exception");
            Log.e("stevie", j.getMessage());
        }

    }
}


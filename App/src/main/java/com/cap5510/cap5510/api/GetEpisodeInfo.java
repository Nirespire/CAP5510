package com.cap5510.cap5510.api;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cap5510.cap5510.R;
import com.cap5510.cap5510.api.objects.AsyncTaskInput;
import com.cap5510.cap5510.api.objects.standard_media_objects.Episode;
import com.cap5510.cap5510.api.objects.standard_media_objects.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetEpisodeInfo extends AsyncTask<AsyncTaskInput, Integer, Response> {

    Activity a = null;
    Context c = null;
    Episode episode = null;

    @Override
    protected Response doInBackground(AsyncTaskInput... params) {
        a = params[0].getActivity();
        c = a.getApplicationContext();
        episode = ((Episode) params[0].getPayload());


        OkHttpClient client = new OkHttpClient();
        String url = c.getString(R.string.url_shows_partial) + Integer.toString(episode.getID()) + "/seasons/" +
                Integer.toString(episode.getSeason()) + "/episodes/" + Integer.toString(episode.getNumber()) +"?extended=full,images";
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
        } catch (Exception e) {
        }

        return response;
    }

    @Override
    protected void onPostExecute(Response result) {

        try {

            String response = result.body().string();
            Log.d("current ep", response);

            JSONObject movie = new JSONObject(response);


            TextView title = (TextView) a.findViewById(R.id.episodeTitle);
            title.setText(movie.getString("title"));
            title.setTextColor(c.getResources().getColor(R.color.colorPrimary));

            TextView rating = (TextView) a.findViewById(R.id.episodeRating);
            rating.setText(String.format("%.0f", movie.getDouble("rating")*10)+ "%");


            TextView overview = (TextView) a.findViewById(R.id.episodeOverview);
            overview.setText(movie.getString("overview"));

            ImageView fullHeart = (ImageView) a.findViewById(R.id.fullHeart);
            fullHeart.setImageResource(R.drawable.favorite2);
            ImageView emptyHeart = (ImageView) a.findViewById(R.id.emptyheart);
            emptyHeart.setImageResource(R.drawable.emptyheart);


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


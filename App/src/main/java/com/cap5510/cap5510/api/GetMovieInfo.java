package com.cap5510.cap5510.api;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cap5510.cap5510.R;
import com.cap5510.cap5510.api.DownloadImageTask;
import com.cap5510.cap5510.api.objects.AsyncTaskInput;
import com.cap5510.cap5510.api.objects.standard_media_objects.Episode;
import com.cap5510.cap5510.api.objects.standard_media_objects.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetMovieInfo extends AsyncTask<AsyncTaskInput, Integer, Response> {

    Activity a = null;
    Context c = null;
    int id = -1;

    @Override
    protected Response doInBackground(AsyncTaskInput... params) {
        a = params[0].getActivity();
        c = a.getApplicationContext();
        id = ((Movie) params[0].getPayload()).getTraktID();


        OkHttpClient client = new OkHttpClient();
        String url = c.getString(R.string.url_movies_partial) + Integer.toString(id) + "?extended=full,images";
        String apiKey = c.getString(R.string.api_key);
        String accessToken = c.getString(R.string.access_token);
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
            //Log.d("stevie", response);

            JSONObject movie = new JSONObject(response);
            String logo = movie.getJSONObject("images").getJSONObject("logo").getString("full");

            ImageView image = (ImageView) a.findViewById(R.id.movieImage);
            new DownloadImageTask(image).execute(logo);

//            TextView title = (TextView) a.findViewById(R.id.movieTitle);
//            title.setText(movie.getString("title"));

            TextView rating = (TextView) a.findViewById(R.id.movieRating);
            rating.setText(String.format("%.0f", movie.getDouble("rating")*10)+ "%");


            TextView overview = (TextView) a.findViewById(R.id.movieOverview);
            overview.setText(movie.getString("overview"));


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


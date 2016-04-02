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

import com.cap5510.cap5510.R;
import com.cap5510.cap5510.api.objects.AsyncTaskInput;
import com.cap5510.cap5510.api.objects.standard_media_objects.Episode;
import com.cap5510.cap5510.api.objects.standard_media_objects.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetPeopleEpisode extends AsyncTask<AsyncTaskInput, Integer, Response> {

    Activity a = null;
    Context c = null;
    int id = -1;

    @Override
    protected Response doInBackground(AsyncTaskInput... params) {
        a = params[0].getActivity();
        c = a.getApplicationContext();
        id = ((Episode) params[0].getPayload()).getID();


        OkHttpClient client = new OkHttpClient();
        String url = c.getString(R.string.url_shows_partial) + Integer.toString(id) + "/people?extended=full,images";
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
            Log.d("stevie", response);

            JSONObject people = new JSONObject(response);

            JSONArray cast = people.getJSONArray("cast");

            int temp;
            if(cast.length() > 10){
                temp = 10;
            }else{
                temp = cast.length();
            }

            if(cast.length() > 0){

                for(int i = 0; i < temp; i++) {
                    JSONObject currentPerson = cast.getJSONObject(i).getJSONObject("person");

                    LinearLayout personLayout = new LinearLayout(c);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    personLayout.setLayoutParams(lp);
                    personLayout.setOrientation(LinearLayout.VERTICAL);
                    personLayout.setPadding(10, 0, 0, 0);

                    JSONObject images = currentPerson.getJSONObject("images").getJSONObject("headshot");

                    ImageView headshot = new ImageView(c);
                    //headshot.setImageResource(R.drawable.daniel_radcliffe);
                    new DownloadImageTask(headshot).execute(images.getString("full"));
                    int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, c.getResources().getDisplayMetrics());
                    int width = (int)  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, c.getResources().getDisplayMetrics());
                    lp = new LinearLayout.LayoutParams(width, height);
                    headshot.setLayoutParams(lp);

                    Button name = new Button(c);
                    name.setText(currentPerson.getString("name"));
                    width = (int)  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 125, c.getResources().getDisplayMetrics());
                    lp = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.gravity = Gravity.CENTER;
                    name.setLayoutParams(lp);

                    personLayout.addView(headshot);
                    personLayout.addView(name);

                    LinearLayout movieCast =(LinearLayout)a.findViewById(R.id.episodeCast);
                    movieCast.addView(personLayout);
                }

            }


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


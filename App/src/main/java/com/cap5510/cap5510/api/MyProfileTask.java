package com.cap5510.cap5510.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cap5510.cap5510.Friend;
import com.cap5510.cap5510.FriendFeedActivity;
import com.cap5510.cap5510.R;
import com.cap5510.cap5510.api.objects.DownloadImageTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Vega on 3/28/2016.
 */
public class MyProfileTask extends AsyncTask<Activity, Integer, Response> {

    Activity a = null;
    Fragment f=null;
    Context c = null;
    Friend friend_data[];
    ListView listview;
    String name;

    @Override
    protected Response doInBackground(Activity... params) {
        a = params[0];
        //a = f.getActivity();
        c = a.getApplicationContext();

        OkHttpClient client = new OkHttpClient();
        String url = "https://api-v2launch.trakt.tv/users/me?extended=images";
        String apiKey = c.getString(R.string.api_key);
        Response response = null;
        SharedPreferences sharedPref = a.getSharedPreferences("api",Context.MODE_PRIVATE);
        String access_token = sharedPref.getString("access_token",null);

        try {
            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("trakt-api-version", "2")
                    .addHeader("trakt-api-key", apiKey)
                    .addHeader("Authorization", "Bearer " + access_token)
                    .url(url)
                    .build();

            response = client.newCall(request).execute();
            //Log.e("aishat", response.body().string());
        } catch (IOException e) {

        }
        return response;
    }


    @Override
    protected void onProgressUpdate(Integer... progress) {

    }

    @Override
    protected void onPostExecute(Response result) {

        try{
            String response = result.body().string();

            Log.e("aishatx", response);

           // JSONArray json = new JSONArray(response);

            JSONObject json = new JSONObject(response);

           // JSONObject currentItem = json.getJSONObject(0);
            Log.e("aishatx", json.getString("username"));
            String current_user = json.getString("username");
            String icon = json.getJSONObject("images").getJSONObject("avatar").getString("full");

            TextView name = (TextView)a.findViewById(R.id.profile_name);
            CircleImageView picture = (CircleImageView)a.findViewById(R.id.profile_image);

            new DownloadImageTask(picture).execute(icon);
            name.setText(current_user);

            new GetFriendWatchedHistoryTask(current_user).execute(a);





        }catch(IOException e){
            Log.e("aishatx","input exception caught");
        }catch(JSONException j){
            Log.e("aishatx","json exception caught");
        }
    }
}

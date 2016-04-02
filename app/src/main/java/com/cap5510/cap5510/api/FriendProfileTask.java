package com.cap5510.cap5510.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cap5510.cap5510.Friend;
import com.cap5510.cap5510.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Vega on 3/27/2016.
 */
public class FriendProfileTask extends AsyncTask<Activity, Integer, Response> {

    Activity a = null;
    Fragment f=null;
    Context c = null;
    Friend friend_data[];
    ListView listview;
    String name;

    public FriendProfileTask(String s) {
        name = s;
    }

    @Override
    protected Response doInBackground(Activity... params) {
        a = params[0];
        //a = f.getActivity();
        c = a.getApplicationContext();

        OkHttpClient client = new OkHttpClient();
        String url = "https://api-v2launch.trakt.tv/users/"+name+"/watchlist?extended=images";
        String apiKey = c.getString(R.string.api_key);
        Response response = null;
        SharedPreferences sharedPref = a.getSharedPreferences("api",Context.MODE_PRIVATE);
        String access_token = sharedPref.getString("access_token",null);

        try {
            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("trakt-api-version", "2")
                    .addHeader("trakt-api-key", apiKey)
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

            JSONArray json = new JSONArray(response);

            ImageView[] img = {(ImageView)a.findViewById(R.id.watchlist1),(ImageView)a.findViewById(R.id.watchlist2),
                    (ImageView)a.findViewById(R.id.watchlist3),(ImageView)a.findViewById(R.id.watchlist4)
                    ,(ImageView)a.findViewById(R.id.watchlist5)};


            for(int i=0;i<5;i++){

                JSONObject currentItem = json.getJSONObject(i);
                Log.e("aishatg",currentItem.toString());

                if(currentItem !=null) {
                    String type = currentItem.getString("type");
                    String logo = "";
                    switch (type) {
                        case "show":
                        case "episode":
                            logo = currentItem.getJSONObject("show").getJSONObject("images").getJSONObject("poster").getString("thumb");
                            break;

                        case "movie":
                            logo = currentItem.getJSONObject("movie").getJSONObject("images").getJSONObject("poster").getString("thumb");
                            break;
                    }
                    Log.e("aishatx", logo);
                    img[i].setImageResource(R.drawable.loading);
                    new DownloadImageTask(img[i]).execute(logo);
                }else{
                    if(i==0) {
                        LinearLayout layout = (LinearLayout)a.findViewById(R.id.nowatchlists);
                        layout.setVisibility(View.VISIBLE);
                    }
                    break;
                }
            }

     }catch(IOException e){
          Log.e("aishatx","input exception caught");
      }catch(JSONException j){
          Log.e("aishatx","json exception caught");
        }
    }
}


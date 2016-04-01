package com.cap5510.cap5510.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
public class GetFriendWatchedHistoryTask extends AsyncTask<Activity, Integer, Response> {

    Activity a = null;
    Fragment f=null;
    Context c = null;
    Friend friend_data[];
    ListView listview;
    String name;

    public GetFriendWatchedHistoryTask(String s) {
        name = s;
    }

    @Override
    protected Response doInBackground(Activity... params) {
        a = params[0];
        //a = f.getActivity();
        c = a.getApplicationContext();

        OkHttpClient client = new OkHttpClient();
        String url = "https://api-v2launch.trakt.tv/users/"+name+"/history?extended=images";
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

            ImageView[] mimg = {(ImageView)a.findViewById(R.id.movie1),(ImageView)a.findViewById(R.id.movie2),
                    (ImageView)a.findViewById(R.id.movie3),(ImageView)a.findViewById(R.id.movie4)
                    ,(ImageView)a.findViewById(R.id.movie5)};

            ImageView[] simg = {(ImageView)a.findViewById(R.id.show1),(ImageView)a.findViewById(R.id.show2),
                    (ImageView)a.findViewById(R.id.show3),(ImageView)a.findViewById(R.id.show4)
                    ,(ImageView)a.findViewById(R.id.show5)};

            TextView[] simgtext = {(TextView)a.findViewById(R.id.showtext1),(TextView)a.findViewById(R.id.showtext2),
                    (TextView)a.findViewById(R.id.showtext3),(TextView)a.findViewById(R.id.showtext4)
                    ,(TextView)a.findViewById(R.id.showtext5)};

            int sh = 0;
            int mo = 0;
            int i=0;
            while((sh<5 && mo<5)||i<json.length()){
                JSONObject currentItem = json.getJSONObject(i);
                String type = currentItem.getString("type");

                String logo="";
                switch(type){
                    case "show":
                        logo= currentItem.getJSONObject("show").getJSONObject("images").getJSONObject("poster").getString("thumb");
                        if(sh<5) {
                            new DownloadImageTask(simg[sh]).execute(logo);
                            sh++;
                        }
                        break;
                    case "episode":
                        logo= currentItem.getJSONObject("show").getJSONObject("images").getJSONObject("poster").getString("thumb");
                        Log.e("aishatx",logo);
                        if(sh<5) {
                            new DownloadImageTask(simg[sh]).execute(logo);
                            String episode = "S"+currentItem.getJSONObject("episode").getString("season")+"E"+
                                    currentItem.getJSONObject("episode").getString("number");
                            simgtext[sh].setText(episode);
                            sh++;
                        }
                        break;
                    case "movie":
                        logo= currentItem.getJSONObject("movie").getJSONObject("images").getJSONObject("poster").getString("thumb");
                        if(mo<5) {
                            new DownloadImageTask(mimg[mo]).execute(logo);
                            mo++;
                        }
                        break;
                }
                i++;
                Log.e("aishatx",logo);

            }

        }catch(IOException e){
            Log.e("aishatx","input exception caught");
        }catch(JSONException j){
            Log.e("aishatx","json exception caught");
        }
        new FriendProfileTask(name).execute(a);
    }
}

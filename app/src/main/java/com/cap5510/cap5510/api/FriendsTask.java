package com.cap5510.cap5510.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import com.cap5510.cap5510.Friend;
import com.cap5510.cap5510.FriendAdapter;
import com.cap5510.cap5510.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.SharedPreferences;
import android.widget.ListView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Vega on 3/21/2016.
 */
public class FriendsTask extends AsyncTask<Activity, Integer, Response> {

    Activity a = null;
    Context c = null;
    Friend friend_data[];
    ListView listview;

    @Override
    protected Response doInBackground(Activity... params) {
        a = params[0];
        c = a.getApplicationContext();

        OkHttpClient client = new OkHttpClient();
        String url = c.getString(R.string.url_get_friends);
        String apiKey = c.getString(R.string.api_key);
        Response response = null;
        SharedPreferences sharedPref = a.getSharedPreferences("api",Context.MODE_PRIVATE);
        String access_token = sharedPref.getString("access_token",null);

        try {
            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("trakt-api-version", "2")
                    .addHeader("trakt-api-key", apiKey)
                    .addHeader("Authorization","Bearer "+access_token)
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
            friend_data = new Friend[json.length()];
            for(int i=0;i<json.length();i++){
                JSONObject currentItem = json.getJSONObject(i);
                JSONObject user = currentItem.getJSONObject("user");
                //String user = currentItem.getString("user");
                String icon = user.getJSONObject("images").getJSONObject("avatar").getString("full");
                String username = user.getString("username");

                friend_data[i] = new Friend(icon,username);

                //Log.e("aishaty", icon);

            }

            FriendAdapter adapter = new FriendAdapter(a,R.layout.row_feed_item, friend_data);


            listview = (ListView)a.findViewById(R.id.feedview);

            //View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
            //listView1.addHeaderView(header);

            listview.setAdapter(adapter);



        }catch(IOException e){
            Log.e("aishatx","input exception caught");
        }catch(JSONException j){
            Log.e("aishatx","json exception caught");
        }
    }
}

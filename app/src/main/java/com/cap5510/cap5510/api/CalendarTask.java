package com.cap5510.cap5510.api;

/**
 * Created by Aishat on 3/30/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cap5510.cap5510.EventView;
import com.cap5510.cap5510.Friend;
import com.cap5510.cap5510.FriendAdapter;
import com.cap5510.cap5510.FriendFeedActivity;
import com.cap5510.cap5510.R;
import com.cap5510.cap5510.SundayFragment;
import com.cap5510.cap5510.api.objects.TimeConversion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Time;
import java.util.Arrays;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Vega on 3/21/2016.
 */
public class CalendarTask extends AsyncTask<Fragment, Integer, Response> {

    Fragment a = null;
    Fragment f=null;
    Context c = null;
    String date="";

    public CalendarTask(String d) {
        date = d;

    }

    @Override
    protected Response doInBackground(Fragment... params) {
        a = params[0];
        //a = f.getActivity();
        c = a.getActivity().getApplicationContext();

        OkHttpClient client = new OkHttpClient();
        String url = "https://api-v2launch.trakt.tv/calendars/my/shows/"+date+"/1";
        Log.e("aishate",url);
        String apiKey = c.getString(R.string.api_key);
        Response response = null;
        SharedPreferences sharedPref = c.getSharedPreferences("api",Context.MODE_PRIVATE);
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
        String[]times = {"19:00:00","19:30:00","20:00:00","20:30:00","21:00:00","21:30:00",
                "22:00:00","22:30:00","23:00:00","23:30:00"};
        int[]eventids = {R.id.sevenpm8pmevent,R.id.seven30pm830pmevent,R.id.eightpm9pmevent,
        R.id.eight30pm9pmevent,R.id.ninepm10pmevent,R.id.nine30pm1030pmevent,R.id.tenpm11pmevent,
        R.id.ten30pm1130pmevent};
        try{
            String response = result.body().string();

            Log.e("aishatd", response);


            JSONArray json = new JSONArray(response);
            Log.e("aishatd",""+json.length());

            if(json.length()!=0) {
                for (int i = 0; i < json.length(); i++) {
                    JSONObject current = json.getJSONObject(i);

                    String first_aired = current.getString("first_aired");

                    first_aired = first_aired.replace("T", " ").replace("Z", "");

                    Date air = TimeConversion.getEasternTime(first_aired);

                    String time = new Time(air.getTime()).toString();

                    String season = current.getJSONObject("episode").getString("season");
                    String episode = current.getJSONObject("episode").getString("number");
                    String title = current.getJSONObject("show").getString("title");

                    int index = Arrays.asList(times).indexOf(time);
                    int id = eventids[index];

                    EventView v = (EventView)a.getView().findViewById(id);
                    v.setTitle(title+" S"+season+"E"+episode);
                    v.setVisibility(View.VISIBLE);



                }

            }


        }catch(IOException e){
            Log.e("aishatx","input exception caught");
        }catch(JSONException j){
            Log.e("aishatx","json exception caught");
        }
    }
}

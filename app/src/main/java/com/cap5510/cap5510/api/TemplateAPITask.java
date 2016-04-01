package com.cap5510.cap5510.api;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.cap5510.cap5510.R;
import com.cap5510.cap5510.api.objects.AsyncTaskInput;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Copy and paste this code when creating a new API call to save some time
 */
public class TemplateAPITask extends AsyncTask<AsyncTaskInput, Integer, Response> {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private Activity a;
    private Fragment f;
    private Context c;
    private SharedPreferences sharedPref;

    @Override
    protected Response doInBackground(AsyncTaskInput... params) {

        a = params[0].getActivity();
        c = a.getApplicationContext();

        OkHttpClient client = new OkHttpClient();
        String url = c.getString(-1);   // TODO ADD YOUR URL HERE
        Response response = null;
        JSONObject json = new JSONObject();

        sharedPref = c.getSharedPreferences("api", c.MODE_PRIVATE);
        String accessToken = sharedPref.getString(c.getString(R.string.json_access_token), null);

        try{

            //RequestBody rb = RequestBody.create(JSON, json.toString());

            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                            //.addHeader("Authorization", "Bearer " + accessToken)
                    .addHeader("trakt-api-version", "2")
                    .addHeader("trakt-api-key", c.getString(R.string.api_key))
                            //.post(rb)
                    .url(url)
                    .build();



            response = client.newCall(request).execute();

            return response;
        } catch (IOException e) {

        }
//      catch(JSONException j){
//
//      }

        return response;
    }


    @Override
    protected void onProgressUpdate(Integer... progress) {

    }

    @Override
    protected void onPostExecute(Response result) {

        Log.e("sanjay", result.toString());

        if(result == null){
            Log.e("sanjay", "Response was null");
            return;
        }

        try {
            String response = result.body().string();

            Log.e("sanjay", response);

        }
        catch(IOException e){
            Log.e("sanjay", "IO Exception when postExecute ... ");
            Log.e("sanjay", e.getMessage());
        }



    }
}

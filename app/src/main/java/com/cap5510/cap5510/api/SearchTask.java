package com.cap5510.cap5510.api;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

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
 * Payload should be in the form of a String that follows the '?' in the search query url.
 *
 * For example:
 *      AsyncTaskInput.payload == "query=batman&type=movie&year=2015"
 *
 * NOTE: this API does not support extended images
 */
public class SearchTask extends AsyncTask<AsyncTaskInput, Integer, Response> {

    private Activity a;
    private Context c;

    @Override
    protected Response doInBackground(AsyncTaskInput... params) {

        a = params[0].getActivity();
        c = a.getApplicationContext();

        String query = (String) params[0].getPayload();

        OkHttpClient client = new OkHttpClient();
        String url = c.getString(R.string.url_search) + query;
        Response response = null;

        try{

            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("trakt-api-version", "2")
                    .addHeader("trakt-api-key", c.getString(R.string.api_key))
                    .url(url)
                    .build();

            response = client.newCall(request).execute();

            return response;

        } catch (IOException e) {
            Log.e("sanjay", "IO Exception doInBackground SearchTask");
            Log.e("sanjay", e.getMessage());
        }

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

            TextView tv = (TextView)a.findViewById(R.id.searchResults);

            tv.setText(response);

            Log.e("sanjay", response);

        }
        catch(IOException e){
            Log.e("sanjay", "IO Exception when postExecute ... ");
            Log.e("sanjay", e.getMessage());
        }



    }
}

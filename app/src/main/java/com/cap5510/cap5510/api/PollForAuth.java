package com.cap5510.cap5510.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.cap5510.cap5510.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class PollForAuth extends AsyncTask<Activity, Integer, Response> {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    boolean flag = false;

    private PollForAuth async;
    private Activity a;
    private Context c;
    private SharedPreferences sharedPref;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        flag = true;
    }

    @Override
    protected Response doInBackground(Activity... params) {

        if (isCancelled()){
            return null;
        }

        Response response = null;

        while(flag) {

            a = params[0];
            c = a.getApplicationContext();
            sharedPref = c.getSharedPreferences("api", c.MODE_PRIVATE);

            String deviceCode = sharedPref.getString(c.getString(R.string.json_device_code), "");
            String clientID = c.getString(R.string.api_key);
            String clientSecret = c.getString(R.string.client_secret);

            OkHttpClient client = new OkHttpClient();

            String url = c.getString(R.string.url_poll_auth_token);
            JSONObject json = new JSONObject();

            try {

                json.put("code", deviceCode);
                json.put("client_id", clientID);
                json.put("client_secret", clientSecret);

                RequestBody rb = RequestBody.create(JSON, json.toString());

                Request request = new Request.Builder()
                        .addHeader("Content-Type", "application/json")
                        .url(url)
                        .post(rb)
                        .build();

                response = client.newCall(request).execute();

                return response;

            } catch (IOException e) {

            } catch (JSONException j) {

            }

            int interval = sharedPref.getInt(c.getString(R.string.json_interval), 5);

            try {
                Thread.sleep(interval * 1000);
            }
            catch(InterruptedException e){

            }
        }

        return response;
    }

    @Override
    protected void onPostExecute(Response result) {

        super.onPostExecute(result);

        try {

            if (result != null && result.code() == 200) {
                flag = false;

                String response = result.body().string();

                Log.e("sanjay", response);

                JSONObject json = new JSONObject(response);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(c.getString(R.string.json_access_token), json.getString("access_token"));
                editor.putString(c.getString(R.string.json_refersh_token), json.getString("refresh_token"));
                // TODO save expires_in field to know when to use refresh token

                editor.commit();

                Toast toast = Toast.makeText(c, "Device Authorized!", Toast.LENGTH_SHORT);
                toast.show();

                TextView codeView = (TextView) a.findViewById(R.id.code);
                codeView.setText("You are Authorized!");

                return;
            }

        }
        catch(IOException e){
            Log.e("sanjay", "IO Exception");
            Log.e("sanjay", e.getMessage());
        }
        catch(JSONException j){
            Log.e("sanjay", "JSON Exception");
            Log.e("sanjay", j.getMessage());
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        flag = false;
    }
}


package com.cap5510.cap5510.api;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cap5510.cap5510.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class GetDeviceCodeTask extends AsyncTask<Activity, Integer, Response> {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private Activity a = null;
    private Context c = null;
    private SharedPreferences sharedPref;
    private TextView codeView;
    private Button genButton;
    private Button openTraktButton;

    @Override
    protected Response doInBackground(Activity... params) {

        a = params[0];
        c = a.getApplicationContext();

        sharedPref = c.getSharedPreferences("api", c.MODE_PRIVATE);
        codeView = (TextView) a.findViewById(R.id.code);
        genButton = (Button)a.findViewById(R.id.generate_code_button);
        openTraktButton = (Button) a.findViewById(R.id.open_trakt_button);

        if(sharedPref.getString(c.getString(R.string.json_access_token), null) != null){
            a.runOnUiThread(new Runnable(){
                public void run() {
                    codeView.setText("You are Authorized!");
                    genButton.setEnabled(false);

                }
            });
            return null;
        }

        OkHttpClient client = new OkHttpClient();
        String url = c.getString(R.string.url_get_device_code);
        Response response = null;

        JSONObject json = new JSONObject();

        try{

            json.put("client_id", c.getString(R.string.api_key));

            RequestBody rb = RequestBody.create(JSON, json.toString());

            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .url(url)
                    .post(rb)
                    .build();

            response = client.newCall(request).execute();

            return response;
        } catch (IOException e) {

        }
        catch(JSONException j){

        }
        return response;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        ProgressBar progressBar = (ProgressBar) a.findViewById(R.id.login_progress);
        progressBar.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(Response result) {

        if(result == null){
            return;
        }


        try {

            String response = result.body().string();

            Log.e("sanjay", response);

            JSONObject json = new JSONObject(response);
            String userCode = json.getString(c.getString(R.string.json_user_code));
            String deviceCode = json.getString(c.getString(R.string.json_device_code));
            String verificationUrl = json.getString(c.getString(R.string.json_verification_url));
            int expiresIn = Integer.parseInt(json.getString(c.getString(R.string.json_expires_in)));
            int interval = Integer.parseInt(json.getString(c.getString(R.string.json_interval)));

            codeView.setText(userCode);
            openTraktButton.setVisibility(View.VISIBLE);

            ClipboardManager clipboard = (ClipboardManager) a.getSystemService(a.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("code", userCode);
            clipboard.setPrimaryClip(clip);

            Toast toast = Toast.makeText(c, "Code Copied to Clipboard", Toast.LENGTH_SHORT);
            toast.show();

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(c.getString(R.string.json_user_code), userCode);
            editor.putString(c.getString(R.string.json_device_code), deviceCode);
            editor.putString(c.getString(R.string.json_verification_url), verificationUrl);
            editor.putInt(c.getString(R.string.json_expires_in), expiresIn);
            editor.putInt(c.getString(R.string.json_interval), interval);
            editor.commit();

            new PollForAuth().execute(a);

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
}

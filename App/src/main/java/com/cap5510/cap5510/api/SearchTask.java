package com.cap5510.cap5510.api;


import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cap5510.cap5510.APITestActivity;
import com.cap5510.cap5510.MainActivity;
import com.cap5510.cap5510.MovieInfoActivity;
import com.cap5510.cap5510.MovieInfoFragment;
import com.cap5510.cap5510.R;
import com.cap5510.cap5510.SearchActivity;
import com.cap5510.cap5510.api.objects.AsyncTaskInput;

import org.json.JSONArray;
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
            Log.e("sanjay", response);

            if(a instanceof APITestActivity) {
                Log.e("sanjay", "called from API Test");
                TextView tv = (TextView) a.findViewById(R.id.searchResults);
                tv.setText(response);
            }
            else if(a instanceof SearchActivity){
                Log.e("sanjay", "called from Search");

                JSONArray results = new JSONArray(response);

                TableLayout resultsTable = (TableLayout) a.findViewById(R.id.search_result_table);

                if(results.length() == 0){
                    TextView noResults = new TextView(a);
                    noResults.setText("No Results");

                    TableRow row = new TableRow(a);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                    row.setLayoutParams(lp);

                    row.addView(noResults);

                    resultsTable.addView(row);
                }

                for(int i = 0; i < results.length(); i++){
                    JSONObject item = results.getJSONObject(i);

                    String type = item.getString("type");
                    JSONObject subItem = item.getJSONObject(type);

                    switch(type){
                        case "movie":
                            createSearchResultMovieItem(subItem, resultsTable);
                            break;
                        case "show":
                            break;
                        case "episode":
                            break;
                        case "person":
                            break;
                        case "list":
                            break;
                        default:
                            break;

                    }
                }


            }

        }
        catch(IOException e){
            Log.e("sanjay", "IO Exception when postExecute ... ");
            Log.e("sanjay", e.getMessage());
        }
        catch(JSONException j){
            Log.e("sanjay", "JSON Exception when postExecute ... ");
            Log.e("sanjay", j.getMessage());
        }
    }

    private void createSearchResultMovieItem(JSONObject item, TableLayout resultsTable){
        ImageView iv = new ImageView(a);

        String imageURL = "https://placeholdit.imgix.net/~text?txtsize=33&txt=poster&w=300&h=450";
        try {
            imageURL = item.getJSONObject("images").getJSONObject("poster").getString("thumb");
        }
        catch(JSONException e){
            Log.e("sanjay", "failed to get poster");
            Log.e("sanjay", e.getMessage());
        }

        int traktID = -1;
        try {
            traktID = item.getJSONObject("ids").getInt("trakt");
        }
        catch(JSONException e){
            Log.e("sanjay", "failed to get trakt ID");
            Log.e("sanjay", e.getMessage());
        }

        new DownloadImageTask(iv).execute(imageURL);

        LinearLayout ll = new LinearLayout(a);
        ll.setOrientation(LinearLayout.VERTICAL);

        TextView title = new TextView(a);
        title.setEllipsize(null);
        title.setHorizontallyScrolling(false);
        title.setMaxLines(100);
        title.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView subtitle = new TextView(a);
        subtitle.setEllipsize(null);
        subtitle.setHorizontallyScrolling(false);
        subtitle.setMaxLines(100);
        subtitle.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView info = new TextView(a);
        info.setEllipsize(null);
        info.setHorizontallyScrolling(false);
        info.setMaxLines(100);
        info.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        if (Build.VERSION.SDK_INT < 23) {
            info.setTextAppearance(c, android.R.style.TextAppearance_Small);
            subtitle.setTextAppearance(c, android.R.style.TextAppearance_Medium);
            title.setTextAppearance(c, android.R.style.TextAppearance_Large);

        } else {
            info.setTextAppearance(android.R.style.TextAppearance_Small);
            subtitle.setTextAppearance(android.R.style.TextAppearance_Medium);
            title.setTextAppearance(android.R.style.TextAppearance_Large);
        }

        Integer year = null;
        String subTitleString = "";
        String infoString = "";
        String titleString = "";

        try{
            titleString = item.getString("title");
        }
        catch(JSONException e){
            Log.e("sanjay", "failed to get title");
            Log.e("sanjay", e.getMessage());
        }

        try{
            year = item.getInt("year");
        }
        catch(JSONException e){
            Log.e("sanjay", "failed to get year");
            Log.e("sanjay", e.getMessage());
        }

        try{
            infoString = item.getString("overview");
        }
        catch(JSONException e){
            Log.e("sanjay", "failed to get overview");
            Log.e("sanjay", e.getMessage());
        }

        if(year != null){
            subTitleString = String.valueOf(year);
        }

        title.setText(titleString);
        subtitle.setText(subTitleString);
        info.setText(infoString);

        TableRow row = new TableRow(a);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);

        ll.addView(title);
        ll.addView(subtitle);
        ll.addView(info);

        row.addView(iv);
        row.addView(ll);
        row.setClickable(true);

        row.setOnClickListener(new searchResultOnClickListener(a, MainActivity.class, traktID));

        resultsTable.addView(row);
    }


    private class searchResultOnClickListener implements View.OnClickListener{

        Activity a;
        Class<MainActivity> n;
        int id;

        public searchResultOnClickListener(Activity a, Class<MainActivity> nextActivity, int id){
            this.a = a;
            this.n = nextActivity;
            this.id = id;
        }

        @Override
        public void onClick(View v){
            Intent intent = new Intent();
            intent.putExtra("traktID", id);

            a.setResult(a.RESULT_OK, intent);

            a.finish();
        }
    }


}

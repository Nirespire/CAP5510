package com.cap5510.cap5510.api;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cap5510.cap5510.R;
import com.cap5510.cap5510.api.objects.AsyncTaskInput;
import com.cap5510.cap5510.api.objects.standard_media_objects.Episode;
import com.cap5510.cap5510.api.objects.standard_media_objects.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetCommentsEpisode extends AsyncTask<AsyncTaskInput, Integer, Response> {

    Activity a = null;
    Context c = null;
    Episode episode = null;

    @Override
    protected Response doInBackground(AsyncTaskInput... params) {
        a = params[0].getActivity();
        c = a.getApplicationContext();
        episode = ((Episode) params[0].getPayload());


        OkHttpClient client = new OkHttpClient();
        String url = c.getString(R.string.url_shows_partial) + Integer.toString(episode.getID()) + "/seasons/" +
                Integer.toString(episode.getSeason()) + "/episodes/" + Integer.toString(episode.getNumber()) +"/comments?extended=images";
        Log.d("comment url", url);
        String apiKey = c.getString(R.string.api_key);
        Response response = null;

        try {
            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("trakt-api-version", "2")
                    .addHeader("trakt-api-key", apiKey)
                    .url(url)
                    .build();

            response = client.newCall(request).execute();
        } catch (Exception e) {
        }

        return response;
    }

    @Override
    protected void onPostExecute(Response result) {

        try {

            String response = result.body().string();
            Log.d("episodecomments", response);



            JSONArray comments = new JSONArray(response);


            TableLayout commentTable = (TableLayout) a.findViewById(R.id.episodeCommentTable);

            if(comments.length() > 0){

                for(int i = 0; i < comments.length(); i++) {
                    JSONObject currentComment = comments.getJSONObject(i);
                    JSONObject currentUser =  currentComment.getJSONObject("user");



                    TableRow metadataRow = new TableRow(c);
                    TableLayout.LayoutParams tp = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    tp.weight = 1;
                    metadataRow.setLayoutParams(tp);
                    metadataRow.setBackgroundColor(a.getResources().getColor(R.color.colorAccent));
                    metadataRow.setPadding(5,5,5,5);

                    CircleImageView profPic = new CircleImageView(c);
                    int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, c.getResources().getDisplayMetrics());
                    int width = (int)  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, c.getResources().getDisplayMetrics());
                    TableRow.LayoutParams trp = new TableRow.LayoutParams(width, height);
                    profPic.setLayoutParams(trp);
                    if(currentUser.has("images")){
                        new DownloadImageTask(profPic).execute(currentUser.getJSONObject("images").getJSONObject("avatar").getString("full"));
                    }else{
                        profPic.setImageResource(R.drawable.profile);
                    }

                    TableLayout usernameTable = new TableLayout(c);
                    tp = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    tp.weight = 0.1f;
//                    tl.setLayoutParams(tp);
                    TableRow nameRow = new TableRow(c);
                    TableRow dateRow = new TableRow(c);

                    TextView username = new TextView(c);
                    username.setText(currentUser.getString("username"));
                    username.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    //username.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    nameRow.addView(username);
                    usernameTable.addView(nameRow);

                    TextView date = new TextView(c);
                    date.setText("March 31, 2016 11:00 AM");
                    dateRow.addView(date);
                    usernameTable.addView(dateRow);

                    TableRow likesAndRepliesRow = new TableRow(c);

//                    ImageButton likeButton = new ImageButton(c);
//                    width = (int)  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, c.getResources().getDisplayMetrics());
//                    tp = new TableLayout.LayoutParams(width, width);
//                    likeButton.setImageResource(R.drawable.small_like);
//                    likeButton.setAdjustViewBounds(true);
//                    likeButton.setLayoutParams(tp);
//                    likeButton.setBackgroundColor(a.getResources().getColor(android.R.color.background_light));
//                    likesAndRepliesRow.addView(likeButton);

//                    likesAndRepliesRow = new TableRow(c);

                    TextView likeNumber = new TextView(c);
                    if(currentComment.getInt("likes") != 1) {
                        likeNumber.setText(currentComment.getString("likes") + " likes");
                    }else{
                        likeNumber.setText(currentComment.getString("likes") + " like");
                    }

                    TextView repliesNumber = new TextView(c);
                    if(currentComment.getInt("replies") != 1){
                        repliesNumber.setText(currentComment.getString("replies")+ " replies");
                    }else {
                        repliesNumber.setText(currentComment.getString("replies")+ " reply");
                    }
                    likesAndRepliesRow.addView(likeNumber);
                    //likeNumber.setTextColor(a.getResources().getColor(R.color.colorPrimaryDark));
                    likesAndRepliesRow.addView(repliesNumber);




                    usernameTable.addView(likesAndRepliesRow);
                    metadataRow.addView(profPic);
                    metadataRow.addView(usernameTable);
                    //metadataRow.addView(likesAndReplies);
                    commentTable.addView(metadataRow);

                    TableRow commentRow = new TableRow(c);
                    tp = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    //commentRow.setLayoutParams(tp);
                    TextView commentText = new TextView(c);
                    width = (int)  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 185, c.getResources().getDisplayMetrics());
                    LinearLayout.LayoutParams lltp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    commentText.setPadding(50, 10, 50, 45);
                    commentText.setText(currentComment.getString("comment"));
                    commentText.setTextColor(a.getResources().getColor(R.color.colorPrimaryDark));
                    commentText.setLayoutParams(lltp);

                    commentTable.addView(commentText);





                }

            }


        }
        catch(IOException e){
            Log.e("stevie", "IO Exception");
            Log.e("stevie", e.getMessage());
        }
        catch(JSONException j){
            Log.e("stevie", "JSON Exception");
            Log.e("stevie", j.getMessage());
        }

    }


}


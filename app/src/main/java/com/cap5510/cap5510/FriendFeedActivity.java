package com.cap5510.cap5510;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.cap5510.cap5510.api.GetFriendWatchedHistoryTask;
import com.cap5510.cap5510.api.DownloadImageTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendFeedActivity extends AppCompatActivity{
    TextView name;
    CircleImageView picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        name = (TextView)findViewById(R.id.profile_name);
        picture = (CircleImageView)findViewById(R.id.profile_image);

        Bundle extras = getIntent().getExtras();
        //Bitmap bmp = (Bitmap) extras.getParcelable("picture");

       /* byte[] b = extras.getByteArray("picture");

        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);*/
        String image_url = extras.getString("picture");
        String username = extras.getString("username");
        new DownloadImageTask(picture).execute(image_url);
        name.setText(username);

       // new FriendProfileTask(username).execute(this);
        new GetFriendWatchedHistoryTask(username).execute(this);


        //picture.setImageBitmap(bmp);

        //generateCode();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
   /*private void generateCode(){
      new FriendsTask().execute(this);
    }*/


}

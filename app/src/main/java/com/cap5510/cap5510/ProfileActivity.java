package com.cap5510.cap5510;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;


import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity {
   // private DrawerLayout mDrawerLayout;
   // private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


       /*
         Here is where the code for the navigation drawer ends
        */

        //This commented code is for a button for the settings, you can copy it and put it anywhere.

//        ((Button)findViewById(R.id.settings)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent settingsActivity = new Intent(getApplicationContext(), SettingsActivity.class);
//                startActivity(settingsActivity);
//
//            }
//
//        });

         //This commented code is for a button to show the friend feed activity.
//        ((Button)findViewById(R.id.feed)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent settingsActivity = new Intent(getApplicationContext(), FriendFeedActivity.class);
//                startActivity(settingsActivity);
//
//            }
//
//        });

    }

}

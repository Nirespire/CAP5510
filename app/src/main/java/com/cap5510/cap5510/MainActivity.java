package com.cap5510.cap5510;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.cap5510.cap5510.api.GetWatchedTvShows;

public class MainActivity extends AppCompatActivity {
    String TITLES[] = {"Calendar","Profile","Recommendation","Watchlist","EpisodeInfo","MovieInfo","FriendFeed","Showinfo", "APITest"};
    String NAME = "JPriya";
    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        toolbar.setNavigationOnClickListener(this);


        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tvEps");
        tabSpec.setContent(R.id.tabEpisodes);
        tabSpec.setIndicator("Episodes");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("movies");
        tabSpec.setContent(R.id.tabMovies);
        tabSpec.setIndicator("movies");
        tabHost.addTab(tabSpec);

        new GetWatchedTvShows().execute(this);


        /*
      Here is where the code for the navigation drawer begins
    */
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size


        mAdapter = new NavAdapter(TITLES,NAME,getApplicationContext());

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        //Handle navigation drawer click events using onTouchItemListener
        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });


        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                Context c = getApplicationContext();
                Intent intent = null;

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Drawer.closeDrawers();


                    switch(recyclerView.indexOfChild(child)) {
                        case 1:
                            intent = new Intent(c,CalendarActivity.class);
                            break;
                        case 2:
                            intent = new Intent(c,ProfileActivity.class);
                            break;
                        case 3:
                            intent = new Intent(c,RecommendationActivity.class);
                            break;
                        case 4:
                            intent = new Intent(c,WatchlistActivity.class);
                            break;
                        case 5:
                            intent = new Intent(c,EpisodeInfoActivity.class);
                            break;
                        case 6:
                            intent = new Intent(c,MovieInfoActivity.class);
                            break;
                        case 7:
                            intent = new Intent(c,FriendFeedActivity.class);
                            break;
                        case 8:
                            intent = new Intent(c,ShowInfoActivity.class);
//                            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
//                            tx.replace(R.id., Fragment.instantiate(MainActivity.this, fragments[0]));
//                            tx.commit();
                            break;
                        case 9:
                            intent = new Intent(c, APITestActivity.class);
                            break;
                        default:
                            Toast.makeText(MainActivity.this, "The Item Clicked is: " + recyclerView.indexOfChild(child), Toast.LENGTH_SHORT).show();



                    }
                    startActivity(intent);
                    return true;

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }


        });

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager




        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }



        }; // Drawer Toggle Object Made


        Drawer.addDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();
    }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void goToSignInPage(MenuItem item){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
            return true;
        }
//        if (id == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }


        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }



}

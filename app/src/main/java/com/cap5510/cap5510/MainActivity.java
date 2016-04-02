package com.cap5510.cap5510;

import android.app.SearchManager;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.cap5510.cap5510.api.GetWatchedTvShows;
import com.cap5510.cap5510.api.UserProfileTask;
import com.cap5510.cap5510.api.objects.TimeConversion;
import com.cap5510.cap5510.api.objects.standard_media_objects.Episode;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    String TITLES[] = {"Queue","Calendar","Profile","Recommendation","Watchlist","EpisodeInfo","MovieInfo","FriendFeed","Showinfo", "APITest"};
    String NAME = "";
    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    ActionBarDrawerToggle mDrawerToggle;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Home");

       /* String timestr = "2016-03-30 03:55:00";

        Date r = TimeConversion.getEasternTime(timestr);

        Log.e("aishatxy",r.toString());*/

        setupActionBar();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        if (getSupportFragmentManager().findFragmentById(R.id.frame_container) == null) {

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            String setting = sharedPref.getString(getString(R.string.pref_start_page_type), "-1");

            Log.e("sanjay", setting);

            //Fragment f;
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();

            if(setting.equals("1")) {
                MainFragment f = new MainFragment();
                MainActivity.this.setTitle("Home");
                tx.add(R.id.frame_container, f, "MainFragment")
                        .addToBackStack(null)
                        .commit();
            }
             if(setting.equals("2")){
                MainCalendarFragment g = new MainCalendarFragment();
                MainActivity.this.setTitle("Home");
                tx.add(R.id.frame_container, g, "CalendarFragment")
                        .addToBackStack(null)
                        .commit();
            }
           MainFragment mainFrag = new MainFragment();
            MainActivity.this.setTitle("Queue");
            tx.add(R.id.frame_container, mainFrag, "MainFragment")
                    .addToBackStack(null)
                    .commit();



        }

        SharedPreferences sharedPref = getSharedPreferences("api", Context.MODE_PRIVATE);
        NAME = sharedPref.getString("profile_name","");
        Log.e("aishatf",NAME);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        toolbar.setNavigationOnClickListener(this);

        /*TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tvEps");
        tabSpec.setContent(R.id.tabEpisodes);
        tabSpec.setIndicator("Episodes");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("movies");
        tabSpec.setContent(R.id.tabMovies);
        tabSpec.setIndicator("movies");
        tabHost.addTab(tabSpec);*/

        //new GetWatchedTvShows().execute(this);


        //Here is where the code for the navigation drawer begins
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
                FragmentTransaction tx = getSupportFragmentManager().beginTransaction();

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Drawer.closeDrawers();


                    switch(recyclerView.indexOfChild(child)) {
                        case 1:
                            //intent = new Intent(c,CalendarActivity.class);
                            MainFragment mainFrag = new MainFragment();
                            MainActivity.this.setTitle("Home");
                            tx.remove(getSupportFragmentManager().findFragmentById(R.id.frame_container))
                                    .replace(R.id.frame_container, mainFrag, "MainFragment")
                                    .addToBackStack(null)
                                    .commit();
                            break;
                        case 2:
                            //intent = new Intent(c,CalendarActivity.class);
                            MainActivity.this.setTitle("Calendar");
                            MainCalendarFragment calendarFrag = new MainCalendarFragment();
                            tx.remove(getSupportFragmentManager().findFragmentById(R.id.frame_container))
                            .replace(R.id.frame_container, calendarFrag, "CalendarFragment")
                                    .addToBackStack(null)
                                    .commit();
                            break;
                        case 3:
                            //intent = new Intent(c,ProfileActivity.class);
                            MainActivity.this.setTitle("Profile");
                            ProfileFragment profileFrag = new ProfileFragment();
                            tx.remove(getSupportFragmentManager().findFragmentById(R.id.frame_container))
                            .replace(R.id.frame_container, profileFrag, "ProfileFragment")
                                    .addToBackStack(null)
                                    .commit();
                            break;
                        case 4:
                            //intent = new Intent(c,RecommendationActivity.class);
                            MainActivity.this.setTitle("Recommendation");
                            RecommendationFragment recommendFrag = new RecommendationFragment();
                            tx.remove(getSupportFragmentManager().findFragmentById(R.id.frame_container))
                                    .replace(R.id.frame_container, recommendFrag, "RecommendationFragment")
                                    .addToBackStack(null)
                                    .commit();
                            break;
                        case 5:
                            //intent = new Intent(c,WatchlistActivity.class);
                            MainActivity.this.setTitle("Watchlist");
                            WatchlistFragment watchlistFrag = new WatchlistFragment();
                            tx.remove(getSupportFragmentManager().findFragmentById(R.id.frame_container))
                                    .replace(R.id.frame_container, watchlistFrag, "WatchlistFragment")
                                    .addToBackStack(null)
                                    .commit();
                            break;
                        case 6:
                            //intent = new Intent(c,EpisodeInfoActivity.class);
                            MainActivity.this.setTitle("Episode Information");
                            EpisodeInfoFragment episodeinfoFrag = new EpisodeInfoFragment();
                            tx.remove(getSupportFragmentManager().findFragmentById(R.id.frame_container))
                                    .replace(R.id.frame_container, episodeinfoFrag, "EpisodeInfoFragment")
                                    .addToBackStack(null)
                                    .commit();
                            break;
                        case 7:
                           // intent = new Intent(c,MovieInfoActivity.class);
                            //startActivity(intent);
                            MainActivity.this.setTitle("Movie Information");
                            MovieInfoFragment movieinfoFrag = new MovieInfoFragment();
                            tx.remove(getSupportFragmentManager().findFragmentById(R.id.frame_container))
                                    .replace(R.id.frame_container, movieinfoFrag, "MovieInfoFragment")
                                    .addToBackStack(null)
                                    .commit();
                            break;
                        case 8:
                            //intent = new Intent(c,FriendFeedActivity.class);
                            MainActivity.this.setTitle("Friends");
                            FriendFeedFragment friendfeedFrag = new FriendFeedFragment();

                            //tx.replace(R.id.frame_container, Fragment.instantiate(MainActivity.this, "com.cap5510.cap5510.FriendFeedFragment"))
                            tx.remove(getSupportFragmentManager().findFragmentById(R.id.frame_container))
                            .replace(R.id.frame_container, friendfeedFrag, "FriendFeedFragment")
                            .addToBackStack(null)
                            .commit();
                            break;
                        case 9:
                            //intent = new Intent(c,ShowInfoActivity.class);
                           // startActivity(intent);
                            MainActivity.this.setTitle("Show Information");
                            ShowInfoFragment showinfoFrag = new ShowInfoFragment();
                            tx.remove(getSupportFragmentManager().findFragmentById(R.id.frame_container))
                                    .replace(R.id.frame_container, showinfoFrag, "ShowInfoFragment")
                                    .addToBackStack(null)
                                    .commit();
                            break;
                        case 10:
                            intent = new Intent(c, APITestActivity.class);
                            startActivity(intent);
                            break;
                        default:
                            Toast.makeText(MainActivity.this, "The Item Clicked is: " + recyclerView.indexOfChild(child), Toast.LENGTH_SHORT).show();



                    }

                    //startActivity(intent);

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
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String text){
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query){

        final Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
        // add query to the Intent Extras
        searchIntent.putExtra(SearchManager.QUERY, query);
        startActivityForResult(searchIntent, 1);

        searchView.setQuery("", false);
        searchView.setIconified(true);
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

    @Override
    protected void onNewIntent(Intent intent){
        handleIntent(intent);
    }

    /*
        Search bar sends the query to the main activity, which then passes it to the SearchActivity
        and waits for a result to come back
        TODO: this this is unnecessary now because of onQueryListener
     */
    private void handleIntent(Intent intent) {
        // Get the intent, verify the action and get the query
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            Log.e("sanjay", "search intent!");
            String query = intent.getStringExtra(SearchManager.QUERY);
            // manually launch the real search activity
            final Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
            // add query to the Intent Extras
            searchIntent.putExtra(SearchManager.QUERY, query);
            startActivityForResult(searchIntent, 1);
        }
    }

    /*
        Handle result when item is clicked in search results and it is closed
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();

        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                int traktID = data.getIntExtra("traktID", -1);
                Log.d("clicked trakt id", String.valueOf(traktID));

                Bundle idInfo = new Bundle();
                idInfo.putInt("traktID", traktID);

                MainActivity.this.setTitle("Movie Information");
                MovieInfoFragment movieInfoFrag = new MovieInfoFragment();
                movieInfoFrag.setArguments(idInfo);
                tx.remove(getSupportFragmentManager().findFragmentById(R.id.frame_container))
                    .replace(R.id.frame_container, movieInfoFrag, "MovieInfoFragment")
                    .addToBackStack(null)
                    .commitAllowingStateLoss();

            }
        }
    }



}

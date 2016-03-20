package com.cap5510.cap5510;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cap5510.cap5510.api.GetWatchlistTask;

public class APITestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apitest);
    }

    public void testGetWatchList(View v){
        new GetWatchlistTask().execute(this);
    }
}

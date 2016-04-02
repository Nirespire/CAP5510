package com.cap5510.cap5510;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cap5510.cap5510.api.CalendarTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vega on 4/1/2016.
 */
public class TuesdayFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";


    private  int mPage;
    private int count=0;

    public static TuesdayFragment newInstance(int page) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TuesdayFragment fragment = new TuesdayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        count++;

        Log.e("aishxy", "" + count);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.content_calendar,container, false);

        CalendarFragment fragment = new CalendarFragment();
        int offset = fragment.getOffset(3);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, offset);

        Date dNow = cal.getTime();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

        String date =  ft.format(dNow);

        new CalendarTask(date).execute(this);


        return root;
    }

}

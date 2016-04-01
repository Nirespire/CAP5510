package com.cap5510.cap5510;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vega on 3/23/2016.
 */
public class CalendarFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";


    private  int mPage;
    private int count=0;

    public static CalendarFragment newInstance(int page) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        CalendarFragment fragment = new CalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
            count++;
        }
        else{
            mPage = 1;
            count++;
        }

        Log.e("aishxy", "" + count);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.content_calendar,container, false);

        //get the date of the fragment selected
        int offset = getOffset(mPage);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, offset);

        Date dNow = cal.getTime();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

        String date =  ft.format(dNow);
        Log.e("aishatw",date);

        EventView v = (EventView)root.findViewById(R.id.sevenpm8pmevent);
        v.setVisibility(View.VISIBLE);

        v.setTitle("Title" + mPage);
        return root;
    }

    private int getOffset(int page) {

        int offset = 0;

        Date now = new Date();
        String current = now.toString();

        String[] getDay = current.split(" ");

        String day = getDay[0];

        switch (day) {

            case "Sun":
                offset = page-1;

                break;
            case "Mon":
                offset = page-2;
                break;
            case "Tue":
                offset = page-3;
                break;
            case "Wed":
                offset = page-4;
                break;
            case "Thu":
                offset=page-5;
                break;
            case "Fri":
                offset=page-6;
                break;
            case "Sat":
                offset=page-7;
                break;
            default:
                break;
        }
        Log.e("aishatw",""+offset);
        return offset;
    }
}

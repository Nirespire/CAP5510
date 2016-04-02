package com.cap5510.cap5510;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vega on 3/31/2016.
 */
public class MainCalendarFragment extends Fragment {

    private TabLayout tabLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.content_calendar_new, container, false);
        ViewPager viewPager = (ViewPager)root.findViewById(R.id.viewpager);

        Log.e("aishath","i am called");
        getActivity().setTitle("Calendar");

        tabLayout = (TabLayout)root.findViewById(R.id.sliding_tabs);

         String tabDate[] = getDays();
        CalendarFragmentAdapter calendarAdapter = new CalendarFragmentAdapter(getActivity().getSupportFragmentManager(),
                getContext(),tabDate);
        viewPager.setAdapter(calendarAdapter);

        // Give the TabLayout the ViewPager

        tabLayout.setupWithViewPager(viewPager);// Iterate over all tabs and set the custom view

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(calendarAdapter.getTabView(i));
        }

        setTabSelected(tabLayout);

        return root;
    }

    private String[] getDays() {
       //ADD ONE TO END DATE TO PREVENT ZEROS
        String[] result = new String[7];
        int offset = 0;
        int enddate = 29;
        Date now = new Date();
        String current = now.toString();

        String[]getDay = current.split(" ");

        String day = getDay[0];

        String month = getDay[1];


        String months[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String month30[] = {"Apr","Jun","Sep","Nov"};

        if(Arrays.asList(month30).contains(month)) {
            enddate = 31;
        }else{
            enddate = 32;
        }

        switch(day) {
            case "Mon":
                offset = 1;
                break;
            case "Tue":
                offset = 2;
                break;
            case "Wed":
                offset = 3;
                break;
            case "Thu":
                offset = 4;

                break;
            case "Fri":
                offset = 5;
                break;
            case "Sat":
                offset = 6;
                break;
            default:
                offset=0;
        }
        int current_day = Integer.parseInt(getDay[2]);

        int sunday = (((current_day - offset)%enddate)+enddate)%enddate;

        if(sunday > current_day)
        {
            int index = Arrays.asList(months).indexOf(month);
            Log.e("aishatb",""+index);
            String prev_month = months[(((index -1)%months.length)+months.length)%months.length];

            if(Arrays.asList(month30).contains(prev_month)) {
                enddate = 31;
            }else{
                enddate = 32;
            }
        }
        result[0] = ""+sunday;

        int next = 1;
        int monday = (sunday + next)%enddate;
        next++;
        if(monday == 0){
            monday++;
            next=1;
            sunday = monday;

        }
        else if(monday==enddate-1)
        {
            if(Arrays.asList(month30).contains(month)) {
                enddate = 31;
            }else{
                enddate = 32;
            }
        }
        result[1] = ""+monday;
        int tuesday =(sunday + next)%enddate;
        next++;
        if(tuesday == 0){
            tuesday++;
            next=1;
            sunday = tuesday;

        }
        else if(tuesday==enddate-1)
        {
            if(Arrays.asList(month30).contains(month)) {
                enddate = 31;
            }else{
                enddate = 32;
            }
        }
        result[2] = ""+tuesday;
        int wednesday =(sunday + next)%enddate;
        next++;
        if(wednesday == 0){
            wednesday++;
            next=1;
            sunday = wednesday;

        }
        else if(wednesday==enddate-1)
        {
            if(Arrays.asList(month30).contains(month)) {
                enddate = 31;
            }else{
                enddate = 32;
            }
        }
        result[3] = ""+wednesday;
        int thursday = (sunday + next)%enddate;
        next++;
        if(thursday == 0){
            thursday++;
            next=1;
            sunday = thursday;

        }
        else if(thursday==enddate-1)
        {
            if(Arrays.asList(month30).contains(month)) {
                enddate = 31;
            }else{
                enddate = 32;
            }
        }
        result[4] = ""+thursday;
        int friday =(sunday + next)%enddate;
        next++;
        if(friday == 0){
            friday++;
            next=1;
            sunday = friday;

        }
        else if(friday==enddate-1)
        {
            if(Arrays.asList(month30).contains(month)) {
                enddate = 31;
            }else{
                enddate = 32;
            }
        }
        result[5] = ""+friday;
        int saturday = (sunday + next)%enddate;
        next++;
        if(saturday == 0){
            saturday++;

        }
        else if(saturday==enddate-1)
        {
            if(Arrays.asList(month30).contains(month)) {
                enddate = 31;
            }else{
                enddate = 32;
            }
        }
        result[6] = ""+saturday;

        /*Calendar cal = Calendar.getInstance();
        // cal.add(Calendar.DATE, -2);

        Date dNow = cal.getTime();
        SimpleDateFormat ft = new SimpleDateFormat("dd");

        String date =  ft.format(dNow);*/

        return result;
    }

    private void setTabSelected(TabLayout pager) {
        //ADD ONE TO END DATE TO PREVENT ZEROS
        Date now = new Date();
        String current = now.toString();

        String[] getDay = current.split(" ");

        String day = getDay[0];

        switch (day) {

            case "Sun":
                pager.getTabAt(0).select();
                break;
            case "Mon":
                pager.getTabAt(1).select();
                break;
            case "Tue":
                pager.getTabAt(2).select();
                break;
            case "Wed":
                pager.getTabAt(3).select();
                break;
            case "Thu":
                pager.getTabAt(4).select();
                break;
            case "Fri":
                pager.getTabAt(5).select();
                break;
            case "Sat":
                pager.getTabAt(6).select();
                break;
            default:
                break;
        }
    }

    public TabLayout getLayout() {
        return tabLayout;
    }

}

package com.cap5510.cap5510;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Vega on 3/31/2016.
 */
public class CalendarFragmentAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 7;
    private String tabDay[] = new String[] { "Sun", "Mon", "Tue","Wed","Thu","Fri","Sat" };
    //private String tabDate[] = new String[]{"27","28","29","30","31","01","02"};
    private String tabDate[];
    private Context context;

    public CalendarFragmentAdapter(FragmentManager fm, Context context,String[]dates) {
        super(fm);
        this.context = context;
        tabDate = dates;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Log.e("aishatu", "" + position);
        switch(position) {
            case 0:
                return SundayFragment.newInstance(position+1);
            case 1:
                return MondayFragment.newInstance(position+1);
            case 2:
                return TuesdayFragment.newInstance(position+1);
            case 3:
                return WednesdayFragment.newInstance(position+1);
            case 4:
                return ThursdayFragment.newInstance(position+1);
            case 5:
                return FridayFragment.newInstance(position+1);
            case 6:
                return SaturdayFragment.newInstance(position+1);

            default:
                return CalendarFragment.newInstance(position+1);
        }


    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabDay[position];
    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.dayofweek);
        tv.setText(tabDay[position]);
        TextView tv1 = (TextView) v.findViewById(R.id.dateofweek);
        tv1.setText(tabDate[position]);
        return v;
    }
}
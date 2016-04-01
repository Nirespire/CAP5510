package com.cap5510.cap5510;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Vega on 3/30/2016.
 */
public class EventView extends LinearLayout {

        private View mValue;
        private TextView mTime;
        private TextView mTitle;

        public EventView(Context context, AttributeSet attrs) {
            super(context, attrs);

            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.EventView, 0, 0);
           // String timeText = a.getString(R.styleable.EventView_timeText);
            String eventText = a.getString(R.styleable.EventView_eventText);
            a.recycle();

            setOrientation(LinearLayout.VERTICAL);
            setBackground(ContextCompat.getDrawable(getContext(),R.drawable.coloredborders));
            setGravity(Gravity.CENTER_VERTICAL);

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.event_view, this, true);



            /*TextView title = (TextView) getChildAt(0);
            title.setText(titleText);

            mValue = getChildAt(1);
            mValue.setBackgroundColor(valueColor);

            mImage = (ImageView) getChildAt(2);*/

           // mTime = (TextView)getChildAt(0);
           // mValue = (View)getChildAt(1);
            mTitle = (TextView)getChildAt(0);

           // mTime.setText(timeText);
            mTitle.setText(eventText);
        }

        public EventView(Context context) {
            this(context, null);
        }

        /*public void setValueColor(int color) {
            mValue.setBackgroundColor(color);
        }

        public void setImageVisible(boolean visible) {
            mImage.setVisibility(visible ? View.VISIBLE : View.GONE);
        }*/
        /*
        public void setEventTime(String start,String end){
            String time = start+" - "+end;
            mTime.setText(time);
        }
        */
        public void setTitle(String title) {
            mTitle.setText(title);
        }
}

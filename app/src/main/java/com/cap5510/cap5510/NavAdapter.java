package com.cap5510.cap5510;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.view.View;

/**
 * Created by hayeesha on 3/10/16.
 */
public class NavAdapter extends RecyclerView.Adapter<NavAdapter.ViewHolder> {

    private String NavTitles[];
    private String name;
    private static final int firstItem = 0;
    private static final int secondItem = 1;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;

        TextView textView;
        TextView Name;



        public ViewHolder(View itemView,int ViewType) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);


            // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created

            if(ViewType == secondItem) {
                textView = (TextView) itemView.findViewById(R.id.rowText); // Creating TextView object with the id of textView from item_row.xml
                Holderid = 1;                                               // setting holder id as 1 as the object being populated are of type item row
            }
            else{


                Name = (TextView) itemView.findViewById(R.id.name);         // Creating Text View object from header.xml for name
                Holderid = 0;                                                // Setting holder id = 0 as the object being populated are of type header view
            }
        }


    }

    public NavAdapter(String[]titles,String mname) {
        NavTitles = titles;
        name = mname;

    }

    @Override
    public NavAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == secondItem) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false); //Inflating the layout

            ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object

            //inflate your layout and pass it to view holder

        } else if (viewType == firstItem) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item,parent,false); //Inflating the layout

            ViewHolder vhHeader = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

            return vhHeader; //returning the object created


        }
        return null;

    }

    //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
    // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
    // which view type is being created 1 for item row
    @Override
    public void onBindViewHolder(NavAdapter.ViewHolder holder, int position) {
        if(holder.Holderid ==1) {                              // as the list view is going to be called after the header view so we decrement the
            // position by 1 and pass it to the holder while setting the text and image
            holder.textView.setText(NavTitles[position - 1]); // Setting the Text with the array of our Titles

        }
        else{


            holder.Name.setText(name);

        }
    }

    @Override
    public int getItemCount() {
        return NavTitles.length+1; // the number of items in the list will be +1 the titles including the header view.
    }

    // Witht the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return firstItem;

        return secondItem;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }



}


package com.cap5510.cap5510;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cap5510.cap5510.api.DownloadImageTask;

/**
 * Created by Vega on 3/22/2016.
 */
public class FriendAdapter extends ArrayAdapter<Friend> {

    Context context;
    int layoutResourceId;
    Friend data[] = null;

    public FriendAdapter(Context context, int layoutResourceId, Friend[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FriendHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new FriendHolder();
            holder.friendpic = (ImageView) row.findViewById(R.id.friendpic);
            holder.username = (TextView) row.findViewById(R.id.friendname);

            row.setTag(holder);
        } else {
            holder = (FriendHolder) row.getTag();
        }

        Friend friend = data[position];
        holder.username.setText(friend.username);
        new DownloadImageTask(holder.friendpic).execute(friend.profile_icon);
       // holder.friendpic.setImageResource(friend.profile_icon);

        return row;
    }

    static class FriendHolder {
        ImageView friendpic;
        TextView username;
    }
}

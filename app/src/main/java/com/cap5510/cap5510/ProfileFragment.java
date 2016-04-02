package com.cap5510.cap5510;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cap5510.cap5510.api.DownloadImageTask;
import com.cap5510.cap5510.api.GetFriendWatchedHistoryTask;
import com.cap5510.cap5510.api.MyProfileTask;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Vega on 3/23/2016.
 */
public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.content_profile, null);

        getActivity().setTitle("Profile");
       // new MyProfileTask().execute(this.getActivity());
        SharedPreferences sharedPref = getActivity().getSharedPreferences("api", Context.MODE_PRIVATE);
        String current_user = sharedPref.getString("profile_name","");
        Log.e("aishatf", current_user);
        String icon = sharedPref.getString("profile_pix", "");

        TextView name = (TextView)root.findViewById(R.id.profile_name);
        CircleImageView picture = (CircleImageView)root.findViewById(R.id.profile_image);

        name.setText(current_user);

        picture.setBackgroundResource(R.drawable.loading);
        new DownloadImageTask(picture).execute(icon);

        new GetFriendWatchedHistoryTask(current_user).execute(this.getActivity());
        return root;
    }

}

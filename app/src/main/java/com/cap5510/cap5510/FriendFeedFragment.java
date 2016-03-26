package com.cap5510.cap5510;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cap5510.cap5510.api.FriendsTask;

/**
 * Created by Vega on 3/22/2016.
 */
public class FriendFeedFragment extends Fragment {
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.content_friend_feed, null);
        mListView = (ListView)root.findViewById(R.id.feedview);
        generateCode();
        return root;
    }


    private void generateCode(){
        new FriendsTask().execute(this.getActivity());
    }
}

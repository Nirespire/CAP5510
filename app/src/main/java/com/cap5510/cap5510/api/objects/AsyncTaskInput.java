package com.cap5510.cap5510.api.objects;


import android.app.Activity;
import android.support.v4.app.Fragment;

public class AsyncTaskInput {
    private Activity activity;
    private Object payload;
    private Fragment fragment;

    public AsyncTaskInput(Activity a, Object p){
        activity = a;
        payload = p;
    }

    public AsyncTaskInput(Activity a, Fragment f, Object p){
        activity = a;
        payload = p;
        fragment = f;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }


}

package com.cap5510.cap5510.api.objects;


import android.app.Activity;

public class AsyncTaskInput {
    private Activity activity;
    private Object payload;

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


}

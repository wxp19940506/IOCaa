package com.example.ioclibrary;

import android.app.Activity;
import android.view.View;

/**
 * Created by XiaopengWang on 2017/5/19.
 * Email:xiaopeng.wang@qaii.ac.cn
 * QQ:839853185
 * WinXin;wxp19940505
 */

public class ViewFinder {
    private Activity mActivity;
    private View mView;
    public ViewFinder(Activity activity) {
        this.mActivity = activity;
    }

    public ViewFinder(View view) {
        this.mView = view;
    }
    public View findViewById(int viewId){
        return mActivity!= null ?mActivity.findViewById(viewId) : mView.findViewById(viewId);
    }
}

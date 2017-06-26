package com.example.ioclibrary;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by XiaopengWang on 2017/5/19.
 * Email:xiaopeng.wang@qaii.ac.cn
 * QQ:839853185
 * WinXin;wxp19940505
 */

public class ViewUtils {
    public static void inject(Activity activity){
        inject(new ViewFinder(activity),activity);

    }
    public static void inject(View view){
        inject(new ViewFinder(view),view);

    }
    public static void inject(View view,Object object){
        inject(new ViewFinder(view),view);

    }
    //兼容
    public static void inject(ViewFinder viewFinder,Object object){
        injectFiled(viewFinder,object);
        injectEvent(viewFinder,object);
    }
    //注入事件
    private static void injectEvent(ViewFinder viewFinder, Object object) {
        //1.获取类里面所有方法
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods){
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null){
                //2.获取里面的value值
                int[] viewIds = onClick.value();
                for (int viewId : viewIds) {
                    //3.findViewById找到View
                    View view = viewFinder.findViewById(viewId);
                    //检测网络
                    boolean isCheckNet = method.getAnnotation(CheckNet.class) != null;
                    if (view != null){
                        //4.setOnClickLister
                        view.setOnClickListener(new DeclaredOnClickLinister(method,object,isCheckNet));
                    }
                }
            }
        }
    }
    private static class DeclaredOnClickLinister implements View.OnClickListener{
        private Method mMethod;
        private Object mObject;
        private boolean mIsCheckNet;
        public DeclaredOnClickLinister(Method method, Object object,boolean isCheckNet) {
            this.mMethod = method;
            this.mObject = object;
            this.mIsCheckNet = isCheckNet;
        }

        @Override
        public void onClick(View v) {
            // 是否需要检测网络
            if (mIsCheckNet){
               if(!isNetworkConnected(v.getContext())){
                   Toast.makeText(v.getContext(),"你的网络不太好",Toast.LENGTH_LONG).show();
               }
            }
            try {
                //5.反射注入方法
                mMethod.setAccessible(true);
                mMethod.invoke(mObject,v);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
    private static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    //注入属性
    private static void injectFiled(ViewFinder viewFinder, Object object) {
        //1.获取所有属性
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null){
                //2.获取ViewById的value值
                int viewId = viewById.value();
                //3.findViewById找到View
                View view = viewFinder.findViewById(viewId);
                if (view != null){
                    field.setAccessible(true);
                    //4.动态注入找到View
                    try {
                        field.set(object,view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}

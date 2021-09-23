package com.example.carsharingtest;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class imagebitmap {
    private static imagebitmap mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private imagebitmap(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue (){
        if(requestQueue==null)
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext() );
        return requestQueue;
    }

    public static synchronized imagebitmap getInstance(Context context){
        if(mInstance==null){
            mInstance = new imagebitmap(context);
        }
        return mInstance;
    }
    public<T> void addToRequestQue(Request<T>  request){
        getRequestQueue().add(request);
    }
}

package com.example.runner_mobile_app;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class AccessToDb {
    private AsyncHttpClient asyncHttpClient;
    private boolean isTrue=false;

    public void create(String url, String port, RequestParams requestParams){
        asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.post(url + port + "/create", requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.i("RESPINFO", response);
                /*if (response.contains("\"auth\": true")) {
                    isTrue=true;
                }*/
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }



}

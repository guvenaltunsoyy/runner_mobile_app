package Services;

import com.example.runner_mobile_app.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import static android.provider.Settings.System.getString;

public class ClientServices extends Application {


    public AsyncHttpClient asyncHttpClient;
    public String _baseUrl;
    android.content.res.Resources res;

    @Override
    public void onCreate() {
        super.onCreate();
        res = getResources();
        _baseUrl = getString(R.string.BaseUrl);
        Log.i("URL clientServices", _baseUrl);
    }

    public ClientServices() {

    }

    public void RunnerLogin(String username, String password) {
        asyncHttpClient = new AsyncHttpClient();
        Log.i("URL clientServices", _baseUrl);
        asyncHttpClient.get(_baseUrl + "/login?username=" + username + "&password=" + password, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.i("RESPINFO", response);
                if (response.contains("\"auth\": true")) {

                } else {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }
}

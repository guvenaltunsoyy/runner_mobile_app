package com.example.runner_mobile_app;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Runners extends AppCompatActivity {
    private RequestQueue mQueue;
    private ProgressDialog progress;
    private String _baseUrl;
    final List<Runner> users = new ArrayList<Runner>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runners);
        mQueue = Volley.newRequestQueue(this);
         _baseUrl = getString(R.string.BaseUrl);
        jsonParse();
    }

    private void jsonParse() {
        progress = ProgressDialog.show(Runners.this, "", "Lütfen Bekleyiniz", true);
        String url = _baseUrl+"list";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //name, surname, password,age,phoneNumber,runcount,mail,title
                            JSONArray jsonArray = response.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject result = jsonArray.getJSONObject(i);
                                String username = result.getString("username");
                                Integer runCount = result.getInt("runcount");
                                String title=result.getString("title");
                                String img=result.getString("image");
                                users.add(new Runner(username,runCount,img,title));
                                final ListView listView = (ListView) findViewById(R.id.listView);
                                CustomAdapter adapter = new CustomAdapter(Runners.this, users);
                                listView.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new RetryPolicy() { //volley timeout icin.
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        mQueue.add(request);
        progress.dismiss();
    }
}

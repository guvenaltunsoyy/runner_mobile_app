package com.example.runner_mobile_app;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListRunner extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    private ProgressDialog progress;
    private final String URL="http://192.168.1.23:3000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_runner);

        mTextViewResult = findViewById(R.id.text_view_result);
        mQueue = Volley.newRequestQueue(this);
        jsonParse();
    }

    private void jsonParse() {
        progress = ProgressDialog.show(ListRunner.this, "", "Lütfen Bekleyiniz", true);
        String url = URL+"/list";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //name, surname, password,age,phoneNumber,runcount,mail,title
                            JSONArray jsonArray = response.getJSONArray("result");
                            for (int i = 0; i < 10; i++) {
                                JSONObject result = jsonArray.getJSONObject(i);
                                String username = result.getString("username");
                                Integer runCount = result.getInt("runcount");
                                String title=result.getString("title");
                                mTextViewResult.append((i+1)+"."+ username +" : " + String.valueOf(runCount) +  "\n"+title+"\n\n");
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

        mQueue.add(request);
        progress.dismiss();
    }
}

package com.example.runner_mobile_app;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

import static android.provider.Telephony.Carriers.PORT;

public class HomePage extends AppCompatActivity {
    private ProgressDialog progress;
    private RequestQueue mQueue;
    private TextView tv_run;
    private ImageView image;
    private TextView tv_username;
    public Button gonder;
    public String urll, PORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urll = getString(R.string.HOST);
        PORT = getString(R.string.PORT);
        setContentView(R.layout.activity_home_page);
        mQueue = Volley.newRequestQueue(this);
        tv_run = findViewById(R.id.tv_runCount);
        image = findViewById(R.id.imageView);
        gonder = findViewById(R.id.btnMessage);
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        tv_username = findViewById(R.id.tv_username);
        tv_username.setText(username);
        jsonParse(username);
        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMessageActivity();
            }
        });
    }

    private void parse(String username) {

        //    progress = ProgressDialog.show(HomaPage.this, "", "Lütfen Bekleyiniz", true);
        Log.i("log", "parse girdi");
        StringRequest jsonForGetRequest = new StringRequest(
                Request.Method.GET, urll + PORT + "/runCount?username=" + username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();

                        Log.i("LOG", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject jsonBody = obj.getJSONObject("result");
                            tv_run.setText(jsonBody.getString("runcount"));
                            String img = (jsonBody.getString("image"));
                            byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            image.setImageBitmap(decodedByte);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                progress.dismiss();

                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    JSONObject jsonObject = null;
                    String errorMessage = null;

                    switch (response.statusCode) {
                        case 400:
                            errorMessage = new String(response.data);

                            try {

                                jsonObject = new JSONObject(errorMessage);
                                String serverResponseMessage = (String) jsonObject.get("hataMesaj");
                                Toast.makeText(getApplicationContext(), "" + serverResponseMessage, Toast.LENGTH_LONG).show();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                }
            }


        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> param = new HashMap<String, String>();

                return param;
            }


        };
        jsonForGetRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Login.getInstance().addToRequestQueue(jsonForGetRequest);

    }

    private void jsonParse(String username) {
        progress = ProgressDialog.show(HomePage.this, "", "Lütfen Bekleyiniz", true);
        String url = urll + PORT + "/runCount?username=" + username;
        Log.i("URL", url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");
                            JSONObject result = jsonArray.getJSONObject(0);
                            Integer runCount = result.getInt("runcount");
                            String img = result.getString("image");
                            byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            image.setImageBitmap(decodedByte);
                            tv_run.setText(String.valueOf(runCount));
                            progress.dismiss();
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
    }

    private void getImage(String username) {
        String url = urll + PORT + "/image?username=" + username;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //name, surname, password,age,phoneNumber,runcount,mail,title
                            JSONArray jsonArray = response.getJSONArray("result");
                            JSONObject result = jsonArray.getJSONObject(0);
                            String img = result.getString("image");
                            byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            image.setImageBitmap(decodedByte);

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
    }

    public void goToMessageActivity() {
        Intent intent = new Intent(HomePage.this, MessageActivity.class);
        intent.putExtra("username", tv_username.getText().toString());
        startActivity(intent);
    }

    public void goToList(View view) {
        Intent intent = new Intent(getApplicationContext(), Runners.class);
        startActivity(intent);
    }
}

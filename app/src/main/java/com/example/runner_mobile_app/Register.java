package com.example.runner_mobile_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class Register extends AppCompatActivity {

    private AsyncHttpClient asyncHttpClient;
    private RequestQueue mQueue;
    private byte[] byteArrayPhoto = null;
    final String url=getString(R.string.HOST), port = getString(R.string.PORT);
    int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText username = (EditText) findViewById(R.id.twUsername);
        final EditText name = (EditText) findViewById(R.id.twName);
        final EditText age = (EditText) findViewById(R.id.twAge);
        final EditText mail = (EditText) findViewById(R.id.twMail);
        final EditText password = (EditText) findViewById(R.id.twPassword);
        final EditText number = (EditText) findViewById(R.id.twNumber);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_kod);
        final RelativeLayout R_layout = (RelativeLayout) findViewById(R.id.rl_);
        final EditText tv_kod = (EditText) findViewById(R.id.tv_kod);
        final TextView tv_gecici = (TextView) findViewById(R.id.tv_gecici);
        Button btnSave = (Button) findViewById(R.id.btnSave);
        Button btnList = (Button) findViewById(R.id.btnList);
        Button btnClean = (Button) findViewById(R.id.btnClean);
        Button btnCleanText = (Button) findViewById(R.id.btnCleanText);
        asyncHttpClient = new AsyncHttpClient();
        btnCleanText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
                name.setText("");
                age.setText("");
                mail.setText("");
                password.setText("");
                number.setText("");
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString() == "" || password.getText().toString() == "") {
                    Toast.makeText(getApplicationContext(), "Lütfen Tüm Bilgileri Doldurunuz", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Kod gönderildi.", Toast.LENGTH_LONG).show();
                R_layout.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.VISIBLE);

                n = getMail(mail.getText().toString(), username.getText().toString());
                tv_gecici.setText(String.valueOf(n));
            }
        });

        tv_kod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (tv_kod.getText().toString().equals(String.valueOf(n))) {
                    tv_gecici.setText(String.valueOf(n));
                    RequestParams requestParams = new RequestParams();
                    requestParams.add("username", username.getText().toString());
                    requestParams.add("name",name.getText().toString());
                    requestParams.add("password", password.getText().toString());
                    requestParams.add("age",String.valueOf(age.getText().toString()));
                    requestParams.add("phonenumber",number.getText().toString());
                    requestParams.add("mail",mail.getText().toString());
                    requestParams.add("runcount", String.valueOf(0));
                    requestParams.add("title","Runner");
                    requestParams.add("state","true");
                    if(byteArrayPhoto != null) {
                        String encodedPhotoStr = Base64.encodeToString(byteArrayPhoto, Base64.DEFAULT);
                        requestParams.add("imageData", encodedPhotoStr);
                    }
                    new AccessToDb().create(url,port,requestParams);
                        Toast.makeText(getApplicationContext(), "Kayit basarili", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                        intent.putExtra("username",username.getText().toString());
                        startActivity(intent);

                   /* asyncHttpClient.post(url+port+"/create", requestParams, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String response = new String(responseBody);
                            Log.i("RESPINFO", response);
                            if (response.contains("\"auth\": true")) {
                                Toast.makeText(getApplicationContext(), "Kayit basarili", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                intent.putExtra("username",username.getText().toString());
                                startActivity(intent);
                            } else
                                Toast.makeText(getApplicationContext(), "Kayit Basarisiz", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });*/
                } else {
                    //Toast.makeText(getApplicationContext(), "Yanlış Kod", Toast.LENGTH_LONG).show();
                    //tv_kod.setText("");
                    tv_gecici.setText("Mailinize Gelen Kodu Giriniz..");
                }
            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Runners.class);
                startActivity(intent);
            }
        });
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private int getMail(final String toMail, final String user) {
        Random rand = new Random();
        int n = 100000+( rand.nextInt(900000));
        final int finalN = n;
        mQueue = Volley.newRequestQueue(this);
        String urll = url + port +"/email";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urll, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");
                            JSONObject result = jsonArray.getJSONObject(0);
                            String mail = result.getString("mail");
                            String pass = result.getString("password");
                            sendMail(mail, pass, toMail, user, finalN);

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
        return finalN;
    }

    public void sendMail(String fromMail, String password, String toMail, String username, int n) {
        Log.i("SendMailActivity", "Send Button Clicked.");
        String fromEmail = fromMail;
        String fromPassword = password;
        String toEmails = toMail;
        List<String> toEmailList = Arrays.asList(toEmails.split("\\s*,\\s*"));
        Log.i("SendMailActivity", "To List: " + toEmailList);
        String emailSubject = "Runner App Verification";
        String emailBody = "Welcome To Runner App " + username + " !\n\n" + "Your Verification Code : " + String.valueOf(n);
        new SendMailTask(Register.this).execute(fromEmail, fromPassword, toEmailList, emailSubject, emailBody);

    }

    public void chooseImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    0);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final ImageView image = findViewById(R.id.imageView);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    image.setImageURI(uri);

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byteArrayPhoto = stream.toByteArray();
                        bitmap.recycle();

                        Log.d("FILEBYTEARR", new String(byteArrayPhoto));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

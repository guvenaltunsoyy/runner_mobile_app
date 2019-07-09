package com.example.runner_mobile_app;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;


public class Login extends AppCompatActivity {
    public AsyncHttpClient asyncHttpClient;
    final String url="http://192.168.42.62:",port="80";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText username = findViewById(R.id.twMail);
        final EditText password = findViewById(R.id.twPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCreate = findViewById(R.id.btnCreate);
        final CheckBox beni_hatirla = findViewById(R.id.checkbox);
        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences sharedPref2 = this.getPreferences(Context.MODE_PRIVATE);
        boolean check =sharedPref2.getBoolean("isChecked",false);
        if (check){
            String name = sharedPref2.getString("username", "");
            String pass = sharedPref.getString("password", "");
            beni_hatirla.setChecked(true);
            username.setText(name);
            password.setText(pass);

        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (beni_hatirla.isChecked()) {
                    if (username.getText() != null || password.getText().toString() != "") {
                        SharedPreferences.Editor editor = sharedPref.edit(); //SharedPreferences'a kayıt eklemek için editor oluşturuyoruz
                        editor.putString("username", username.getText().toString()); //string değer ekleniyor
                        editor.putString("password", password.getText().toString());
                        editor.putBoolean("isChecked",true);
                        editor.commit(); //Kayıt
                    }else
                        Toast.makeText(getApplicationContext(),"null",Toast.LENGTH_LONG).show();
                }
                asyncHttpClient = new AsyncHttpClient();
                if (username.getText().toString() == "" || password.getText().toString() == "") {
                    Toast.makeText(getApplicationContext(), "Lütfen Tüm Bilgileri Doldurunuz", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    asyncHttpClient.get("http://192.168.42.62:80/login?username=" + username.getText().toString() + "&password=" + password.getText().toString(), new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String response = new String(responseBody);
                            Log.i("RESPINFO", response);
                            if (response.contains("\"auth\": true")) {
                                Toast.makeText(getApplicationContext(), "giris basarili", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                intent.putExtra("username", username.getText().toString());
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "giris basarisiz", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        }
                    });
                }
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent state = new Intent(getApplicationContext(), Register.class);
                startActivity(state);

            }
        });
    }


}


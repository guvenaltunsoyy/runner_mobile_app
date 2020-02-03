package com.example.runner_mobile_app;


import android.app.Activity;
import android.app.ProgressDialog;
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

import java.util.concurrent.ExecutionException;

import Services.ClientServices;
import cz.msebera.android.httpclient.Header;

public class Login extends AppCompatActivity {
    public AsyncHttpClient asyncHttpClient;
    private ClientServices _clientService;
    private ProgressDialog progress;

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
        boolean check = sharedPref2.getBoolean("isChecked", false);
        final String _baseUrl = getString(R.string.BaseUrl);
        _clientService = new ClientServices();

        Log.i("URL", String.valueOf(_baseUrl));
        if (check) {
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
                        editor.putBoolean("isChecked", true);
                        editor.commit(); //Kayıt
                    } else
                        Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_LONG).show();
                }
                String _username = sharedPref.getString("username", null);
                String _password = sharedPref.getString("password", null);
                if (_username != null && _password != null && username.getText().toString().equals(_username) && password.getText().toString().equals(_password)) {
                    //progress.dismiss();
                    Toast.makeText(getApplicationContext(), "Giris Basarili", Toast.LENGTH_LONG).show();
                    Intent intent3 = new Intent(getApplicationContext(), HomePage.class);
                    intent3.putExtra("username", username.getText().toString());
                    startActivity(intent3);
                    return;
                }
                asyncHttpClient = new AsyncHttpClient();
                if (username.getText().toString() == "" || password.getText().toString() == "") {
                    Toast.makeText(getApplicationContext(), "Lütfen Tüm Bilgileri Doldurunuz", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    progress = ProgressDialog.show(Login.this, "Giriş Yapılıyor", "Lütfen Bekleyiniz", true);
                    asyncHttpClient.get(_baseUrl + "/login?username=" + username.getText().toString() + "&password=" + password.getText().toString(), new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String response = new String(responseBody);
                            Log.i("RESPINFO", response);
                            if (response.contains("\"auth\": true")) {
                                progress.dismiss();
                                Toast.makeText(getApplicationContext(), "giris basarili", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                intent.putExtra("username", username.getText().toString());
                                startActivity(intent);
                            } else {
                                progress.dismiss();
                                Toast.makeText(getApplicationContext(), "giris basarisiz", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            progress.dismiss();
                            Toast.makeText(getApplicationContext(), "UnknowErrorWhenLogin", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

       /* btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startService(new Intent(getApplicationContext(), UserService.class));
                //Intent state = new Intent(getApplicationContext(), Register.class);
                //startActivity(state);
                Log.i("Create", "create");
                //_clientService.RunnerLogin(username.getText().toString(), password.getText().toString());
                _clientService = new ClientServices();
                _clientService.RunnerLogin("guven","1");


            }
        });*/

    }

    public void Login(View view) throws ExecutionException, InterruptedException {
        Log.i("LOGIN", "CREATE BUTTON");
        _clientService.RunnerLogin("guven", "1");
    }


}


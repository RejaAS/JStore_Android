package com.coba.jstore_android_reja.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.coba.jstore_android_reja.Request.LoginRequest;
import com.coba.jstore_android_reja.R;

import org.json.JSONException;
import org.json.JSONObject;

public class  LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null){
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                                builder1.setMessage("Login Success!").create().show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("id_customer", jsonResponse.getInt("id"));
                                intent.putExtra("name", jsonResponse.getString("name"));
                                startActivity(intent);
                            }
                        } catch (JSONException e){
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                            builder1.setMessage("Login Failed!").create().show();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        final TextView registerClickable = (TextView) findViewById(R.id.tvRegister);
        registerClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent (LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }
}

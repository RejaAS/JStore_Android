package com.coba.jstore_android_reja.Request;

import android.util.Log;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String Regis_URL = "http://192.168.43.221:8080/newcustomer";
    private Map<String, String> params;

    public RegisterRequest(String name,String username, String email, String password, Response.Listener<String> Listener){
        super(Method.POST, Regis_URL, Listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        params = new HashMap<>();
        params.put("name", name);
        params.put("email",email);
        params.put("username", username);
        params.put ("password", password);
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}

package com.coba.jstore_android_reja.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    private static final String Regis_URL = "http://192.168.43.221:8080/logincust";
    private Map<String, String> params;

    public LoginRequest( String email, String password, Response.Listener<String> Listener){
        super(Method.POST, Regis_URL, Listener, null);
        params = new HashMap<>();
        params.put("email",email);
        params.put ("password", password);
    }
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}

package com.coba.jstore_android_reja;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MenuRequest extends StringRequest {
    private static final String Regis_URL = "http://10.0.2.2:8080/logincust";
    private Map<String, String> params;

    public MenuRequest(Response.Listener<String> Listener){
        super(Method.GET, Regis_URL, Listener, null);
    }
}

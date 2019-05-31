package com.coba.jstore_android_reja.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PesananBatalRequest extends StringRequest {
    private static final String pesananBatal_URL = "http://192.168.43.221:8080/canceltransaction";
    private Map<String, String> params;

    public PesananBatalRequest(int invoiceID, Response.Listener<String> Listener){
        super(Method.POST, pesananBatal_URL+"/"+invoiceID, Listener, null);
        params = new HashMap<>();
        params.put("invoiceID", invoiceID+"");
    }
}

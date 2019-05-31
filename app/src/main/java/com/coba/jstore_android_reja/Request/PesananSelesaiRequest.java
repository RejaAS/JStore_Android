package com.coba.jstore_android_reja.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PesananSelesaiRequest extends StringRequest {
    private static final String pesananSelesai_URL = "http://192.168.43.221:8080/finishtransaction";
    private Map<String, String> params;

    public PesananSelesaiRequest(int invoiceID, Response.Listener<String> Listener){
        super(Method.POST, pesananSelesai_URL+"/"+invoiceID, Listener, null);
        params = new HashMap<>();
        params.put("invoiceID", invoiceID+"");
    }
}

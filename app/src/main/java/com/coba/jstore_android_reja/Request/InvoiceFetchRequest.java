package com.coba.jstore_android_reja.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class InvoiceFetchRequest extends StringRequest {
    private static final String invoice_URL = "http://192.168.43.221:8080/invoice";
    public InvoiceFetchRequest(int id, Response.Listener<String> listener) {
        super(Method.GET, invoice_URL+"/"+id, listener, null);
    }
}

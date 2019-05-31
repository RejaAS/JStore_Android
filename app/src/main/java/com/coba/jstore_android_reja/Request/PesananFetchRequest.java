package com.coba.jstore_android_reja.Request;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PesananFetchRequest extends StringRequest {
    private static final String invoiceCustomer_URL = "http://192.168.43.221:8080/invoicecustomer/";
    private Map<String, String> params;

    public PesananFetchRequest(int customerID, Response.Listener<String> Listener){
        super(Method.GET, invoiceCustomer_URL+customerID, Listener, null);
        params = new HashMap<>();
        params.put("id_customer", String.valueOf(customerID));
        Log.d("", "PesananFetchRequest: " + customerID+"");

    }
}

package com.coba.jstore_android_reja.Request;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuatPesananRequest extends StringRequest {
    private static final String Paid_URL = "http://192.168.43.221:8080/createinvoicepaid";
    private static final String Unpaid_URL = "http://192.168.43.221:8080/createinvoiceunpaid";
    private static final String Installment_URL = "http://192.168.43.221:8080/createinvoiceinstallment";
    private Map<String, String> params;

    public BuatPesananRequest( ArrayList<Integer> arrayListItem, int customerID, Response.Listener<String> Listener){
        super(Method.POST, Paid_URL, Listener, null);
        params = new HashMap<>();
        params.put("listItem", arrayListItem.get(0)+"");
        params.put ("customerID", customerID+"");
        Log.d("", "BuatPesananRequest: "+arrayListItem + customerID);
    }
    public BuatPesananRequest( ArrayList<Integer> arrayListItem, int customerID, Response.Listener<String> Listener, String beda){
        super(Method.POST, Unpaid_URL, Listener, null);
        params = new HashMap<>();
        params.put("listItem", arrayListItem.get(0)+"");
        params.put ("customerID", customerID+"");
    }

    public BuatPesananRequest( ArrayList<Integer> arrayListItem, int customerID,int installmentPeriod, Response.Listener<String> Listener){
        super(Method.POST, Installment_URL, Listener, null);
        params = new HashMap<>();
        params.put("listItem", arrayListItem.get(0)+"");
        params.put ("customerID", customerID+"");
        params.put("installmentPeriod", installmentPeriod+"");
    }
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}


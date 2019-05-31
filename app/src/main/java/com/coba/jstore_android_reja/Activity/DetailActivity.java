package com.coba.jstore_android_reja.Activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.coba.jstore_android_reja.R;
import com.coba.jstore_android_reja.Request.InvoiceFetchRequest;
import com.coba.jstore_android_reja.Request.PesananBatalRequest;
import com.coba.jstore_android_reja.Request.PesananSelesaiRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
    TextView tvDate, tvId, tvPayment, tvDueDate, tvItem, tvStatusCategory, tvPrice, tvTotalPrice;
    Button btnCancel, btnFinish;

    int invoiceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvDate = findViewById(R.id.tvDate);
        tvId = findViewById(R.id.tvId);
        tvPayment = findViewById(R.id.tvPayment);
        tvDueDate = findViewById(R.id.tvDueDate);
        tvItem = findViewById(R.id.tvItem);
        tvStatusCategory = findViewById(R.id.tvStatusCategory);
        tvPrice = findViewById(R.id.tvPrice);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);

        btnCancel = findViewById(R.id.btnCancel);
        btnFinish = findViewById(R.id.btnFinish);

        invoiceId = getIntent().getExtras().getInt("id");

        invoiceDetail();
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                                builder.setMessage("Invoice Finished!").create().show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                                builder.setMessage("Operation Failed! Please try again").create().show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                            builder.setMessage("Operation Failed! Please try again").create().show();
                        }
                    }
                };
                PesananSelesaiRequest request = new PesananSelesaiRequest(invoiceId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(DetailActivity.this);
                queue.add(request);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                                builder.setMessage("Invoice Cancelled!").create().show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                                builder.setMessage("Operation Failed! Please try again").create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                            builder.setMessage("Invoice Cancelled!").create().show();
                        }
                    }
                };

                PesananBatalRequest request = new PesananBatalRequest(invoiceId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(DetailActivity.this);
                queue.add(request);
            }
        });
    }

    public void invoiceDetail(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("isActive").equals("false")) {
                        btnCancel.setEnabled(false);
                        btnFinish.setEnabled(false);

                    }
                    else {
                        String date = jsonObject.getString("date");
                        JSONArray item_json = jsonObject.getJSONArray("item");

                        String invoiceStatus = jsonObject.getString("invoiceStatus");
                        int totalPrice = jsonObject.getInt("totalPrice");
                        Log.d("", "on: " + totalPrice + "");
                        switch (invoiceStatus) {
                            case "Unpaid":
                                String dueDate = jsonObject.getString("dueDate");
                                tvDueDate.setText(dueDate.substring(0,10));
                                tvTotalPrice.setText("Rp. " + totalPrice);
                                break;
                            case "Installment":
                                String installmentPeriod = jsonObject.getString("installmentPeriod");
                                String installmentPrice = jsonObject.getString("installmentPrice");
                                tvTotalPrice.setText("Rp. " + installmentPrice + "x" + installmentPeriod);
                        }
                        tvDate.setText(date.substring(0,10));
                        tvId.setText("Invoice ID: " + invoiceId);
                        tvItem.setText(String.valueOf(item_json));
                        tvPayment.setText(invoiceStatus);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        InvoiceFetchRequest request = new InvoiceFetchRequest(invoiceId, responseListener);
        RequestQueue queue = new Volley().newRequestQueue(DetailActivity.this);
        queue.add(request);

    }
}

package com.coba.jstore_android_reja.Activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.coba.jstore_android_reja.Request.BuatPesananRequest;
import com.coba.jstore_android_reja.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuatPesananActivity extends AppCompatActivity {

    private double itemPrice;
    private int currentUserId, installmentPeriod, customerID;
    ;
    private String itemName, itemCategory, itemStatus, selectedPayment;
    private ArrayList<Integer> listItem = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);

        final int id_item = getIntent().getIntExtra("id_item", 0);
        final String itemName = getIntent().getStringExtra("itemName");
        final String itemCategory = getIntent().getStringExtra("itemCategory");
        final String itemStatus = getIntent().getStringExtra("itemStatus");
        final double itemPrice = getIntent().getIntExtra("itemPrice", 0);
        final int currentUserId = getIntent().getIntExtra("id_customer", 0);
        listItem = new ArrayList<>();
        listItem.add(id_item);

        final TextView item_name = findViewById(R.id.item_status);
        final TextView item_category = findViewById(R.id.invoiceStatus);
        final TextView item_status = findViewById(R.id.item_name);
        final TextView item_price = findViewById(R.id.item_price);
        final TextView item_period = findViewById(R.id.textPeriod);
        final EditText installment_period = findViewById(R.id.installment_period);
        final TextView total_Price = findViewById(R.id.total_price);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final RadioButton paid = findViewById(R.id.paid);
        final RadioButton unpaid = findViewById(R.id.unpaid);
        final RadioButton installment = findViewById(R.id.installment);
        final Button hitung = findViewById(R.id.hitung);
        final Button pesan = findViewById(R.id.pesan);

        pesan.setVisibility(View.INVISIBLE);
        item_period.setVisibility(View.INVISIBLE);
        installment_period.setVisibility(View.INVISIBLE);

        item_name.setText(itemName);
        item_category.setText(itemCategory);
        item_status.setText(itemStatus);
        item_price.setText(String.format("%s", itemPrice));
        total_Price.setText("0");


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.installment) {
                    item_period.setVisibility(View.VISIBLE);
                    installment_period.setVisibility(View.VISIBLE);
                    hitung.setVisibility(View.VISIBLE);
                    selectedPayment = "Installment";
                } else if (checkedId == R.id.paid) {
                    item_period.setVisibility(View.INVISIBLE);
                    installment_period.setVisibility(View.INVISIBLE);
                    selectedPayment = "Paid";
                }else if (checkedId == R.id.unpaid) {
                    item_period.setVisibility(View.INVISIBLE);
                    installment_period.setVisibility(View.INVISIBLE);
                    selectedPayment = "Unpaid";
                }
            }
        });

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPayment == "Paid" || selectedPayment == "Unpaid") {
                    total_Price.setText(itemPrice + "");
                } else if (selectedPayment == "Installment" && installment_period != null) {
                    installmentPeriod = Integer.parseInt(installment_period.getText().toString());
                    total_Price.setText( itemPrice/installmentPeriod + "");
                }

                hitung.setVisibility(View.INVISIBLE);
                pesan.setVisibility(View.VISIBLE);
            }
        });

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (response != null) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(BuatPesananActivity.this);
                                builder1.setMessage("Order Success!").create().show();
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(BuatPesananActivity.this);
                            builder1.setMessage("Order Failed!").create().show();
                        }
                    }
                };

                if (selectedPayment.equals("Paid")) {
                    BuatPesananRequest pesananRequest = new BuatPesananRequest(listItem, currentUserId, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                    queue.add(pesananRequest);
                } else if (selectedPayment.equals("Unpaid")) {
                    BuatPesananRequest pesananRequest = new BuatPesananRequest(listItem, currentUserId, responseListener, "beda");
                    RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                    queue.add(pesananRequest);
                } else if (selectedPayment.equals("Installment")) {
                    BuatPesananRequest pesananRequest = new BuatPesananRequest(listItem, currentUserId, installmentPeriod, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                    queue.add(pesananRequest);
                }
            }
        });
    }
}
package com.coba.jstore_android_reja.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.coba.jstore_android_reja.Adapter.SelesaiPesananAdapter;
import com.coba.jstore_android_reja.Models.Invoice;
import com.coba.jstore_android_reja.R;
import com.coba.jstore_android_reja.Request.PesananFetchRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelesaiPesananActivity extends AppCompatActivity {
    TextView tvId, tvDate, item_name, invoiceStatus, total_price;
    Button btnFinish;
    RecyclerView rvInvoice;

    private int userId;
    private String itemName, itemCategory;
    private ArrayList<Invoice> invoices = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_pesanan);

        tvId = findViewById(R.id.tvDate);
        tvDate = findViewById(R.id.tvId);
        item_name = findViewById(R.id.item_name);
        invoiceStatus = findViewById(R.id.invoiceStatus);
        total_price = findViewById(R.id.total_price);
        btnFinish = findViewById(R.id.btnSelesai);
        rvInvoice = findViewById(R.id.rvInvoice);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            userId = extras.getInt("id_customer");
            itemName = extras.getString("itemName");
            itemCategory = extras.getString("itemCategory");
        }

        recyclerInv();
    }

    private void recyclerInv(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject invoice =jsonArray.getJSONObject(i);

                        int id = invoice.getInt("id");
                        String date = invoice.getString("date");
                        int totalPrice = invoice.getInt("totalPrice");
                        JSONArray itemJson = invoice.getJSONArray("item");

                        ArrayList<String> items = new ArrayList<>();
                        for (int x=0; x< itemJson.length(); x++){
                            String m = String.valueOf(itemJson.getInt(0));
                            items.add(m);
                        }

                        String invoiceStatus = invoice.getString("invoiceStatus");

                        if(invoiceStatus.equals("Paid")){
                            Invoice temp = new Invoice(id,items,date,totalPrice,invoiceStatus);
                            invoices.add(temp);
                        }else if(invoiceStatus.equals("Unpaid")){
                            String dueDate = invoice.getString("dueDate");
                            Invoice temp = new Invoice(id,items,date,totalPrice,dueDate, invoiceStatus);
                            invoices.add(temp);
                        }else if(invoiceStatus.equals("Installment")){
                            int installmentPeriod = invoice.getInt("installmentPeriod");
                            int installmentPrice = invoice.getInt("installmentPrice");
                            Invoice temp = new Invoice(id,items,date,totalPrice,installmentPeriod,installmentPrice, invoiceStatus);
                            invoices.add(temp);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SelesaiPesananAdapter adapter = new SelesaiPesananAdapter(invoices, SelesaiPesananActivity.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                rvInvoice.setLayoutManager(layoutManager);
                rvInvoice.setAdapter(adapter);
            }
        };
        PesananFetchRequest request = new PesananFetchRequest(userId, responseListener);
        RequestQueue queue = new Volley().newRequestQueue(SelesaiPesananActivity.this);
        queue.add(request);
    }
}

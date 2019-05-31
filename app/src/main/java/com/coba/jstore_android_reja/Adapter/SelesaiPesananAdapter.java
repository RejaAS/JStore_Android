package com.coba.jstore_android_reja.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coba.jstore_android_reja.Activity.DetailActivity;
import com.coba.jstore_android_reja.Models.Invoice;
import com.coba.jstore_android_reja.R;

import java.util.ArrayList;

public class SelesaiPesananAdapter extends RecyclerView.Adapter<SelesaiPesananAdapter.invoiceViewHolder> {
    private ArrayList<Invoice> invoices;
    private Context context;

    public SelesaiPesananAdapter(ArrayList<Invoice> invoices, Context context){
        this.invoices = invoices;
        this.context = context;
    }

    @NonNull
    @Override
    public invoiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.invoice_view, viewGroup, false);
        return new invoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull invoiceViewHolder invoiceViewHolder, int i) {
        final Invoice invoice = invoices.get(i);
//        StringBuilder text = new StringBuilder(i);

//        text = text.append(invoices.get(i).getItem().get(0));
//        if (invoice.getItem().size() >= 1){
//            for (int x=1; x < invoice.getItem().size(); x++){
//                text.append(", ").append(invoice.getItem().get(x));
//            }
//        }


        invoiceViewHolder.item_name.setText(String.valueOf(invoice.getItem()));
        invoiceViewHolder.tvDate.setText(invoice.getDate().substring(0,10));
        invoiceViewHolder.tvId.setText("ID: " + invoice.getId()+"");
        Log.d("", String.valueOf(invoice.getId()));
        invoiceViewHolder.total_price.setText("Rp. " + invoice.getTotalPrice());
        invoiceViewHolder.invoiceStatus.setText(invoice.getInvoiceStatus());

        invoiceViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, DetailActivity.class);
                intent.putExtra("id", invoice.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }


    public class invoiceViewHolder extends RecyclerView.ViewHolder{
        TextView tvId, tvDate, item_name, invoiceStatus, total_price;
        ConstraintLayout layout;

        public invoiceViewHolder (View view){
            super(view);
            tvId =view.findViewById(R.id.tvDate);
            tvDate = view.findViewById(R.id.tvId);
            item_name = view.findViewById(R.id.item_name);
            invoiceStatus = view.findViewById(R.id.invoiceStatus);
            total_price = view.findViewById(R.id.total_price);
            layout = view.findViewById(R.id.invoiceItem);

        }
    }




}

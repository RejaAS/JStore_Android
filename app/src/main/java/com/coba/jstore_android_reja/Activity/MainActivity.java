package com.coba.jstore_android_reja.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.coba.jstore_android_reja.Models.Item;
import com.coba.jstore_android_reja.Models.Location;
import com.coba.jstore_android_reja.Adapter.MainListAdapter;
import com.coba.jstore_android_reja.Request.MenuRequest;
import com.coba.jstore_android_reja.R;
import com.coba.jstore_android_reja.Models.Supplier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Supplier> listSupplier = new ArrayList<>();
    private ArrayList<Item> listItem = new ArrayList<>();
    private HashMap<Supplier, ArrayList<Item>> childMapping = new HashMap<>();

    MainListAdapter listAdapter;
    ExpandableListView expListView;
//    ArrayList<String> listDataHeader;
//    HashMap<String, List<String>> listDataChild;
    int currentUserId;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSelesai = findViewById(R.id.btnSelesai);

        currentUserId = getIntent().getExtras().getInt("id_customer");
        userName = getIntent().getExtras().getString("name");
        Log.d("", "main: " + currentUserId);

        TextView tvWelcome = findViewById(R.id.tvWelcome);

        tvWelcome.setText("Welcome," + userName);

        refreshList();
        expListView = findViewById(R.id.lvExp);
        listAdapter = new MainListAdapter(MainActivity.this, listSupplier, childMapping);

        expListView.setAdapter(listAdapter);

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelesaiPesananActivity.class);
                intent.putExtra("id_customer", currentUserId);
                startActivity(intent);
            }
        });
    }


    protected void refreshList(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    if(jsonResponse!=null){
                        for(int i=0; i<jsonResponse.length(); i++){
                            JSONObject item = jsonResponse.getJSONObject(i);
                            JSONObject supplier = item.getJSONObject("supplier");
                            JSONObject location = supplier.getJSONObject("location");
                            Location location1 = new Location(location.getString("province"), location.getString("description"), location.getString("city"));
                            Supplier supplier1 = new Supplier(supplier.getInt("id"), supplier.getString("name"), supplier.getString("email"), supplier.getString("phoneNumber"), location1);

                            if(listSupplier.size()>0){
                                for(Supplier sup : listSupplier){
                                    if(!(sup.getId() == supplier1.getId())){
                                        listSupplier.add(supplier1);
                                    }
                                }
                            }
                            else{
                                listSupplier.add(supplier1);
                            }

                            Item item1 = new Item(item.getInt("id"),item.getString("name"), item.getInt("price"), item.getString("category"), item.getString("status"), supplier1);
                            listItem.add(item1);
                        }
                        for(Supplier sup : listSupplier){
                            ArrayList<Item> temp = new ArrayList<>();
                            for(Item item : listItem){
                                if(item.getSupplier().getName().equals(sup.getName()) || item.getSupplier().getEmail().equals(sup.getEmail()) || item.getSupplier().getPhoneNumber().equals(sup.getPhoneNumber())){
                                    temp.add(item);
                                }
                            }
                            childMapping.put(sup,temp);
                        }
                    }
                    expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                            Item selected = childMapping.get(listSupplier.get(groupPosition)).get(childPosition);
                            Log.d("", "onChildClick: "+ selected);
                            Intent intent = new Intent(MainActivity.this, BuatPesananActivity.class);
                            intent.putExtra("id_item", selected.getId());
                            intent.putExtra("itemName", selected.getName());
                            intent.putExtra("itemCategory", selected.getCategory());
                            intent.putExtra("itemStatus", selected.getStatus());
                            intent.putExtra("itemPrice", selected.getPrice());
                            intent.putExtra("id_customer", currentUserId);
                            startActivity(intent);
                            return false;
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);


    }
}

package com.coba.jstore_android_reja;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Supplier> listSupplier = new ArrayList<>();
    private ArrayList<Item> listItem = new ArrayList<>();
    private HashMap<Supplier,ArrayList<Item>> childMapping = new HashMap<>();

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        listAdapter = new MainListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
    }

        protected void refreshList() {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONArray jsonResponse = new JSONArray(response);
                        if(jsonResponse != null){
                            for (int i = 0; i < jsonResponse.length(); i++){
                                JSONObject item = jsonResponse.getJSONObject(i);
                                JSONObject supplier = item.getJSONObject("supplier");
                                JSONObject location = supplier.getJSONObject("location");
                                Location location1 = new Location(location.getString("province"), location.getString("description"), location.getString("city"));
                                Supplier supplier1 = new Supplier(supplier.getInt("id"), supplier.getString("name"), supplier.getString("phoneNumber"),location1);
                                listSupplier.add(supplier1);
                                Item item1 = new Item(item.getInt("id"), item.getString("name"), item.getInt("price"), item.getString("category"), item.getString("status"), supplier1);
                                listItem.add(item1);
                                childMapping.put(listSupplier.get(i), listItem);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            MenuRequest menuRequest = new MenuRequest(responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(menuRequest);
        }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        int counter = 0;

        for (Supplier sup : listSupplier){
            ArrayList<Item> list = childMapping.get(sup);
            List<String> temp = new ArrayList<>();
            for (Item item : listItem){
                temp.add(item.getName());
            }
            listDataChild.put(listDataHeader.get(counter), temp);
        }
        counter++;
    }
}

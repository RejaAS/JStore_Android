package com.coba.jstore_android_reja.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.coba.jstore_android_reja.Models.Item;
import com.coba.jstore_android_reja.R;
import com.coba.jstore_android_reja.Models.Supplier;

import java.util.ArrayList;
import java.util.HashMap;

public class MainListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private ArrayList<Supplier> listSupplier; // header titles
    // child data in format of header title, child title
    private HashMap<Supplier, ArrayList<Item>> childMapping;

    public MainListAdapter(Context context, ArrayList<Supplier> listSupplier,
                           HashMap<Supplier, ArrayList<Item>> childMapping) {
        this._context = context;
        this.listSupplier = listSupplier;
        this.childMapping = childMapping;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.childMapping.get(this.listSupplier.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Item childText = (Item) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        String text = childText.getName();

        txtListChild.setText(text);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childMapping.get(this.listSupplier.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listSupplier.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listSupplier.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Supplier headerTitle = (Supplier) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_supplier, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);

        String text = headerTitle.getName();
        lblListHeader.setText(text);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
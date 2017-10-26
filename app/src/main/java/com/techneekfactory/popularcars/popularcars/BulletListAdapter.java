package com.techneekfactory.popularcars.popularcars;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

/**
 * Created by catch on 8/26/2017.
 */

public class BulletListAdapter extends ArrayAdapter<String> {


    private ArrayList<String> item;
    private Activity context;

    public BulletListAdapter(Activity context, ArrayList<String> item) {
        super(context, R.layout.bullet_list_item, item);
        this.context = context;
        this.item = item;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.bullet_list_item, null, true);
        TextView textViewName = listViewItem.findViewById(R.id.textLabel);

        textViewName.setText(item.get(position));

        return  listViewItem;
    }
}

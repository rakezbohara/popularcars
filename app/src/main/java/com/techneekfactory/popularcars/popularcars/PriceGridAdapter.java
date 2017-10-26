package com.techneekfactory.popularcars.popularcars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by arafat on 19/08/17.
 */

public class PriceGridAdapter extends BaseAdapter {

    private Context mContext;
    private final String[] priceLabel;


    public PriceGridAdapter(Context mContext, String[] priceLabel) {
        this.mContext = mContext;
        this.priceLabel = priceLabel;
    }

    @Override
    public int getCount() {
        return priceLabel.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.price_grid_item, null);
        }
        TextView textView =  view.findViewById(R.id.grid_text);

        textView.setText(priceLabel[i]);


        return view;
    }




}

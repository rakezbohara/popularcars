package com.techneekfactory.popularcars.popularcars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by arafat on 19/08/17.
 */

public class BrandGridAdapter extends BaseAdapter {

    private Context mContext;
    private final String[] brandName;
    private final String[] Imageid;


    public BrandGridAdapter(Context mContext, String[] brandName, String[] imageid) {
        this.mContext = mContext;
        this.brandName = brandName;
        Imageid = imageid;
    }

    @Override
    public int getCount() {
        return brandName.length;
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

//        View grid;
//        LayoutInflater inflater = (LayoutInflater) mContext
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        if (view == null) {
//
//            grid = new View(mContext);
//            grid = inflater.inflate(R.layout.brand_grid_item, null);
//            TextView textView =  grid.findViewById(R.id.grid_text);
//            ImageView imageView = grid.findViewById(R.id.grid_image);
//            textView.setText(brandName[i]);
//            imageView.setImageResource(Imageid[i]);
//        } else {
//            grid = (View) view;
//        }
//
//        return grid;





        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.brand_grid_item, null);
        }
            TextView textView =  view.findViewById(R.id.grid_text);
            ImageView imageView = view.findViewById(R.id.grid_image);
            textView.setText(brandName[i]);
//            imageView.setImageResource(Imageid[i]);

        Glide.with(mContext).load(Imageid[i]).into(imageView);


        return view;

    }




}

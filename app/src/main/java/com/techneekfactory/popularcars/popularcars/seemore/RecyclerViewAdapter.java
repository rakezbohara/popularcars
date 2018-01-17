package com.techneekfactory.popularcars.popularcars.seemore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.techneekfactory.popularcars.popularcars.CarInfoActivity;
import com.techneekfactory.popularcars.popularcars.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<ItemObject> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<ItemObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_view_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, final int position) {
        holder.carTitle.setText(itemList.get(position).getMake());
        holder.carPrice.setText(itemList.get(position).getPrice());
        holder.carYear.setText(String.valueOf(itemList.get(position).getYear()));

//        holder.carYear.setImageResource(itemList.get(position).getPhoto());
        Glide.with(context).load(itemList.get(position).getImageUrl()).into(holder.carImage);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                Intent i = new Intent(context, CarInfoActivity.class);
                i.putExtra("vehicleID", itemList.get(position).getId());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
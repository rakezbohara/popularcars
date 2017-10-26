package com.techneekfactory.popularcars.popularcars;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.techneekfactory.popularcars.popularcars.extras.SpecialOffers;

import java.util.List;

/**
 * Created by arafat on 18/08/17.
 */

public class OffersCustomAdapter extends RecyclerView.Adapter<OffersCustomAdapter.ViewHolder> {

    private Context context;
    private List<SpecialOffers> offersList;
    private Boolean horizontalOrientation = false;

    public OffersCustomAdapter(Context context, List<SpecialOffers> offersList) {
        this.context = context;
        this.offersList = offersList;

    }

    public OffersCustomAdapter(Context context, List<SpecialOffers> offersList, Boolean horizontalOrientation) {
        this.context = context;
        this.offersList = offersList;
        this.horizontalOrientation = horizontalOrientation;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = null;
        if(this.horizontalOrientation)
        {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_card_view, parent, false);
        }
        else
        {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_card_view, parent, false);
        }


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.offerTitle.setText(offersList.get(position).getOfferTitle());
        Glide.with(context).load(offersList.get(position).getImage()).into(holder.offerImage);


    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView offerTitle;
        public ImageView offerImage;

        public ViewHolder(View itemView) {
            super(itemView);

            offerTitle = (TextView) itemView.findViewById(R.id.offerTitle);
            offerImage = (ImageView) itemView.findViewById(R.id.offerImage);


        }
    }
}

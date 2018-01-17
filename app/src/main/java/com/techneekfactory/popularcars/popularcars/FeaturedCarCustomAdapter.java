package com.techneekfactory.popularcars.popularcars;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.techneekfactory.popularcars.popularcars.extras.Vehicles;

import java.util.List;

/**
 * Created by arafat on 18/08/17.
 */

public class FeaturedCarCustomAdapter extends RecyclerView.Adapter<FeaturedCarCustomAdapter.ViewHolder> {

    private Activity activity;
    private List<Vehicles> vehiclesList;
    private Boolean horizontalOrientation = false;
    private CarsCardClickListener carsCardClickListener = null;

    public FeaturedCarCustomAdapter(Activity activity, List<Vehicles> vehiclesList) {
        this.activity= activity;
        this.vehiclesList = vehiclesList;

    }

    public FeaturedCarCustomAdapter(List<Vehicles> vehiclesList) {

        this.vehiclesList = vehiclesList;

    }

    public FeaturedCarCustomAdapter(Activity activity, List<Vehicles> vehiclesList, Boolean horizontalOrientation) {
        this.activity= activity;
        this.vehiclesList = vehiclesList;
        this.horizontalOrientation = horizontalOrientation;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = null;
//        if (this.horizontalOrientation) {
//            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cars_card_horizontal_view, parent, false);
//        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cars_card_view_featured, parent, false);
//        }


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.carTitle.setText(vehiclesList.get(position).getMake() + " " + vehiclesList.get(position).getModel());
        holder.carYear.setText(String.valueOf(vehiclesList.get(position).getYear()));
        holder.carPrice.setText(vehiclesList.get(position).getPrice());
        Glide.with(activity).load(vehiclesList.get(position).getImage()).into(holder.carImage);
    }

    @Override
    public int getItemCount() {
        return vehiclesList.size();
    }

    public void setCarsCardClickListener(CarsCardClickListener carsCardClickListener){
        this.carsCardClickListener = carsCardClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView carTitle;
        public TextView carYear;
        public TextView carPrice;
        public ImageView carImage;
        public CardView cardView;

        public ViewHolder(final View itemView) {
            super(itemView);

            carTitle = (TextView) itemView.findViewById(R.id.carTitle);
            carYear = (TextView) itemView.findViewById(R.id.carYear);
            carPrice = (TextView) itemView.findViewById(R.id.carPrice);
            carImage = (ImageView) itemView.findViewById(R.id.carImage);

            cardView = (CardView) itemView.findViewById(R.id.carsCardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getAdapterPosition()), Toast.LENGTH_SHORT).show();

                    Log.d("Card ID", vehiclesList.get(getAdapterPosition()).getMake());

                    if(carsCardClickListener != null){
                        carsCardClickListener.carsCardItemClicked(view, getAdapterPosition(), vehiclesList.get(getAdapterPosition()).getVehicleID());
                    }

                }
            });


        }
    }
}

package com.techneekfactory.popularcars.popularcars;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.techneekfactory.popularcars.popularcars.extras.CarImages;
import com.techneekfactory.popularcars.popularcars.extras.SpecialOffers;

import java.util.List;

/**
 * Created by arafat on 18/08/17.
 */

public class CarImagesCustomAdapter extends RecyclerView.Adapter<CarImagesCustomAdapter.ViewHolder> {

    private Context context;
    private List<CarImages> carImagesList;

    public CarImagesCustomAdapter(Context context, List<CarImages> carImagesList) {
        this.context = context;
        this.carImagesList = carImagesList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = null;
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_gallery_card_view, parent, false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

//        Glide.with(context).load(carImagesList.get(position).getImage()).into(holder.carGalleryImage);
        Glide.with(context).load(carImagesList.get(position).getImage()).into(holder.carGalleryImage);

//        holder.viewFlipper.startFlipping();

    }

    @Override
    public int getItemCount() {
        return carImagesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView carGalleryImage;
        public ViewFlipper viewFlipper;

        public ViewHolder(View itemView) {
            super(itemView);

            carGalleryImage = (ImageView) itemView.findViewById(R.id.carGalleryImage);
//            viewFlipper = (ViewFlipper) itemView.findViewById(R.id.viewFlipper);


        }
    }
}

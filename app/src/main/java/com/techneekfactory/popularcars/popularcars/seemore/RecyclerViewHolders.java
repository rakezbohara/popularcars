package com.techneekfactory.popularcars.popularcars.seemore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.techneekfactory.popularcars.popularcars.CarInfoActivity;
import com.techneekfactory.popularcars.popularcars.R;

import java.util.ArrayList;

import static java.security.AccessController.getContext;


public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView countryName;
    public ImageView countryPhoto;
    public TextView carTitle;
    public TextView carYear;
    public TextView carPrice;
    public ImageView carImage;
    public CardView cardView;
    public Context context;
    ItemClickListener itemClickListener;
    private ItemObject currentItem;



    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        carTitle = (TextView) itemView.findViewById(R.id.carTitle);
        carYear = (TextView) itemView.findViewById(R.id.carYear);
        carPrice = (TextView) itemView.findViewById(R.id.carPrice);
        carImage = (ImageView) itemView.findViewById(R.id.carImage);
        cardView = (CardView) itemView.findViewById(R.id.carsCardView);

    }


    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());

    }

    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener=ic;
    }

    public void bind(ItemObject item) {

//        carTitle.setText(item.getMake());
//        carYear.setText(item.getYear());
//        carPrice.setText(item.getPrice());


        currentItem= item;
    }


//    @Override
//    public void onClick(View view) {
//        Toast.makeText(view.getContext(), "Clicked Country Position = " + items.get(getPosition()-1).getMake().toString(), Toast.LENGTH_SHORT).show();
////        Intent i = new Intent(context, CarInfoActivity.class);
////
////        i.putExtra("vehicleID", items.get());
////        context.startActivity(i);
//    }
}
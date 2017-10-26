package com.techneekfactory.popularcars.popularcars;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;
import com.squareup.picasso.Picasso;
import com.techneekfactory.popularcars.popularcars.database.Contact;
import com.techneekfactory.popularcars.popularcars.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import technolifestyle.com.imageslider.FlipperView;

public class FullScreenActivity extends AppCompatActivity{

//    private SliderLayout mDemoSlider;
//    DatabaseHandler db ;
//    String pos = "";
      String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen2);

        Bundle bundle = getIntent().getExtras();




       url = bundle.getString("url");


        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this)
                .load(url.toString())
                .into(imageView);
    }


}

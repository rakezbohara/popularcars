package com.techneekfactory.popularcars.popularcars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class FullScreenActivity extends AppCompatActivity{

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

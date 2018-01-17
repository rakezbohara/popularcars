package com.techneekfactory.popularcars.popularcars;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techneekfactory.popularcars.popularcars.database.Contact;
import com.techneekfactory.popularcars.popularcars.database.DatabaseHandler;
import com.techneekfactory.popularcars.popularcars.extras.CarImages;
import com.techneekfactory.popularcars.popularcars.extras.URLEndpoints;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class CarInfoActivity extends AppCompatActivity {

    public  int VehicleID;
    private List<CarImages> carImagesList;

//    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CarImagesCustomAdapter carImagesCustomAdapter;

    FlipperLayout flipperLayout;
    DatabaseHandler db ;

    String[] imageUrl;




    TextView bodyTypeTV, kilometersTV, carColorTV, locationTV, doorsTV, transmissionTV, fuelTV, carTitle, carPrice;

    ListView basicFeaturesListView, comfortFeaturesListView;


    TextInputEditText fullNameT, emailT, phoneT, mobileT, additionalInfoT;

    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<String> comfortFeaturesItems = new ArrayList<>();

    Button sendEnquiryButton;
    ProgressDialog webLoadingProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setTitle("Loading..");


        Bundle bundle = getIntent().getExtras();
        VehicleID = bundle.getInt("vehicleID");

        try{
            if (VehicleID < 1){

            } else{
                Log.d("VehicleID", "onCreate: "+ VehicleID);
            }
        } catch (Error e){

        }


        bodyTypeTV = (TextView) findViewById(R.id.bodyTypeTV);
        kilometersTV = (TextView) findViewById(R.id.kilometersTV);
        carColorTV = (TextView) findViewById(R.id.carColorTV);
        locationTV = (TextView) findViewById(R.id.locationTV);
        doorsTV = (TextView) findViewById(R.id.doorsTV);
        transmissionTV = (TextView) findViewById(R.id.transmissionTV);
        fuelTV = (TextView) findViewById(R.id.fuelTV);
        carTitle = (TextView) findViewById(R.id.carTitle);
        carPrice = (TextView) findViewById(R.id.carPrice);

        webLoadingProgressBar = new ProgressDialog(this);
        webLoadingProgressBar.setIndeterminate(true);
        webLoadingProgressBar.setCancelable(false);
        webLoadingProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        webLoadingProgressBar.setMessage("Loading...");


        carImagesList = new ArrayList<>();

        Log.d("Vehicle ID: ", String.valueOf(VehicleID));

//        recyclerView = (RecyclerView) findViewById(R.id.carGalleryRecycleView);
//        progressBarGallery = (ProgressBar) findViewById(R.id.progressBarGallery);

        db = new DatabaseHandler(this);

        flipperLayout = (FlipperLayout) findViewById(R.id.carGalleryRecycleView);

        getVehicleInfo();

       final List<Contact> contacts = db.getAllContacts();

        Log.d("Size", "onResponse: "+ String.valueOf(db.getContactsCount()));

        db.deleteAll();




        for (int i = 0; i < contacts.size(); i++) {

            Log.d("databaseList", "onCreate: " + contacts.get(i).getID() +  " "+ contacts.get(i).getName());
            FlipperView view = new FlipperView(getBaseContext());
            view.setImageUrl(contacts.get(i).getName().toString())
                    .setDescription("");
            flipperLayout.addFlipperView(view);
            view.setOnFlipperClickListener(new FlipperView.OnFlipperClickListener() {
                @Override
                public void onFlipperClick(FlipperView flipperView) {

                    Intent i = new Intent(CarInfoActivity.this, FullScreenActivity.class);
                    i.putExtra("position", String.valueOf(flipperLayout.getCurrentPagePosition()));
                    i.putExtra("url", String.valueOf(contacts.get(flipperLayout.getCurrentPagePosition()).getName().toString()));
                    startActivity(i);
//                    Toast.makeText(CarInfoActivity.this
//                            , "Here " + (flipperLayout.getCurrentPagePosition() + 1)
//                            , Toast.LENGTH_SHORT).show();
                }
            });
        }


        Log.d("Size", "onResponse: "+ String.valueOf(db.getContactsCount()));




        getVehicleInfoFromServer();





//        gridLayoutManager = new GridLayoutManager(this, 1);
//        gridLayoutManager.setSpanCount(1);
//        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//
////        recyclerView.setLayoutManager(gridLayoutManager);
//
//        carImagesCustomAdapter = new CarImagesCustomAdapter(this, carImagesList);
//        recyclerView.setAdapter(carImagesCustomAdapter);

        TabHost host = (TabHost) findViewById(R.id.featuresTabHost);
        host.setup();

        TabHost.TabSpec spec = host.newTabSpec("Comfort Features");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Comfort Features");
        host.addTab(spec);

        spec = host.newTabSpec("Basic Features");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Basic Features");
        host.addTab(spec);

        basicFeaturesListView = (ListView) findViewById(R.id.basicFeaturesListView);
        comfortFeaturesListView = (ListView) findViewById(R.id.comfortFeaturesListView);



        //Enquiry Form
        fullNameT = (TextInputEditText) findViewById(R.id.enqFullNameT);
        emailT = (TextInputEditText) findViewById(R.id.enqEmailT);
        phoneT = (TextInputEditText) findViewById(R.id.enqPhoneT);
        mobileT = (TextInputEditText) findViewById(R.id.enqMobileT);
        additionalInfoT = (TextInputEditText) findViewById(R.id.enqAdditionalInfoT);

        sendEnquiryButton = (Button) findViewById(R.id.enquirySendButton);

        sendEnquiryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEnquiryToServer();
            }
        });
    }


    public void getVehicleInfo(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLEndpoints.GateWayEndPointURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        String status, message, customerID, fullName, mobileNumber, password, language, mobileNumberVerified, active;

                        try {
                            JSONObject obj = new JSONObject(response);
                            status = obj.getString("status");


                            Log.d("JSON Reply", obj.toString());

                            if (status.equals("success")) {


                                JSONArray jsonDataArray = obj.getJSONArray("data");
                                JSONArray jsonImageArray = obj.getJSONArray("images");
                                JSONArray jsonBasicFeatureArray = obj.getJSONArray("basicFeatures");
                                JSONArray jsonComfortFeatureArray = obj.getJSONArray("comfortFeatures");


                                JSONObject data = jsonDataArray.getJSONObject(0);

//                                if (db.getContactsCount() > jsonImageArray.length()){
//                                    int i = db.getContactsCount() - jsonImageArray.length();
//                                    for( int j = 0; j< db.getContactsCount(); j++){
//
//                                    }
//
//
//                                }{}




                                for (int i = 0; i < jsonImageArray.length(); i++) {
                                    JSONObject images = jsonImageArray.getJSONObject(i);
//
//                                        Log.d("Size", "onResponse: " +db.getContactsCount());
//
                                    if (db.getContactsCount() <= jsonImageArray.length()){

                                        db.addContact(new Contact(images.getString("imageurl"), String.valueOf(i)));}


                                }



//                                imageId = new String[carImagesList.size()];
//                                carImagesList.toArray(imageId);

//                                Log.d("Car Image", "onResponse: "+ carImagesList.get(0).getImage());

                            }
                            db.notifyAll();


                        } catch (Throwable tx) {
                            Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"" + tx.getMessage());
                        }

                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "VEHICLEINFO");
                params.put("vehicleID", String.valueOf(VehicleID));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



    private void sendEnquiryToServer() {

        if (fullNameT.getText().length() < 1 || emailT.getText().length() < 4 || mobileT.getText().length() < 6) {
            Toast.makeText(this, "Please fill out all the fields correctly", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Thank you for your interest. We will get back to shortly.", Toast.LENGTH_LONG).show();
        }

    }

//    AutoSlider Setting Layout


    private void getVehicleInfoFromServer() {

//        progressBarGallery.setVisibility(View.VISIBLE);
//        webLoadingProgressBar.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLEndpoints.GateWayEndPointURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        String status, message, customerID, fullName, mobileNumber, password, language, mobileNumberVerified, active;

                        try {
                            JSONObject obj = new JSONObject(response);
                            status = obj.getString("status");


                            Log.d("JSON Reply", obj.toString());

                            if (status.equals("success")) {


                                JSONArray jsonDataArray = obj.getJSONArray("data");
                                JSONArray jsonImageArray = obj.getJSONArray("images");
                                JSONArray jsonBasicFeatureArray = obj.getJSONArray("basicFeatures");
                                JSONArray jsonComfortFeatureArray = obj.getJSONArray("comfortFeatures");

                                Log.d("basic", "onResponse: " + jsonBasicFeatureArray );


                                JSONObject data = jsonDataArray.getJSONObject(0);

                                carTitle.setText(data.getString("make") + " " + data.getString("model") + " " + data.getString("year"));
                                carPrice.setText(data.getString("price"));

                                bodyTypeTV.setText(data.getString("bodytype"));
                                kilometersTV.setText(data.getString("kilometers"));
                                carColorTV.setText(data.getString("car_color"));
                                locationTV.setText(data.getString("location"));
                                doorsTV.setText(data.getString("no_of_doors"));
                                transmissionTV.setText(data.getString("transmission"));
                                fuelTV.setText(data.getString("fuel"));

                                setTitle(carTitle.getText().toString());

                                for (int i = 0; i < jsonImageArray.length(); i++) {
                                    JSONObject images = jsonImageArray.getJSONObject(i);

                                    CarImages fImages =
                                            new CarImages(images.getString("imageurl"));

                                    carImagesList.add(fImages);
                                }

//                                imageId = new String[carImagesList.size()];
//                                carImagesList.toArray(imageId);

                                Log.d("Car Image", "onResponse: "+ carImagesList.get(0).getImage());






                                if (carImagesList.size() > 0 ){
                                    Log.d("imageUrl", carImagesList.get(0).getImage().toString());

                                    webLoadingProgressBar.dismiss();
                                }



                                for (int i = 0; i < jsonBasicFeatureArray.length(); i++) {
                                    JSONObject item = jsonBasicFeatureArray.getJSONObject(i);
                                    items.add(item.getString("basicfeature"));
                                    Log.d("Basicf", "onResponse: "+item.getString("basicfeature" ).toString());
                                }
                                updateBasicFeatures();


                                for (int i = 0; i < jsonComfortFeatureArray.length(); i++) {
                                    JSONObject item = jsonComfortFeatureArray.getJSONObject(i);

                                    comfortFeaturesItems.add(item.getString("comfortfeature"));
                                }

                                updateComfortFeatures();
                                carImagesCustomAdapter.notifyDataSetChanged();
                            }


                        } catch (Throwable tx) {
                            Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"" + tx.getMessage());
                        }

                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "VEHICLEINFO");
                params.put("vehicleID", String.valueOf(VehicleID));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

    public void updateBasicFeatures() {

        BulletListAdapter bulletListAdapterBasicFeatures = new BulletListAdapter(this, items);
        Log.d("items", "updateBasicFeatures: "+ items);
        basicFeaturesListView.setAdapter(bulletListAdapterBasicFeatures);
        bulletListAdapterBasicFeatures.notifyDataSetChanged();
        Utils.setListViewHeightBasedOnChildren(basicFeaturesListView);
    }

    public void updateComfortFeatures() {

        BulletListAdapter bulletListAdapterBasicFeatures = new BulletListAdapter(this, comfortFeaturesItems);
        comfortFeaturesListView.setAdapter(bulletListAdapterBasicFeatures);
        bulletListAdapterBasicFeatures.notifyDataSetChanged();
        Utils.setListViewHeightBasedOnChildren(comfortFeaturesListView);
    }
}
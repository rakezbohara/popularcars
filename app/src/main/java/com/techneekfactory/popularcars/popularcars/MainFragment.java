package com.techneekfactory.popularcars.popularcars;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techneekfactory.popularcars.popularcars.extras.SpecialOffers;
import com.techneekfactory.popularcars.popularcars.extras.URLEndpoints;
import com.techneekfactory.popularcars.popularcars.extras.Vehicles;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements  CarsCardClickListener{



    private TabLayout tabLayout;
    private ViewPager viewPager;

    private TextView seeAllFeaturedButton;

    private RecyclerView recyclerView;
    private RecyclerView latestCarsRecycleView;
    private RecyclerView specialOffersRecycleView;

    private GridLayoutManager gridLayoutManager;
    private GridLayoutManager latestCarsGridLayoutManager;
    private GridLayoutManager specialOffersGridLayoutManager;


    private CarsCustomAdapter carsCustomAdapter;
    private List<Vehicles> vehiclesList;

    private CarsCustomAdapter latestCarsCustomAdapter;
    private List<Vehicles> latestVehiclesList;


    private OffersCustomAdapter offersCustomAdapter;
    private List<SpecialOffers> specialOffersList;

    public MainFragment() {
        // Required empty public constructor
    }

    public ProgressBar progressBarFeatured;
    public ProgressBar progressBarSpecial;
    public ProgressBar progressBarLatest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_main, container, false);





        tabLayout = v.findViewById(R.id.filterTabLayout);

        viewPager = v.findViewById(R.id.filterViewPager);
        viewPager.setAdapter(new BrowseFilterFragmentPagerAdapter(getActivity().getSupportFragmentManager(), getContext()));

        tabLayout.setupWithViewPager(viewPager);


        seeAllFeaturedButton = v.findViewById(R.id.seeAllFeturedButton);

        seeAllFeaturedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start NewActivity.class
                Intent myIntent = new Intent(getContext(), BrowseCarsActivity.class);
                myIntent.putExtra("FEATUREDCARS", "YES");


                startActivity(myIntent);
            }
        });






        recyclerView = (RecyclerView) v.findViewById(R.id.featuredCarsRecycleView);
        latestCarsRecycleView = (RecyclerView) v.findViewById(R.id.latestCarsRecycleView);
        specialOffersRecycleView = (RecyclerView) v.findViewById(R.id.specialOffersRecycleView);

        vehiclesList = new ArrayList<>();
        latestVehiclesList = new ArrayList<>();
        specialOffersList = new ArrayList<>();


        progressBarSpecial = (ProgressBar) v.findViewById(R.id.progressBarSpecial);
        progressBarFeatured = (ProgressBar)  v.findViewById(R.id.progressBarFeatured);
        progressBarLatest = (ProgressBar) v.findViewById(R.id.progressBarLatest);




        loadSpecialOffers();
        loadFeaturedVehicles();
        loadLatestVehicles();


        gridLayoutManager = new GridLayoutManager(this.getContext(), 1);
        gridLayoutManager.setSpanCount(1);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        latestCarsGridLayoutManager = new GridLayoutManager(this.getContext(), 1);
        latestCarsGridLayoutManager.setSpanCount(1);
        latestCarsGridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        specialOffersGridLayoutManager = new GridLayoutManager(this.getContext(), 1);
        specialOffersGridLayoutManager.setSpanCount(1);
        specialOffersGridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(gridLayoutManager);
        latestCarsRecycleView.setLayoutManager(latestCarsGridLayoutManager);
        specialOffersRecycleView.setLayoutManager(specialOffersGridLayoutManager);

        carsCustomAdapter = new CarsCustomAdapter(this.getContext(), vehiclesList, true);
        latestCarsCustomAdapter = new CarsCustomAdapter(this.getContext(), latestVehiclesList, true);
        offersCustomAdapter = new OffersCustomAdapter(this.getContext(), specialOffersList, true);

        recyclerView.setAdapter(carsCustomAdapter);
        latestCarsRecycleView.setAdapter(latestCarsCustomAdapter);
        specialOffersRecycleView.setAdapter(offersCustomAdapter);


        carsCustomAdapter.setCarsCardClickListener(this);
        latestCarsCustomAdapter.setCarsCardClickListener(this);


        return v;
    }


    private void loadFeaturedVehicles() {


        progressBarFeatured.setVisibility(View.VISIBLE);

        //Get the Featured Cars
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLEndpoints.GateWayEndPointURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        String status, message, customerID, fullName, mobileNumber, password, language, mobileNumberVerified, active;

                        try {
                            JSONObject obj = new JSONObject(response);
                            status = obj.getString("status");
                            message = obj.getString("message");

                            Log.d("JSON Reply", obj.toString());

                            if (status.equals("success")) {

                                JSONArray jsonDataArray = obj.getJSONArray("data");

                                for (int i = 0; i < jsonDataArray.length(); i++) {
                                    JSONObject data = jsonDataArray.getJSONObject(i);

                                    Vehicles fVehicles =
                                            new Vehicles(
                                                    data.getInt("vehicleID"),
                                                    data.getInt("year"),
                                                    data.getString("price"),
                                                    data.getString("image"),
                                                    data.getString("make"),
                                                    data.getString("model"));


                                    vehiclesList.add(fVehicles);
                                }

                                carsCustomAdapter.notifyDataSetChanged();

                                Log.d("FeaturedList", vehiclesList.toString() + " -- " + vehiclesList.size());

                                if (vehiclesList != null){
                                    progressBarFeatured.setVisibility(View.GONE);
                                }

//                                customerID = data.getString("customerID");


                            }

                        } catch (Throwable tx) {
                            Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
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
//                String userMobileNumber = mobileText.getText().toString();
//                String userPassword = passwordText.getText().toString();

                params.put("action", "FEATUREDCARS");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);


    }


    private void loadLatestVehicles() {

        progressBarLatest.setVisibility(View.VISIBLE);

        //Get the Featured Cars
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLEndpoints.GateWayEndPointURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        String status, message, customerID, fullName, mobileNumber, password, language, mobileNumberVerified, active;

                        try {
                            JSONObject obj = new JSONObject(response);
                            status = obj.getString("status");
                            message = obj.getString("message");

                            Log.d("JSON Reply", obj.toString());

                            if (status.equals("success")) {

                                JSONArray jsonDataArray = obj.getJSONArray("data");

                                for (int i = 0; i < jsonDataArray.length(); i++) {
                                    JSONObject data = jsonDataArray.getJSONObject(i);

                                    Vehicles fVehicles =
                                            new Vehicles(
                                                    data.getInt("vehicleID"),
                                                    data.getInt("year"),
                                                    data.getString("price"),
                                                    data.getString("image"),
                                                    data.getString("make"),
                                                    data.getString("model"));


                                    latestVehiclesList.add(fVehicles);
                                }

                                latestCarsCustomAdapter.notifyDataSetChanged();

                                if (latestVehiclesList != null){
                                    progressBarLatest.setVisibility(View.GONE);
                                }


//                                customerID = data.getString("customerID");


                            }

                        } catch (Throwable tx) {
                            Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
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
//                String userMobileNumber = mobileText.getText().toString();
//                String userPassword = passwordText.getText().toString();

                params.put("action", "LATESTCARS");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);


    }

    private void loadSpecialOffers() {
        progressBarSpecial.setVisibility(View.VISIBLE);

        //Get the Featured Cars
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLEndpoints.GateWayEndPointURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        String status, message, customerID, fullName, mobileNumber, password, language, mobileNumberVerified, active;

                        try {
                            JSONObject obj = new JSONObject(response);
                            status = obj.getString("status");
                            message = obj.getString("message");

                            Log.d("JSON Reply", obj.toString());

                            if (status.equals("success")) {

                                JSONArray jsonDataArray = obj.getJSONArray("data");

                                for (int i = 0; i < jsonDataArray.length(); i++) {
                                    JSONObject data = jsonDataArray.getJSONObject(i);

                                    SpecialOffers tSpecialOffers =
                                            new SpecialOffers(
                                                    data.getInt("offerid"),
                                                    data.getString("title"),
                                                    data.getString("image"));


                                    specialOffersList.add(tSpecialOffers);
                                }

                                offersCustomAdapter.notifyDataSetChanged();

                                if (specialOffersList != null){
                                    progressBarSpecial.setVisibility(View.GONE);
                                }

//                                customerID = data.getString("customerID");
                            }

                        } catch (Throwable tx) {
                            Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
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
//                String userMobileNumber = mobileText.getText().toString();
//                String userPassword = passwordText.getText().toString();

                params.put("action", "SPECIALOFERS");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);


    }

    @Override
    public void carsCardItemClicked(View view, int position, int vehicleID) {

        Log.d("Helloo ", String.valueOf(vehicleID));

//        ((MainActivity)getActivity()).showCarInfoPage(vehicleID);

        Intent i = new Intent(getContext(), CarInfoActivity.class);

        i.putExtra("vehicleID", vehicleID);
        startActivity(i);




    }
}

package com.techneekfactory.popularcars.popularcars;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techneekfactory.popularcars.popularcars.extras.Makes;
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
public class FilterByBodyTypeFragment extends Fragment {


    private ArrayList<Integer> bodyTypesIDs;
    private ArrayList<String> bodyTypesa;
    private ArrayList<String> imageIds;
    BrandGridAdapter adapter;

    public ProgressBar progressBarBrandGrid;

    GridView grid;
    Integer[] bodyTypesID ;
//            = {
//            "1","2","3","4","7","6"
//    };
    String[] bodyTypes;
//            = {
//            "Sedan",
//            "SUV",
//            "Hatchback",
//            "MPV",
//            "Crossover",
//            "Commercial"
//    };
    String[] imageId;
//        = {
//            R.drawable.body_sedan,
//            R.drawable.body_suv,
//            R.drawable.body_hatchback,
//            R.drawable.body_mpv,
//            R.drawable.body_crossover,
//            R.drawable.body_commercial
//    };

    public FilterByBodyTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       final View v = inflater.inflate(R.layout.fragment_filter_by_brand, container, false);

        bodyTypesIDs = new ArrayList<>();
         bodyTypesa = new ArrayList<>();
       imageIds = new ArrayList<>();

        progressBarBrandGrid = (ProgressBar) v.findViewById(R.id.progressBarBrandGrid);

        progressBarBrandGrid.setVisibility(View.VISIBLE);

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

                                    bodyTypesIDs.add(data.getInt("body_type_id"));
                                    bodyTypesa.add(data.getString("body_type"));
                                    imageIds.add(data.getString("body_type_icon"));




//                                    Makes makes =
//                                            new Makes(
//                                                    data.getInt("make_id"),
//                                                    data.getString("make"),
//                                                    data.getString("make_icon")
//                                            );
//
//
//                                    makesList.add(makes);
                                }

                                bodyTypesID = new Integer[bodyTypesIDs.size()];
                                bodyTypesIDs.toArray(bodyTypesID);
                                imageId = new String[imageIds.size()];
                                imageIds.toArray(imageId);
                                bodyTypes = new String[bodyTypesa.size()];
                                bodyTypesa.toArray(bodyTypes);


                                final BrandGridAdapter adapter = new BrandGridAdapter(getContext(), bodyTypes, imageId);
                                adapter.notifyDataSetChanged();
                                if (bodyTypesIDs != null){
                                    progressBarBrandGrid.setVisibility(View.GONE);
                                }

                                grid = (GridView) v.findViewById(R.id.brandGridView);
                                grid.setAdapter(adapter);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    grid.setNestedScrollingEnabled(true);
                                }
                                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int position, long id) {
//                Toast.makeText(getContext(), "You Clicked at " + bodyTypes[+position], Toast.LENGTH_SHORT).show();

                                        // Start NewActivity.class
                                        Intent myIntent = new Intent(getActivity(),
                                                BrowseCarsActivity.class);

                                        myIntent.putExtra("bodyTypeID", String.valueOf(bodyTypesID[+position]));
                                        myIntent.putExtra("type", "body");
                                        myIntent.putExtra("bodyType", String.valueOf(bodyTypes[+position]));

                                        startActivity(myIntent);




                                    }
                                });



                                Log.d("FiletredbyBodytype", bodyTypesa.toString() + " -- " + bodyTypesa.size());


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

                params.put("action", "VEHICLEBODYTYPES");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);







//        makesList = new Makes()





        return v;
    }

    private void loadFeaturedVehicles() {

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

                                    bodyTypesIDs.add(data.getInt("body_type_id"));
                                    bodyTypesa.add(data.getString("body_type"));
                                    imageIds.add(data.getString("body_type_icon"));




//                                    Makes makes =
//                                            new Makes(
//                                                    data.getInt("make_id"),
//                                                    data.getString("make"),
//                                                    data.getString("make_icon")
//                                            );
//
//
//                                    makesList.add(makes);
                                }

                                adapter.notifyDataSetChanged();

                                Log.d("FiletredbyBodytype", bodyTypesa.toString() + " -- " + bodyTypesa.size());


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

                params.put("action", "VEHICLEBODYTYPES");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);


    }



}

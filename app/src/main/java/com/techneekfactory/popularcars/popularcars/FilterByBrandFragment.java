package com.techneekfactory.popularcars.popularcars;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StringDef;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilterByBrandFragment extends Fragment {


    Makes makes;

    private ArrayList<String> brands;
    private ArrayList<String> imageUrls;
    private ArrayList<Integer> brandIDs;
    BrandGridAdapter adapter;

    public ProgressBar progressBarBrandGrid;



    GridView grid;
    String[] brandNames;
//            "KIA",
//            "Mazda",
//            "Mitsubishi",
//            "Nissan",
//            "Renault",
//            "Suzuki",
//            "Ford",
//            "Inifiniti"
//    };
    String[] imageUrl;
//        = {
//            R.drawable.logo_kia,
//            R.drawable.logo_mazda,
//            R.drawable.logo_mitsubishi,
//            R.drawable.logo_nissan,
//            R.drawable.logo_renault,
//            R.drawable.logo_suzuki,
//            R.drawable.make_ford,
//            R.drawable.make_infiniti
//    };

    Integer[] brandID;
//            = {
//            9,
//            22,
//            26,
//            3,
//            4,
//            35,
//            15,
//            17
//    };

    public FilterByBrandFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       final View v = inflater.inflate(R.layout.fragment_filter_by_brand, container, false);

        progressBarBrandGrid = (ProgressBar) v.findViewById(R.id.progressBarBrandGrid);

        brands = new ArrayList<String>();
        brandIDs = new ArrayList<Integer>();
        imageUrls = new ArrayList<String>();

        progressBarBrandGrid.setVisibility(View.VISIBLE);

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

                                    brandIDs.add(data.getInt("make_id"));
                                    brands.add(data.getString("make"));
                                    imageUrls.add(data.getString("make_icon"));




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

                                brandNames = new String[brands.size()];
                                brands.toArray(brandNames);
                                Log.d("brandBame",brandNames[0] + brandNames [1]);
                                brandID = new Integer[brandIDs.size()];
                                brandIDs.toArray(brandID);
                                imageUrl = new String[imageUrls.size()];
                                imageUrls.toArray(imageUrl);

                                if (brandNames != null){
                                    progressBarBrandGrid.setVisibility(View.GONE);
                                }
                                final BrandGridAdapter adapter = new BrandGridAdapter(getContext(), brandNames, imageUrl);

                                adapter.notifyDataSetChanged();

                                grid = (GridView) v.findViewById(R.id.brandGridView);
                                grid.setAdapter(adapter);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    grid.setNestedScrollingEnabled(true);
                                }
                                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int position, long id) {
//                Toast.makeText(getContext(), "You Clicked at " + brandID[+position] + " - " + brandNames[+position], Toast.LENGTH_SHORT).show();



                                        // Start NewActivity.class
                                        Intent myIntent = new Intent(getActivity(),
                                                BrowseCarsActivity.class);

                                        myIntent.putExtra("brandID", String.valueOf(brandID[+position]));
                                        myIntent.putExtra("type", "brand");
                                        myIntent.putExtra("brand",String.valueOf(brandNames[+position]));

                                        startActivity(myIntent);



                                    }
                                });

                                Log.d("FilteredByBrands", brands.toString() + " -- " + brands.size());


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

                params.put("action", "VEHICLEMAKES");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);
//        loadFeaturedVehicles();









//        BrandGridAdapter adapter = new BrandGridAdapter(getContext(), brandNames, imageUrl);



        return v;
    }




}

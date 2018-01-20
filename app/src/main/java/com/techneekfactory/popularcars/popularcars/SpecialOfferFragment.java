package com.techneekfactory.popularcars.popularcars;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techneekfactory.popularcars.popularcars.extras.SpecialOffers;
import com.techneekfactory.popularcars.popularcars.extras.URLEndpoints;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by RAKEZ on 11/10/2017.
 */

public class SpecialOfferFragment extends Fragment {

    public ProgressBar progressBarSpecial;
    private OffersCustomAdapter offersCustomAdapter;
    private List<SpecialOffers> specialOffersList;
    private GridLayoutManager specialOffersGridLayoutManager;
    private RecyclerView specialOffersRecycleView;

    public SpecialOfferFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_special_offer, container, false);
        progressBarSpecial = v.findViewById(R.id.progressBarSpecial);
        specialOffersRecycleView =  v.findViewById(R.id.specialOffersRecycleView);
        specialOffersList = new ArrayList<>();
        loadSpecialOffers();
        specialOffersGridLayoutManager = new GridLayoutManager(this.getContext(), 1);
        specialOffersGridLayoutManager.setSpanCount(1);
        specialOffersGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        specialOffersRecycleView.setLayoutManager(specialOffersGridLayoutManager);
        offersCustomAdapter = new OffersCustomAdapter(this.getContext(), specialOffersList, true);
        specialOffersRecycleView.setAdapter(offersCustomAdapter);
        return v;
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
}

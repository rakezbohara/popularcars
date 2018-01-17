package com.techneekfactory.popularcars.popularcars;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techneekfactory.popularcars.popularcars.extras.URLEndpoints;
import com.techneekfactory.popularcars.popularcars.seemore.ItemObject;
import com.techneekfactory.popularcars.popularcars.seemore.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LatestListActivity extends AppCompatActivity {


    private LinearLayoutManager lLayout;
    List<ItemObject> rowListItem;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.featured_recycle);

        progressDialog = new ProgressDialog(this);
//        setTitle("Featured Cars");


        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Latest Cars");

//        topToolBar.setLogo(R.drawable.logo);
//        topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));

        rowListItem = new ArrayList<>();
        lLayout = new LinearLayoutManager(LatestListActivity.this);

        RecyclerView rView = (RecyclerView)findViewById(R.id.featuredCarsRecycleView);
        rView.setLayoutManager(lLayout);

        final RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(LatestListActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLEndpoints.GateWayEndPointURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        progressDialog.dismiss();

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

                                    ItemObject fVehicles =
                                            new ItemObject(
                                                    data.getInt("vehicleID"),
                                                    data.getInt("year"),
                                                    data.getString("price"),
                                                    data.getString("image"),
                                                    data.getString("make"),
                                                    data.getString("model"));


                                    rowListItem.add(fVehicles);
                                }


                                rcAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();



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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        if(id == R.id.action_refresh){
//            Toast.makeText(FeaturedListActivity.this, "Refresh App", Toast.LENGTH_LONG).show();
//        }
//        if(id == R.id.action_new){
//            Toast.makeText(FeaturedListActivity.this, "Create Text", Toast.LENGTH_LONG).show();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    private List<ItemObject> getAllItemList(){
//
//        List<ItemObject> allItems = new ArrayList<ItemObject>();
//        allItems.add(new ItemObject("United States", R.drawable.newyork));
//        allItems.add(new ItemObject("Canada", R.drawable.canada));
//        allItems.add(new ItemObject("United Kingdom", R.drawable.uk));
//        allItems.add(new ItemObject("Germany", R.drawable.germany));
//        allItems.add(new ItemObject("Sweden", R.drawable.sweden));
//
//        return allItems;
//    }

    private List<ItemObject> loadFeaturedVehicles() {

        final List<ItemObject> allItems = new ArrayList<ItemObject>();
//
//        progressBarFeatured.setVisibility(View.VISIBLE);

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

                                    ItemObject fVehicles =
                                            new ItemObject(
                                                    data.getInt("vehicleID"),
                                                    data.getInt("year"),
                                                    data.getString("price"),
                                                    data.getString("image"),
                                                    data.getString("make"),
                                                    data.getString("model"));


                                    allItems.add(fVehicles);
                                }




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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return allItems;


    }
}

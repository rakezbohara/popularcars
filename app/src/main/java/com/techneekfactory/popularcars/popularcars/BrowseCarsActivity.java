package com.techneekfactory.popularcars.popularcars;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techneekfactory.popularcars.popularcars.extras.URLEndpoints;
import com.techneekfactory.popularcars.popularcars.extras.Vehicles;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BrowseCarsActivity extends AppCompatActivity implements CarsCardClickListener {


    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private CarsCustomAdapter carsCustomAdapter;
    private List<Vehicles> vehiclesList;
    private TextView noResultsTV;


    private String carBrandID = "0";
    private String bodyTypeID = "0";
    private String budget = "0";
    private String carAge = "0";
    private String transmission = "0";
    private String kilometers = "0";
    private String fuelType = "0";


    private Boolean showFeatured = false;

    private Spinner brandSpinner, bodyTypeSpinner, budgetSpinner, kilometerSpinner, carAgeSpinner, fuelTypeSpinner, transmissionSpinner;

    private Boolean brandSpinnerFirst = false;
    private Boolean bodyTypeSpinnerFirst = false;
    private Boolean budgetSpinnerFirst = false;
    private Boolean kilometerSpinnerFirst = false;
    private Boolean carAgeSpinnerFirst = false;
    private Boolean fuelTypeSpinnerFirst = false;
    private Boolean transmissionSpinnerFirst = false;


    private LinkedHashMap<String, String> brandKV;
    private LinkedHashMapAdapter<String, String> brandKVAdapter;

    private LinkedHashMap<String, String> bodyTypeKV;
    private LinkedHashMapAdapter<String, String> bodyTypeKVAdapter;

    private LinkedHashMap<String, String> budgetKV;
    private LinkedHashMapAdapter<String, String> budgetKVAdapter;

    private LinkedHashMap<String, String> kilometerKV;
    private LinkedHashMapAdapter<String, String> kilometerKVAdapter;

    private LinkedHashMap<String, String> carAgeKV;
    private LinkedHashMapAdapter<String, String> carAgeKVAdapter;

    private LinkedHashMap<String, String> fuelKV;
    private LinkedHashMapAdapter<String, String> fuelKVAdapter;

    private LinkedHashMap<String, String> transmissionKV;
    private LinkedHashMapAdapter<String, String> transmissionKVAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_cars);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        noResultsTV = (TextView) findViewById(R.id.noResultsTV);

        brandSpinner = (Spinner) findViewById(R.id.brandSpinner);
        bodyTypeSpinner = (Spinner) findViewById(R.id.bodyTypeSpinner);
        budgetSpinner = (Spinner) findViewById(R.id.budgetSpinner);
        kilometerSpinner = (Spinner) findViewById(R.id.kilometerSpinner);
        carAgeSpinner = (Spinner) findViewById(R.id.carAgeSpinner);
        fuelTypeSpinner = (Spinner) findViewById(R.id.fuelTypeSpinner);
        transmissionSpinner = (Spinner) findViewById(R.id.transmissionSpinner);

        //Brand Filter
        brandKV = new LinkedHashMap<>();
        loadBrandKV();

//        brandKV.put("0", "Any Brand");
//        brandKV.put("9", "KIA");
//        brandKV.put("22", "Mazda");
//        brandKV.put("26", "Mitsubishi");
//        brandKV.put("3", "Nissan");
//        brandKV.put("4", "Renault");
//        brandKV.put("35", "Suzuki");
//        brandKV.put("15", "Ford");
//        brandKV.put("17", "Inifiniti");

        brandKVAdapter = new LinkedHashMapAdapter<String, String>(this, android.R.layout.simple_spinner_item, brandKV);
        brandKVAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        brandSpinner.setAdapter(brandKVAdapter);


        brandSpinner.setOnItemSelectedListener(brandSpinnerItemSelected);


        //Body Type Filter
        bodyTypeKV = new LinkedHashMap<>();

        loadbodyTypeKV();

//        bodyTypeKV.put("0", "Any Body Type");
//        bodyTypeKV.put("1", "Sedan");
//        bodyTypeKV.put("2", "SUV");
//        bodyTypeKV.put("3", "Hatchback");
//        bodyTypeKV.put("4", "MPV");
//        bodyTypeKV.put("7", "Crossover");
//        bodyTypeKV.put("6", "Commercial");


        bodyTypeKVAdapter = new LinkedHashMapAdapter<String, String>(this, android.R.layout.simple_spinner_item, bodyTypeKV);
        bodyTypeKVAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bodyTypeSpinner.setAdapter(bodyTypeKVAdapter);
        bodyTypeSpinner.setOnItemSelectedListener(bodyTypeSpinnerItemSelected);


        //Budget Filter
        budgetKV = new LinkedHashMap<>();

        budgetKV.put("0", "Any Budget");

        budgetKV.put("0|1000", "OMR 0 - OMR 1000");
        budgetKV.put("1001|2000", "OMR 1001 - OMR 2000");
        budgetKV.put("2001|3000", "OMR 2001 - OMR 3000");
        budgetKV.put("3001|4000", "OMR 3001 - OMR 4000");
        budgetKV.put("4001|5000", "OMR 4001 - OMR 5000");
        budgetKV.put("5001|10000", "OMR 5001 - OMR 10000");
        budgetKV.put("10001|15000", "OMR 10001 - OMR 15000");
        budgetKV.put("15001|20000", "OMR 15001 - OMR 20000");
        budgetKV.put("20001|25000", "OMR 20001 - OMR 25000");
        budgetKV.put("25001|30000", "OMR 25001 - OMR 30000");
        budgetKV.put("30001|35000", "OMR 30001 - OMR 35000");
        budgetKV.put("35001|40000", "OMR 35001 - OMR 40000");
        budgetKV.put("40001|45000", "OMR 40001 - OMR 45000");
        budgetKV.put("45001|50000", "OMR 45001 - OMR 50000");
        budgetKV.put("50001|55000", "OMR 50001 - OMR 55000");
        budgetKV.put("55001|60000", "OMR 55001 - OMR 60000");


        budgetKVAdapter = new LinkedHashMapAdapter<String, String>(this, android.R.layout.simple_spinner_item, budgetKV);
        budgetKVAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        budgetSpinner.setAdapter(budgetKVAdapter);
        budgetSpinner.setOnItemSelectedListener(budgetSpinnerItemSelected);


        //KiloMeter Filter
        kilometerKV = new LinkedHashMap<>();

        kilometerKV.put("0", "Any Kilometer");
        kilometerKV.put("1|20000", "Below 20000");
        kilometerKV.put("20001|40000", "20001 to 40000");
        kilometerKV.put("40001|60000", "40001 to 60000");
        kilometerKV.put("60001|80000", "60001 to 80000");
        kilometerKV.put("80001|99000", "80001 to 99000");
        kilometerKV.put("99001|1000000", "99001 and Above");

        kilometerKVAdapter = new LinkedHashMapAdapter<String, String>(this, android.R.layout.simple_spinner_item, kilometerKV);
        kilometerKVAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        kilometerSpinner.setAdapter(kilometerKVAdapter);
        kilometerSpinner.setOnItemSelectedListener(kilometersSpinnerItemSelected);

        //Car Age
        carAgeKV = new LinkedHashMap<>();
        carAgeKV.put("0", "Any Car Age");
        carAgeKV.put("0|5", "0 year - 5 years");
        carAgeKV.put("6|10", "6 years - 10 years");
        carAgeKV.put("11|16", "11 years - 16 years");
        carAgeKV.put("17|20", "17 years - 26 years");
        carAgeKV.put("21|25", "21 years - 25 years");

        carAgeKVAdapter = new LinkedHashMapAdapter<String, String>(this, android.R.layout.simple_spinner_item, carAgeKV);
        carAgeKVAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        carAgeSpinner.setAdapter(carAgeKVAdapter);
        carAgeSpinner.setOnItemSelectedListener(carAgeSpinnerItemSelected);


        //Transmission
        transmissionKV = new LinkedHashMap<>();
        transmissionKV.put("0", "Any Transmission");
        transmissionKV.put("1", "Automatic");
        transmissionKV.put("2", "Manual");

        transmissionKVAdapter = new LinkedHashMapAdapter<String, String>(this, android.R.layout.simple_spinner_item, transmissionKV);
        transmissionKVAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        transmissionSpinner.setAdapter(transmissionKVAdapter);
        transmissionSpinner.setOnItemSelectedListener(transmissionSpinnerItemSelected);


        //Fuel
        fuelKV = new LinkedHashMap<>();
        fuelKV.put("0", "Any Fuel Type");
        fuelKV.put("1", "Petrol");
        fuelKV.put("2", "Diesel");

        fuelKVAdapter = new LinkedHashMapAdapter<String, String>(this, android.R.layout.simple_spinner_item, fuelKV);
        fuelKVAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fuelTypeSpinner.setAdapter(fuelKVAdapter);
        fuelTypeSpinner.setOnItemSelectedListener(fuelSpinnerItemSelected);


        Bundle bundle = getIntent().getExtras();


        String type = "0";
        String brand = "0";
        String bodytype = "0";
        String featuredString = bundle.getString("FEATUREDCARS");
        type = bundle.getString("type");
        brand = bundle.getString("brand");
        bodytype = bundle.getString("bodyType");
        if (type.equals("brand")){
            brandSpinner.setVisibility(View.GONE);
            setTitle("Brand:" + brand);
        }
        if (type.equals("0") && brand.equals("0") && bodytype.equals("0")){
            System.out.println("hi");
            setTitle("Browse Cars");
        }
        if (type.equals("body")){
            bodyTypeSpinner.setAdapter(brandKVAdapter);
            brandSpinner.setVisibility(View.GONE);
            setTitle("Body:" + bodytype);
//            budgetSpinner.setAdapter(brandKVAdapter);
        }

        try {
            if (featuredString != null) {
                showFeatured = true;
            }
        } catch (Error ignored) {

        }


        carBrandID = bundle.getString("brandID");
        if (carBrandID == null) {
            carBrandID = "0";
        }

        bodyTypeID = bundle.getString("bodyTypeID");
        if (bodyTypeID == null) {
            bodyTypeID = "0";
        }

        budget = bundle.getString("budget");
        if (budget == null) {
            budget = "0";
        }


        int kIndex = 0;
        for (Object key : brandKV.keySet()) {
            if (key.toString().equals(carBrandID)) {
                Log.d("KEYS", "Comingn here");
                brandSpinner.setSelection(kIndex);
            }
            kIndex++;
        }


        kIndex = 0;
        for (Object key : bodyTypeKV.keySet()) {
            if (key.toString().equals(bodyTypeID)) {
                bodyTypeSpinner.setSelection(kIndex);
            }
            kIndex++;
        }

        kIndex = 0;
        for (Object key : budgetKV.keySet()) {
            if (key.toString().equals(budget)) {
                budgetSpinner.setSelection(kIndex);
            }
            kIndex++;
        }


        kIndex = 0;
        for (Object key : kilometerKV.keySet()) {
            if (key.toString().equals(kilometers)) {
                kilometerSpinner.setSelection(kIndex);
            }
            kIndex++;
        }

        kIndex = 0;
        for (Object key : carAgeKV.keySet()) {
            if (key.toString().equals(carAge)) {
                carAgeSpinner.setSelection(kIndex);
            }
            kIndex++;
        }

        kIndex = 0;
        for (Object key : fuelKV.keySet()) {
            if (key.toString().equals(fuelType)) {
                fuelTypeSpinner.setSelection(kIndex);
            }
            kIndex++;
        }


        kIndex = 0;
        for (Object key : transmissionKV.keySet()) {
            if (key.toString().equals(transmission)) {
                transmissionSpinner.setSelection(kIndex);
            }
            kIndex++;
        }


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.browseCarsRecycleView);

        vehiclesList = new ArrayList<>();


        String searchFlag = bundle.getString("SEARCH");
        Boolean searchMode = false;

        try {
            if (searchFlag != null) {
                searchMode = true;
            }
        } catch (Error ignored) {

        }


        if (!searchMode){
            loadVehicles();
    }
        else
        {
            setTitle("Search Cars");
        }


Log.d("SEARCHMODE ", String.valueOf(searchMode));


        gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setSpanCount(2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setNestedScrollingEnabled(false);

        carsCustomAdapter = new CarsCustomAdapter(this, vehiclesList);
        recyclerView.setAdapter(carsCustomAdapter);

        carsCustomAdapter.setCarsCardClickListener(this);

        noResultsTV.setVisibility(View.INVISIBLE);

    }

    AdapterView.OnItemSelectedListener brandSpinnerItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            String[] separated = adapterView.getSelectedItem().toString().split("=");
            carBrandID = separated[0];
            Log.d("Key:", separated[0]);
            Log.d("Value: ", separated[1]);
            if (brandSpinnerFirst)
                loadVehicles();
            else
                brandSpinnerFirst = true;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    AdapterView.OnItemSelectedListener bodyTypeSpinnerItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            String[] separated = adapterView.getSelectedItem().toString().split("=");
            bodyTypeID = separated[0];

            if (bodyTypeSpinnerFirst)
                loadVehicles();
            else
                bodyTypeSpinnerFirst = true;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    AdapterView.OnItemSelectedListener budgetSpinnerItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            String[] separated = adapterView.getSelectedItem().toString().split("=");
            budget = separated[0];

            if (budgetSpinnerFirst)
                loadVehicles();
            else
                budgetSpinnerFirst = true;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    AdapterView.OnItemSelectedListener fuelSpinnerItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String[] separated = adapterView.getSelectedItem().toString().split("=");
            fuelType = separated[0];

            if (fuelTypeSpinnerFirst)
                loadVehicles();
            else
                fuelTypeSpinnerFirst = true;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    AdapterView.OnItemSelectedListener transmissionSpinnerItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String[] separated = adapterView.getSelectedItem().toString().split("=");
            transmission = separated[0];
            if (transmissionSpinnerFirst)
                loadVehicles();
            else
                transmissionSpinnerFirst = true;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    AdapterView.OnItemSelectedListener carAgeSpinnerItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String[] separated = adapterView.getSelectedItem().toString().split("=");
            carAge = separated[0];

            if (carAgeSpinnerFirst)
                loadVehicles();
            else
                carAgeSpinnerFirst = true;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    AdapterView.OnItemSelectedListener kilometersSpinnerItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            String[] separated = adapterView.getSelectedItem().toString().split("=");
            kilometers = separated[0];

            if (kilometerSpinnerFirst)
                loadVehicles();
            else
                kilometerSpinnerFirst = true;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    private void loadVehicles() {

        vehiclesList.clear();
        noResultsTV.setVisibility(View.INVISIBLE);

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


                                if (vehiclesList.size() < 1) {
                                    noResultsTV.setVisibility(View.VISIBLE);
                                } else {
                                    noResultsTV.setVisibility(View.INVISIBLE);
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

                params.put("action", "ALLCARS");

                if (showFeatured) {
                    params.put("featured", "featured");
                }

                if (!carBrandID.equals("0")) {
                    params.put("makeID", carBrandID);
                }
                if (!bodyTypeID.equals("0")) {
                    params.put("bodyTypeID", bodyTypeID);
                }
                if (!budget.equals("0")) {
                    params.put("budget", budget);
                }
                if (!kilometers.equals("0")) {
                    params.put("kilometers", kilometers);
                }

                if (!carAge.equals("0")) {
                    params.put("carage", carAge);
                }

                if (!transmission.equals("0")) {
                    params.put("transmission", transmission);
                }

                if (!fuelType.equals("0")) {
                    params.put("fueltype", fuelType);
                }


                Log.d("Params = ", params.toString());


//
//
//                Log.d("Car brand ID: " , carBrandID);
//                Log.d("Car bodyTypeID: " , bodyTypeID);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void loadBrandKV() {


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

//                                    brandIDs.add(data.getInt("make_id"));
//                                    brands.add(data.getString("make"));
//                                    imageUrls.add(data.getString("make_icon"));

                                    brandKV.put(data.getString("make_id"), data.getString("make"));


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

                                brandKVAdapter.notifyDataSetChanged();

//                                brandNames = new String[brands.size()];
//                                brands.toArray(brandNames);
//                                Log.d("brandBame", brandNames[0] + brandNames[1]);
//                                brandID = new Integer[brandIDs.size()];
//                                brandIDs.toArray(brandID);
//                                imageUrl = new String[imageUrls.size()];
//                                imageUrls.toArray(imageUrl);
//                                final BrandGridAdapter adapter = new BrandGridAdapter(getContext(), brandNames, imageUrl);

//                                adapter.notifyDataSetChanged();
//
//                                grid = (GridView) v.findViewById(R.id.brandGridView);
//                                grid.setAdapter(adapter);
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                    grid.setNestedScrollingEnabled(true);
//                                }
//                                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//
//                                    @Override
//                                    public void onItemClick(AdapterView<?> parent, View view,
//                                                            int position, long id) {
////                Toast.makeText(getContext(), "You Clicked at " + brandID[+position] + " - " + brandNames[+position], Toast.LENGTH_SHORT).show();
//
//
//                                        // Start NewActivity.class
//                                        Intent myIntent = new Intent(getActivity(),
//                                                BrowseCarsActivity.class);
//
//                                        myIntent.putExtra("brandID", String.valueOf(brandID[+position]));
//                                        myIntent.putExtra("type", "brand");
//                                        myIntent.putExtra("brand", String.valueOf(brandNames[+position]));
//
//                                        startActivity(myIntent);
//
//
//                                    }
//                                });

//                                Log.d("FilteredByBrands", brands.toString() + " -- " + brands.size());
//
//
////                                customerID = data.getString("customerID");
//
//
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
//        loadFeaturedVehicles();


    }


    private void loadbodyTypeKV() {


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

//                                    brandIDs.add(data.getInt("make_id"));
//                                    brands.add(data.getString("make"));
//                                    imageUrls.add(data.getString("make_icon"));

                                    bodyTypeKV.put(data.getString("body_type_id"), data.getString("body_type"));



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

                                bodyTypeKVAdapter.notifyDataSetChanged();

//                                brandNames = new String[brands.size()];
//                                brands.toArray(brandNames);
//                                Log.d("brandBame", brandNames[0] + brandNames[1]);
//                                brandID = new Integer[brandIDs.size()];
//                                brandIDs.toArray(brandID);
//                                imageUrl = new String[imageUrls.size()];
//                                imageUrls.toArray(imageUrl);
//                                final BrandGridAdapter adapter = new BrandGridAdapter(getContext(), brandNames, imageUrl);

//                                adapter.notifyDataSetChanged();
//
//                                grid = (GridView) v.findViewById(R.id.brandGridView);
//                                grid.setAdapter(adapter);
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                    grid.setNestedScrollingEnabled(true);
//                                }
//                                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//
//                                    @Override
//                                    public void onItemClick(AdapterView<?> parent, View view,
//                                                            int position, long id) {
////                Toast.makeText(getContext(), "You Clicked at " + brandID[+position] + " - " + brandNames[+position], Toast.LENGTH_SHORT).show();
//
//
//                                        // Start NewActivity.class
//                                        Intent myIntent = new Intent(getActivity(),
//                                                BrowseCarsActivity.class);
//
//                                        myIntent.putExtra("brandID", String.valueOf(brandID[+position]));
//                                        myIntent.putExtra("type", "brand");
//                                        myIntent.putExtra("brand", String.valueOf(brandNames[+position]));
//
//                                        startActivity(myIntent);
//
//
//                                    }
//                                });

//                                Log.d("FilteredByBrands", brands.toString() + " -- " + brands.size());
//
//
////                                customerID = data.getString("customerID");
//
//
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
//        loadFeaturedVehicles();


    }




//        BrandGridAdapter adapter = new BrandGridAdapter(getContext(), brandNames, imageUrl);





    @Override
    public void carsCardItemClicked(View view, int position, int vehicleID) {

//        MainActivity mainActivity = new MainActivity();
//        mainActivity.showCarInfoPage(vehicleID);

        Intent i = new Intent(getApplicationContext(), CarInfoActivity.class);

        i.putExtra("vehicleID", vehicleID);
        startActivity(i);

    }
}

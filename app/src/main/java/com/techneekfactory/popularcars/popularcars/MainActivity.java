package com.techneekfactory.popularcars.popularcars;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techneekfactory.popularcars.popularcars.extras.Vehicles;
import com.techneekfactory.popularcars.popularcars.extras.URLEndpoints;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import layout.ContactPageFragment;
import layout.ThirdPartyFinanceFragment;
import layout.WebViewFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private List<Vehicles> vehiclesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setLogo(R.drawable.popular_logo_small);
        toolbar.setTitle("Popular");
        toolbar.setSubtitle("Pre-owned Cars");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MainFragment mainFragment = new MainFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, mainFragment);
        ft.commit();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            loadFeaturedVehicles();
        }
        else
        {
            Toast.makeText(MainActivity.this, "Please check the internet connectivity", Toast.LENGTH_LONG).show();
        }





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent myIntent = new Intent(this, SearchcarActivity.class);
            myIntent.putExtra("SEARCH", "");
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        displaySelectedScreen(id);
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void displaySelectedScreen(int id) {

        Fragment fragment = null;

        switch (id) {
            case R.id.nav_home:

                fragment = new MainFragment();
                break;

            case R.id.nav_browse_cars:

                // Start NewActivity.class
                Intent myIntent = new Intent(this, SearchcarActivity.class);
                myIntent.putExtra("ALLVEHICLES", "");
                startActivity(myIntent);
                break;

            case R.id.nav_special_offer:
                fragment = new SpecialOfferFragment();
                break;

            case R.id.nav_inhousefinance:
                fragment = new WebViewFragment();
                Bundle fBundle = new Bundle();
//                fBundle.putString("URL", "http://popularcarsoman.dev.techneek.in/app_page_dynamic.php?pid=1");
                fBundle.putString("URL", URLEndpoints.GateWayEndPointURL+"/app_page_inHouseFinance.php");
                fragment.setArguments(fBundle);
                break;


            case R.id.tips:
                fragment = new WebViewFragment();
                Bundle gBundle = new Bundle();
//                fBundle.putString("URL", "http://popularcarsoman.dev.techneek.in/app_page_dynamic.php?pid=1");
                gBundle.putString("URL", URLEndpoints.GateWayEndPointURL+"/app_page_buyers_guide.php?slug=tips-for-buying");
                fragment.setArguments(gBundle);
                break;

            case R.id.inspect:
                fragment = new WebViewFragment();
                Bundle hBundle = new Bundle();
//                fBundle.putString("URL", "http://popularcarsoman.dev.techneek.in/app_page_dynamic.php?pid=1");
                hBundle.putString("URL", URLEndpoints.GateWayEndPointURL+"/app_page_buyers_guide.php?slug=inspection");
                fragment.setArguments(hBundle);
                break;

            case R.id.test:
                fragment = new WebViewFragment();
                Bundle iBundle = new Bundle();
//                fBundle.putString("URL", "http://popularcarsoman.dev.techneek.in/app_page_dynamic.php?pid=1");
                iBundle.putString("URL", URLEndpoints.GateWayEndPointURL+"/app_page_buyers_guide.php?slug=test-drive");
                fragment.setArguments(iBundle);
                break;

            case R.id.deal_close:
                fragment = new WebViewFragment();
                Bundle jBundle = new Bundle();
//                fBundle.putString("URL", "http://popularcarsoman.dev.techneek.in/app_page_dynamic.php?pid=1");
                jBundle.putString("URL", URLEndpoints.GateWayEndPointURL+"/app_page_buyers_guide.php?slug=close-the-deal");
                fragment.setArguments(jBundle);
                break;

            case R.id.nav_3rdpartyfinance:
                fragment = new ThirdPartyFinanceFragment();
                break;

            case R.id.nav_contact_page:
                fragment = new ContactPageFragment();
                break;

            case R.id.general:
                fragment = new GeneralLoanFragment();
                break;

            case R.id.carLoan:
                fragment = new CarLoanFragment();
                break;


        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    private void loadFeaturedVehicles() {

        vehiclesList = new ArrayList<>();
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


                                Log.d("FeaturedList", vehiclesList.toString() + " -- " + vehiclesList.size());


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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


    public void showCarInfoPage(int vehicleID) {
        Log.d("From CardView: ", String.valueOf(vehicleID));

        ContactPageFragment contactPageFragment = new ContactPageFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("vehicleID", vehicleID);

        contactPageFragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, contactPageFragment);
        ft.commit();

    }

}

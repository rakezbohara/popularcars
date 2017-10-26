package layout;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techneekfactory.popularcars.popularcars.BrowseFilterFragmentPagerAdapter;
import com.techneekfactory.popularcars.popularcars.CarsCardClickListener;
import com.techneekfactory.popularcars.popularcars.CarsCustomAdapter;
import com.techneekfactory.popularcars.popularcars.MainActivity;
import com.techneekfactory.popularcars.popularcars.R;
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
public class BrowseCarsFragment extends Fragment implements CarsCardClickListener {




    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private CarsCustomAdapter carsCustomAdapter;
    private List<Vehicles> vehiclesList;


    public BrowseCarsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_browse_cars, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.browseCarsRecycleView);

        vehiclesList = new ArrayList<>();

        loadVehicles();

        gridLayoutManager = new GridLayoutManager(this.getContext(), 1);
        gridLayoutManager.setSpanCount(2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);



        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setNestedScrollingEnabled(false);

        carsCustomAdapter = new CarsCustomAdapter(this.getContext(), vehiclesList);
        recyclerView.setAdapter(carsCustomAdapter);

        carsCustomAdapter.setCarsCardClickListener(this);
        return v;
    }



    private void loadVehicles() {




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

    @Override
    public void carsCardItemClicked(View view, int position, int vehicleID) {
        ((MainActivity)getActivity()).showCarInfoPage(vehicleID);
    }
}

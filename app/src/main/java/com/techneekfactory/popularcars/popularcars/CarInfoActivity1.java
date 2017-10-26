package com.techneekfactory.popularcars.popularcars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class CarInfoActivity1 extends AppCompatActivity {

    FlipperLayout flipperLayout;
    DatabaseHandler db ;

    String[] url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info1);

        db = new DatabaseHandler(this);
        flipperLayout =(FlipperLayout) findViewById(R.id.flipper_layout);







        getVehicleInfo();

        List<Contact> contacts = db.getAllContacts();

        Log.d("Size", "onResponse: "+ String.valueOf(db.getContactsCount()));

        db.deleteAll();

        url = new String[contacts.size()];


        for (int i = 0; i < contacts.size(); i++) {

            Log.d("databaseList", "onCreate: " + contacts.get(i).getID() +  " "+ contacts.get(i).getName());
            FlipperView view = new FlipperView(getBaseContext());
            view.setImageUrl(contacts.get(i).getName().toString())
                    .setDescription("Cool" + (i + 1));
            flipperLayout.addFlipperView(view);
            view.setOnFlipperClickListener(new FlipperView.OnFlipperClickListener() {
                @Override
                public void onFlipperClick(FlipperView flipperView) {
                    Toast.makeText(CarInfoActivity1.this
                            , "Here " + (flipperLayout.getCurrentPagePosition() + 1)
                            , Toast.LENGTH_SHORT).show();
                }
            });
        }


        Log.d("Size", "onResponse: "+ String.valueOf(db.getContactsCount()));
        }





//        setLayout();



    private void setLayout(String url[]) {


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
                params.put("vehicleID", "1");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }






}

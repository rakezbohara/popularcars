package layout;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techneekfactory.popularcars.popularcars.R;
import com.techneekfactory.popularcars.popularcars.Utils;
import com.techneekfactory.popularcars.popularcars.extras.URLEndpoints;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactPageFragment extends Fragment {

    Button sendButton;

    EditText nameText, phoneText, emailText, subjectText, messageText;


    TextView phoneCallButton;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE};

    public ContactPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contactpage, container, false);

        if(!hasPermissions(getContext(), PERMISSIONS)){
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
        }


        nameText = v.findViewById(R.id.nameText);
        phoneText = v.findViewById(R.id.phoneText);
        emailText = v.findViewById(R.id.emailText);
//        subjectText = v.findViewById(R.id.subjectText);
        messageText = v.findViewById(R.id.messageText);


        phoneCallButton = v.findViewById(R.id.phoneCallButton);

        sendButton = v.findViewById(R.id.submitButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sendContactInfoToServer();
            }
        });




        phoneCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+ phoneCallButton.getText().toString()));
                    startActivity(callIntent);
                } catch (ActivityNotFoundException activityException) {
                    Log.e("Phone call failed.", "Call failed", activityException);
                }
            }
        });

        return v;
    }


    public void sendContactInfoToServer() {

        Boolean valid = true;

        if (nameText.getText().length() < 3) {
            nameText.setError("Invalid Name");
            valid = false;
        }
        if (phoneText.getText().length() < 3) {
            phoneText.setError("Phone is required");
            valid = false;
        }
        if (emailText.getText().length() < 1) {
            emailText.setError("Email is required");
            valid = false;
        }
        if (!emailText.getText().toString().contains("@")) {
            emailText.setError("Email is Invalid");
            valid = false;
        }

        if (messageText.getText().length() < 5) {
            emailText.setError("Message is too short");
            valid = false;
        }

        if (valid){
           sendInfo(nameText.getText().toString(), phoneText.getText().toString(), emailText.getText().toString(), messageText.getText().toString());
        }


    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void sendInfo( final String name, final String phoneNo, final String email, final String message){

        //Get the Featured Cars
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLEndpoints.GateWayEndPointURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String status, messag, customerID, fullName, mobileNumber, password, language, mobileNumberVerified, active;

                        try {
                            JSONObject obj = new JSONObject(response);
                            status = obj.getString("status");
                            messag = obj.getString("message");

                            Log.d("JSON Reply", obj.toString());

                            if (status.equals("SUCCESS")) {

                                Toast.makeText(getContext(), messag, Toast.LENGTH_LONG).show();
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

                params.put("action", "ENQUIRYFORM");

                params.put("name", name);
//                params.put("phoneNo", String.valueOf(phoneNo));

                params.put("email", email);
                params.put("message", message);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);
      

    }


}

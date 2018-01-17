package com.techneekfactory.popularcars.popularcars;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techneekfactory.popularcars.popularcars.extras.QuestionsModel;
import com.techneekfactory.popularcars.popularcars.extras.URLEndpoints;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link GeneralLoanFragment#} factory method to
 * create an instance of this fragment.
 */
public class GeneralLoanFragment extends Fragment {


    private ArrayList<String> questions;
    private ArrayList<String> answers;

    public String[] ques;
    public String[] ans;

    public ArrayList<QuestionsModel> questionsModels;

    public RecyclerView rvItems;

    public QuestionAnswerAdapter adapter;


    public GeneralLoanFragment() {
        // Required empty public constructor
    }

    ProgressDialog webLoadingProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_general_loan, container, false);


        webLoadingProgressBar = new ProgressDialog(getActivity());
        webLoadingProgressBar.setIndeterminate(true);
        webLoadingProgressBar.setCancelable(false);
        webLoadingProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        webLoadingProgressBar.setMessage("Loading...");
        webLoadingProgressBar.show();
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        questionsModels = new ArrayList<QuestionsModel>();
        rvItems = (RecyclerView) v.findViewById(R.id.rvGeneralLoan);
        rvItems.setLayoutManager(new LinearLayoutManager(getContext()));
        rvItems.setItemAnimator(new DefaultItemAnimator());
        Log.d("Flow Check","Got here 1");
        //Get the Featured Cars
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLEndpoints.GateWayEndPointURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        webLoadingProgressBar.dismiss();
                        Log.d("JSON Reply", "Data is : "+response);
                        String status, message, customerID, fullName, mobileNumber, password, language, mobileNumberVerified, active;
                        try {
                            JSONObject obj = new JSONObject(response);
                            status = obj.getString("status");
                            message = obj.getString("message");
                            Log.d("JSON Reply", obj.toString());
                            if (status.equals("success")) {
                                JSONObject dataObj = obj.getJSONObject("data");
                                JSONArray jsonDataArray = dataObj.getJSONArray("faqs");
                                for (int i = 0; i < jsonDataArray.length(); i++) {
                                    JSONObject data = jsonDataArray.getJSONObject(i);
                                    String s = data.getString("question");
                                    String r = data.getString("answer");
                                    QuestionsModel questionsModel = new QuestionsModel(s,r );
                                    questionsModels.add(questionsModel);
                                    Log.d("TAG", "Fetched data : "+questionsModels.get(i).getQuestion());
                                    Log.d("TAG", "Fetched data : "+questionsModels.get(i).getAnswer());
                                    //System.out.println("Question: " +data.getString("question")+ "  Answer: "+ data.getString("answer"));
                                }
                                Log.d("Array" , questionsModels.get(0).getAnswer()+ questionsModels.get(1).getQuestion());
                                QuestionAnswerAdapter adapter = new QuestionAnswerAdapter(getContext(),questionsModels);
                                adapter.notifyDataSetChanged();
                                rvItems.setAdapter(adapter);
                            }
                        } catch (Throwable tx) {
                            Log.d("My App", "Could not parse malformed JSON: \""+ tx.getMessage() + response + "\"");
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

                params.put("action", "GENERALFAQ");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);



        return v;
    }

}
package com.gpuntd.adminapp.ui.Fragments;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gpuntd.adminapp.MainActivity;
import com.gpuntd.adminapp.ManageCreatedIdActivity;
import com.gpuntd.adminapp.Models.Create_ID_Data;
import com.gpuntd.adminapp.Models.HomeTopDepositsDTO;
import com.gpuntd.adminapp.Models.My_ID_Data;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.adapter.CreateDataAdapter;
import com.gpuntd.adminapp.adapter.ManageIDAdapter;
import com.gpuntd.adminapp.adapter.TopDepositDataAdapter;
import com.gpuntd.adminapp.adapter.WitdrawalsDataAdapter;
import com.gpuntd.adminapp.databinding.FragmentCreateidBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class UserCreateIDFragment extends Fragment {
    private static final String TAG ="KINGSN" ;
    FragmentCreateidBinding binding;
    private Method method;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private List<My_ID_Data> my_id_data;
    RecyclerView MyrecyclerView;
    ManageIDAdapter MyRecyclerAdapter;
    com.airbnb.lottie.LottieAnimationView animationView;


    @SuppressLint("CommitPrefEdits")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = FragmentCreateidBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        preferences = this.requireActivity().getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();

        method = new Method(requireActivity());
        MyrecyclerView = binding.rvCreateID;
        animationView= requireActivity().findViewById(R.id.animationView);
        animationView.setVisibility(View.GONE);

       // setData();
        getMyId();
        return root;
    }




 /*   public void getMyId(){
        MyrecyclerView=binding.rvCreateID;
        my_id_data = new ArrayList<>();
        MyrecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        MyRecyclerAdapter= new ManageIDAdapter(my_id_data,requireActivity());

        method.loadingDialogg(requireActivity());
        //method.loadingDialog.dismiss();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_GET_MYID,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // method.loadingDialog.dismiss();
                        // Toast.makeText(LoginActivity.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
                        try {
                            System.out.println(response);
                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject jsonObject1 = jsonObject.getJSONObject(GlobalVariables.AppSid);

                            String success = jsonObject1.getString("success");

                            if (success.equals("1")) {
                                JSONArray jsonArray2 = jsonObject1.getJSONArray("Results");

                                if(jsonArray2.length() !=0) {


                                    for (int i = 0; i < jsonArray2.length(); i++) {
                                        JSONObject object = jsonArray2.getJSONObject(i);
                                        // Log.d(TAG, "onResponse: result"+jsonArray2);


                                        String id = object.getString("id");
                                        String userMobile = object.getString("userMobile");
                                        String corderId = object.getString("CorderId");
                                        String createdId = object.getString("createdId");
                                        String payMentMode = object.getString("CpayMentMode");
                                        String idStatus = object.getString("idStatus");
                                        String idUsername = object.getString("idUsername");
                                        String idPassword = object.getString("idPassword");
                                        String idBalance = object.getString("idBalance");
                                        String idWithdrawal = object.getString("idWithdrawal");
                                        String idTotalDeposited = object.getString("idTotalDeposited");
                                        String idTotalWithdrawals = object.getString("idTotalWithdrawals");
                                        String idApproval = object.getString("idApproval");
                                        String adminComment = object.getString("adminComment");
                                        String idCreatedDate = object.getString("idCreatedDate");
                                        String idUpdatedDate = object.getString("idUpdatedDate");
                                        String idCancelledDate = object.getString("idCancelledDate");
                                        String idVerifiedDate = object.getString("idVerifiedDate");
                                        String idName = object.getString("id_name");
                                        String idWebsite = object.getString("id_website");
                                        String demoLink = object.getString("demoLink");
                                        String demoId = object.getString("demoId");
                                        String demoPass = object.getString("demoPass");
                                        String minRefill = object.getString("minRefill");
                                        String minWithdrawal = object.getString("minWithdrawal");
                                        String minMaintainBal = object.getString("minMaintainBal");
                                        String idCreatedDdate = object.getString("id_created_ddate");
                                        String img = object.getString("img");
                                        String maxWithdrawal = object.getString("maxWithdrawal");


                                        My_ID_Data ld11 = new My_ID_Data(id, userMobile, corderId, createdId, payMentMode, idStatus,
                                                idUsername, idPassword, idBalance, idWithdrawal, idTotalDeposited,
                                                idTotalWithdrawals, idApproval, adminComment, idCreatedDate, idUpdatedDate,
                                                idCancelledDate, idVerifiedDate, idName, idWebsite, demoLink, demoId, demoPass, minRefill,
                                                minWithdrawal, minMaintainBal, idCreatedDdate, img, maxWithdrawal);
                                        my_id_data.add(ld11);

                                        MyrecyclerView.clearFocus();
                                        MyRecyclerAdapter = new ManageIDAdapter(my_id_data, requireActivity());
                                        MyrecyclerView.setAdapter(MyRecyclerAdapter);

                                        method.loadingDialog.dismiss();

                                        Log.d(TAG, "onResponse: myId" + idWebsite);

                                    }
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            method.loadingDialog.dismiss();


                                        }
                                    }, 1000);
                                }else{

                                    recyclerView2.setVisibility(View.GONE);
                                    animationView.setVisibility(View.VISIBLE);

                                }

                            }else{
                                method.loadingDialog.dismiss();

                                //  Method.alertBox("2","Something Went Wrong","Close & Restart", requireActivity(),"Go to Home");

                            }


                        }catch (JSONException e){
                            e.printStackTrace();
                            //  Toast.makeText(getActivity(), "hello"+e, Toast.LENGTH_LONG).show();
                        }
                    },
                            new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            // Do something when error occurred
                            //Toast.makeText(WalletActivity.this,"Error...!",Toast.LENGTH_SHORT).show();
                            method.loadingDialog.dismiss();
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() {

                        Map<String, String> params = new HashMap<>();
                        params.put("users_login", "KINGSN");
                        params.put("mobile",preferences.getString(GlobalVariables.SELUSER_MOBILE,""));
                        params.put("device_id",method.getDeviceId(requireActivity()));

                        return params;
                    }
                    };

                    RequestQueue requestQueue= Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);

                }
    }*/

    public void getMyId(){
        MyrecyclerView = binding.rvCreateID;
        MyrecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
       my_id_data = new ArrayList<>();

        method.loadingDialogg(requireActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_GET_MYID,
                response -> {
                    // Toast.makeText(LoginActivity.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
                    try {
                        System.out.println(response);
                        Log.d(TAG, "getHomeData: "+response);
                        JSONObject jsonObject = new JSONObject(response);

                        JSONObject jsonObject1 = jsonObject.getJSONObject(GlobalVariables.AppSid);

                        String success = jsonObject1.getString("success");

                        if (success.equals("1")) {
                            JSONArray jsonArray2 = jsonObject1.getJSONArray("Results");
                            if(jsonArray2.length() !=0) {

                                for (int i = 0; i < jsonArray2.length(); i++) {

                                    JSONObject object = jsonArray2.getJSONObject(i);
                                    Log.d(TAG, "onResponse: result"+jsonArray2);

                                    String id = object.getString("id");
                                    String userMobile = object.getString("userMobile");
                                    String corderId = object.getString("CorderId");
                                    String createdId = object.getString("createdId");
                                    String payMentMode = object.getString("CpayMentMode");
                                    String idStatus = object.getString("idStatus");
                                    String idUsername = object.getString("idUsername");
                                    String idPassword = object.getString("idPassword");
                                    String idBalance = object.getString("idBalance");
                                    String idWithdrawal = object.getString("idWithdrawal");
                                    String idTotalDeposited = object.getString("idTotalDeposited");
                                    String idTotalWithdrawals = object.getString("idTotalWithdrawals");
                                    String idApproval = object.getString("idApproval");
                                    String adminComment = object.getString("adminComment");
                                    String idCreatedDate = Method.convertTimestampDateToTime(Method.correctTimestamp(object.getString ("idCreatedDate")));
                                    String idUpdatedDate = Method.convertTimestampDateToTime(Method.correctTimestamp(object.getString ("idUpdatedDate")));
                                    String idCancelledDate = Method.convertTimestampDateToTime(Method.correctTimestamp(object.getString ("idCancelledDate")));
                                    String idVerifiedDate =Method.convertTimestampDateToTime(Method.correctTimestamp( object.getString ("idVerifiedDate")));
                                    String idName = object.getString("id_name");
                                    String idWebsite = object.getString("id_website");
                                    String demoLink = object.getString("demoLink");
                                    String demoId = object.getString("demoId");
                                    String demoPass = object.getString("demoPass");
                                    String minRefill = object.getString("minRefill");
                                    String minWithdrawal = object.getString("minWithdrawal");
                                    String minMaintainBal = object.getString("minMaintainBal");
                                    String idCreatedDdate = Method.convertTimestampDateToTime(Method.correctTimestamp(object.getString("id_created_ddate")));
                                    String img = object.getString("img");
                                    String maxWithdrawal = object.getString("maxWithdrawal");


                                    My_ID_Data ld11 = new My_ID_Data(id, userMobile, corderId, createdId, payMentMode, idStatus,
                                            idUsername, idPassword, idBalance, idWithdrawal, idTotalDeposited,
                                            idTotalWithdrawals, idApproval, adminComment, idCreatedDate, idUpdatedDate,
                                            idCancelledDate, idVerifiedDate, idName, idWebsite, demoLink, demoId, demoPass, minRefill,
                                            minWithdrawal, minMaintainBal, idCreatedDdate, img, maxWithdrawal);
                                    my_id_data.add(ld11);

                                    binding.rvCreateID.setVisibility(View.VISIBLE);
                                    MyrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                    MyrecyclerView.clearFocus();
                                    ManageIDAdapter manageIDAdapter = new ManageIDAdapter(my_id_data, requireActivity());
                                    MyrecyclerView.setAdapter(manageIDAdapter);



                                }

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        method.loadingDialog.dismiss();


                                    }
                                }, GlobalVariables.dismissAfter);
                            }else{

                                binding.rvCreateID.setVisibility(View.GONE);
                                binding.animationView.setVisibility(View.VISIBLE);
                                method.loadingDialog.dismiss();
                                binding.animationView.playAnimation();


                            }

                        }else{

                             // Method.alertBox("2","Something Went Wrong","Close & Restart", requireActivity(),"Go to Home");
                            binding.rvCreateID.setVisibility(View.GONE);
                            binding.animationView.setVisibility(View.VISIBLE);
                            method.loadingDialog.dismiss();
                            binding.animationView.playAnimation();
                        }


                        //loadingDialog.dismiss();

                    }catch (JSONException e){
                        e.printStackTrace();
                        //  Toast.makeText(getActivity(), "hello"+e, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        //Toast.makeText(WalletActivity.this,"Error...!",Toast.LENGTH_SHORT).show();
                        method.loadingDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("users_login", "KINGSN");
                params.put("mobile",preferences.getString(GlobalVariables.SELUSER_MOBILE,""));
                params.put("device_id",method.getDeviceId(requireActivity()));

                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);

    }
}
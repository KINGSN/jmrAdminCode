package com.gpuntd.adminapp.ui.Fragments;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gpuntd.adminapp.HistoryActivity;
import com.gpuntd.adminapp.MainActivity;
import com.gpuntd.adminapp.Models.Create_ID_Data;
import com.gpuntd.adminapp.Models.HomeTopDepositsDTO;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.adapter.CreateDataAdapter;
import com.gpuntd.adminapp.adapter.TopDepositDataAdapter;
import com.gpuntd.adminapp.databinding.ActivityHistoryBinding;
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


public class UserAllDepositFragment extends Fragment {
    private static final String TAG ="KINGSN" ;
    FragmentCreateidBinding binding;
    private Method method;
    private HistoryActivity binding2;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    TopDepositDataAdapter topDepositDataAdapter;
    RecyclerView  recyclerView2;
    ArrayList<HomeTopDepositsDTO> homeTopDepositsDTOArrayList = new ArrayList<>();
    com.airbnb.lottie.LottieAnimationView animationView;



    @SuppressLint("CommitPrefEdits")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = FragmentCreateidBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        preferences = this.requireActivity().getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        binding.rvCreateID.setVisibility(View.VISIBLE);
        method = new Method(requireActivity());
        animationView= requireActivity().findViewById(R.id.animationView);
        binding.animationView.setVisibility(View.GONE);
        recyclerView2 = binding.rvCreateID;
       // setData();
        getDepositData();
        return root;
    }


    private void setData() {

        binding.searchtv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                    topDepositDataAdapter.filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

                    topDepositDataAdapter.filter(s.toString());

            }
        });

    }


    public void getDepositData(){

        homeTopDepositsDTOArrayList.clear();
        method.loadingDialogg(requireActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_GET_ALLDEPOSIT,
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

                        JSONObject object2 = jsonArray2.getJSONObject(i);
                        Log.d(TAG, "onResponse: result"+jsonArray2);

                        String id =object2.getString("id");
                        String mobile =object2.getString("mobile");
                        String password =object2.getString("password");
                        String name =object2.getString("name");
                        String city =object2.getString("city");
                        String email =object2.getString("email");
                        String wallet =object2.getString("wallet");
                        String totalPaid =object2.getString("total_paid");
                        String totalRedeemed =object2.getString("total_redeemed");
                        String userReferalCode =object2.getString("user_referal_code");
                        String refferedBy =object2.getString("reffered_by");
                        String refferedPaid =object2.getString("reffered_paid");
                        String totalReferals =object2.getString("total_referals");
                        String allow =object2.getString("allow");
                        String deviceId =object2.getString("device_id");
                        String deviceToken =object2.getString("device_token");
                        String profilePic =object2.getString("profile_pic");
                        String activeDate = Method.convertTimestampDateToTime(object2.getString("active_date"));
                        String onesignalPlayerid =object2.getString("onesignal_playerid");
                        String onesignalPushtoken =object2.getString("onesignal_pushtoken");
                        String joiningTime = Method.convertTimestampDateToTime(object2.getString("joining_time"));
                        String userMobile =object2.getString("userMobile");
                        String txnType =object2.getString("txnType");
                        String orderId =object2.getString("orderId");
                        String amount =object2.getString("amount");
                        String createdId =object2.getString("createdId");
                        String idcreated =object2.getString("Idcreated");
                        String payMentMode =object2.getString("payMentMode");
                        String payMode =object2.getString("payMode");
                        String txnStatus =object2.getString("txnStatus");
                        String idUsername =object2.getString("idUsername");
                        String idPassword =object2.getString("idPassword");
                        String adminComment =object2.getString("adminComment");
                        String txnDate = Method.convertTimestampDateToTime(Method.correctTimestamp(object2.getString("txnDate")));
                        // String cancelledDate =object2.getString("cancelledDate");
                        String cancelledDate = Method.convertTimestampDateToTime(Method.correctTimestamp(object2.getString("cancelledDate")));
                        String verifiedDate = Method.convertTimestampDateToTime(Method.correctTimestamp(object2.getString("verifiedDate")));
                        String img =object2.getString("img");
                        String idWebsite =object2.getString("idWebsite");
                        String idName =object2.getString("idName");
                        String wDetails =object2.getString("wDetails");

                        HomeTopDepositsDTO homeTopDeposits = new HomeTopDepositsDTO( id,  mobile,  password,  name,  city,  email,  wallet,
                                totalPaid,  totalRedeemed,  userReferalCode,  refferedBy,  refferedPaid,  totalReferals,  allow,  deviceId,
                                deviceToken,  profilePic,  activeDate,  onesignalPlayerid,  onesignalPushtoken,  joiningTime,  userMobile,
                                txnType,  orderId,  amount,  createdId,  idcreated,  payMentMode,  payMode,  txnStatus,  idUsername,
                                idPassword,  adminComment,  txnDate,  cancelledDate,  verifiedDate,img,idWebsite,idName,wDetails);
                        homeTopDepositsDTOArrayList.add(homeTopDeposits);
                        GlobalVariables.homeTopDepositsDTO=homeTopDeposits;
                        editor.apply();
                        recyclerView2 = binding.rvCreateID;
                        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView2.clearFocus();
                        topDepositDataAdapter= new TopDepositDataAdapter(homeTopDepositsDTOArrayList,getContext());
                        recyclerView2.setAdapter(topDepositDataAdapter);




                                }
                                setData();
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

                          //  Method.alertBox("2","Something Went Wrong","Close & Restart", requireActivity(),"Go to Home");
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

package com.gpuntd.adminapp.ui.users;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gpuntd.adminapp.Models.HomeGraphDTO;
import com.gpuntd.adminapp.Models.HomeTopDepositsDTO;
import com.gpuntd.adminapp.Models.HomeTopUserDTO;
import com.gpuntd.adminapp.Models.Offer_Data;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.adapter.TopDepositDataAdapter;
import com.gpuntd.adminapp.adapter.TopUserDataAdapter;
import com.gpuntd.adminapp.adapter.UserListDataAdapter;
import com.gpuntd.adminapp.databinding.FragmentUsersBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UsersFragment extends Fragment {

    private static final String TAG = "KINGSN";
    FragmentUsersBinding binding;
    RecyclerView offerrecyclerView;
    private Method method;
    private List<Offer_Data> offer_data;
    RecyclerView CreaterecyclerView, recyclerView2, MyrecyclerView;
    ArrayList<HomeTopUserDTO> homeTopUserDTOArrayList = new ArrayList<>();
    UserListDataAdapter topUserDataAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUsersBinding.inflate(inflater, container, false);
        // offerrecyclerView = binding.offersRv;
        method = new Method(requireContext());
        //MyrecyclerView = binding.rvMyID;
        View root = binding.getRoot();
        getUserData();

        binding.searchtv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                topUserDataAdapter.filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

                topUserDataAdapter.filter(s.toString());

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getUserData(){

        method.loadingDialogg(requireActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_GET_ALLUSER,
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
                            for (int i = 0; i < jsonArray2.length(); i++) {

                                JSONObject object = jsonArray2.getJSONObject(i);
                                Log.d(TAG, "onResponse: result"+jsonArray2);

                                String id =object.getString("id");
                                String mobile =object.getString("mobile");
                                String password =object.getString("password");
                                String name =object.getString("name");
                                String city =object.getString("city");
                                String email =object.getString("email");
                                String wallet =object.getString("wallet");
                                String totalPaid =object.getString("total_paid");
                                String totalRedeemed =object.getString("total_redeemed");
                                String userReferalCode =object.getString("user_referal_code");
                                String refferedBy =object.getString("reffered_by");
                                String refferedPaid =object.getString("reffered_paid");
                                String totalReferals =object.getString("total_referals");
                                String allow =object.getString("allow");
                                String deviceId =object.getString("device_id");
                                String deviceToken =object.getString("device_token");
                                String profilePic =object.getString("profile_pic");
                               // String activeDate =object.getString("active_date");
                                String activeDate = Method.convertTimestampDateToTime(Method.correctTimestamp(object.getString("active_date")));
                                String onesignalPlayerid =object.getString("onesignal_playerid");
                                String onesignalPushtoken =object.getString("onesignal_pushtoken");
                               // String joiningTime =object.getString("joining_time");
                                String joiningTime1=  Method.convertTimestampDateToTime(object.getString("joining_time"));
                                String joiningTime =(Method.convertTimestampDateToTime(Method.correctTimestamp((object.getString("joining_time")))));


                                HomeTopUserDTO homeTopUserDTO = new HomeTopUserDTO( id,  mobile,  password,  name,  city,  email,  wallet,  totalPaid,  totalRedeemed,
                                        userReferalCode,  refferedBy,  refferedPaid,  totalReferals,  allow,  deviceId,  deviceToken,  profilePic,  activeDate,
                                        onesignalPlayerid,  onesignalPushtoken,  joiningTime);
                                // editor.apply();
                                homeTopUserDTOArrayList.add(homeTopUserDTO);
                                MyrecyclerView = binding.rvTopUser;
                                MyrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                topUserDataAdapter= new UserListDataAdapter(homeTopUserDTOArrayList,getContext());
                                MyrecyclerView.setAdapter(topUserDataAdapter);





                            }
                           // setData();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    method.loadingDialog.dismiss();
                                }
                            }, GlobalVariables.dismissAfter);
                        }else{
                            method.loadingDialog.dismiss();

                            Method.alertBox("2","Something Went Wrong","Close & Restart", requireActivity(),"Go to Home");

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
               // params.put("mobile",preferences.getString(GlobalVariables.USER_MOBILE,"") );
                params.put("device_id",method.getDeviceId(requireActivity()));

                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);

    }

}
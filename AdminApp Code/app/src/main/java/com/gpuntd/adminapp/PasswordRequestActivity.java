package com.gpuntd.adminapp;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gpuntd.adminapp.Models.HomeTopDepositsDTO;
import com.gpuntd.adminapp.Models.My_ID_Data;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.adapter.ManageIDAdapter;
import com.gpuntd.adminapp.adapter.PasswordDataAdapter;
import com.gpuntd.adminapp.adapter.TopDepositDataAdapter;
import com.gpuntd.adminapp.databinding.ActivityManageCreatedIdBinding;
import com.gpuntd.adminapp.databinding.ActivityPasswordRequestBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasswordRequestActivity extends AppCompatActivity {

    private static final String TAG = "KINGSN";
    ActivityPasswordRequestBinding binding;
    RecyclerView MyrecyclerView;
    Toolbar toolbar;
    private Method method;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    ArrayList<HomeTopDepositsDTO> homeTopDepositsDTOArrayList = new ArrayList<>();
    public static Uri selectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_request);
        binding = ActivityPasswordRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.toolbarPass);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        preferences = PasswordRequestActivity.this.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        method=new Method(this);

        MyrecyclerView = findViewById(R.id.rvPassRequest);


        // preferences = this.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);

        method = new Method(this);
        getMyId();

       /* binding.btnAddcreateID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.offerType="add";
                CreateDataAdapter.setupUpdateDialog(v);
            }
        });
*/

    }


    public void getMyId(){
        MyrecyclerView = binding.rvPassRequest;
        MyrecyclerView.setLayoutManager(new LinearLayoutManager(PasswordRequestActivity.this));
        homeTopDepositsDTOArrayList.clear();


        method.loadingDialogg(PasswordRequestActivity.this);
        //method.loadingDialog.dismiss();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_GET_ALLPASSWORDREQ,
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
                                for (int i = 0; i < jsonArray2.length(); i++) {

                                    JSONObject object2 = jsonArray2.getJSONObject(i);
                                    Log.d(TAG, "onResponse: result"+jsonArray2);

                                    String id =object2.getString("id");
                                    String mobile =object2.getString("userMobile");
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
                                    String activeDate =Method.convertTimestampDateToTime(Method.correctTimestamp(object2.getString("active_date")));
                                    String onesignalPlayerid =object2.getString("onesignal_playerid");
                                    String onesignalPushtoken =object2.getString("onesignal_pushtoken");
                                    String joiningTime =Method.convertTimestampDateToTime(Method.correctTimestamp(object2.getString("joining_time")));
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


                                    PasswordDataAdapter  passwordDataAdapter= new PasswordDataAdapter(homeTopDepositsDTOArrayList,PasswordRequestActivity.this);
                                    MyrecyclerView.setAdapter(passwordDataAdapter);



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
                                Method.alertBox("2",jsonObject1.getString("title"),jsonObject1.getString("msg"), PasswordRequestActivity.this,GlobalVariables.btntxt);

                            }


                            //loadingDialog.dismiss();

                        }catch (JSONException e){
                            e.printStackTrace();
                            //  Toast.makeText(getActivity(), "hello"+e, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        //Toast.makeText(WalletActivity.this,"Error...!",Toast.LENGTH_SHORT).show();
                        method.loadingDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("users_login", "KINGSN");

                /*  params.put("device_id",method.getDeviceId(getApplicationContext()));*/

                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(PasswordRequestActivity.this);
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);

    }
}
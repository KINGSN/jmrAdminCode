package com.gpuntd.adminapp;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gpuntd.adminapp.Models.My_ID_Data;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.adapter.CreateDataAdapter;
import com.gpuntd.adminapp.adapter.ManageIDAdapter;
import com.gpuntd.adminapp.databinding.ActivityManageCreatedIdBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageCreatedIdActivity extends AppCompatActivity {
    private static final String TAG = "KINGSN";
    ActivityManageCreatedIdBinding binding;
    RecyclerView MyrecyclerView;
    ManageIDAdapter MyRecyclerAdapter;
    Toolbar toolbar;
    private Method method;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private List<My_ID_Data> my_id_data;
    public static Uri selectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_created_id);
        binding = ActivityManageCreatedIdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.toolbarManId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        preferences = ManageCreatedIdActivity.this.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        method=new Method(this);

        MyrecyclerView = findViewById(R.id.rvManageCreatedId);


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

        my_id_data = new ArrayList<>();
        MyrecyclerView.setLayoutManager(new LinearLayoutManager(ManageCreatedIdActivity.this));
        MyRecyclerAdapter= new ManageIDAdapter(my_id_data,ManageCreatedIdActivity.this);
        method.loadingDialogg(ManageCreatedIdActivity.this);
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



                                        My_ID_Data ld11=new My_ID_Data( id,  userMobile,  corderId,  createdId,  payMentMode,  idStatus,
                                                idUsername,  idPassword,  idBalance,  idWithdrawal,  idTotalDeposited,
                                                idTotalWithdrawals,  idApproval,  adminComment,  idCreatedDate,  idUpdatedDate,
                                                idCancelledDate,  idVerifiedDate,idName,idWebsite,demoLink,demoId,demoPass, minRefill,
                                                minWithdrawal,minMaintainBal,idCreatedDdate,img,maxWithdrawal);
                                        my_id_data.add(ld11);


                                    //Toast.makeText(getActivity(), "hello"+ob.getString("amount"), Toast.LENGTH_LONG).show();

                                    Log.d(TAG, "onResponse: myId"+idWebsite);

                                }
                                Log.d(TAG, "onResponse: "+jsonArray2);

                                MyRecyclerAdapter= new ManageIDAdapter(my_id_data,ManageCreatedIdActivity.this);
                                //recyclerView.setLayoutManager(new LinearLayoutManager(view));
                                //GridLayoutManager lm = new GridLayoutManager(view, 1);
                                // recyclerView.setLayoutManager();
                                MyrecyclerView.setAdapter(MyRecyclerAdapter);
                                // method.loadingDialog.dismiss();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        method.loadingDialog.dismiss();

                                    }
                                }, GlobalVariables.dismissAfter);
                            }else{
                                method.loadingDialog.dismiss();
                                Method.alertBox("2",jsonObject1.getString("title"),jsonObject1.getString("msg"), ManageCreatedIdActivity.this,GlobalVariables.btntxt);
                                //sendVerificationCodeToUser(mobileNumber);
                              /*  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireActivity());
                                alertDialogBuilder.setTitle(jsonObject1.getString("title"));
                                alertDialogBuilder.setMessage(jsonObject1.getString("msg"));
                                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                                alertDialogBuilder.setPositiveButton(requireActivity().getResources().getString(R.string.ok_message),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                method.loadingDialog.dismiss();
                                                requireActivity().finish();
                                                startActivity(new Intent(requireActivity(), MainActivity.class));
                                                //Log.d("Response",msg);
                                                // finishAffinity();

                                            }
                                        });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();*/
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
        RequestQueue requestQueue= Volley.newRequestQueue(ManageCreatedIdActivity.this);
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);

    }

}
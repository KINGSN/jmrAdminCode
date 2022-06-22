package com.gpuntd.adminapp.ui.Fragments;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import com.cocosw.bottomsheet.BottomSheet;
import com.gpuntd.adminapp.MainActivity;
import com.gpuntd.adminapp.Models.Create_ID_Data;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.ImageCompression;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.adapter.CreateDataAdapter;
import com.gpuntd.adminapp.databinding.FragmentAppcontrolBinding;
import com.gpuntd.adminapp.databinding.FragmentApppaymentBinding;
import com.gpuntd.adminapp.databinding.FragmentCreateidBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class AppCreateIdFragment extends Fragment {
    private static final String TAG ="KINGSN" ;
    FragmentCreateidBinding binding;
    private Method method;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private List<Create_ID_Data> create_id_data;
    RecyclerView CreaterecyclerView,recyclerView1,MyrecyclerView;
    CreateDataAdapter CreateRecyclerAdapter;



    @SuppressLint("CommitPrefEdits")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = FragmentCreateidBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        preferences = this.requireActivity().getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();

        method = new Method(requireActivity());
        CreaterecyclerView = binding.rvCreateID;

       // setData();
        getCreateId();
        return root;
    }


   /* private void setData() {

        binding.etInAppPaytmUpiName.setText(GlobalVariables.settings.getAdminUpiName());
        binding.etInAppPaytmUpiMobile.setText(GlobalVariables.settings.getUpiMobileNo());
        binding.etInAppPaytmUpiId.setText(GlobalVariables.settings.getAdminUpi());
        binding.etInAppPaytmSettingName.setText(GlobalVariables.settings.getAdminPaytmName());
        binding.etInAppPaytmSettingMobile.setText(GlobalVariables.settings.getAdminPaytmNo());
        binding.etInAppPaytmSettingId.setText(GlobalVariables.settings.getAdminUpi());
        binding.etInAppGooglePayName.setText(GlobalVariables.settings.getAdminGpayName());
        binding.etInAppGooglePayMobile.setText(GlobalVariables.settings.getAdminGpaymobileNo());
        binding.etInAppGooglePayId.setText(GlobalVariables.settings.getAdminGpay());
        binding.etInAppBankHolderName.setText(GlobalVariables.settings.getAdminAccountName());
        binding.etInAppBankIFSCCode.setText(GlobalVariables.settings.getAdminAccountIfsc());
        binding.etInAppBankAccountNumber.setText(GlobalVariables.settings.getAdminAccountNo());
//        binding.etInAppBankHolderName.setText(GlobalVariables.settings.getAppAuthor());




        binding.btnSaveOfflineScreenshots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.requireNonNull(binding.etInAppPaytmUpiName.getText()).toString().isEmpty()) {
                    binding.etInAppPaytmUpiName.setError("Field is Required");
                }
                else if (Objects.requireNonNull(binding.etInAppPaytmUpiMobile.getText()).toString().isEmpty()) {
                    binding.etInAppPaytmUpiMobile.setError("Field is Required");
                }
                else if (Objects.requireNonNull(binding.etInAppPaytmUpiId.getText()).toString().isEmpty()) {
                    binding.etInAppPaytmUpiId.setError("Field is Required");
                }else if (Objects.requireNonNull(binding.etInAppPaytmSettingName.getText()).toString().isEmpty()) {
                    binding.etInAppPaytmSettingName.setError("Field is Required");
                }
                else if (Objects.requireNonNull(binding.etInAppPaytmSettingMobile.getText()).toString().isEmpty()) {
                    binding.etInAppPaytmSettingMobile.setError("Field is Required");
                }
                else  if (Objects.requireNonNull(binding.etInAppGooglePayName.getText()).toString().isEmpty()) {
                    binding.etInAppGooglePayName.setError("Field is Required");
                } else if (Objects.requireNonNull(binding.etInAppGooglePayMobile.getText()).toString().isEmpty()) {
                    binding.etInAppGooglePayMobile.setError("Field is Required");
                }
                else if (Objects.requireNonNull(binding.etInAppGooglePayId.getText()).toString().isEmpty()) {
                    binding.etInAppGooglePayId.setError("Field is Required");
                }else if (Objects.requireNonNull(binding.etInAppBankHolderName.getText()).toString().isEmpty()) {
                    binding.etInAppBankHolderName.setError("Field is Required");
                }
                else if (Objects.requireNonNull(binding.etInAppBankIFSCCode.getText()).toString().isEmpty()) {
                    binding.etInAppBankIFSCCode.setError("Field is Required");
                }
                else  if (Objects.requireNonNull(binding.etInAppBankAccountNumber.getText()).toString().isEmpty()) {
                    binding.etInAppBankAccountNumber.setError("Field is Required");
                }
                else {
                    method.params.clear();
                    method.params.put("appPayment_submit", "appPayment_submit");
                    method.params.put("adminUpiName",(binding.etInAppPaytmUpiName.getText()).toString());
                    method.params.put("upiMobileNo",(binding.etInAppPaytmUpiMobile.getText()).toString());
                    method.params.put("adminUpi",(binding.etInAppPaytmUpiId.getText()).toString());
                    method.params.put("adminPaytmName",(binding.etInAppPaytmSettingName.getText()).toString());
                    method.params.put("adminGpayName",(binding.etInAppGooglePayName.getText()).toString());

                    method.params.put("adminGpaymobileNo",(binding.etInAppGooglePayMobile.getText()).toString());
//                    method.params.put("adminUpi",(binding.etInAppGooglePayId.getText()).toString());
                    method.params.put("adminAccountNo",(binding.etInAppBankAccountNumber.getText()).toString());
                    method.params.put("adminAccountName",(binding.etInAppBankHolderName.getText()).toString());
                    method.params.put("adminAccountIfsc",(binding.etInAppBankIFSCCode.getText()).toString());
                    method.updateSetting(requireActivity());



                }
            }

        });


    }*/


    public void getCreateId(){
        create_id_data = new ArrayList<>();

        CreaterecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        method.loadingDialogg(requireActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_GET_CREATEID,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(LoginActivity.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
                        try {
                            //  System.out.println(response);
                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject jsonObject1 = jsonObject.getJSONObject(GlobalVariables.AppSid);
                            JSONArray jsonArray2 = jsonObject1.getJSONArray("Results");
                            String success = jsonObject1.getString("success");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    JSONObject object = jsonArray2.getJSONObject(i);
                                    Log.d(TAG, "onResponse: result"+jsonArray2);


                                    String id = object.getString("id");
                                    String idName = object.getString("id_name");
                                    String idImage = object.getString("id_image");
                                    String idWebsite = object.getString("id_website");
                                    String idStatus = object.getString("id_status");
                                    String demoId = object.getString("demoId");
                                    String demoPass = object.getString("demoPass");
                                    String demoLink = object.getString("demoLink");
                                    String minRefill = object.getString("minRefill");
                                    String minWithdrawal = object.getString("minWithdrawal");
                                    String minMaintainBal = object.getString("minMaintainBal");
                                    String maxWithdrawal = object.getString("maxWithdrawal");
                                    String idCreatedDate = object.getString("id_total_created");
                                    String idUpdatedDate = object.getString("id_created_date");
                                    String idTotalCreated = object.getString("id_total_created");

                                    Create_ID_Data ld1=new Create_ID_Data( id,  idName,  idImage,  idWebsite,  idStatus, demoId,demoPass,demoLink,
                                            minRefill,minWithdrawal,minMaintainBal,maxWithdrawal,idCreatedDate,
                                            idUpdatedDate,  idTotalCreated);
                                    create_id_data.add(ld1);
                                    //Toast.makeText(getActivity(), "hello"+ob.getString("amount"), Toast.LENGTH_LONG).show();


                                }
                                Log.d(TAG, "onResponse: "+jsonArray2);

                                CreaterecyclerView.clearFocus();
                                CreateRecyclerAdapter= new CreateDataAdapter(create_id_data,getContext());
                                //recyclerView.setLayoutManager(new LinearLayoutManager(view));
                                //GridLayoutManager lm = new GridLayoutManager(view, 1);
                                // recyclerView.setLayoutManager();
                                CreaterecyclerView.setAdapter(CreateRecyclerAdapter);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        method.loadingDialog.dismiss();
                                    }
                                }, 1000);
                            }else{
                                method.loadingDialog.dismiss();
                                //sendVerificationCodeToUser(mobileNumber);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireActivity());
                                alertDialogBuilder.setTitle(jsonObject.getString("title"));
                                alertDialogBuilder.setMessage(jsonObject.getString("msg"));
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
                                alertDialog.show();
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
                }
        );

        RequestQueue requestQueue= Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);

    }
}

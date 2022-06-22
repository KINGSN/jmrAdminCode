package com.gpuntd.adminapp.ui.deposits;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
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
import com.gpuntd.adminapp.Models.Passbook_Data;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.adapter.TopDepositDataAdapter;
import com.gpuntd.adminapp.adapter.TopUserDataAdapter;
import com.gpuntd.adminapp.databinding.DialogFilterBinding;
import com.gpuntd.adminapp.databinding.FragmentDepositsBinding;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class DepositsFragment extends Fragment {
    DialogFilterBinding dailogFilterJobBinding;
    private static final String TAG = "KINGSN";
    RecyclerView PassbookrecyclerView, recyclerView1;
    TextView tvViewDetails;
    EasyFlipView easyFlipView;
    private FragmentDepositsBinding binding;
    private Method method;
    private List<Passbook_Data> passbook_data;
    private SharedPreferences preferences, sharedPreferences;
    private SharedPreferences.Editor editor;
    TopDepositDataAdapter topDepositDataAdapter;
    private Dialog dialogFilterJob;
    RecyclerView  recyclerView2;
    ArrayList<HomeTopDepositsDTO> homeTopDepositsDTOArrayList = new ArrayList<>();


    @SuppressLint("CommitPrefEdits")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDepositsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        method = new Method(requireActivity());
        preferences = this.requireActivity().getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        getDepositData();

      /*  binding.searchtv.addTextChangedListener(new TextWatcher() {
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
        });*/


        binding.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  method.dialogAbout(requireActivity(),homeTopDepositsDTOArrayList);
                dialogAbout(requireActivity());

               // topDepositDataAdapter.filter(s.toString());
            }
        });







        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void flipView(View view) {

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
                              //  String activeDate =object2.getString("active_date");
                                String activeDate = Method.convertTimestampDateToTime(object2.getString("active_date"));

                                String onesignalPlayerid =object2.getString("onesignal_playerid");
                                String onesignalPushtoken =object2.getString("onesignal_pushtoken");
                               // String joiningTime =object2.getString("joining_time");
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
                                recyclerView2 = binding.rvTopDeposit;
                                recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

                                topDepositDataAdapter= new TopDepositDataAdapter(homeTopDepositsDTOArrayList,getContext());
                                recyclerView2.setAdapter(topDepositDataAdapter);



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

                            Method.alertBox("2",jsonObject1.getString("title"),jsonObject1.getString("msg"), requireActivity(),"Go to Home");

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

                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);

    }


    public void dialogAbout(Activity activity) {
        dialogFilterJob = new Dialog(activity/*, android.R.style.Theme_Dialog*/);
        dialogFilterJob.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFilterJob.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dailogFilterJobBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_filter, null, false);
        dialogFilterJob.setContentView(dailogFilterJobBinding.getRoot());

        dialogFilterJob.setContentView(dailogFilterJobBinding.getRoot());

        dailogFilterJobBinding.etCategoryD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if ( dailogFilterJobBinding.etCategoryD.getSelectedItemPosition()==0){
                    method.preferences.setValue("selCategory","all");
                }else if ( dailogFilterJobBinding.etCategoryD.getSelectedItemPosition()==1){
                    method.preferences.setValue("selCategory","0");
                }else if ( dailogFilterJobBinding.etCategoryD.getSelectedItemPosition()==2){
                    method.preferences.setValue("selCategory","1");
                }else if ( dailogFilterJobBinding.etCategoryD.getSelectedItemPosition()==3){
                    method.preferences.setValue("selCategory","2");
                }else if ( dailogFilterJobBinding.etCategoryD.getSelectedItemPosition()==4){
                    method.preferences.setValue("selCategory","3");
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                method.preferences.setValue("selCategory","all");
            }
        });


        dailogFilterJobBinding.etsort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if ( dailogFilterJobBinding.etsort.getSelectedItemPosition()==0){
                    method.preferences.setValue("selCatStatus","all");
                }else if ( dailogFilterJobBinding.etCategoryD.getSelectedItemPosition()==1){
                    method.preferences.setValue("selCatStatus","4");
                }else if ( dailogFilterJobBinding.etCategoryD.getSelectedItemPosition()==2){
                    method.preferences.setValue("selCatStatus","1");
                }else if ( dailogFilterJobBinding.etCategoryD.getSelectedItemPosition()==3){
                    method.preferences.setValue("selCatStatus","3");
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                method.preferences.setValue("selCatStatus","all");
            }
        });

        if (dialogFilterJob.getWindow() != null){
            dialogFilterJob.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        dialogFilterJob.show();
        dialogFilterJob.setCancelable(false);

        dailogFilterJobBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFilterJob.dismiss();

            }
        });

      /*  dailogFilterJobBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFilterJob.dismiss();

            }
        });
        dailogFilterJobBinding.tvSubmit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // Log.e(TAG, "onClick: " + dailogFilterJobBinding.seekBar.getProgress());
                        filteredList();
                    }
                });*/

        dailogFilterJobBinding.btnSubmit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Log.e(TAG, "onClick: " + dailogFilterJobBinding.seekBar.getProgress());
                        // filteredList();
                        dialogFilterJob.dismiss();
                        topDepositDataAdapter.filter2(method.preferences.getValue("selCategory"),method.preferences.getValue("selCatStatus"));

                    }
                });
    }

  /*  public void   filter( ArrayList arrayList, String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if(charText.equals("pending")){
            charText="0";
        } if((charText.equals("success"))||(charText.equals("approved"))){
            charText="1";
        } if((charText.equals("rejected"))||(charText.equals("cancelled"))){
            charText="2";
        } if((charText.equals("processing"))||(charText.equals("process"))){
            charText="4";
        }
        topDepositsDTOS.clear();
        if (charText.length() == 0) {
            topDepositsDTOS.addAll(arrayList);
        } else {
            for (HomeTopDepositsDTO userBooking : userBookingsList) {
                if ((userBooking.getIdName().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getOrderId().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getAmount().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getMobile().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getTxnStatus().toLowerCase(Locale.getDefault())
                        .contains(charText))) {
                    topDepositsDTOS.add(userBooking);
                }

            }
        }
        //notifyDataSetChanged();
        return   userBookingsList;
    }*/
}
package com.gpuntd.adminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gpuntd.adminapp.Models.Offer_Data;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.adapter.OfferDataAdapter;
import com.gpuntd.adminapp.databinding.ActivityCreateAppOfferBinding;
import com.gpuntd.adminapp.retrofit.GopuntAPI;
import com.gpuntd.adminapp.retrofit.GopuntRetrofitClient;
import com.gpuntd.adminapp.retrofit.model.OfferPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CreateAppOfferActivity2 extends AppCompatActivity {
    private static final String TAG = "shubhcode";
    public static Uri selectedImage;
    ActivityCreateAppOfferBinding binding;
    Toolbar toolbar;
    RecyclerView offerrecyclerView;
    OfferDataAdapter OfferRecyclerAdapter;
    private Method method;
    private List<Offer_Data> offer_data_list;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    List<OfferPojo.Result> result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_app_offer);

        binding = ActivityCreateAppOfferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.toolbarAppOffer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // preferences = this.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);

        preferences = CreateAppOfferActivity2.this.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        method = new Method(this);


        offerrecyclerView = binding.rvAppOffer;
        // getOffer();

        binding.btnAddOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.offerType = "add";
                OfferDataAdapter.setupUpdateDialog(v);
            }
        });

        getOfferByRetrofit();

    }

    public void getOfferByRetrofit() {
        GopuntAPI gopuntAPI = GopuntRetrofitClient.getInstance().getApi();
        Call<OfferPojo> offerCall = gopuntAPI.getOffers();
        offer_data_list = new ArrayList<>();
        offerrecyclerView.setLayoutManager(new LinearLayoutManager(CreateAppOfferActivity2.this));
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor se = sharedPreferences.edit();
        if (sharedPreferences.getString("offervalue", "").equals("")) { //check if shared preference having empty string, if yes then call retrofit
            Toast.makeText(this,"from retrofit", Toast.LENGTH_SHORT).show();

            offerCall.enqueue(new Callback<OfferPojo>() {
                @Override
                public void onResponse(Call<OfferPojo> call, retrofit2.Response<OfferPojo> response) {
                    if (response.isSuccessful()) {
                        String offerJsonString = new Gson().toJson(response.body());

                        se.putString("offervalue", offerJsonString);
                        se.commit();

                        result = response.body().getDarwinbarkk().getResults();

                        if (result!=null && result.size() > 0){
                            for (OfferPojo.Result curr : result) {
                                Offer_Data item = new Offer_Data(curr.getId(), curr.getTitle(), curr.getBody(), curr.getOfferStatus(), curr.getOfCreatedDate());
                                offer_data_list.add(item);
                                // Toast.makeText(CreateAppOfferActivity2.this, offer_data_list.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                            }
                            offerrecyclerView.clearFocus();
                            OfferRecyclerAdapter = new OfferDataAdapter(offer_data_list, CreateAppOfferActivity2.this);
                            offerrecyclerView.setAdapter(OfferRecyclerAdapter);
                        }                        //  Toast.makeText(CreateAppOfferActivity2.this, "succsesful "+ new Gson().toJson(response.body()), Toast.LENGTH_SHORT).show();
                        //Log.d("retroTag", "passed =>");
                    } else {
                        //  Toast.makeText(CreateAppOfferActivity2.this, "failed retrofit ", Toast.LENGTH_SHORT).show();

                        Log.d("retroTag", "onResponse: failed retrofit");
                    }
                }

                @Override
                public void onFailure(Call<OfferPojo> call, Throwable t) {
                    //saale ye hai na
                    Toast.makeText(CreateAppOfferActivity2.this, "failed retrofit function", Toast.LENGTH_SHORT).show();

                    Log.d("retroTag", "faileure: failed retrofit");
                }
            });
        }else{
            Toast.makeText(this,"from shared pref", Toast.LENGTH_SHORT).show();
            OfferPojo offerpojo= new Gson().fromJson(sharedPreferences.getString("offervalue", ""), OfferPojo.class);
            result = offerpojo.getDarwinbarkk().getResults();


            if (result!=null && result.size() > 0){
                for (OfferPojo.Result curr : result) {
                    Offer_Data item = new Offer_Data(curr.getId(), curr.getTitle(), curr.getBody(), curr.getOfferStatus(), curr.getOfCreatedDate());
                    offer_data_list.add(item);
                    // Toast.makeText(CreateAppOfferActivity2.this, offer_data_list.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                }
                offerrecyclerView.clearFocus();
                OfferRecyclerAdapter = new OfferDataAdapter(offer_data_list, CreateAppOfferActivity2.this);
                offerrecyclerView.setAdapter(OfferRecyclerAdapter);
            }
        }




    }

//    public void getOffer() {
//        offer_data_list = new ArrayList<>();
//
//        offerrecyclerView.setLayoutManager(new LinearLayoutManager(CreateAppOfferActivity2.this));
//        //  CreateRecyclerAdapter= new CreateDataAdapter(create_id_data,getContext());
//        method.loadingDialogg(CreateAppOfferActivity2.this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_GET_OFFER,
//                new com.android.volley.Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Toast.makeText(LoginActivity.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
//                        try {
//                            //  System.out.println(response);
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            JSONObject jsonObject1 = jsonObject.getJSONObject(GlobalVariables.AppSid);
//                            JSONArray jsonArray2 = jsonObject1.getJSONArray("Results");
//                            String success = jsonObject1.getString("success");
//
//                            if (success.equals("1")) {
//
//                                for (int i = 0; i < jsonArray2.length(); i++) {
//                                    JSONObject object = jsonArray2.getJSONObject(i);
//                                    Log.d(TAG, "onResponse: result" + jsonArray2);
//
//
//                                    String id = object.getString("id");
//                                    String title = object.getString("Title");
//                                    String body = object.getString("Body");
//                                    String offerStatus = object.getString("offerStatus");
//                                    String ofCreatedDate = object.getString("ofCreatedDate");
//
//                                    Offer_Data ld1 = new Offer_Data(id, title, body, offerStatus, ofCreatedDate);
//                                    offer_data_list.add(ld1);
//                                    //Toast.makeText(getActivity(), "hello"+ob.getString("amount"), Toast.LENGTH_LONG).show();
//
//
//                                }
//                                Log.d(TAG, "onResponse: " + jsonArray2);
//
//                                offerrecyclerView.clearFocus();
//                                OfferRecyclerAdapter = new OfferDataAdapter(offer_data_list, CreateAppOfferActivity2.this);
//                                //recyclerView.setLayoutManager(new LinearLayoutManager(view));
//                                //GridLayoutManager lm = new GridLayoutManager(view, 1);
//                                // recyclerView.setLayoutManager();
//                                offerrecyclerView.setAdapter(OfferRecyclerAdapter);
//
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        method.loadingDialog.dismiss();
//                                    }
//                                }, 1000);
//                            } else {
//                                method.loadingDialog.dismiss();
//                                //sendVerificationCodeToUser(mobileNumber);
//                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateAppOfferActivity2.this);
//                                alertDialogBuilder.setTitle(jsonObject.getString("title"));
//                                alertDialogBuilder.setMessage(jsonObject.getString("msg"));
//                                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
//                                alertDialogBuilder.setPositiveButton(CreateAppOfferActivity2.this.getResources().getString(R.string.ok_message),
//                                        new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface arg0, int arg1) {
//                                                method.loadingDialog.dismiss();
//                                                CreateAppOfferActivity2.this.finish();
//                                                startActivity(new Intent(CreateAppOfferActivity2.this, MainActivity.class));
//                                                //Log.d("Response",msg);
//                                                // finishAffinity();
//
//                                            }
//                                        });
//
//                                AlertDialog alertDialog = alertDialogBuilder.create();
//                                alertDialog.show();
//                            }
//
//
//                            //loadingDialog.dismiss();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            //  Toast.makeText(getActivity(), "hello"+e, Toast.LENGTH_LONG).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Do something when error occurred
//                        //Toast.makeText(WalletActivity.this,"Error...!",Toast.LENGTH_SHORT).show();
//                        method.loadingDialog.dismiss();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//
//                Map<String, String> params = new HashMap<>();
//                params.put("users_login", "KINGSN");
//                // params.put("mobile",GlobalVariables.profileuser.getMobile());
//                /*  params.put("device_id",method.getDeviceId(getApplicationContext()));*/
//
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(CreateAppOfferActivity2.this);
//        requestQueue.add(stringRequest);
//        stringRequest.setShouldCache(false);
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            CreateDataAdapter.onActivityResult(requestCode, resultCode, data);
            //Toast.makeText(this, "" + selectedImage, Toast.LENGTH_SHORT).show();


        }*/
        method.onActivityResult(CreateAppOfferActivity2.this, requestCode, resultCode, data, null, OfferRecyclerAdapter.idImage);
    }
}
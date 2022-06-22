package com.gpuntd.adminapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import com.gpuntd.adminapp.Models.Create_ID_Data;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.adapter.CreateDataAdapter;
import com.gpuntd.adminapp.databinding.ActivityCreateAppOfferBinding;
import com.gpuntd.adminapp.databinding.ActivityCreateIdModeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CreateIdModeActivity extends AppCompatActivity {

    private static final String TAG = "KINGSN";
    ActivityCreateIdModeBinding binding;
    RecyclerView CreaterecyclerView;
    CreateDataAdapter CreateRecyclerAdapter;
    CreateDataAdapter.MyViewHolder viewHolder;
    View view;
    Toolbar toolbar;
    private Method method;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private List<Create_ID_Data> create_id_data;
    public static Uri selectedImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_id_mode);
        binding = ActivityCreateIdModeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.toolbarIdMode);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = CreateIdModeActivity.this.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        method=new Method(this);

        CreaterecyclerView = findViewById(R.id.rvCreateID);


        // preferences = this.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);

        method = new Method(this);
         getCreateId();

         binding.btnAddcreateID.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if (method.adminDTO.getManageCreateIDList().equals("2")) {
                     GlobalVariables.offerType = "add";
                     CreateDataAdapter.setupUpdateDialog(v);
                 }else{
                     method.showToasty(CreateIdModeActivity.this ,"2","You Dont Have This Access \n Please" +
                             " Ask Super To Grant Full Access ");
                 }
             }
         });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      /*  if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
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

        method.onActivityResult(CreateIdModeActivity.this,requestCode, resultCode, data,CreateDataAdapter.etEditCreateIdLogo,null);

    }

    public void getCreateId() {
        create_id_data = new ArrayList<>();

        CreaterecyclerView.setLayoutManager(new LinearLayoutManager(this));
        method.loadingDialogg(CreateIdModeActivity.this);
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
                                    Log.d(TAG, "onResponse: result" + jsonArray2);


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
                                    String idCreatedDate = Method.convertTimestampDateToTime(Method.correctTimestamp(object.getString("id_created_date")));
                                    String idUpdatedDate = Method.convertTimestampDateToTime(Method.correctTimestamp(object.getString("id_updated_date")));
                                    String idTotalCreated = object.getString("id_total_created");

                                    Create_ID_Data ld1 = new Create_ID_Data(id, idName, idImage, idWebsite, idStatus, demoId, demoPass, demoLink,
                                            minRefill, minWithdrawal, minMaintainBal, maxWithdrawal, idCreatedDate,
                                            idUpdatedDate, idTotalCreated);
                                    create_id_data.add(ld1);
                                    //Toast.makeText(getActivity(), "hello"+ob.getString("amount"), Toast.LENGTH_LONG).show();


                                }
                                Log.d(TAG, "onResponse: " + jsonArray2);

                                CreaterecyclerView.clearFocus();
                                CreateRecyclerAdapter = new CreateDataAdapter(create_id_data, CreateIdModeActivity.this);
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
                            } else {
                                method.loadingDialog.dismiss();
                                //sendVerificationCodeToUser(mobileNumber);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateIdModeActivity.this);
                                alertDialogBuilder.setTitle(jsonObject.getString("title"));
                                alertDialogBuilder.setMessage(jsonObject.getString("msg"));
                                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                                alertDialogBuilder.setPositiveButton(getApplicationContext().getResources().getString(R.string.ok_message),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                method.loadingDialog.dismiss();
                                                finish();
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                //Log.d("Response",msg);
                                                // finishAffinity();

                                            }
                                        });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }


                            //loadingDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //  Toast.makeText(getActivity(), "hello"+e, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        //Toast.makeText(WalletActivity.this,"Error...!",Toast.LENGTH_SHORT).show();
                        method.loadingDialog.dismiss();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(CreateIdModeActivity.this);
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);

    }


}
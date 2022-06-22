package com.gpuntd.adminapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gpuntd.adminapp.Models.Profileuser;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.adapter.AdminsListAdapter;
import com.gpuntd.adminapp.databinding.ActivityAdminBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {
    public static String TAG = "KINGSN";
    ActivityAdminBinding binding;
    ArrayList<Profileuser> AdminListArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    private Method method;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin);
        setSupportActionBar(binding.toolbarAdmin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
      /*  binding.toolbarAdmin.setNavigationOnClickListener(view -> {
            //super.onBackPressed();
            openDialog();
        });*/
        recyclerView = findViewById(R.id.rvAdmin);
        preferences = AdminActivity.this.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        method=new Method(this);
        getAdminData();

    }

    void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_admin, findViewById(R.id.editAdminContainer));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }


    public void getAdminData() {
        method.loadingDialogg(AdminActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_ADMIN_LIST1,
                response -> {
                    try {
                        System.out.println(response);
                        Log.d(TAG, "getAdminData: " + response);
                        JSONObject jsonObject = new JSONObject(response);


                        String success = jsonObject.getString("success");

                        if (success.equals("1")) {
                            JSONArray jsonArray2 = jsonObject.getJSONArray("data");

                           // Toast.makeText(this, "" + jsonArray2.length(), Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < jsonArray2.length(); i++) {

                                JSONObject object = jsonArray2.getJSONObject(i);
                                Log.d(TAG, "onResponse: result" + jsonArray2);

                                String id = object.getString("id");
                                String admin_user_id = object.getString("admin_user_id");
                                String admin_refer_code = object.getString("admin_refer_code");
                                String admin_password = object.getString("admin_password");
                                String admin_name = object.getString("admin_name");
                                String admin_email = object.getString("admin_email");
                                String admin_mobile = object.getString("admin_mobile");
                                String profileImg = object.getString("profileImg");
                                String deviceToken = object.getString("device_token");
                                String admin_status = object.getString("admin_status");
                                String manageUserList = object.getString("manageUserList");
                                String manageDepositList = object.getString("manageDepositList");
                                String manageCreateIDList = object.getString("manageCreateIDList");
                                String manageWithdrawal = object.getString("manageWithdrawal");
                                String managePassword = object.getString("managePassword");
                                String manageCloseID = object.getString("manageCloseID");
                                String manageNotification = object.getString("manageNotification");
                                String manageAppSettings = object.getString("manageAppSettings");
                                String manageAdminList = object.getString("manageAdminList");
                                String admin_login_date = (Method.convertTimestampDateToTime(object.getString("admin_login_date")));
                                String admin_joining_date = (Method.convertTimestampDateToTime(object.getString("admin_joining_date")));



                                //constant.sharedEditor.putBoolean(constant.isLogin, true);

                                Profileuser profileuser = new Profileuser(id, admin_user_id, admin_refer_code, admin_password, admin_name, admin_email, admin_mobile, profileImg,
                                        deviceToken,admin_status,
                                        manageUserList, manageDepositList, manageCreateIDList, manageWithdrawal, managePassword, manageCloseID,
                                        manageNotification, manageAppSettings, manageAdminList,admin_login_date, admin_joining_date);


                                AdminListArrayList.add(profileuser);

                            }
                            Log.d(TAG, "onResponse: " + jsonArray2);

                             binding.rvAdmin.clearFocus();
                            binding.rvAdmin.setLayoutManager(new LinearLayoutManager(AdminActivity.this));
                             AdminsListAdapter adminsListAdapter = new AdminsListAdapter( AdminListArrayList,AdminActivity.this);
                            binding.rvAdmin.setAdapter(adminsListAdapter);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    method.loadingDialog.dismiss();
                                }
                            }, GlobalVariables.dismissAfter);
                        } else {
                            method.loadingDialog.dismiss();
                            Method.alertBox("2",jsonObject.getString("title"),jsonObject.getString("msg"), AdminActivity.this,GlobalVariables.btntxt);


                        }


                        //loadingDialog.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                          Toast.makeText(this, "hello"+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        //Toast.makeText(WalletActivity.this,"Error...!",Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("users_login", "KINGSN");
                // params.put("mobile",preferences.getString(GlobalVariables.USER_MOBILE,"") );
                //params.put("device_id",method.getDeviceId(requireActivity()));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);

    }
}
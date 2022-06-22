package com.gpuntd.adminapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gpuntd.adminapp.Models.Notification_data;
import com.gpuntd.adminapp.Models.Profileuser;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.ImageCompression;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.PrefManager;
import com.gpuntd.adminapp.Util.RestAPI;

import com.gpuntd.adminapp.adapter.NotificationDataAdapter;
import com.gpuntd.adminapp.databinding.ActivityNotificationsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class NotificationsActivity extends AppCompatActivity {

    private static final String TAG = "KINGSN";
    ActivityNotificationsBinding binding;
    private Method method;
    private Profileuser profileuser;
    private SharedPreferences preferences;
    Bitmap bm;
    ImageCompression imageCompression;
    private SharedPreferences.Editor editor;
    public ImageView CommonImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notifications);
        setSupportActionBar(binding.toolbarNoti);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbarNoti.setNavigationOnClickListener(view -> {
            super.onBackPressed();
        });

        preferences = NotificationsActivity.this.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        method=new Method(this);


        binding.btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if()*/
                method.getGalleryImage(NotificationsActivity.this);
            }
        });

        binding.btnNotiSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.etNotiTitle.getText().toString().equals("")){
                    binding.etNotiTitle.setError("Please Enter A Notification Title ");
                }else if(binding.etNotiMessage.getText().toString().equals("")){
                    binding.etNotiMessage.setError("Please Enter A Notification message ");
                }else{
                    try {
                        //method.updateWithImg(CreateIdEditActivity.this);
                        onSubmit(Method.bm);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        method.onActivityResult(NotificationsActivity.this,requestCode, resultCode, data,null,binding.ivBanner1);
    }

    public void onSubmit(Bitmap bm) throws JSONException {

        if (Method.file != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            String encodedImage2 = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            try {

                method.jsonObject = new JSONObject();
                String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());
                method.jsonObject.put("imagename", imgname);
                method.jsonObject.put("image", encodedImage);
                method.jsonObject.put("external_link", Objects.requireNonNull(binding.etNotiExternalLink.getText()).toString());
                method.jsonObject.put("notification_msg", Objects.requireNonNull(binding.etNotiMessage.getText()).toString());
                method.jsonObject.put("notification_title", Objects.requireNonNull(binding.etNotiTitle.getText()).toString());


            } catch (JSONException e) {
                Log.e(TAG,"JSONObject Here"+ e.toString());
            }


        }
        if (Method.file == null){
            try {


                method.jsonObject = new JSONObject();
                //  Log.e("Image name", etxtUpload.getText().toString().trim());
                method.jsonObject.put("external_link", Objects.requireNonNull(binding.etNotiExternalLink.getText()).toString());
                method.jsonObject.put("notification_msg", Objects.requireNonNull(binding.etNotiMessage.getText()).toString());
                method.jsonObject.put("notification_title", Objects.requireNonNull(binding.etNotiTitle.getText()).toString());





            } catch (JSONException e) {
                // Log.e(TAG,"JSONObject Here"+ e.toString());
            }


        }

        if(method.adminDTO.getManageNotification().equals("2")){
            method.updateWithImg(NotificationsActivity.this, RestAPI.SEND_NOTIFICATION);
        }else{
            method.showToasty(NotificationsActivity.this ,"2","You Dont Have This Access \n Please" +
                    " Ask Super To Grant Full Access ");
        }



    }

}
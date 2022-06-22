package com.gpuntd.adminapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gpuntd.adminapp.Models.Profileuser;
import com.gpuntd.adminapp.Util.Ex;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.ImageCompression;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.databinding.ActivityProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "KINGSN";
    private final HashMap<String, String> params = new HashMap<>();
    ActivityProfileBinding binding;
    private Method method;
    private Profileuser profileuser;
    private SharedPreferences preferences;
    Bitmap bm;
    ImageCompression imageCompression;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        setSupportActionBar(binding.toolbarProf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        method = new Method(this);
        preferences = getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
        SetDAta();


        binding.toolbarProf.setNavigationOnClickListener(view -> {
            super.onBackPressed();
        });


        binding.toolbarProf.setNavigationOnClickListener(view -> {
            super.onBackPressed();
        });

    }

    @SuppressLint("SetTextI18n")
    private void SetDAta() {


        Glide.with(ProfileActivity.this)
                .load(GlobalVariables.profileuser.getProfileImg())
                .placeholder(R.drawable.dummyuser_image)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.prifileImageView);

        binding.etEditProfileUsername.setText(GlobalVariables.profileuser.getAdmin_user_id());
        binding.etEditProfilePassword.setText(GlobalVariables.profileuser.getAdmin_password());

        binding.prifileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if()*/
                method.getGalleryImage(ProfileActivity.this);
            }
        });

        binding.btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //method.updateWithImg(CreateIdEditActivity.this);
                    onSubmit(Method.bm);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        method.onActivityResult(ProfileActivity.this,requestCode, resultCode, data,binding.prifileImageView,null);
    }



    public void onSubmit(Bitmap bm) throws JSONException {

        if (Method.file != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            try {
                method.jsonObject = new JSONObject();
                String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());
                method.jsonObject.put("imagename", imgname);
                method.jsonObject.put("image", encodedImage);
                //  Log.e("Image name", etxtUpload.getText().toString().trim());
                method.jsonObject.put("Uid",method.adminDTO.getId());
                method.jsonObject.put("admin_user_id",binding.etEditProfileUsername.getText().toString());
                method.jsonObject.put("admin_password",binding.etEditProfilePassword.getText().toString());

            } catch (JSONException e) {
                Log.e(TAG,"JSONObject Here"+ e.toString());
            }


        }
        if (Method.file == null){
            try {
                method.jsonObject = new JSONObject();
                //  Log.e("Image name", etxtUpload.getText().toString().trim());
                method.jsonObject.put("Uid",method.adminDTO.getId());
                method.jsonObject.put("admin_user_id",binding.etEditProfileUsername.getText().toString());
                method.jsonObject.put("admin_password",binding.etEditProfilePassword.getText().toString());

            } catch (JSONException e) {
                // Log.e(TAG,"JSONObject Here"+ e.toString());
            }


        }

        method.updateWithImg(ProfileActivity.this,RestAPI.ADMINP_ID_UPDATE);

    }


}
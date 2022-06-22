package com.gpuntd.adminapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.icu.text.Transliterator;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gpuntd.adminapp.Models.Create_ID_Data;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.ImageCompression;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.adapter.CreateDataAdapter;
import com.gpuntd.adminapp.databinding.ActivityCreateidEditBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateIdEditActivity extends AppCompatActivity {

    private static final String TAG = "KINGSN";
    ActivityCreateidEditBinding binding;
    View view;
    Toolbar toolbar;
    private Method method;
    private SharedPreferences preferences;
    public static Uri selectedImage;
    private SharedPreferences.Editor editor;
    public String mobile;
    public String minRefill;
    public String createId;
    public String idName;
    public String idWebsite;
    public String minWithdrawal;
    public String idImageUrl;
    public String minMaintainBal;
    public String maxWithdrawal;
    public String demoIDidUsername;
    public String idPassword;
    public int position;
    public Create_ID_Data createIdData1;
    public String AStatus="1";
    public ArrayList<String> createIdData;
    Bitmap bm;
    ImageCompression imageCompression;
    File file;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createid_edit);
        binding = ActivityCreateidEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.toolbarIdMode);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // preferences = this.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);

        preferences = CreateIdEditActivity.this.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        method=new Method(this);
        Log.d(TAG, "createIdDAta: "+GlobalVariables.createIdData.getIdWebsite()+"\n"+GlobalVariables.createIdData.getIdName());
        Log.d(TAG, "onCreate: "+getIntent().getExtras());
        position = getIntent().getIntExtra("Position",0);
        createIdData= getIntent().getStringArrayListExtra("createIdData");
        if (createIdData != null) {
            Log.d(TAG, "onCreate: "+createIdData);
        }
        createId = getIntent().getStringExtra("createId");
         idImageUrl = getIntent().getStringExtra("idimageurl");
         idName = getIntent().getStringExtra("idname");
         idWebsite = getIntent().getStringExtra("idwebsite");
         minRefill = getIntent().getStringExtra("minRefill");
         minWithdrawal = getIntent().getStringExtra("minWithdrawal");
         minMaintainBal = getIntent().getStringExtra("minMaintainBal");
         maxWithdrawal = getIntent().getStringExtra("maxWithdrawal");
        Log.d(TAG, "onCreate: "+createId);

      //  getCreateId();
        setData();
    }

    private void setData() {
        binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    AStatus="1";
                } else {
                    // The toggle is disabled
                    AStatus="2";
                }
            }
        });

        binding.etEditCreateIdName.setText(idName);
        binding.etEditCreateIdWebsite.setText(idWebsite);
       // binding.etEditDemoId.setText();
        binding.etEditDemoId.setText(GlobalVariables.createIdData.getDemoId());
        binding.etEditDemoPassword.setText(GlobalVariables.createIdData.getDemoPass());
        binding.etEditDemoLink.setText(GlobalVariables.createIdData.getDemoLink());
        binding.etEditMinimumRefill.setText(GlobalVariables.createIdData.getMinRefill());
        binding.etEditMinimumWithdrawal.setText(GlobalVariables.createIdData.getMinWithdrawal());
        binding.etEditMinimumMaintainBalance.setText(GlobalVariables.createIdData.getMinMaintainBal());
        binding.etEditMaximumWithdrawal.setText(GlobalVariables.createIdData.getMaxWithdrawal());
        binding.etEditCreatedDate.setText(GlobalVariables.createIdData.getIdCreatedDate());
        binding.etEditCreatedDate.setVisibility(View.GONE);
        binding.etEditId.setVisibility(View.GONE);

        if(!GlobalVariables.createIdData.getIdStatus().equals("1")){
            binding.switch1.getTextOff();
            binding.switch1.setSelected(false);
            binding.switch1.setChecked(false);
            AStatus="2";
        }else{
            binding.switch1.setSelected(true);
            binding.switch1.setChecked(true);
            AStatus="1";
        }





        //  etEditDemoId.setText(createIdData.getDemoId());
        if(!GlobalVariables.createIdData.getIdImage().equals("")){
            Glide.with(CreateIdEditActivity.this)
                    .load(GlobalVariables.createIdData.getIdImage())
                    .placeholder(R.drawable.dummyuser_image)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into((ImageView) binding.etEditCreateIdLogo);

        }else{
            Glide.with(CreateIdEditActivity.this)
                    .load(CreateIdEditActivity.this.getResources().getDrawable(R.drawable.ic_profile))
                    .placeholder(R.drawable.dummyuser_image)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into((ImageView) binding.etEditCreateIdLogo);
        }

        binding.btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                method.getGalleryImage(CreateIdEditActivity.this);
            }
        });

        binding.btnSaveCreatedId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //method.updateWithImg(CreateIdEditActivity.this);
                    onSubmit(method.bm);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        method.onActivityResult(CreateIdEditActivity.this,requestCode, resultCode, data,binding.etEditCreateIdLogo,null);
    }


    public void onSubmit(Bitmap bm) throws JSONException {

        if (method.file != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            try {
                method.jsonObject = new JSONObject();
                String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());
                method.jsonObject.put("imagename", imgname);
                method.jsonObject.put("image", encodedImage);
                //  Log.e("Image name", etxtUpload.getText().toString().trim());
                method.jsonObject.put("Uid",GlobalVariables.createIdData.getId());
                method.jsonObject.put("idName",binding.etEditCreateIdName.getText().toString());
                method.jsonObject.put("idStatus",AStatus);
                method.jsonObject.put("idWebsite",binding.etEditCreateIdWebsite.getText().toString());
                method.jsonObject.put("idCreatedAt",binding.etEditCreatedDate.getText().toString());

                method.jsonObject.put("minRefill",binding.etEditMinimumRefill.getText().toString());
                method.jsonObject.put("minWithdrawal",binding.etEditMinimumWithdrawal.getText().toString());
                method.jsonObject.put("maxWithdrawal",binding.etEditMaximumWithdrawal.getText().toString());
                method.jsonObject.put("minMaintainBal",binding.etEditMinimumMaintainBalance.getText().toString());
                method.jsonObject.put("demoId",binding.etEditDemoId.getText().toString());
                method.jsonObject.put("demoPass",binding.etEditDemoPassword.getText().toString());
                method.jsonObject.put("demoLink",binding.etEditDemoLink.getText().toString());


            } catch (JSONException e) {
                 Log.e(TAG,"JSONObject Here"+ e.toString());
            }


        }
        if (method.file == null){
            try {
                method.jsonObject = new JSONObject();
                //  Log.e("Image name", etxtUpload.getText().toString().trim());
                method.jsonObject.put("Uid",GlobalVariables.createIdData.getId());
                method.jsonObject.put("idName",binding.etEditCreateIdName.getText().toString());
                method.jsonObject.put("idStatus",AStatus);
                method.jsonObject.put("idWebsite",binding.etEditCreateIdWebsite.getText().toString());
                method.jsonObject.put("idCreatedAt",binding.etEditCreatedDate.getText().toString());

                method.jsonObject.put("minRefill",binding.etEditMinimumRefill.getText().toString());
                method.jsonObject.put("minWithdrawal",binding.etEditMinimumWithdrawal.getText().toString());
                method.jsonObject.put("maxWithdrawal",binding.etEditMaximumWithdrawal.getText().toString());
                method.jsonObject.put("minMaintainBal",binding.etEditMinimumMaintainBalance.getText().toString());
                method.jsonObject.put("demoId",binding.etEditDemoId.getText().toString());
                method.jsonObject.put("demoPass",binding.etEditDemoPassword.getText().toString());
                method.jsonObject.put("demoLink",binding.etEditDemoLink.getText().toString());


            } catch (JSONException e) {
                // Log.e(TAG,"JSONObject Here"+ e.toString());
            }


        }

        method.updateWithImg(CreateIdEditActivity.this,RestAPI.CREATE_ID_UPDATE);

    }


}
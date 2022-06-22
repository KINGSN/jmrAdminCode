package com.gpuntd.adminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.databinding.ActivityAppBannerBinding;
import com.gpuntd.adminapp.databinding.ActivityCreateidEditBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Objects;

public class AppBannerActivity extends AppCompatActivity {
    private static final String TAG = "KINGSN";
    ActivityAppBannerBinding binding;
    Toolbar toolbar;
    private Method method;
    private SharedPreferences preferences;
    public static Uri selectedImage;
    private SharedPreferences.Editor editor;
    public ImageView CommonImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_banner);

        binding = ActivityAppBannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.toolbarAppBanner);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // preferences = this.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);

        preferences = AppBannerActivity.this.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        method=new Method(this);

        setData();

    }

    private void setData() {
        binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });

        binding.etBannerLink.setText(GlobalVariables.settings.getHomeBannerimg1());
        binding.etBannerLink2.setText(GlobalVariables.settings.getHomeBannerimg2());
        // binding.etEditDemoId.setText();
        binding.etBannerLink3.setText(GlobalVariables.settings.getHomeBannerimg3());
        binding.etBannerLink4.setText(GlobalVariables.settings.getHomeBannerimg2Enabled());

        if(GlobalVariables.settings.getHomeBannerimg1().equals("false")){
            binding.switch1.getTextOff();
            binding.switch1.setSelected(false);

            //AStatus="2";
        }else{
            binding.switch1.setSelected(true);
            //AStatus="1";
            Glide.with(AppBannerActivity.this)
                    .load(GlobalVariables.settings.getHomeBannerimg1())
                    .placeholder(R.drawable.dummyuser_image)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into((ImageView) binding.ivBanner1);
        }
        if(GlobalVariables.settings.getHomeBannerimg2().equals("false")){
            binding.switch1.getTextOff();
            binding.switch1.setSelected(false);

            //AStatus="2";
        }else{
            binding.switch1.setSelected(true);
            //AStatus="1";
            Glide.with(AppBannerActivity.this)
                    .load(GlobalVariables.settings.getHomeBannerimg2())
                    .placeholder(R.drawable.dummyuser_image)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into((ImageView) binding.ivBanner2);
        }

        if(GlobalVariables.settings.getHomeBannerimg3().equals("false")){
            binding.switch1.getTextOff();
            binding.switch1.setSelected(false);

            //AStatus="2";
        }else{
            binding.switch1.setSelected(true);
            //AStatus="1";
            Glide.with(AppBannerActivity.this)
                    .load(GlobalVariables.settings.getHomeBannerimg3())
                    .placeholder(R.drawable.dummyuser_image)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into((ImageView) binding.ivBanner3);
        }
        if(GlobalVariables.settings.getHomeBannerimg2Enabled().equals("false")){
            binding.switch1.getTextOff();
            binding.switch1.setSelected(false);

            //AStatus="2";
        }else{
            binding.switch1.setSelected(true);
            //AStatus="1";
            Glide.with(AppBannerActivity.this)
                    .load(GlobalVariables.settings.getHomeBannerimg2Enabled())
                    .placeholder(R.drawable.dummyuser_image)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into((ImageView) binding.ivBanner4);
        }

        //  etEditDemoId.setText(createIdData.getDemoId());


        binding.ivBanner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                method.getGalleryImage(AppBannerActivity.this);
                CommonImage=binding.ivBanner1;
            }
        });

        binding.ivBanner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                method.getGalleryImage(AppBannerActivity.this);
                CommonImage=binding.ivBanner2;
            }
        });

        binding.ivBanner3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                method.getGalleryImage(AppBannerActivity.this);
                CommonImage=binding.ivBanner3;
            }
        });

        binding.ivBanner4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                method.getGalleryImage(AppBannerActivity.this);
                CommonImage=binding.ivBanner4;
                Log.d(TAG, "onClick: "+CommonImage);
            }
        });

        binding.btnSaveAppBanner.setOnClickListener(new View.OnClickListener() {
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

        method.onActivityResult(AppBannerActivity.this,requestCode, resultCode, data,null,CommonImage);
    }



    public void onSubmit(Bitmap bm) throws JSONException {

        if (method.file != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            String encodedImage2 = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            try {

                method.jsonObject = new JSONObject();
                String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());
                method.jsonObject.put("imagename", imgname);
                method.jsonObject.put("image", encodedImage);
                method.jsonObject.put("CommonImage", CommonImage);
               if(CommonImage==binding.ivBanner1){
                   method.jsonObject.put("home_bannerimg1", encodedImage);
                   method.jsonObject.put("home_bannerimg2", "");
                   method.jsonObject.put("home_bannerimg3", "");
                   method.jsonObject.put("home_bannerimg2_enabled", "");
               }  else if(CommonImage==binding.ivBanner2){
                   method.jsonObject.put("home_bannerimg1", "");
                   method.jsonObject.put("home_bannerimg2", encodedImage);
                   method.jsonObject.put("home_bannerimg3", "");
                   method.jsonObject.put("home_bannerimg2_enabled", "");
                }else  if(CommonImage==binding.ivBanner3){
                   method.jsonObject.put("home_bannerimg1", "");
                   method.jsonObject.put("home_bannerimg2", "");
                   method.jsonObject.put("home_bannerimg3", encodedImage);
                   method.jsonObject.put("home_bannerimg2_enabled", "");
                }else  if(CommonImage==binding.ivBanner4){
                   method.jsonObject.put("home_bannerimg1", "");
                   method.jsonObject.put("home_bannerimg2", "");
                   method.jsonObject.put("home_bannerimg3", "");
                    method.jsonObject.put("home_bannerimg2_enabled", encodedImage);
                }

                //  Log.e("Image name", etxtUpload.getText().toString().trim());



            } catch (JSONException e) {
                Log.e(TAG,"JSONObject Here"+ e.toString());
            }


        }
        if (method.file == null){
            try {


                method.jsonObject = new JSONObject();
                //  Log.e("Image name", etxtUpload.getText().toString().trim());
                method.jsonObject.put("Uid",GlobalVariables.createIdData.getId());
                method.jsonObject.put("home_bannerimg1",binding.etBannerLink.getText().toString());
                method.jsonObject.put("home_bannerimg2",binding.etBannerLink2.getText().toString());
                method.jsonObject.put("home_bannerimg3",binding.etBannerLink3.getText().toString());
                method.jsonObject.put("home_bannerimg2_enabled",binding.etBannerLink4.getText().toString());



            } catch (JSONException e) {
                // Log.e(TAG,"JSONObject Here"+ e.toString());
            }


        }

        method.updateWithImg(AppBannerActivity.this, RestAPI.APP_BANNER_UPDATE);

    }
}
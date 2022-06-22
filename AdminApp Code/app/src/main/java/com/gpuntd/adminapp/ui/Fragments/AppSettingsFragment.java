package com.gpuntd.adminapp.ui.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cocosw.bottomsheet.BottomSheet;
import com.gpuntd.adminapp.MainActivity;
import com.gpuntd.adminapp.Models.AdminDTO;
import com.gpuntd.adminapp.Models.HomeDataDTO;
import com.gpuntd.adminapp.Models.HomeGraphDTO;
import com.gpuntd.adminapp.Models.HomeTopDepositsDTO;
import com.gpuntd.adminapp.Models.HomeTopUserDTO;
import com.gpuntd.adminapp.Models.Passbook_Data;
import com.gpuntd.adminapp.Models.Profileuser;
import com.gpuntd.adminapp.Models.Settings;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.Ex;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.ImageCompression;
import com.gpuntd.adminapp.Util.MainFragment;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.adapter.TopDepositDataAdapter;
import com.gpuntd.adminapp.adapter.TopUserDataAdapter;
import com.gpuntd.adminapp.databinding.FragmentAppsettingsBinding;
import com.gpuntd.adminapp.preferences.SharedPrefrence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class AppSettingsFragment extends Fragment {
    private static final String TAG ="KINGSN" ;
    private ProgressBar progressBar;
    FragmentAppsettingsBinding binding;
    private Method method;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private HashMap<String, String> params = new HashMap<>();
    BottomSheet.Builder builder;
    int PICK_FROM_CAMERA = 1, PICK_FROM_GALLERY = 2;
    int CROP_CAMERA_IMAGE = 3, CROP_GALLERY_IMAGE = 4;
    Uri picUri;
    String encodedImage;
    String pathOfImage;
    Bitmap bm;
    ImageCompression imageCompression;
    File file;
    HomeDataDTO homeDataDTO;
    public  SharedPrefrence preferences1;


    private HashMap<String, File> paramsFile = new HashMap<>();
    HashMap<String, String> values = new HashMap<>();
    JSONObject jsonObject;
    RequestQueue rQueue;
    String encodeImageString;
    AdminDTO adminDTO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = FragmentAppsettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        preferences = this.requireActivity().getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        preferences1= SharedPrefrence.getInstance(requireActivity());

        method=new Method(requireActivity());

        adminDTO = preferences1.getParentUser2(GlobalVariables.ADMIN_DTO);

        method = new Method(requireActivity());
        getHomeData();
     // setData();

        return root;
    }

    private void setData() {

        binding.etAppName.setText(GlobalVariables.settings.getAppName());
        binding.etAppEmail.setText(GlobalVariables.settings.getAppEmail());
        binding.etAppContact.setText(GlobalVariables.settings.getAppContact());
        binding.etAppVersion.setText(GlobalVariables.settings.getAppVersion());
        binding.etAppAuthor.setText(GlobalVariables.settings.getAppAuthor());
        binding.etAppReferText.setText(GlobalVariables.settings.getReferTxt());
        binding.etHostEmail.setText(GlobalVariables.settings.getAppEmail());
        binding.etCurrencyCode.setText(GlobalVariables.settings.getRedeemCurrency());
        if(!GlobalVariables.settings.getAppLogo().equals("")){
            Glide.with(requireActivity())
                    .load(GlobalVariables.settings.getAppLogo())
                    .placeholder(R.drawable.dummyuser_image)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into((ImageView) binding.ivAppLogo);

        }else{
            Glide.with(requireActivity())
                    .load(getResources().getDrawable(R.drawable.ic_profile))
                    .placeholder(R.drawable.dummyuser_image)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into((ImageView) binding.ivAppLogo);
        }

        binding.etAppPrivacy.setText(GlobalVariables.settings.getAppDescription());

        binding.btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.show();
            }
        });



        binding.btnSaveAppSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.requireNonNull(binding.etAppName.getText()).toString().isEmpty()) {
                    binding.etAppName.setError("Field is Required");
                }
                if (Objects.requireNonNull(binding.etAppPrivacy.getText()).toString().isEmpty()) {
                    binding.etAppPrivacy.setError("Field is Required");
                }
                if (Objects.requireNonNull(binding.etAppContact.getText()).toString().isEmpty()) {
                    binding.etAppContact.setError("Field is Required");
                }
                if (Objects.requireNonNull(binding.etAppEmail.getText()).toString().isEmpty()) {
                    binding.etAppEmail.setError("Field is Required");
                }
                if (Objects.requireNonNull(binding.etAppAuthor.getText()).toString().isEmpty()) {
                    binding.etAppAuthor.setError("Field is Required");
                }
                if (Objects.requireNonNull(binding.etAppVersion.getText()).toString().isEmpty()) {
                    binding.etAppVersion.setError("Field is Required");
                }
                if (Objects.requireNonNull(binding.etAppReferText.getText()).toString().isEmpty()) {
                    binding.etAppReferText.setError("Field is Required");
                }
                if (Objects.requireNonNull(binding.etHostEmail.getText()).toString().isEmpty()) {
                    binding.etHostEmail.setError("Field is Required");
                }
                if (Objects.requireNonNull(binding.etCurrencyCode.getText()).toString().isEmpty()) {
                    binding.etCurrencyCode.setError("Field is Required");
                } else {
                    try {
                        submit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

        });

                builder = new BottomSheet.Builder(requireActivity()).sheet(R.menu.menu_cards);
                builder.title(getResources().getString(R.string.take_image));
                builder.listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.camera_cards:
                                if (Method.hasPermissionInManifest(requireActivity(), PICK_FROM_CAMERA, Manifest.permission.CAMERA)) {
                                    if (Method.hasPermissionInManifest(requireActivity(), PICK_FROM_GALLERY, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                        try {
                                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            File file = getOutputMediaFile(1);
                                            if (!file.exists()) {
                                                try {

                                                    file.createNewFile();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                //Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.asd", newFile);
                                                picUri = FileProvider.getUriForFile(requireActivity().getApplicationContext(), requireActivity().getApplicationContext().getPackageName() + ".fileprovider", file);
                                            } else {
                                                picUri = Uri.fromFile(file); // create
                                            }

//                                            preferences.setValue(Consts.IMAGE_URI_CAMERA, picUri.toString());
                                            editor.putString(GlobalVariables.IMAGE_URI_CAMERA,picUri.toString());
                                            editor.apply();
                                            intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri); // set the image file
                                            startActivityForResult(intent, PICK_FROM_CAMERA);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                break;
                            case R.id.gallery_cards:
                                if (Method.hasPermissionInManifest(requireActivity(), PICK_FROM_CAMERA, Manifest.permission.CAMERA)) {
                                    if (Method.hasPermissionInManifest(requireActivity(), PICK_FROM_GALLERY, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                        File file = getOutputMediaFile(1);
                                        if (!file.exists()) {
                                            try {
                                                file.createNewFile();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        picUri = Uri.fromFile(file);

                                        Intent intent = new Intent();
                                        intent.setType("image/*");
                                        intent.setAction(Intent.ACTION_PICK);
                                        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_img)), PICK_FROM_GALLERY);

                                    }
                                }
                                break;
                            case R.id.cancel_cards:
                                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        dialog.dismiss();
                                    }
                                });
                                break;
                        }
                    }
                });
    }

    private File getOutputMediaFile(int type) {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File mediaStorageDir = new File(root, GlobalVariables.APP_NAME);

        /**Create the storage directory if it does not exist*/
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        /**Create a media file name*/
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    GlobalVariables.APP_NAME + timeStamp + ".png");

            // imageName =  Consts.APP_NAME + timeStamp + ".png";
        } else {
            return null;
        }
        return mediaFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CROP_CAMERA_IMAGE) {
            if (data != null) {
                Log.d(TAG, "onActivityResult1: "+(data.getExtras()));
                picUri = Uri.parse(Objects.requireNonNull(data.getExtras()).getString("resultUri"));
                try {
                    //bitmap = MediaStore.Images.Media.getBitmap(SaveDetailsActivityNew.this.getContentResolver(), resultUri);
                    pathOfImage = picUri.getPath();
                    imageCompression = new ImageCompression(requireActivity());
                    imageCompression.execute(pathOfImage);
                    imageCompression.setOnTaskFinishedEvent(new ImageCompression.AsyncResponse() {
                        @Override
                        public void processFinish(String imagePath) {
                           // showImageContainer();
                            Glide.with(requireActivity()).load("file://" + imagePath)
                                    .thumbnail(0.5f)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(binding.ivAppLogo);
                            try {
                                // bitmap = MediaStore.Images.Media.getBitmap(SaveDetailsActivityNew.this.getContentResolver(), resultUri);
                                file = new File(imagePath);
                                method.paramsFile.put("image", file);
                                Log.e("image", imagePath);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == CROP_GALLERY_IMAGE) {
            if (data != null) {
                Log.d(TAG, "onActivityResult2: "+(data.getExtras()));
                picUri = Uri.parse(Objects.requireNonNull(data.getExtras()).getString("resultUri"));

                try {
                    bm = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), picUri);
                    pathOfImage = picUri.getPath();
                    imageCompression = new ImageCompression(requireActivity());
                    imageCompression.execute(pathOfImage);
                    imageCompression.setOnTaskFinishedEvent(new ImageCompression.AsyncResponse() {
                        @Override
                        public void processFinish(String imagePath) {
                          //  showImageContainer();
                            Glide.with(requireActivity()).load("file://" + imagePath)
                                    .thumbnail(0.5f)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(binding.ivAppLogo);
                            Log.e("image", imagePath);
                            try {
                                file = new File(imagePath);
                                method.paramsFile.put("image", file);

                                Log.e("image", imagePath);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == PICK_FROM_CAMERA && resultCode == RESULT_OK) {
            if (picUri != null) {
                //  Log.d(TAG, "onActivityResult3: "+(data.getExtras()));
                picUri = Uri.parse(preferences.getString(GlobalVariables.IMAGE_URI_CAMERA,""));
                startCropping(picUri, CROP_CAMERA_IMAGE);
            } else {
                picUri = Uri.parse(preferences
                        .getString(GlobalVariables.IMAGE_URI_CAMERA,""));
                startCropping(picUri, CROP_CAMERA_IMAGE);
            }
        }
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            try {
                // Log.d(TAG, "onActivityResult4: "+(data.getExtras()));
                Uri tempUri = Objects.requireNonNull(data).getData();
                Log.e("front tempUri", "" + tempUri);
                if (tempUri != null) {
                    startCropping(tempUri, CROP_GALLERY_IMAGE);
                } else {

                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public void startCropping(Uri uri, int requestCode) {
        Intent intent = new Intent(requireActivity(), MainFragment.class);
        intent.putExtra("imageUri", uri.toString());
        intent.putExtra("requestCode", requestCode);
        startActivityForResult(intent, requestCode);
    }



    public void submit() throws JSONException {
        doComment(bm);
    }

    public void doComment(Bitmap bm) throws JSONException {

        if (file != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            try {
                 jsonObject = new JSONObject();
                String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());
                jsonObject.put("updateType", "1");
                jsonObject.put("imagename", "applogo");
                jsonObject.put("image", encodedImage);
                //  Log.e("Image name", etxtUpload.getText().toString().trim());
                jsonObject.put("app_name", Objects.requireNonNull(binding.etAppName.getText()).toString());
                jsonObject.put("app_version", Objects.requireNonNull(binding.etAppVersion.getText()).toString());
                jsonObject.put("app_author", Objects.requireNonNull(binding.etAppAuthor.getText()).toString());
                jsonObject.put("app_email", Objects.requireNonNull(binding.etAppEmail.getText()).toString());
                jsonObject.put("app_contact", Objects.requireNonNull(binding.etAppContact.getText()).toString());
                jsonObject.put("app_description", Objects.requireNonNull(binding.etAppPrivacy.getText()).toString());
                jsonObject.put("redeem_currency", Objects.requireNonNull(binding.etCurrencyCode.getText()).toString());
                jsonObject.put("refer_txt", Objects.requireNonNull(binding.etAppReferText.getText()).toString());
                jsonObject.put("adminUserId", GlobalVariables.USER_MOBILE);
                // jsonObject.put("aa", "aa");


            } catch (JSONException e) {
                // Log.e(TAG,"JSONObject Here"+ e.toString());
            }


        }
        if (file == null){
            try {
                jsonObject = new JSONObject();
                jsonObject.put("updateType", "appSetting_update");
                //  Log.e("Image name", etxtUpload.getText().toString().trim());
                jsonObject.put("app_name", Objects.requireNonNull(binding.etAppName.getText()).toString());
                jsonObject.put("app_version", Objects.requireNonNull(binding.etAppVersion.getText()).toString());
                jsonObject.put("app_author", Objects.requireNonNull(binding.etAppAuthor.getText()).toString());
                jsonObject.put("app_email", Objects.requireNonNull(binding.etAppEmail.getText()).toString());
                jsonObject.put("app_contact", Objects.requireNonNull(binding.etAppContact.getText()).toString());
                jsonObject.put("app_description", Objects.requireNonNull(binding.etAppPrivacy.getText()).toString());
                jsonObject.put("redeem_currency", Objects.requireNonNull(binding.etCurrencyCode.getText()).toString());
                jsonObject.put("refer_txt", Objects.requireNonNull(binding.etAppReferText.getText()).toString());
                jsonObject.put("adminUserId", GlobalVariables.USER_MOBILE);
                // jsonObject.put("aa", "aa");


            } catch (JSONException e) {
                // Log.e(TAG,"JSONObject Here"+ e.toString());
            }


        }

     method.loadingDialogg(requireActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, RestAPI.SETTING_UPDATE, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.e(TAG,"aaaaaaa"+ jsonObject.toString());
                        method.loadingDialog.dismiss();
                        try {


                            JSONObject jsonObject1 = jsonObject.getJSONObject(GlobalVariables.AppSid);
                            // Toast.makeText(getApplication(), jsonObject.getString("title"), Toast.LENGTH_SHORT).show();
                            String success = jsonObject1.getString("success");
                            String title = jsonObject1.getString("title");
                            String message = jsonObject1.getString("message");
                            String btntxt=GlobalVariables.btntxt;

                            if(success.equals("1")){
//                                edittextMessage.setText("");
//                                hideImageContainer();
//                                getComment();
                                file = null;
                                pathOfImage = "";
                                 Method.alertBox("1",title,message,requireActivity(),btntxt);
                            }else{
                                Method.alertBox("0",title,message,requireActivity(),btntxt);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        rQueue.getCache().clear();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                method.loadingDialog.dismiss();
                Log.e(TAG,"aaaaaaa"+ volleyError.toString());
                Method.alertBox("0","Something went wrong","We Regret For Such Inconvenience..! Please try Again ",requireActivity(),GlobalVariables.btntxt);

            }
        });

        rQueue = Volley.newRequestQueue(requireActivity());
        rQueue.add(jsonObjectRequest);
        //  Log.d(TAG, "uploadImage: "+jsonObjectRequest+jsonObjectRequest+jsonObject);

    }

    public void getHomeData(){

     //   method.loadingDialogg(requireActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_GET_HOMEDATA,
                response -> {
                    // Toast.makeText(LoginActivity.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject jsonObject1 = jsonObject.getJSONObject(GlobalVariables.AppSid);


                        String success = jsonObject1.getString("success");

                        if (success.equals("1")) {
                            JSONObject jsonArray2 = jsonObject1.getJSONObject("Results");


                            JSONArray jsonArray_homeGraph = jsonArray2.getJSONArray("homeGraph");

                            Log.d(TAG, "getHomeData: hbvh"+homeDataDTO);
                            //  globalState.setHomeData(homeDataDTO);

                            for (int i = 0; i < jsonArray_homeGraph.length(); i++) {

                                JSONObject object = jsonArray_homeGraph.getJSONObject(i);

                                String  totalActiveUser=object.getString("totalActiveUser");
                                String  totaldeActiveUser=object.getString("totaldeActiveUser");
                                String  totalBlockedUser=object.getString("totalBlockedUser");
                                String  totalUser=object.getString("totalUser");
                                String  totalD=object.getString("totalD");
                                String  totalDPending=object.getString("totalDPending");
                                String  totalDVerified=object.getString("totalDVerified");
                                String  totalDRejected=object.getString("totalDRejected");
                                String totalcID=object.getString("totalcID");
                                String  totalcIDPending=object.getString("totalcIDPending");
                                String  totalcIDVerified=object.getString("totalcIDVerified");
                                String  totalcIDRejected=object.getString("totalcIDRejected");
                                String totalw=object.getString("totalw");
                                String  totalwPending=object.getString("totalwPending");
                                String  totalwVerified=object.getString("totalwVerified");
                                String  totalwRejected=object.getString("totalwRejected");
                                String totalClose=object.getString("totalClose");
                                String  totalclosePending=object.getString("totalclosePending");
                                String  totalcloseVerified=object.getString("totalcloseVerified");
                                String  totalcloseRejected=object.getString("totalcloseRejected");
                                String  totalADeposited=object.getString("totalADeposited");
                                String  totalAWithdrawals=object.getString("totalAWithdrawals");




                                HomeGraphDTO homegrph = new HomeGraphDTO( totalActiveUser, totaldeActiveUser, totalBlockedUser,  totalUser
                                        ,totalD,totalDPending,  totalDVerified,
                                        totalDRejected,totalcID,  totalcIDPending,  totalcIDVerified,  totalcIDRejected,
                                        totalw,totalwPending,  totalwVerified,  totalwRejected,totalClose,
                                        totalclosePending,  totalcloseVerified,  totalcloseRejected,totalADeposited,totalAWithdrawals);

                                GlobalVariables.homeGraph = homegrph;

                            }

                            // prefrence.putListString("homeGraph", object);
                            JSONArray jsonArray_adminData = jsonArray2.getJSONArray("adMinProf");


                            for (int i = 0; i < jsonArray_adminData.length(); i++) {
                                JSONObject object = jsonArray_adminData.getJSONObject(i);


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
                                // String admin_login_date = object.getString("admin_login_date");
                                String admin_login_date = (Method.convertTimestampDateToTime(object.getString("admin_login_date")));
                                // String admin_joining_date = object.getString("admin_joining_date");
                                String admin_joining_date = (Method.convertTimestampDateToTime(object.getString("admin_joining_date")));


                                //constant.sharedEditor.putBoolean(constant.isLogin, true);

                                Profileuser profileuser = new Profileuser(id, admin_user_id, admin_refer_code, admin_password, admin_name, admin_email, admin_mobile, profileImg,
                                        deviceToken, admin_status,
                                        manageUserList, manageDepositList, manageCreateIDList, manageWithdrawal, managePassword, manageCloseID,
                                        manageNotification, manageAppSettings, manageAdminList, admin_login_date, admin_joining_date);

                                String appName = object.getString("app_name");
                                String appLogo = object.getString("app_logo");
                                String appDescription = object.getString("app_description");
                                String appVersion = object.getString("app_version");
                                String appAuthor = object.getString("app_author");
                                String appContact = object.getString("app_contact");
                                String appEmail = object.getString("app_email");
                                String appWebsite = object.getString("app_website");
                                String appDevelopedBy = object.getString("app_developed_by");
                                String redeemCurrency = object.getString("redeem_currency");
                                String homeBannerimg1Enabled = object.getString("bannerimg1_enabled");
                                String homeBannerimg1 = object.getString("home_bannerimg1");
                                String homeBannerimg2Enabled = object.getString("home_bannerimg2_enabled");
                                String homeBannerimg2 = object.getString("home_bannerimg2");
                                String homeBannerimg3 = object.getString("home_bannerimg3");
                                String onesignalappId = object.getString("onesignalapp_id");
                                String onesignalappKey = object.getString("onesignalapp_key");
                                String referTxt = object.getString("refer_txt");
                                String adminUpiName = object.getString("adminUpiName");
                                String upiMobileNo = object.getString("upiMobileNo");
                                String adminUpi = object.getString("adminUpi");
                                String adminPaytmName = object.getString("adminPaytmName");
                                String adminPaytmNo = object.getString("adminPaytmNo");
                                String adminGpayName = object.getString("adminGpayName");
                                String adminGpaymobileNo = object.getString("adminGpaymobileNo");
                                String adminGpay = object.getString("adminGpay");
                                String adminAccountName = object.getString("adminAccountName");
                                String adminAccountNo = object.getString("adminAccountNo");
                                String adminAccountIfsc = object.getString("adminAccountIfsc");
                                String adminAccountType = object.getString("adminAccountType");
                                String image = object.getString("image");
                                String joiningBonus = object.getString("joining_bonus");
                                String perRefer = object.getString("per_refer");
                                String minDepositcoin = object.getString("minDepositcoin");
                                String hourlyQuizCoin = object.getString("hourly_quiz_coin");
                                String mathsQuizCoin = object.getString("maths_quiz_coin");
                                String maxmMathsQuestn = object.getString("maxm_maths_questn");
                                String hourlySpinLimit = object.getString("hourly_spin_limit");
                                String hourlyMathsquizLimit = object.getString("hourly_mathsquiz_limit");
                                String mathsQuizUnlockMin = object.getString("mathsQuiz_unlockMin");
                                String perNewsCoin = object.getString("per_news_coin");
                                String minimumWidthrawal = object.getString("minimum_widthrawal");
                                String minRedeemAmount = object.getString("min_redeem_amount");
                                String telegramlink = object.getString("telegramlink");
                                String youtubeLink = object.getString("youtube_link");
                                String facebookPage = object.getString("facebook_page");
                                String newVersion = object.getString("new_version");
                                String updateLink = object.getString("update_link");
                                String adminMsg = object.getString("admin_msg");
                                String joinGroup = object.getString("join_group");
                                String appPromo1 = object.getString("app_promo1");
                                String appPromo2 = object.getString("app_promo2");
                                // String date = object.getString("date");
                                String date = (Method.convertTimestampDateToTime(object.getString("date")));
                                String payment_gateway = object.getString("payment_gateway");
                                String offline_paymentGateway = object.getString("offline_paymentGateway");
                                String paytm_mid = object.getString("paytm_mid");
                                String paytm_Key = object.getString("paytm_key");
                                String razorpay_mid = object.getString("razorpay_mid");
                                String razorpay_key = object.getString("razorpay_key");
                                String payumoney_mid = object.getString("payumoney_mid");
                                String payumoney_key = object.getString("payumoney_key");
                                String payumoney_salt = object.getString("payumoney_salt");


                                Settings settings = new Settings(appName, appLogo, appDescription, appVersion, appAuthor, appContact,
                                        appEmail, appWebsite, appDevelopedBy, redeemCurrency, homeBannerimg1Enabled, homeBannerimg1,
                                        homeBannerimg2Enabled, homeBannerimg2, homeBannerimg3, onesignalappId, onesignalappKey, referTxt,
                                        adminUpiName, upiMobileNo, adminUpi, adminPaytmName, adminPaytmNo, adminGpayName, adminGpaymobileNo,
                                        adminGpay, adminAccountName, adminAccountNo, adminAccountIfsc, adminAccountType,
                                        image, joiningBonus, perRefer, minDepositcoin, hourlyQuizCoin, mathsQuizCoin, maxmMathsQuestn,
                                        hourlySpinLimit, hourlyMathsquizLimit, mathsQuizUnlockMin, perNewsCoin, minimumWidthrawal,
                                        minRedeemAmount, telegramlink, youtubeLink, facebookPage, newVersion, updateLink, adminMsg,
                                        joinGroup, appPromo1, appPromo2, date, payment_gateway, offline_paymentGateway, paytm_mid, paytm_Key,
                                        razorpay_mid, razorpay_key, payumoney_mid, payumoney_key, payumoney_salt);

                                GlobalVariables.profileuser = profileuser;
                                GlobalVariables.settings = settings;


                            }


                            JSONArray jsonArray_hometopUser = jsonArray2.getJSONArray("topUsers");
                            for (int i = 0; i < jsonArray_hometopUser.length(); i++) {

                                JSONObject object = jsonArray_hometopUser.getJSONObject(i);
                                String id =object.getString("id");
                                String mobile =object.getString("mobile");
                                String password =object.getString("password");
                                String name =object.getString("name");
                                String city =object.getString("city");
                                String email =object.getString("email");
                                String wallet =object.getString("wallet");
                                String totalPaid =object.getString("total_paid");
                                String totalRedeemed =object.getString("total_redeemed");
                                String userReferalCode =object.getString("user_referal_code");
                                String refferedBy =object.getString("reffered_by");
                                String refferedPaid =object.getString("reffered_paid");
                                String totalReferals =object.getString("total_referals");
                                String allow =object.getString("allow");
                                String deviceId =object.getString("device_id");
                                String deviceToken =object.getString("device_token");
                                String profilePic =object.getString("profile_pic");
                                // String activeDate =object.getString("active_date");

                                String activeDate = Method.convertTimestampDateToTime(Method.correctTimestamp(object.getString("active_date")));
                                // String activeDate = Method.convertTimestampDateToTime(object.getString("active_date"));
                          /*  Log.d(TAG, "getHomeData:activeDate "+(object.getString("active_date"))+"\n"+activeDate);
                            Log.d(TAG, "getHomeData:activeDate "+Method.convertTimestampDateToTime(Method.correctTimestamp(object.getString("joining_time"))));*/


                                String onesignalPlayerid =object.getString("onesignal_playerid");
                                String onesignalPushtoken =object.getString("onesignal_pushtoken");
                                // String joiningTime =object.getString("joining_time");
                                String joiningTime=  Method.convertTimestampDateToTime(Method.correctTimestamp(object.getString("joining_time")));



                                HomeTopUserDTO homeTopUserDTO = new HomeTopUserDTO( id,  mobile,  password,  name,  city,  email,  wallet,  totalPaid,  totalRedeemed,
                                        userReferalCode,  refferedBy,  refferedPaid,  totalReferals,  allow,  deviceId,  deviceToken,  profilePic,  activeDate,
                                        onesignalPlayerid,  onesignalPushtoken,  joiningTime);
                                // editor.apply();
                           /*     homeTopUserDTOArrayList.add(homeTopUserDTO);
                                MyrecyclerView = binding.rvTopUser;
                                MyrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                MyrecyclerView.setLayoutManager(linearLayoutManager);
                                topUserDataAdapter= new TopUserDataAdapter(homeTopUserDTOArrayList,getContext());
                                MyrecyclerView.setAdapter(topUserDataAdapter);*/


                            }
                            JSONArray jsonArray_hometopDeposit = jsonArray2.getJSONArray("topDeposit");
                            for (int i = 0; i < jsonArray_hometopDeposit.length(); i++) {

                                JSONObject object2 = jsonArray_hometopDeposit.getJSONObject(i);
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
                                String activeDate = (Method.convertTimestampDateToTime(object2.getString("active_date")));

                                String onesignalPlayerid =object2.getString("onesignal_playerid");
                                String onesignalPushtoken =object2.getString("onesignal_pushtoken");
                                // String joiningTime =object2.getString("joining_time");
                                String joiningTime = (Method.convertTimestampDateToTime(object2.getString("joining_time")));

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
                                // String txnDate =object2.getString("txnDate"); Method.convertTimestampDateToTime(method.correctTimestamp(
                                String txnDate = (Method.convertTimestampDateToTime(Method.correctTimestamp(object2.getString("txnDate"))));
                                // String cancelledDate =object2.getString("cancelledDate");
                                String cancelledDate = (Method.convertTimestampDateToTime(Method.correctTimestamp(object2.getString("cancelledDate"))));
                                // String verifiedDate =object2.getString("verifiedDate");
                                String verifiedDate = (Method.convertTimestampDateToTime(Method.correctTimestamp(object2.getString("verifiedDate"))));
                                String img =object2.getString("img");
                                String idWebsite =object2.getString("idWebsite");
                                String idName =object2.getString("idName");
                                String wDetails =object2.getString("wDetails");

                                HomeTopDepositsDTO homeTopDeposits = new HomeTopDepositsDTO( id,  mobile,  password,  name,  city,  email,  wallet,
                                        totalPaid,  totalRedeemed,  userReferalCode,  refferedBy,  refferedPaid,  totalReferals,  allow,  deviceId,
                                        deviceToken,  profilePic,  activeDate,  onesignalPlayerid,  onesignalPushtoken,  joiningTime,  userMobile,
                                        txnType,  orderId,  amount,  createdId,  idcreated,  payMentMode,  payMode,  txnStatus,  idUsername,
                                        idPassword,  adminComment,  txnDate,  cancelledDate,  verifiedDate,img,idWebsite,idName,wDetails);

                           /*     homeTopDepositsDTOArrayList.add(homeTopDeposits);
                                recyclerView2 = binding.rvTopDeposit;
                                recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                recyclerView2.setLayoutManager(linearLayoutManager);
                                topDepositDataAdapter= new TopDepositDataAdapter(homeTopDepositsDTOArrayList,getContext());
                                recyclerView2.setAdapter(topDepositDataAdapter);*/



                            }
                            setData();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // method.loadingDialog.dismiss();
                                    // setData();
                                }
                            }, GlobalVariables.dismissAfter);
                        }else{
                            method.loadingDialog.dismiss();

                            Method.alertBox("2","Something Went Wrong","Close & Restart", requireActivity(),"Go to Home");

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
                params.put("adminUserId", "super_admin");
                //  params.put("mobile",preferences.getString(GlobalVariables.profileuser.getAdmin_mobile(),"") );
                params.put(GlobalVariables.adminUserId, preferences1.getValue(GlobalVariables.adminUserId));
                params.put("device_id",method.getDeviceId(requireActivity()));
                Log.d(TAG, "getParams: "+params);
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);
        Log.d(TAG, "getHomeData: "+stringRequest);

    }

}

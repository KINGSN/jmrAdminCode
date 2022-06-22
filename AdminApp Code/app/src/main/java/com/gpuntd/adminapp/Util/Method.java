package com.gpuntd.adminapp.Util;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CLIPBOARD_SERVICE;
import static com.gpuntd.adminapp.MainActivity.navController;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.android.volley.AuthFailureError;
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
import com.facebook.ads.Ad;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

import com.google.gson.Gson;
import com.gpuntd.adminapp.BuildConfig;
import com.gpuntd.adminapp.HelpActivity;
import com.gpuntd.adminapp.Interface.Helper;
import com.gpuntd.adminapp.LoginActivity;
import com.gpuntd.adminapp.MainActivity;
import com.gpuntd.adminapp.Models.AdminDTO;
import com.gpuntd.adminapp.Models.HomeTopDepositsDTO;
import com.gpuntd.adminapp.Models.Settings;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.adapter.TopDepositDataAdapter;
import com.gpuntd.adminapp.databinding.DialogFilterBinding;
import com.gpuntd.adminapp.https.HttpsRequest;
import com.gpuntd.adminapp.preferences.SharedPrefrence;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;


public class Method {

    public static final String WEBSITE = "DEFAULT";
    public static final String CHANNEL_ID = "WAP";
    public static final String INDUSTRY_TYPE_ID = "Retail";
    public static final String CALLBACK_URL = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
    private static final String TAG = "KINGSN";
    public static boolean share = false, loginBack = false, allowPermitionExternalStorage = false, personalization_ad = false;
    public static AlertDialog alertDialog;
    public static Dialog dialog;
    private static Method method;
    private final Context _context;
    public com.facebook.ads.InterstitialAd interstitialAd;
    public InterstitialAd mInterstitialAd;
    public Dialog loadingDialog;
    com.facebook.ads.AdView adViewfb;
    String verificationcodebysystem;
    String codeBySystem, mobileNumber;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    String checksum;
    String orderId;
    String mobile, minRefill;
    // User Loging logs added
    private AdView adView;
    private Ad fbadView;
    private Activity activity;
    private DBHelper dbHelper;
    private String countryCode;
    private FirebaseAuth auth, mAuth;
    public HashMap<String, String> params = new HashMap<>();
    public JSONObject jsonObject;
    RequestQueue rQueue;
    public HashMap<String, File> paramsFile = new HashMap<>();
    public BottomSheet.Builder builder;
    public int PICK_FROM_CAMERA = 1, PICK_FROM_GALLERY = 2;
    public int CROP_CAMERA_IMAGE = 3, CROP_GALLERY_IMAGE = 4;
    public Uri picUri;
    public String encodedImage;
    public String pathOfImage;
    public static Bitmap bm;
    public ImageCompression imageCompression;
    public static File file, file2, file3, file4;
   public TopDepositDataAdapter topDepositDataAdapter;

    private Dialog dialogFilterJob;
    DialogFilterBinding dailogFilterJobBinding;
    public SharedPrefrence preferences;
    public AdminDTO adminDTO,usermdto;
    public Settings settingsDTO;
    ArrayList<HomeTopDepositsDTO> topDepositsDTOS;
    private ArrayList<HomeTopDepositsDTO> userBookingsList;


    public String categoryValue ="";

    public Method(Context context) {
        this._context = context;
        preferences = SharedPrefrence.getInstance(context);
        adminDTO = preferences.getParentUser2(GlobalVariables.ADMIN_DTO);
        //settingsDTO=preferences.getSettings(GlobalVariables.SettingsDto);
    }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    long now = System.currentTimeMillis();



    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable();
        }
        return false;
    }

    //---------------Interstitial Ad---------------//

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void onSuccessPay(Activity activity, String refillAmount, String orderId, String createId, String TxnType, String paymentMode, String payMode) {
        Method method = new Method(activity);
        method.loadingDialogg(activity);
        if (orderId.equals("")) {
            Date dNow = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat ft = new SimpleDateFormat("dMYYhmms");
            String datetime = ft.format(dNow);

            orderId = "Order" + (datetime);
        }

        String finalOrderId = orderId;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_insert_payment_verification,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        method.loadingDialog.dismiss();
                        //  Toast.makeText(activity, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
                        try {
                            System.out.println(response);
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray(GlobalVariables.AppSid);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String success = object.getString("success");

                                if (success.equals("1")) {
                                    //   Toast.makeText(activity, "Updated", Toast.LENGTH_SHORT).show();
                                    // finish();
                                    // startActivity(getIntent());
                                    // method.loadingDialog.dismiss();
                                    // loadingDialog.dismiss();

                                  /*  if((TxnType.equals("0"))||(payMode.equals("5"))){
                                        showPay_Status_AlertDialog(activity,2,refillAmount,createId,TxnType,finalOrderId);
                                    }else if(payMode.equals("7")){
                                        showPay_Status_AlertDialog(activity,3,refillAmount,createId,TxnType,finalOrderId);
                                    }else if(payMode.equals("6")){
                                        showPay_Status_AlertDialog(activity,4,refillAmount,createId,TxnType,finalOrderId);
                                    }else{
                                        showPay_Status_AlertDialog(activity,1,refillAmount,createId,TxnType,finalOrderId);
                                    }
*/


                                } else {
                                    method.loadingDialog.dismiss();
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
                                    alertDialogBuilder.setTitle(object.getString("title"));
                                    alertDialogBuilder.setMessage(object.getString("msg"));
                                    alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                                    alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.ok_message),
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    activity.finish();
                                                    method.loadingDialog.dismiss();
                                                    activity.finishAffinity();
                                                }
                                            });

                                    android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                    //  Toast.makeText(Payment.this, object.getString("msg"), Toast.LENGTH_LONG).show();
                                }

                            }

                            // progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(Payment.this, "RESPONSE: " + error, Toast.LENGTH_SHORT).show();
                Log.e("Error", "" + error.getMessage());
                //ifvolleyfail();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                switch (TxnType) {
                    case "0":

                        params.put("payment_type", "idCreation");
                        params.put("txnType", "0");
                        params.put("createdId", createId);
                        params.put("Idcreated", createId);
                        params.put("idUsername", GlobalVariables.idUsername);
                        params.put("idPassword", GlobalVariables.idPassword);


                        break;
                    case "1":

                        params.put("payment_type", "id_deposit");
                        params.put("txnType", "1");
                        params.put("createdId", createId);
                        params.put("Idcreated", "");
                        params.put("idUsername", GlobalVariables.idUsername);
                        params.put("idPassword", GlobalVariables.idPassword);
                        break;
                    case "2":

                        params.put("payment_type", "Wallet_deposit");
                        params.put("txnType", "2");
                        params.put("createdId", createId);
                        params.put("Idcreated", "");
                        params.put("idUsername", "");
                        params.put("idPassword", "");
                        break;
                    case "3":

                        params.put("payment_type", "Change ID Password");
                        params.put("txnType", "3");
                        params.put("createdId", createId);
                        params.put("Idcreated", "");
                        params.put("idUsername", "");
                        params.put("idPassword", "");
                        break;

                    case "4":

                        params.put("payment_type", "Refer Bonus");
                        params.put("txnType", "4");
                        params.put("createdId", createId);
                        params.put("Idcreated", "");
                        params.put("idUsername", "");
                        params.put("idPassword", "");
                        break;

                    case "5":

                        params.put("payment_type", "Withdrawal From ID");
                        params.put("txnType", "5");
                        params.put("txnStatus", "0");
                        params.put("createdId", createId);
                        params.put("Idcreated", "");
                        params.put("payMode", payMode);
                        params.put("idUsername", GlobalVariables.idUsername);
                        params.put("idPassword", GlobalVariables.idPassword);
                        break;

                    case "6":

                        params.put("payment_type", "Withdrawal From Wallet");
                        params.put("txnType", "6");
                        params.put("txnStatus", "0");
                        params.put("createdId", createId);
                        params.put("Idcreated", "");
                        params.put("payMode", payMode);
                        params.put("idUsername", "");
                        params.put("idPassword", "");
                        break;

                    case "7":

                        params.put("payment_type", "Close ID");
                        params.put("txnType", "7");
                        params.put("createdId", createId);
                        params.put("Idcreated", "");
                        params.put("payMode", payMode);
                        params.put("idUsername", "");
                        params.put("idPassword", "");
                        break;
                }
                if (paymentMode.equals("0")) {
                    params.put("payMentMode", "0");
                } else {
                    params.put("payMentMode", paymentMode);
                }

//                params.put("mobile", GlobalVariables.profileuser.getMobile());
//                params.put("name", GlobalVariables.profileuser.getName());
//                params.put("email", GlobalVariables.profileuser.getEmail());
//                params.put("amount", refillAmount);
//                params.put("order_id", finalOrderId);
//                params.put("city", GlobalVariables.profileuser.getCity());
                Log.d(TAG, "getParams: " + params);
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);


    }


    public static void alertBox(String type, String title, String message, Activity activity, String btntxt) {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.alert_dialog, viewGroup, false);
        builder.setView(dialogView);
        builder.setCancelable(false);
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        ImageView iconImage = dialogView.findViewById(R.id.iconImage);
        TextView alertTitle = dialogView.findViewById(R.id.alertTitle);
        TextView alertMsg = dialogView.findViewById(R.id.alertMsg);

        alertTitle.setText(title);
        alertMsg.setText(message);
        if (type.equals("1")) {
            iconImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_check));
            iconImage.setBackground(activity.getResources().getDrawable(R.drawable.round_icon_bg));
        } else if (type.equals("update")) {
            iconImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_update));
            iconImage.setBackground(activity.getResources().getDrawable(R.drawable.round_icon_bg));
        } else if (type.equals("blocked")) {
            iconImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_baseline));
            iconImage.setBackground(activity.getResources().getDrawable(R.drawable.round_icon_bg));
        } else {

            iconImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_baseline));
            iconImage.setBackground(activity.getResources().getDrawable(R.drawable.round_icon_bg));

        }


        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

        Button alertBtn = dialogView.findViewById(R.id.alertBtn);
        alertBtn.setText(btntxt);
        alertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "alertBox: ok clicked");
                //alertDialog.dismiss();
                if (btntxt.equals(GlobalVariables.btntxt)) {
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    alertDialog.dismiss();
                } else if (btntxt.equals(GlobalVariables.UpdateKyc)) {
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    alertDialog.dismiss();
                }else if (btntxt.equals(GlobalVariables.selectUser)) {
                   /* Intent intent = new Intent(activity, MainActivity.class);

                    activity.startActivity(intent);
                    alertDialog.dismiss();*/
                    alertDialog.dismiss();
                    navController.navigate(R.id.navigation_users);
                } else if (btntxt.equals(GlobalVariables.update)) {
                   /* Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);*/

                    Uri rate = Uri.parse(GlobalVariables.settings.getUpdateLink());
                    Intent intent = new Intent(Intent.ACTION_VIEW, rate);
                    activity.startActivity(intent);
                    if (Float.parseFloat(GlobalVariables.settings.getNewVersion()) == BuildConfig.VERSION_CODE) {
                        alertDialog.dismiss();
                    }


                } else if (btntxt.equals(GlobalVariables.blocked)) {
                   /* Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);*/

                    Uri rate = Uri.parse(GlobalVariables.settings.getUpdateLink());
                    Intent intent = new Intent(activity, HelpActivity.class);
                    intent.putExtra("blocked", "blocked");
                    activity.startActivity(intent);


                } else {
                    alertDialog.dismiss();
                }
            }
        });

    }

    public static void commonAlert(String type, String title, String message, Activity activity) {
       /* AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View root = LayoutInflater.from(activity).inflate(R.layout.common_dialog, activity.findViewById(R.id.commonDialog));
        builder.setView(root);
         alertDialog = builder.create();
        Button btnRequest = root.findViewById(R.id.btnRequestConfirm);
       builder.setCancelable(true);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "alertBox: ok clicked");
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();*/


    }

    public static boolean hasPermissionInManifest(Activity activity, int requestCode, String permissionName) {
        if (ContextCompat.checkSelfPermission(activity,
                permissionName)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(activity,
                    new String[]{permissionName},
                    requestCode);
        } else {
            return true;
        }
        return false;
    }


    public static void openSupportMail(Context context) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", GlobalVariables.settings.getAppEmail(), null));
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public static void openShareIntent(Context context, @Nullable View itemview, String shareText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (itemview != null) {
            try {
                Uri imageUri = getImageUri(context, itemview, "postBitmap.jpeg");
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } catch (IOException e) {
                intent.setType("text/plain");
                e.printStackTrace();
            }
        } else {
            intent.setType("text/plain");
        }
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        context.startActivity(Intent.createChooser(intent, "Share Via:"));
    }

    private static Uri getImageUri(Context context, View view, String fileName) throws IOException {
        Bitmap bitmap = loadBitmapFromView(view);
        File pictureFile = new File(context.getExternalCacheDir(), fileName);
        FileOutputStream fos = new FileOutputStream(pictureFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
        fos.close();
        return Uri.parse("file://" + pictureFile.getAbsolutePath());
    }

    private static Bitmap loadBitmapFromView(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            v.setDrawingCacheEnabled(true);
            return v.getDrawingCache();
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    public void loadInter(Activity activity) {
        //mInterstitial = new InterstitialAd(activity);

        // Constant.AD_COUNT = Constant.AD_COUNT + 1;

        //Constant.AD_COUNT = 0;
        AdRequest adRequest;

        if (ConsentInformation.getInstance(activity).getConsentStatus() == ConsentStatus.PERSONALIZED) {
            adRequest = new AdRequest.Builder().build();
        } else {
            Bundle extras = new Bundle();
            extras.putString("npa", "1");
            adRequest = new AdRequest.Builder()
                    .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                    .build();
        }
        InterstitialAd.load(activity, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {

                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }


                });


    }

    public void loadingDialogg(Activity activity) {
        loadingDialog = new Dialog(activity);
        loadingDialog.setContentView(R.layout.lotiee_loading);
        loadingDialog.setCancelable(false);
        Objects.requireNonNull(loadingDialog.getWindow()).setBackgroundDrawableResource(
                R.color.transparent);
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        } else {
            loadingDialog = new Dialog(activity);
            loadingDialog.setContentView(R.layout.lotiee_loading);
            loadingDialog.setCancelable(false);
            Objects.requireNonNull(loadingDialog.getWindow()).setBackgroundDrawableResource(
                    R.color.transparent);
            loadingDialog.show();
        }
    }

    public void loadingDialogg2(Context activity) {
        loadingDialog = new Dialog(activity);
        loadingDialog.setContentView(R.layout.lotiee_loading);
        loadingDialog.setCancelable(false);
        Objects.requireNonNull(loadingDialog.getWindow()).setBackgroundDrawableResource(
                R.color.transparent);
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        } else {
            loadingDialog = new Dialog(activity);
            loadingDialog.setContentView(R.layout.lotiee_loading);
            loadingDialog.setCancelable(false);
            Objects.requireNonNull(loadingDialog.getWindow()).setBackgroundDrawableResource(
                    R.color.transparent);
            loadingDialog.show();
        }
    }

    public void loadingDialoggDismiss(Activity activity) {
        loadingDialog.dismiss();

    }

    public void showToasty(Activity activity, String Type, String Message) {
        if (Type.equals("1")) {
            Toasty.success(activity, "" + Message, Toast.LENGTH_SHORT).show();
        } else {
            Toasty.error(activity, "" + Message, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("HardwareIds")
    public String getDeviceId(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            deviceId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            // Toast.makeText(context,  deviceId, Toast.LENGTH_SHORT).show();

        } else {

            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);


            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                //  Toast.makeText(LoginActivity.this,  deviceId, Toast.LENGTH_SHORT).show();
            }
        }

        return deviceId;

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void GenerateChecksum(Activity activity, String refillAmount, String createId, String TxnType) {
        Random r = new Random(System.currentTimeMillis());
        orderId = "ORDER" + (1 + r.nextInt(2)) * 10000
                + r.nextInt(10000);

        Date dNow = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat ft = new SimpleDateFormat("dMYYhmms");
        String datetime = ft.format(dNow);

        orderId = "Order" + (datetime);
        System.out.println(orderId);

      /*  $date=date("dd-mm-y | h:i:s a");
        $orderId ="Order".(date("dmyhis"));
*/
        Log.d(TAG, "orderId: " + orderId);

        String url = RestAPI.API_Paytm_Url;
        // String url="https://darwinbark.com/paytmsdk/generateChecksum.php";
        // String url="https://darwinbark.com/projects/gopunt/paytmsdk/generateChecksum.php";


        Map<String, String> params = new HashMap<>();
        params.put("MID", GlobalVariables.Paytm_mid);
        params.put("ORDER_ID", orderId);
//        params.put("CUST_ID", GlobalVariables.profileuser.getAdmin_mobile());
//        params.put("MOBILE_NO", GlobalVariables.profileuser.getMobile());
//        params.put("EMAIL", GlobalVariables.profileuser.getEmail());
        params.put("CHANNEL_ID", "WAP");
        params.put("TXN_AMOUNT", refillAmount);
        params.put("WEBSITE", WEBSITE);
        params.put("INDUSTRY_TYPE_ID", INDUSTRY_TYPE_ID);
        params.put("CALLBACK_URL", CALLBACK_URL);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Toast.makeText(Wallet.this,response,Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has("CHECKSUMHASH")) {
                                checksum = jsonObject.getString("CHECKSUMHASH");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(activity,"Error...!",Toast.LENGTH_SHORT).show();
                Toasty.error(activity, "Error...!" + error, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("MID", GlobalVariables.Paytm_mid);
                params.put("ORDER_ID", orderId);
//                params.put("CUST_ID", GlobalVariables.profileuser.getMobile());
//                params.put("MOBILE_NO", GlobalVariables.profileuser.getMobile());
//                params.put("EMAIL", GlobalVariables.profileuser.getEmail());
                params.put("CHANNEL_ID", "WAP");
                params.put("TXN_AMOUNT", refillAmount);
                params.put("WEBSITE", WEBSITE);
                params.put("INDUSTRY_TYPE_ID", INDUSTRY_TYPE_ID);
                params.put("CALLBACK_URL", CALLBACK_URL);

                return params;
            }
        };


        queue.add(stringRequest);
// Access the RequestQueue through your singleton class.
        //MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }


    public void ifvolleyfail() {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("Something Went Wrong");
        alertDialogBuilder.setMessage("Please Try With Active Internet ");
        alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
        alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.ok_message),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        activity.finish();
                        //  method.loadingDialog.dismiss();
                        activity.finishAffinity();
                    }
                });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void copyToclipboard(Activity activity, TextView txt) {
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", txt.getText());
        assert clipboard != null;
        clipboard.setPrimaryClip(clip);
        Toasty.success(activity, "Copied text", Toast.LENGTH_SHORT).show();
    }

    public void copyToclipboard1(Context activity, TextView txt) {
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", txt.getText());
        assert clipboard != null;
        clipboard.setPrimaryClip(clip);
        Toasty.success(activity, "Copied text", Toast.LENGTH_SHORT).show();
    }

    public void shareApp(Activity activity) {
        String shareBody = GlobalVariables.settings.getNewVersion();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "I have earned cash using " +
                GlobalVariables.settings.getAppName() + " app.you can also earn by downloading app from below link " +
                "and enter referral code while login-" + GlobalVariables.profileuser.getAdmin_refer_code() + " \n" + shareBody);
        activity.startActivity(Intent.createChooser(sharingIntent, "Invite Friend Using"));
    }

    public void open(Activity activity, String url) {
        Uri uri = Uri.parse("googlechrome://navigate?url=" + url);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        if (i.resolveActivity(activity.getPackageManager()) == null) {
            i.setData(Uri.parse(url));
        }
        activity.startActivity(i);
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertTimestampDateToTime(String timestamp) {
        if (!timestamp.equals("")) {
            Timestamp tStamp = new Timestamp(Long.parseLong(timestamp));
            SimpleDateFormat simpleDateFormat;
            simpleDateFormat = new SimpleDateFormat("dd MMM yyyy | hh:mm a");
            return simpleDateFormat.format(tStamp);
        } else {
            return "";
        }
    }


    public static String correctTimestamp(String timestampInMessage) {
        if (!timestampInMessage.equals("")) {
            long correctedTimestamp = Long.parseLong(timestampInMessage);

            if (String.valueOf(timestampInMessage).length() < 13) {

                int difference = 13 - String.valueOf(timestampInMessage).length(), i;
                String differenceValue = "1";
                for (i = 0; i < difference; i++) {
                    differenceValue += "0";
                }
                correctedTimestamp = (Long.parseLong(timestampInMessage) * Integer.parseInt(differenceValue))
                        + (System.currentTimeMillis() % (Integer.parseInt(differenceValue)));
            }
            return String.valueOf(correctedTimestamp);
        } else {
            return "";
        }


    }

    public static String getEditTextValue(EditText text) {
        return text.getText().toString().trim();
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertTimestampDate(long timestamp) {
        Timestamp tStamp = new Timestamp(timestamp);
        SimpleDateFormat simpleDateFormat;
       // simpleDateFormat = new SimpleDateFormat("MMM dd");
        simpleDateFormat = new SimpleDateFormat("dd MMM yy");
        return simpleDateFormat.format(tStamp);
    }

    public static String convertDateToTimestamp(String  timestamp) throws ParseException {
        String str_date = "13-09-2011";
        @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = (Date) formatter.parse(timestamp);
        System.out.println("Today is " + date.getTime());

        long output=date.getTime()/1000L;
        String str=Long.toString(output);
        long timestamp1 = Long.parseLong(str) ;

        return String.valueOf(timestamp1);
    }
    public void updateSetting(Activity activity, String restAPI) {

        loadingDialogg(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, restAPI,
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(LoginActivity.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
                        try {
                            System.out.println(response);
                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject jsonObject1 = jsonObject.getJSONObject(GlobalVariables.AppSid);

                            String success = jsonObject1.getString("success");
                            String title = jsonObject1.getString("title");
                            String message = jsonObject1.getString("message");


                            if (success.equals("1")) {
                                //Toast.makeText(LoginActivity.this, Constant.userPhone, Toast.LENGTH_LONG).show();
                                // LoadSettings();
                                loadingDialog.dismiss();
                                if (!params.containsKey("deviceIdUpdate")) {
                                    Method.alertBox("1", title, message, activity, "Go to Home");
                                }


                            } else {
                                // mobileNumber=binding.countryCodePicker.getSelectedCountryCode()+binding.phone.getText().toString();

                                loadingDialog.dismiss();

                                Method.alertBox("0", title, message, activity, "Go to Home");
                            }


                            // progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(),
                            // Toast.LENGTH_SHORT).show();
                        }

                    }

                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // method.loadingDialog.dismiss();
                //  method.loadingDialog.dismiss();
                Log.e("Error", "" + error.getMessage());
                Ex.AlertBox("Internet Connection Not Available", activity);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {


                return params;
            }
        };
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
        Log.d(TAG, "login:stringrequest " + stringRequest + params);


    }

    public void getGalleryImage(Activity activity) {
        // Log.d(TAG, "getGalleryImage: ");
        builder = new BottomSheet.Builder(activity).sheet(R.menu.menu_cards);
        Log.d(TAG, "getGalleryImage: ");
        builder.title("Select Img");
        builder.listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.camera_cards:
                        if (Method.hasPermissionInManifest(activity, PICK_FROM_CAMERA, Manifest.permission.CAMERA)) {
                            if (Method.hasPermissionInManifest(activity, PICK_FROM_GALLERY, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
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
                                        picUri = FileProvider.getUriForFile(activity.getApplicationContext(), activity.getApplicationContext().getPackageName() + ".fileprovider", file);
                                    } else {
                                        picUri = Uri.fromFile(file); // create
                                    }

                                    /*editor.putString(GlobalVariables.IMAGE_URI_CAMERA,picUri.toString());
                                    editor.apply();*/
                                    GlobalVariables.IMAGE_URI_CAMERA = picUri.toString();
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri); // set the image file
                                    activity.startActivityForResult(intent, PICK_FROM_CAMERA);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        break;
                    case R.id.gallery_cards:
                        if (Method.hasPermissionInManifest(activity, PICK_FROM_CAMERA, Manifest.permission.CAMERA)) {
                            if (Method.hasPermissionInManifest(activity, PICK_FROM_GALLERY, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

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
                                activity.startActivityForResult(Intent.createChooser(intent, activity.getResources().getString(R.string.select_img)), PICK_FROM_GALLERY);

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


        // show it
        builder.show();
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


    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, CircleImageView imageView, ImageView imageView2) {


        if (requestCode == CROP_CAMERA_IMAGE) {
            if (data != null) {
                Log.d(TAG, "onActivityResult1: " + (data.getExtras()));
                picUri = Uri.parse(Objects.requireNonNull(data.getExtras()).getString("resultUri"));
                try {
                    //bitmap = MediaStore.Images.Media.getBitmap(SaveDetailsActivityNew.this.getContentResolver(), resultUri);
                    pathOfImage = picUri.getPath();
                    imageCompression = new ImageCompression(activity);
                    imageCompression.execute(pathOfImage);
                    imageCompression.setOnTaskFinishedEvent(new ImageCompression.AsyncResponse() {
                        @Override
                        public void processFinish(String imagePath) {
                            //showImageContainer();
                            if (imageView != null) {
                                Glide.with(activity).load("file://" + imagePath)
                                        .thumbnail(0.5f)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(imageView);
                            } else {
                                Glide.with(activity).load("file://" + imagePath)
                                        .thumbnail(0.5f)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(imageView2);
                            }

                            try {
                                // bitmap = MediaStore.Images.Media.getBitmap(SaveDetailsActivityNew.this.getContentResolver(), resultUri);
                                file = new File(imagePath);
                                paramsFile.put(GlobalVariables.IMAGE, file);
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
                Log.d(TAG, "onActivityResult2: " + (data.getExtras()));
                picUri = Uri.parse(Objects.requireNonNull(data.getExtras()).getString("resultUri"));

                try {
                    bm = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), picUri);
                    pathOfImage = picUri.getPath();
                    imageCompression = new ImageCompression(activity);
                    imageCompression.execute(pathOfImage);
                    imageCompression.setOnTaskFinishedEvent(new ImageCompression.AsyncResponse() {
                        @Override
                        public void processFinish(String imagePath) {
                            //showImageContainer();

                            if (imageView != null) {
                                Glide.with(activity).load("file://" + imagePath)
                                        .thumbnail(0.5f)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(imageView);
                            } else {
                                Glide.with(activity).load("file://" + imagePath)
                                        .thumbnail(0.5f)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(imageView2);
                            }

                            Log.e("image", imagePath);
                            try {
                                file = new File(imagePath);
                                paramsFile.put(GlobalVariables.IMAGE, file);

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
                picUri = Uri.parse(GlobalVariables.IMAGE_URI_CAMERA);
                startCropping(activity, picUri, CROP_CAMERA_IMAGE);
            } else {
                picUri = Uri.parse((GlobalVariables.IMAGE_URI_CAMERA));
                startCropping(activity, picUri, CROP_CAMERA_IMAGE);
            }
        }
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            try {
                // Log.d(TAG, "onActivityResult4: "+(data.getExtras()));
                Uri tempUri = Objects.requireNonNull(data).getData();
                Log.e("front tempUri", "" + tempUri);
                if (tempUri != null) {
                    startCropping(activity, tempUri, CROP_GALLERY_IMAGE);
                } else {

                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public void startCropping(Activity activity, Uri uri, int requestCode) {
        Intent intent = new Intent(activity, MainFragment.class);
        intent.putExtra("imageUri", uri.toString());
        intent.putExtra("requestCode", requestCode);
        activity.startActivityForResult(intent, requestCode);
    }

    public void updateWithImg(Activity activity, String restAPI) throws JSONException {

        loadingDialogg(activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, restAPI, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.e(TAG, "aaaaaaa" + jsonObject.toString());
                        loadingDialog.dismiss();
                        try {


                            JSONObject jsonObject1 = jsonObject.getJSONObject(GlobalVariables.AppSid);
                            // Toast.makeText(getApplication(), jsonObject.getString("title"), Toast.LENGTH_SHORT).show();
                            String success = jsonObject1.getString("success");
                            String title = jsonObject1.getString("title");
                            String message = jsonObject1.getString("message");
                            String btntxt = GlobalVariables.btntxt;

                            if (success.equals("1")) {
//                                edittextMessage.setText("");
//                                hideImageContainer();
//                                getComment();
                                file = null;
                                pathOfImage = "";
                                Method.alertBox("1", title, message, activity, btntxt);
                            } else {
                                Method.alertBox("0", title, message, activity, btntxt);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        rQueue.getCache().clear();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // method.loadingDialog.dismiss();
                Log.e(TAG, "aaaaaaa" + volleyError.toString());
                Method.alertBox("0", "Something went wrong", "We Regret For Such Inconvenience..! Please try Again ", activity, GlobalVariables.btntxt);

            }
        });

        rQueue = Volley.newRequestQueue(activity);
        rQueue.add(jsonObjectRequest);
        Log.d(TAG, "uploadImage: " + jsonObjectRequest + jsonObjectRequest + jsonObject);

    }

    public void setupLogoutDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_logout, activity.findViewById(R.id.logoutView));
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnLogoutDialog).setOnClickListener(view1 -> {
            alertDialog.dismiss();
            Toast.makeText(activity, "Logging out!", Toast.LENGTH_SHORT).show();
            preferences.clearAllPreferences();
            Intent intent = new Intent(activity, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity .startActivity(intent);
            activity.finish();

          /*  activity.startActivity(new Intent(activity, LoginActivity.class));
            activity.finish();*/

        });
        view.findViewById(R.id.btnCancelDialog).setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }


    @SuppressLint("SimpleDateFormat")
    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }


        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "Now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
           // return String.valueOf(new SimpleDateFormat("hh:mm a"));
          //  return "an hour ago";
            SimpleDateFormat simpleDateFormat;
            simpleDateFormat = new SimpleDateFormat("hh:mm a");
            return simpleDateFormat.format(time);
        } else if (diff < 24 * HOUR_MILLIS) {
           // return diff / HOUR_MILLIS + " Hour ago";
         //  // return String.valueOf(new SimpleDateFormat("hh:mm a"));
            SimpleDateFormat simpleDateFormat;
            simpleDateFormat = new SimpleDateFormat("hh:mm a");
            return simpleDateFormat.format(time);
        } else if (diff < 48 * HOUR_MILLIS) {
            return "Yesterday";
        } else {
            Log.d(TAG, "getTimeAgo: "+time);
            return convertTimestampDate(time);
//            Timestamp tStamp = new Timestamp(time);
//            SimpleDateFormat simpleDateFormat;
//            simpleDateFormat = new SimpleDateFormat("MMM dd");
//            return simpleDateFormat.format(tStamp);
         //   Log.d(TAG, "getTimeAgo: ");
            //return diff / DAY_MILLIS + " days ago";
//            SimpleDateFormat simpleDateFormat;
//            simpleDateFormat = new SimpleDateFormat("dd/mm/yy");
//            return simpleDateFormat.format(correctTimestamp(String.valueOf(time)));
            //return method.convertTimestampDateToTime(method.correctTimestamp((String.valueOf(time))));


           /* Timestamp tStamp = new Timestamp(time);
            SimpleDateFormat simpleDateFormat;
            simpleDateFormat = new SimpleDateFormat("dd/mm/yy");
            return simpleDateFormat.format(time);*/



        }

    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
            }
        }
    }
    public String ReturnDate(int pos){
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        if(pos == 1){
            cal.add(Calendar.DATE, -1);
        }else if(pos == 2){
            cal.add(Calendar.DATE, -7);
        }else if(pos == 3){
            cal.add(Calendar.DATE, -28);
        }else if(pos == 4){
            cal.add(Calendar.DATE, 0);
        }

        dateFormat.format(cal.getTime());
        return dateFormat.format(cal.getTime());
    }

    public void dialogAbout(Activity activity,ArrayList arrayList) {
         ArrayList<HomeTopDepositsDTO> userBookingsList = new ArrayList<>();
        dialogFilterJob = new Dialog(activity/*, android.R.style.Theme_Dialog*/);
        dialogFilterJob.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFilterJob.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dailogFilterJobBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_filter, null, false);
        dialogFilterJob.setContentView(dailogFilterJobBinding.getRoot());

        dailogFilterJobBinding.etCategoryD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if ( dailogFilterJobBinding.etCategoryD.getSelectedItemPosition()==0){
                    preferences.setValue("selCategory","all");
                }else if ( dailogFilterJobBinding.etCategoryD.getSelectedItemPosition()==1){
                    preferences.setValue("selCategory","0");
                }else if ( dailogFilterJobBinding.etCategoryD.getSelectedItemPosition()==2){
                    preferences.setValue("selCategory","1");
                }else if ( dailogFilterJobBinding.etCategoryD.getSelectedItemPosition()==3){
                    preferences.setValue("selCategory","2");
                }else if ( dailogFilterJobBinding.etCategoryD.getSelectedItemPosition()==4){
                    preferences.setValue("selCategory","3");
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                preferences.setValue("selCategory","all");
            }
        });


        dailogFilterJobBinding.etsort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if ( dailogFilterJobBinding.etsort.getSelectedItemPosition()==0){
                    preferences.setValue("selCatStatus","all");
                }else if ( dailogFilterJobBinding.etCategoryD.getSelectedItemPosition()==1){
                    preferences.setValue("selCatStatus","4");
                }else if ( dailogFilterJobBinding.etCategoryD.getSelectedItemPosition()==2){
                    preferences.setValue("selCatStatus","1");
                }else if ( dailogFilterJobBinding.etCategoryD.getSelectedItemPosition()==3){
                    preferences.setValue("selCatStatus","3");
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                preferences.setValue("selCatStatus","all");
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
                         Log.e(TAG, "onClick: " + preferences.getValue("selCategory"));
                        // filteredList();
                        topDepositDataAdapter.filter2("0","1");

                    }
                });
    }

   /* public  ArrayList<HomeTopDepositsDTO> filter( ArrayList arrayList, String charText) {
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



    public void getHomeData2(Activity activity) {
        params.clear();
        params.put(GlobalVariables.adminUserId,preferences.getValue(GlobalVariables.adminUserId) );
       // method.showToasty(activity,"1",""+GlobalVariables.adminUserID);
        Log.d(TAG, "getHomeData2: called"+activity.toString());
        new HttpsRequest(RestAPI.API_GET_OFFER, params, activity).stringPost2(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, JSONObject darwinbarkk, String msg, JSONObject response) {
                //  ProjectUtils.pauseProgressDialog();
                //  binding.swipeRefreshLayout.setRefreshing(false);
                if (flag) {
                    // binding.tvNo.setVisibility(View.GONE);
                    try {
                        adminDTO = new Gson().fromJson(response.getJSONObject("darwinbarkk").getJSONObject("Results").toString(), AdminDTO.class);
                        preferences.setParentUser2(adminDTO, GlobalVariables.ADMIN_DTO);
                        adminDTO = preferences.getParentUser2(GlobalVariables.ADMIN_DTO);
                        //globalState.setHomeData(homeDataDTO);
                      //  MainActivity.setData2(adminDTO,activity);
                        usermdto=preferences.getParentUser2(GlobalVariables.ADMIN_DTO);
                        Log.d(TAG, "hKINGSN123:"+usermdto.getAdminAccountIfsc()+usermdto.getAdminMobile());

                        Log.d(TAG, "hactivebookingotp:"+usermdto.getAdminMobile());
                        // Toast.makeText(getContext(), "You Selected Your Slot"+homeDataDTO , Toast.LENGTH_SHORT).show();
                        // setData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}

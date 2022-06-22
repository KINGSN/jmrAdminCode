package com.gpuntd.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.gpuntd.adminapp.Models.AdminDTO;
import com.gpuntd.adminapp.Models.User1DTO;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.databinding.ActivitySplashBinding;
import com.gpuntd.adminapp.preferences.SharedPrefrence;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.onesignal.OSSubscriptionObserver;
import com.onesignal.OSSubscriptionStateChanges;
import com.onesignal.OneSignal;

public class SplashActivity extends AppCompatActivity implements OSSubscriptionObserver {
    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1003;
    ActivitySplashBinding binding;
    private static final String TAG = "KINGSN";
    public static int SPLASH_DISPLAY_LENGTH = 1000;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private  Method method;
    AdminDTO adminDTO;
    SharedPrefrence prefs;


   Runnable mTask = new Runnable() {
        @Override
        public void run() {
            if (!prefs.getBooleanValue(String.valueOf(GlobalVariables.USER_IS_LOGIN))) {
                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(mainIntent);
               // SplashActivity.this.finish();
                finish();

            } else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
                /*overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);*/
            }


        }

    };
    private final String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE};
    public boolean accessNetState;
    private final Handler handler = new Handler();

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        method=new Method(SplashActivity.this);
        prefs = SharedPrefrence.getInstance(SplashActivity.this);
       // setTheme(R.style.splashScreenTheme);
        if (prefs.getBooleanValue(String.valueOf(GlobalVariables.USER_IS_LOGIN))) {
            method.getHomeData2(SplashActivity.this);
            Log.d(TAG, "onCreate:Splash "+(method.adminDTO.getId()));
        }


        OneSignal.initWithContext(this);
        FirebaseMessaging.getInstance().subscribeToTopic("customer")
                .addOnCompleteListener(task -> {
                    // Log.d(TAG, "onCreate: "+n);

                });


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d(TAG, token);

                        //  Toast.makeText(SplashActivity.this,"HELLO"+ msg, Toast.LENGTH_SHORT).show();
                        prefs.setValue(GlobalVariables.DEVICE_TOKEN,token);


                   /*     Toast.makeText(SplashActivity.this,"global Device Token"+
                                preferences.getString(GlobalVariables.DEVICE_TOKEN,""), Toast.LENGTH_SHORT).show();*/
                    }
                });

        if (!hasPermissions(SplashActivity.this, permissions)) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        } else {
            // callinappupdate();
            handler.postDelayed(mTask, 3000);
        }

       /* if (Environment.isExternalStorageManager()) {
            //todo when permission is granted
            // Toast.makeText(this, "all files permission granted", Toast.LENGTH_SHORT).show();
        } else {
            //request for the permission
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }*/

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId("33e054eb-472d-4116-a712-c03c1a39a011");
        OneSignal.addSubscriptionObserver(this);

        OneSignal.getDeviceState();
        OneSignal.disablePush(false);
        if (NotificationManagerCompat.from(SplashActivity.this).areNotificationsEnabled()) {
            //Do your Work
        } else {
            //Ask for permission
            PermissionListener permissionlistener = new PermissionListener() {
                /**
                 * Method called whenever a requested permission has been granted
                 *
                 * @param response A response object that contains the permission that has been requested and
                 *                 any additional flags relevant to this response
                 */
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {

                }

                /**
                 * Method called whenever a requested permission has been denied
                 *
                 * @param response A response object that contains the permission that has been requested and
                 *                 any additional flags relevant to this response
                 */
                @Override
                public void onPermissionDenied(PermissionDeniedResponse response) {
                    finish();
                }

                /**
                 * Method called whenever Android asks the application to inform the user of the need for the
                 * requested permission. The request process won't continue until the token is properly used
                 *
                 * @param permission The permission that has been requested
                 * @param token      Token used to continue or cancel the permission request process. The permission
                 */
                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                }

            };
        }
        }

        @Override
        protected void onResume () {
            super.onResume();
            if (!hasPermissions(SplashActivity.this, permissions)) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            } else {
                //callinappupdate();
                handler.postDelayed(mTask, 3000);

            }
        }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            try {

                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
               // preferences.setBooleanValue(GlobalVariables.CAMERA_ACCEPTED, cameraAccepted);
                Log.d(TAG, "onRequestPermissionsResult: "+GlobalVariables.CAMERA_ACCEPTED.toString());

                editor.putBoolean(GlobalVariables.CAMERA_ACCEPTED, cameraAccepted);
                editor.apply();


                boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
               // preferences.setBooleanValue(GlobalVariables.STORAGE_ACCEPTED, storageAccepted);

                editor.putBoolean(GlobalVariables.STORAGE_ACCEPTED, storageAccepted);
                editor.apply();


                accessNetState = grantResults[2] == PackageManager.PERMISSION_GRANTED;
              //  preferences.setBooleanValue(GlobalVariables.MODIFY_AUDIO_ACCEPTED, accessNetState);

                editor.putBoolean(GlobalVariables.MODIFY_AUDIO_ACCEPTED, accessNetState);
                editor.apply();

              //  Intentscreen();
                handler.postDelayed(mTask, 3000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Intentscreen(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(!preferences.getBoolean(String.valueOf(GlobalVariables.USER_IS_LOGIN),false)){
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                    SplashActivity.this.finish();
                }else{
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    SplashActivity.this.finish();
                }


            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onOSSubscriptionChanged(OSSubscriptionStateChanges stateChanges) {
        if (!stateChanges.getFrom().isSubscribed() &&
                stateChanges.getTo().isSubscribed()) {
            new AlertDialog.Builder(this)
                    .setMessage("You've successfully subscribed to push notifications!")
                    .show();

            stateChanges.getTo().getUserId();
        }

        Log.i("Debug", "onOSSubscriptionChanged: " + stateChanges);
    }
}
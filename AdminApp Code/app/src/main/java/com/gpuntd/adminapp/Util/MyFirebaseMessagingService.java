package com.gpuntd.adminapp.Util;
/**
 * Created by VARUN on 01/01/19.
 */

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gpuntd.adminapp.MainActivity;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.preferences.SharedPrefrence;


import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "KINGSN";
     private Method method;
    int i = 0;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "ADMIN PREFERENCES" ;
    @SuppressLint("CommitPrefEdits")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Context context = getApplicationContext();
        method=new Method(context);


        Log.e(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getData() != null) {
            if (remoteMessage.getData().containsKey(GlobalVariables.TYPE)) {
                if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.CHAT_NOTIFICATION)) {
                    sendNotification(context,getValue(remoteMessage.getData(), "sendBy"),getValue(remoteMessage.getData(), "sendTo"),getValue(remoteMessage.getData(), "username"),getValue(remoteMessage.getData(), "title"),getValue(remoteMessage.getData(), "body"), GlobalVariables.CHAT_NOTIFICATION);
                }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.ALL_DEPOSIT)) {
                    sendNotification(context,getValue(remoteMessage.getData(), "sendBy"),getValue(remoteMessage.getData(), "sendTo"),getValue(remoteMessage.getData(), "username"),getValue(remoteMessage.getData(), "title"),getValue(remoteMessage.getData(), "body"), GlobalVariables.ALL_DEPOSIT);
                }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.USER_WITHDRAWALS)) {
                    sendNotification(context,getValue(remoteMessage.getData(), "sendBy"),getValue(remoteMessage.getData(), "sendTo"),getValue(remoteMessage.getData(), "username"),getValue(remoteMessage.getData(), "title"),getValue(remoteMessage.getData(), "body"), GlobalVariables.USER_WITHDRAWALS);
                }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.USER_PASSID)) {
                    sendNotification(context,getValue(remoteMessage.getData(), "sendBy"),getValue(remoteMessage.getData(), "sendTo"),getValue(remoteMessage.getData(), "username"),getValue(remoteMessage.getData(), "title"),getValue(remoteMessage.getData(), "body"), GlobalVariables.USER_PASSID);
                }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.ADMIN_NOTIFICATION)) {
                    sendNotification(context,getValue(remoteMessage.getData(), "sendBy"),getValue(remoteMessage.getData(), "sendTo"),getValue(remoteMessage.getData(), "username"),getValue(remoteMessage.getData(), "title"),getValue(remoteMessage.getData(), "body"), GlobalVariables.ADMIN_NOTIFICATION);
                }/*else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.START_BOOKING_ARTIST_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.START_BOOKING_ARTIST_NOTIFICATION);
                }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.END_BOOKING_ARTIST_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.END_BOOKING_ARTIST_NOTIFICATION);
                }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.ACCEPT_BOOKING_ARTIST_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.ACCEPT_BOOKING_ARTIST_NOTIFICATION);
                }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.JOB_APPLY_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.JOB_APPLY_NOTIFICATION);
                }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.BRODCAST_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.BRODCAST_NOTIFICATION);
                }*/else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.ADMIN_NOTIFICATION)) {
                    sendNotification(context,getValue(remoteMessage.getData(), "sendBy"),getValue(remoteMessage.getData(), "sendTo"),getValue(remoteMessage.getData(), "username"),getValue(remoteMessage.getData(), "title"),getValue(remoteMessage.getData(), "body"), GlobalVariables.ADMIN_NOTIFICATION);
                }else {
                    //sendNotification(getValue(remoteMessage.getData(), "body"), "");
                    sendNotification(context,getValue(remoteMessage.getData(), "sendBy"),getValue(remoteMessage.getData(), "sendTo"),getValue(remoteMessage.getData(), "username"),getValue(remoteMessage.getData(), "title"),getValue(remoteMessage.getData(), "body"), "");

                }
            }

        }

    }

    public String getValue(Map<String, String> data, String key) {
        try {
            if (data.containsKey(key))
                return data.get(key);
            else
                return getString(R.string.app_name);
        } catch (Exception ex) {
            ex.printStackTrace();
            return getString(R.string.app_name);
        }
    }
    @Override
    public void onNewToken(@NotNull String token) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(GlobalVariables.DEVICE_TOKEN, token);
        editor.apply();
        SharedPreferences userDetails = MyFirebaseMessagingService.this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Log.d(TAG, "Refreshed token: " + token);

    }

    //  send custom notification
    private void sendNotification(Context context,String sendBy,String sendTo,String username ,String title,String messageBody, String tag) {

        Log.d(TAG, "sendNotification: "+sendTo+sendBy);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(tag);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("sendBy",sendBy);
        intent.putExtra("sendTo",sendTo);
        intent.putExtra(GlobalVariables.userName,username);
        /*editor.putString(GlobalVariables.sendBy, sendBy);
        editor.putString(GlobalVariables.sendTo, sendTo);
        editor.apply();
        */

        method.preferences.setValue(GlobalVariables.userName,username);
        method.preferences.setValue(GlobalVariables.userMobile,sendBy);

        //  intent.putExtra(GlobalVariables.userMobile, sendTo);
        intent.putExtra(GlobalVariables.SCREEN_TAG, tag);
      /*  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        String channelId = "Default";
        Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification);

        RemoteViews collapsedView = new RemoteViews(context.getPackageName(),
                R.layout.notification_layout);

        collapsedView.setTextViewText(R.id.tvNotiTitle, title);
        collapsedView.setTextViewText(R.id.tvNotiDetails, messageBody);
        // collapsedView.setOnClickPendingIntent(R.id.btnViewDetails, pendingIntent);

    /*   RemoteViews expandedView = new RemoteViews(context.getPackageName(),
                R.layout.notification_expanded);

        expandedView.setTextViewText(R.id.tvNotiTitle, messageBody);
        expandedView.setTextViewText(R.id.tvNotiDetails, messageBody.concat("body"));
*/
        collapsedView.setOnClickPendingIntent(R.id.notiContainer, pendingIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                /*.setCustomBigContentView(new RemoteViews(context.getPackageName(),
                        R.layout.notification_layout))*/
                .setCustomContentView(collapsedView)
                // .setCustomBigContentView(expandedView)
                //.setContentTitle(messageBody.concat("title"))
                .setAutoCancel(false)
                .setSound(defaultSoundUri)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle());


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }

    private void sendNotification1(String messageBody, String tag) {

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(tag);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(GlobalVariables.SCREEN_TAG, tag);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                //.setContentTitle(messageBody.concat("title"))
                .setSound(defaultSoundUri)
                /*.setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))*/
                .setContentText(messageBody).setAutoCancel(true).setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }









}


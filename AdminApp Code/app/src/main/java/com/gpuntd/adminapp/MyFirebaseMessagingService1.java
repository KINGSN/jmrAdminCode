package com.gpuntd.adminapp;
/**
 * Created by KINGSN on 01/01/19.
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

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gpuntd.adminapp.Util.GlobalVariables;

import java.util.Map;

public class MyFirebaseMessagingService1 extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    int i = 0;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    @SuppressLint("CommitPrefEdits")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
      /*  preferences = SharedPrefrence.getInstance(this);*/
        preferences = getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
       // method = new Method(this);

        Log.e(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload1: " + remoteMessage.getData());
        }

        remoteMessage.getData();
        if (remoteMessage.getData().containsKey(GlobalVariables.TYPE)) {
            if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.CHAT_NOTIFICATION)) {
                sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.CHAT_NOTIFICATION);
            }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.TICKET_COMMENT_NOTIFICATION)) {
                sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.TICKET_COMMENT_NOTIFICATION);
            }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.TICKET_STATUS_NOTIFICATION)) {
                sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.TICKET_STATUS_NOTIFICATION);
            }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.WALLET_NOTIFICATION)) {
                sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.WALLET_NOTIFICATION);
            }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.DECLINE_BOOKING_ARTIST_NOTIFICATION)) {
                sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.DECLINE_BOOKING_ARTIST_NOTIFICATION);
            }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.START_BOOKING_ARTIST_NOTIFICATION)) {
                sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.START_BOOKING_ARTIST_NOTIFICATION);
            }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.END_BOOKING_ARTIST_NOTIFICATION)) {
                sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.END_BOOKING_ARTIST_NOTIFICATION);
            }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.ACCEPT_BOOKING_ARTIST_NOTIFICATION)) {
                sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.ACCEPT_BOOKING_ARTIST_NOTIFICATION);
            }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.JOB_APPLY_NOTIFICATION)) {
                sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.JOB_APPLY_NOTIFICATION);
            }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.BRODCAST_NOTIFICATION)) {
                sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.BRODCAST_NOTIFICATION);
            }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.ADMIN_NOTIFICATION)) {
                sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.ADMIN_NOTIFICATION);
            }else {
                sendNotification(getValue(remoteMessage.getData(), "body"), "");
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
    public void onNewToken(String token) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(GlobalVariables.DEVICE_TOKEN, token);
        editor.commit();
        SharedPreferences userDetails = MyFirebaseMessagingService1.this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Log.d(TAG, "Refreshed token: " + token);

    }
    private void sendNotification(String messageBody, String tag) {

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
                .setContentTitle("Gpunt Msg")
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


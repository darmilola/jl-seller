package com.dammy.android.emarketvendor.service;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.openingPageActivity;
import com.dammy.android.emarketvendor.splashscreen;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log.e("from",remoteMessage.getFrom());
        //Log.e("body", remoteMessage.getNotification().getBody() );



            Log.e("data", remoteMessage.getData().toString());


                try {
                    JSONObject jsonObject = new JSONObject(remoteMessage.getData().toString());
                    handledatamessage(jsonObject);

                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }

            }



    private void handledatamessage(JSONObject JSON) {

        try {
            JSONObject data = JSON.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");

            String channelid = "JL Seller";
            Intent intent = new Intent(this,splashscreen.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
            bigTextStyle.setBigContentTitle(title);
            bigTextStyle.bigText(message);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channelid)
                    .setSmallIcon(R.drawable.newicon)
                    .setStyle(bigTextStyle)
                    .setChannelId(channelid)
                    .setContentIntent(pendingIntent)
                    .setColor(getResources().getColor(R.color.colorPrimary));
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                CharSequence name = "JL Seller";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(channelid,name,importance);
                notificationManager.createNotificationChannel(notificationChannel);
            }
           notificationManager.notify(1, builder.build());
        } catch (JSONException E) {
            Log.e("error in json", E.getMessage());
        }
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }

}
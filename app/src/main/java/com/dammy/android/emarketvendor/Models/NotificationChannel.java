package com.dammy.android.emarketvendor.Models;

import android.os.Build;

public class NotificationChannel  {

   public NotificationChannel(){

    }

    private void createNotitication(){
       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
           CharSequence name = "channelname";
       }
    }
}

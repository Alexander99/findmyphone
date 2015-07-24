package com.example.tutorialspoint;

import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
 

public class onBootReceiver extends BroadcastReceiver{
    @Override

    public void onReceive(Context context, Intent intent) {          
           //Start App On Boot Start Up
    	Intent intent2 = new Intent(context, MainActivity.class);  
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent2); 
    }
}
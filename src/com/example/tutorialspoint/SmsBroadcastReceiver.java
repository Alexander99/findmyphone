package com.example.tutorialspoint;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationProvider;
import android.location.LocationManager;
import android.location.LocationListener;

@SuppressLint("NewApi") 
public class SmsBroadcastReceiver extends BroadcastReceiver {
	//set up an empty location and an mContext
	Location location;
	
    public static final String SMS_BUNDLE = "pdus";
        
    @TargetApi(Build.VERSION_CODES.DONUT) @SuppressLint("NewApi") 
      public void onReceive(Context context, Intent intent) {
    	//set up location manager to get the location of the phone
        LocationManager locationManager = (LocationManager) context
        		.getSystemService(Context.LOCATION_SERVICE);
        //fill location with the last known location of the phone, which should be the current location after being texted
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody();
                String address = smsMessage.getOriginatingAddress();
                smsMessageStr += "SMS From: " + address + "\n";
                //Get the latitude and longitude to return the google maps image of location
                if((address.equals("+19702235598") || address.equals("+19705561977")) && smsBody.equals("Ebon"))
                {
                 
                 SmsManager smsManager = SmsManager.getDefault();
                 double latitude = location.getLatitude();
                 double longitude = location.getLongitude();
                 smsManager.sendTextMessage(address, null, "Drake is at: " + "google.com/maps/@" + latitude + ","+ longitude + ",16z", null, null);
                }
                smsMessageStr += smsBody + "\n";
            }
            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();

            //this will update the UI with message
            MainActivity inst = MainActivity.instance();
            inst.updateList(smsMessageStr);
        }
    }
}

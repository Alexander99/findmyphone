package com.example.tutorialspoint;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.EditText;
import android.widget.Toast;
import android.location.Location;
import android.app.IntentService;
import android.location.LocationProvider;
import android.location.LocationManager;
import android.location.LocationListener;
import android.app.AlarmManager;

import java.util.Timer;



import android.os.CountDownTimer;

@SuppressLint("NewApi") 
public class SmsBroadcastReceiver extends BroadcastReceiver {
	//set up an empty location and an mContext
	Location location;
	Location newLocation;
	private MalibuCountDownTimer CountDownTimer;
	private final long startTime = 300000;
	private final long interval = 1000;
	static String Password = "Drake";
	static List<String> PhoneNumber = new ArrayList<String>();
	//static String PhoneNumber;
	static String allowedAddress;
	static String oldAddress;
    public static final String SMS_BUNDLE = "pdus";
    String smsBody;
        
    @TargetApi(Build.VERSION_CODES.DONUT) @SuppressLint("NewApi") 
      public void onReceive(Context context, Intent intent) {
    	   //Start App On Boot Start Up
    	
 /*   	Intent intent2 = new Intent(context, MainActivity.class);  
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent2);*/
    	
    	//Set up the countdown timer for the second text
		CountDownTimer = new MalibuCountDownTimer(startTime, interval);
		//make sure the context matches that of onReceive, or else horrible bugs will likely appear
		CountDownTimer.context = context;

    	
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

                smsBody = smsMessage.getMessageBody();
                String address = smsMessage.getOriginatingAddress();
                smsMessageStr += "SMS From: " + address + "\n";
                //Get the latitude and longitude to return the google maps image of location
               // if(address.equals(PhoneNumber.get(0))&& smsBody.equals(Password))
                if((PhoneNumber.contains(address) != false)&& smsBody.equals(Password))
                {
                 
                 SmsManager smsManager = SmsManager.getDefault();
                 double latitude = location.getLatitude();
                 double longitude = location.getLongitude();
                 allowedAddress = address;
                 smsManager.sendTextMessage(address, null, "I'm at: " + "google.com/maps/@" + latitude + ","+ longitude + ",16z", null, null);
                }
                smsMessageStr += smsBody + "\n";
            }
            
            //this will update the UI with message
            MainActivity inst = MainActivity.instance();
            inst.updateList(smsMessageStr);
            //Do the update
            oldAddress = allowedAddress;
            //Need to set count down timer so that it goes off once, and then is done
            //best acheived by checking for the proper text. else, all texts will trigger this
            if((PhoneNumber.contains(allowedAddress) != false)&& smsBody.equals(Password))
            {
             CountDownTimer.start();
            }
        }
    }
    //public method used in main activity to set the pass phrase
    public static void setPass(Context context, String PassPhrase){
    	//Toast.makeText(context, "This is a test!", Toast.LENGTH_LONG).show();
    	//set the global for password to what passphrase is
    	Password = PassPhrase;
    	Toast.makeText(context,"Set password to " + Password,Toast.LENGTH_LONG).show();
    }
    //public method used in main activity to set one of the phone numbers
    public static void setPhoneNos(Context context, String PhoneNo){
    	//Set numbers allowed to whatever is set inside of PhoneNo
    	//Note: will want several numbers installed, so will have to make an array which is incremented
    	//Add a number to the list of strings available
    	PhoneNumber.add("+" + PhoneNo);
    	Toast.makeText(context, "added the number " + PhoneNo, Toast.LENGTH_LONG).show();
    	
    }
    public void killTimer(){
    	//Use to kill the timer once it is down counting down
    	CountDownTimer.cancel();
    }
    public class MalibuCountDownTimer extends CountDownTimer{
    	//Function called if the location of the person changes, meant to inform
    	//the person who texted a while back that the location has changed
    	//set up sms message to original individual who sent the text, informing them
    	//of movement
    	Context context;
    	public MalibuCountDownTimer(long startTime, long interval)
		{
			super(startTime, interval);
		}
    	

		@Override
		public void onFinish() {
			//Set up the location manager for the countdown timer to determine the current
			//location
			
			LocationManager locationManager = (LocationManager) context
	        		.getSystemService(Context.LOCATION_SERVICE);
	        //fill location with the last known location of the phone, which should be the current location after being texted
	        newLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	        //if we have moved in the last few minutes, send a text to the previous individual
			if(((location.getLatitude() - 0.0001) < newLocation.getLatitude()) &&
				(newLocation.getLatitude() < (location.getLatitude() + 0.0001)) && 
				(location.getLongitude() - 0.0001 < newLocation.getLongitude()) &&
				(newLocation.getLongitude() < (location.getLongitude() + 0.0001)))
			
	    	{
				//inform that the text was not sent
				 Toast.makeText(context, "Text not sent; have not moved from spot since the last " + startTime/(60*1000) + " minutes", Toast.LENGTH_LONG).show();
				 CountDownTimer.start();
	    	}
			else
			{
				//send the message indicating the movement
		    	 SmsManager smsManager = SmsManager.getDefault();
		    	 smsManager.sendTextMessage(oldAddress, null, "I just moved; I'm now at: " + "google.com/maps/@" + newLocation.getLatitude() + ","+ newLocation.getLongitude() + ",16z", null, null);
		    	 //Toast to check new location
		    	 killTimer();
			
			}
			
			
		}

		@Override
		public void onTick(long arg0) {
			// TODO Auto-generated method stub
			
		}
    }
}

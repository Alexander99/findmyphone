package com.example.tutorialspoint;

import java.io.FileOutputStream;
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
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.location.Location;
import android.app.IntentService;
import android.location.LocationProvider;
import android.location.LocationManager;
import android.location.LocationListener;
import android.app.AlarmManager;

import java.io.FileOutputStream;
import java.io.FileInputStream;
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
	static List<String> TimerQueue = new ArrayList<String>();
	static int TimerQueueSize = 0;
	//static String PhoneNumber;
	static String allowedAddress;
	static String oldAddress;
    public static final String SMS_BUNDLE = "pdus";
    String smsBody;
    static boolean isTimerActive = false;
        
    @TargetApi(Build.VERSION_CODES.DONUT) @SuppressLint("NewApi") 
      public void onReceive(Context context, Intent intent) {
    	
    	//Set up the countdown timer for the second text
		CountDownTimer = new MalibuCountDownTimer(startTime, interval);
		//make sure the context matches that of onReceive, or else horrible bugs will likely appear
		CountDownTimer.context = context;

    	
    	//set up location manager to get the location of the phone
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //fill location with the last known location of the phone, which should be the current location after being texted
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                smsBody = smsMessage.getMessageBody();
                
                //as soon as we get the message, we want to abort the broadcast so
                //that it doesn't show up in the user's inbox
                if(smsMessage.getMessageBody().contains("Ebon")) {
                    abortBroadcast();
                    try{
                    	//to delete an sms, use the below function to do so.
                    	//URL must be "content://sms/" + id (where id is the size of the text to remove)
                    	String URL = "content://sms/" + smsBody.length();
                    	//where is date=?
                    	//selectionArgs is new String[] { c.getString(4) }
                    	
                    	//context.getContentResolver().delete(url, where, selectionArgs)
                    }
                    catch(Exception e){
                    	
                    }
                }
                String address = smsMessage.getOriginatingAddress();
                //Get the latitude and longitude to return the google maps image of location
                if((PhoneNumber.contains(address) != false)&& smsBody.equals(Password))
                {
                 
                 SmsManager smsManager = SmsManager.getDefault();
                 double latitude = location.getLatitude();
                 double longitude = location.getLongitude();
                 allowedAddress = address;
                //send the text out!
                 smsManager.sendTextMessage(address, null, "I'm at: " + "https://google.com/maps?q="+latitude+","+longitude+"+(My+Point)&z=14&ll="+latitude+","+longitude,null,null);
                 
                 //update the queue for the location update
                 TimerQueue.add("+"+address);
                 TimerQueueSize++;
                 //MainActivity.CountTimerQueue();
                 
                 //Do the update
                 //Need to set count down timer so that it goes off once, and then is done
                 //best acheived by checking for the proper text. else, all texts will trigger this
               
                 if(isTimerActive == false)
                 {
                	 isTimerActive = true;
                	 CountDownTimer.start();
                 }
                 
                }
             
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
    	//Toast.makeText(context, "added the number " + PhoneNo, Toast.LENGTH_LONG).show();
    	
    }
    public void killTimer(){
    	//Use to kill the timer once it is down counting down
    	CountDownTimer.cancel();
    }
    public void loadQ(List<String> Queue){
    	//load the queue from last time, remembering to load old information
    	 //for each element in queue, load one by one into TimerQueue
    	 //each time we load an element, increment itemsInQueue by 1
    	
    }
    //Unfortunately, MalibuCountDownTimer seems necessary, even though it seems
    //like it can easily be changed
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
				//inform that the text was not sent, and restart the timer
				 Toast.makeText(context, "Text not sent; have not moved from spot since the last " + startTime/(60*1000) + " minutes", Toast.LENGTH_LONG).show();
				 CountDownTimer.start();
	    	}
			else
			{
				//send the message indicating the movement
				//Note, this has to be done for every number that asks, so itemsInQueue should increase over time
				for(int numberIndex = 0; numberIndex < TimerQueue.size(); numberIndex++)
				{
				 //After the 5 minutes, if outside the range, send everybody who asked a text!
				 //Completely dependent on the receiver
		    	 SmsManager smsManager = SmsManager.getDefault();
		    	 smsManager.sendTextMessage(TimerQueue.get(numberIndex), null, "I just moved; I'm now at: https://google.com/maps?q="+newLocation.getLatitude()+","+newLocation.getLongitude()+"+(My+Point)&z=14&ll="+newLocation.getLatitude()+","+newLocation.getLongitude(),null,null);
				}
				TimerQueue.clear();
				//set the timer to deactive so a new timer can start
				isTimerActive = false;
				//kill the timer so it doesn't repeat itself
				killTimer();
			
			}
			
			
		}

		@Override
		public void onTick(long arg0) {
			// TODO Auto-generated method stub
			
		}
    }
}

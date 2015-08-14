package com.example.tutorialspoint;

import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.content.BroadcastReceiver;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.CountDownTimer;
import android.app.AlarmManager;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.FileWriter;
import java.io.FileReader;

import com.example.tutorialspoint.SmsBroadcastReceiver;

public class MainActivity extends ActionBarActivity /*implements OnItemClickListener*/ {
	//buttons used to save data
	Button saveNOButton;
	Button savePhraseButton;
	
	//Textview, to count the number of items in TimerQueue
	//static TextView timerTextView;
	
	//edit text fields used to set up numbers and pass phrases
	EditText Number;
	EditText PassPhrase;
//	File file;
	int NumberofNumbers = 1;
    private static MainActivity inst;
    ArrayList<String> PhoneNos;
    static List<String> QueueLoad;
    int counter = 1;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView numListView;
    ArrayList <String> PassPhrases;

    
  //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    @SuppressWarnings("rawtypes")
	ArrayAdapter arrayAdapter;

    public static MainActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PhoneNos = new ArrayList<String>();
		PassPhrases = new ArrayList<String>(20);

      //Used to 
        //Set up the array adapter

        //set up the listview
        numListView = (ListView) findViewById(R.id.listView1);
        arrayAdapter = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_1, PhoneNos);
        numListView.setAdapter(arrayAdapter);
        
        numListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	//Initialize and set up a sendable addr
            	String SendableAddr = "";
            	SendableAddr = PhoneNos.get(position);
            	//Set up function link to "clickable"
                ListClickable(SendableAddr,position);   
                }
              });
        //set the button that saves the number to determine what number can send the passphrase to
        //get the image
        //saveNOButton = (Button) findViewById(R.id.button1);
        //set the button that saves the passphrase to determine what is sent to get the image
        savePhraseButton = (Button) findViewById(R.id.button2);
        
        //set up edit text fields
        //Number = (EditText) findViewById(R.id.editText1);
        PassPhrase = (EditText) findViewById(R.id.editText2);
        
        
        //load the recently previous passcode used
        try{
        	FileInputStream countIn = openFileInput("CounterData");
        	counter = countIn.read();
        	countIn.close();
        	
        }
        catch(Exception e){
        	
        }
        try{
            FileInputStream fin = openFileInput("mydata");
            
            int c;
            String temp="";
            
            while( (c = fin.read()) != -1){
               temp = temp + Character.toString((char)c);
            }
        	SmsBroadcastReceiver.setPass(this,temp);
        	fin.close();
         }
         catch(Exception e){
         }
        
        while(NumberofNumbers <= counter)
        {
         try{
        	FileInputStream fin1 = openFileInput("PhoneNumber" + NumberofNumbers);
        	int c;
        	String temp="";
        	
        	while( (c = fin1.read()) != -1){
        		temp = temp + Character.toString((char)c);
        
        	}
        	SmsBroadcastReceiver.setPhoneNos(this,temp);
        	PhoneNos.add(temp);
        	fin1.close();
        	
         }
         catch(Exception e){
        	
         }
         NumberofNumbers++;
        }
        for(int phraseCounter = 0; phraseCounter < counter;phraseCounter++)
        {
         try{
        	 FileInputStream fin2 = openFileInput("PhraseSave" +phraseCounter);
        	 int c;
        	 String temp="";
        	 
        	 while( (c = fin2.read()) != -1){
        		 temp = temp+Character.toString((char) c);
        		 
        	 }
        	 //Toast.makeText(this, "counter: " + phraseCounter, Toast.LENGTH_LONG).show();
        	 
        	 PassPhrases.add(phraseCounter,temp);
        	// Toast.makeText(this, PassPhrases.get(phraseCounter) + " Counter: " +phraseCounter, Toast.LENGTH_LONG).show();
        	 fin2.close();
            }
         catch(Exception e)
         {
        	
         }
        }
        //Set up the list of contacts that can send the text by accessing internal data
        //will need to use a for loop to access
        //Set up the passphrase that is sent to get
        
    }

    @SuppressWarnings("unchecked")
	public void updateList(final String smsAddress) {
        arrayAdapter.insert(smsAddress, 0);
        arrayAdapter.notifyDataSetChanged();
    }
    
    public void SAVEPHRASE(View view){
    	//savephrase should automatically save a passphrase to a global used
    	//whenever the text is sent
    	//password = PassPhrase.getText().toString();
    	//Call upon the function in SmsBroadcastReceiver to store the password
    	SmsBroadcastReceiver.setPass(this,PassPhrase.getText().toString());
    	//Save the phrase used to the internal data; whenever created, this data should
    	//be written back to the phrase. Can be overwritten
    	try{
       	 
       	 FileOutputStream FOS = openFileOutput("mydata",Context.MODE_WORLD_READABLE);
       	 //should be written to the list again. Should only be an append
       	 FOS.write((PassPhrase.getText().toString()).getBytes());
       	 FOS.close();
       	}
       	catch (Exception e) {
               // TO DO Auto-generated catch block
               e.printStackTrace();
            }
    }
    
   public void EDITNUMBERS(View view){
	   //editnumbers button should take us to DeleteNoActivity, where we can delete
	   //numbers we select from a radio button
	   Intent jumpIntent = new Intent(this, DeleteNoActivity.class);
	   startActivity(jumpIntent);
   }
   
    public void ListClickable(String address,int position) {
    	//used to send a text to the specific number
    	//Depending on the item clicked, sends a text to that number in question by using the address
    	SmsManager smsManager = SmsManager.getDefault();
    	//Toast.makeText(this, "Position: " + position, Toast.LENGTH_LONG).show();
    	//Toast.makeText(this, PassPhrases.get(position), Toast.LENGTH_LONG).show();
    	smsManager.sendTextMessage(address, null, PassPhrases.get(position), null, null);
    }
}
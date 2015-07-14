package com.example.tutorialspoint;

import android.support.v7.app.ActionBarActivity;
import android.content.ContentResolver;
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
import android.widget.Toast;
import java.util.ArrayList;
import com.example.tutorialspoint.SmsBroadcastReceiver;

public class MainActivity extends ActionBarActivity /*implements OnItemClickListener*/ {
	//buttons used to save data
	Button saveNOButton;
	Button savePhraseButton;
	//edit text fields used to set up numbers and pass phrases
	EditText Number;
	EditText PassPhrase;
	
    private static MainActivity inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    //ListView smsListView;
    
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
      //  smsListView = (ListView) findViewById(R.id.SMSList);
        //Set up the array adapter
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        //set the button that saves the number to determine what number can send the passphrase to
        //get the image
        saveNOButton = (Button) findViewById(R.id.button1);
        //set the button that saves the passphrase to determine what is sent to get the image
        savePhraseButton = (Button) findViewById(R.id.button2);
        
        //set up edit text fields
        Number = (EditText) findViewById(R.id.editText1);
        PassPhrase = (EditText) findViewById(R.id.editText2);
        
      //  smsListView.setAdapter(arrayAdapter);
      //  smsListView.setOnItemClickListener(this);
        refreshSmsInbox();
    }

    @SuppressWarnings("unchecked")
	public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        do {
        	 if(smsInboxCursor.getString(indexAddress).equals("+19702235598"))
             {
              String str = "SMS From Mom: " + smsInboxCursor.getString(indexAddress) +
                      "\n" + smsInboxCursor.getString(indexBody) + "\n";
              arrayAdapter.add(str);
             }
           } while (smsInboxCursor.moveToNext());
        
    }

    @SuppressWarnings("unchecked")
	public void updateList(final String smsMessage) {
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
    }
    public void SAVENO(View view){
    	//Toast.makeText(this, "This is a test!", Toast.LENGTH_LONG).show();
    	//saveno should, whenever the button is hit, save the individual's number to
    	//a list of numbers that is searched whenever a text is received.
    	SmsBroadcastReceiver.setPhoneNos(this,Number.getText().toString());
    }
    public void SAVEPHRASE(View view){
    	//savephrase should automatically save a passphrase to a global used
    	//whenever the text is sent
    	//password = PassPhrase.getText().toString();
    	//Call upon the function in SmsBroadcastReceiver to store the password
    	SmsBroadcastReceiver.setPass(this,PassPhrase.getText().toString());
    }

 /*   public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        try {
            String[] smsMessages = smsMessagesList.get(pos).split("\n");
            String address = smsMessages[0];
            String smsMessage = "";
            for (int i = 1; i < smsMessages.length; ++i) {
                smsMessage += smsMessages[i];
            }

            String smsMessageStr = address + "\n";
            smsMessageStr += smsMessage;
            Toast.makeText(this, smsMessageStr, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }*/
}
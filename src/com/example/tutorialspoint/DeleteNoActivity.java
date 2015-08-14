package com.example.tutorialspoint;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.app.*;
@SuppressWarnings("unused")
public class DeleteNoActivity extends ActionBarActivity {

	//set up temp counter for this activity
	int CounterChange;
	int NumberofNumbers = 1;
	int PassCounter = 1;
    ArrayList<String> PhoneNos;
    ArrayList <String> PassPhrases;
    EditText AddDelNum;
    ListView PassPhraseListView;
    @SuppressWarnings("rawtypes")
	ArrayAdapter PassAdapter;
    PopupWindow popUp;
	LinearLayout mainActivity;
	AlertDialog.Builder alertPassPhrase;
	//Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_no);
        PhoneNos = new ArrayList<String>();
		AddDelNum = (EditText) findViewById(R.id.editText3);
		PassPhrases = new ArrayList<String>();
		//set up popup window
		alertPassPhrase = new AlertDialog.Builder(this);
		
		//set up listview and arrayAdapter
		PassPhraseListView = (ListView) findViewById(R.id.listView1);
        PassAdapter = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_1, PhoneNos);
        PassPhraseListView.setAdapter(PassAdapter);
		
        PassPhraseListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	//Initialize and set up a sendable addr
            	String SendableAddr = "";
            	SendableAddr = PhoneNos.get(position);
            	//Set up function link to "clickable"
                ListClickable(SendableAddr,position);   
                }
              });
		//load counter info
		try{
        	FileInputStream countIn = openFileInput("CounterData");
        	CounterChange = countIn.read();
        	countIn.close();
        }
        catch(Exception e){
        }
		try{
			FileInputStream countIn = openFileInput("CounterData1");
			PassCounter = countIn.read();
			countIn.close();
			
		}
		catch(Exception e){
			
		}
		while(NumberofNumbers <= CounterChange)
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
		
	}
	
	public void ADDNUMBER(View view){
		//Used to add a number to the list of contacts that can send the text,
		//and have the text sent to them
		//variables needed:
		String addNumber;
		CounterChange++;
		addNumber = AddDelNum.getText().toString();
		//Save the number used to the internal data; whenever created, this data
		 if(PhoneNos.contains(addNumber) == false)
		 {
    	 PhoneNos.add(addNumber);
    	 try{
    		//need to save each number separately
    	
    	    FileOutputStream FOS1 = openFileOutput("PhoneNumber" + CounterChange,Context.MODE_WORLD_READABLE);
    	    FOS1.write(addNumber.getBytes());
    	    FOS1.close();
    	    
    	    
    	  }
    	 catch (Exception e) {
            // TO DO Auto-generated catch block
            e.printStackTrace();
          }
    	 try{
    		//count up to determine how many numbers have been saved, for next time
    		FileOutputStream CountStream = openFileOutput("CounterData",Context.MODE_WORLD_READABLE);
    		CountStream.write(CounterChange);
    	 }
    	 catch (Exception e) {
    		e.printStackTrace();
    	 }
		 //use the number in the editText box as AddNumber and compare to PhoneNos;
		 //If already in PhoneNos, do nothing
		 //If not yet in PhoneNos, but not 11 characters long, don't do anything
		 //else, add the number to be saved for future use and save an increase to the counter
		}
	}
	
	public void DELNUMBER(View view){
		//Used to remove a number on the list of contacts
		//variables needed:
		String delNumber;
		
		//use the number in the editText box as delNumber
		delNumber = AddDelNum.getText().toString();
		//If not in PhoneNos, do nothing
		if(PhoneNos.contains(delNumber))
		{
		 //If in PhoneNos, remove it by taking the next ones in line and saving over it.
//			Toast.makeText(this,PhoneNos.indexOf(delNumber),Toast.LENGTH_LONG).show();
		 int PhoneNosPos;
		 for(PhoneNosPos = PhoneNos.indexOf(delNumber); PhoneNosPos < (PhoneNos.size()-1);PhoneNosPos++)
		 {
		 //i.e., in a list of 1,2,3,4,5, deleting 3 means overwrite it with 4, overwrite 4 with 5		
			 PhoneNos.set(PhoneNosPos, PhoneNos.get(PhoneNosPos+1));
		 }
		 PhoneNos.remove(PhoneNos.size()-1);
			try{
				//save new numbers
				int counter;
				for(counter = 1; counter <= (PhoneNos.size()); counter++)
				{
	    	     FileOutputStream FOS1 = openFileOutput("PhoneNumber" + counter,Context.MODE_WORLD_READABLE);
	    	     FOS1.write(PhoneNos.get(counter-1).toString().getBytes());
	    	     FOS1.close();
				}
	    	    
	    	 }
	    	catch (Exception e) {
	            // TO DO Auto-generated catch block
	            e.printStackTrace();
	         }
			CounterChange--;
			try{
	    		//count up to determine how many numbers have been saved, for next time
	    		FileOutputStream CountStream = openFileOutput("CounterData",Context.MODE_WORLD_READABLE);
	    		CountStream.write(CounterChange);
	    	}
	    	catch (Exception e) {
	    		e.printStackTrace();
	    	}
			PhoneNos.remove(delNumber);
			
		}
		//decrement counter to signify decrease of list size
	}
	
	public void GOBACK(View view){
		
		//use to return to main screen
		 Intent jumpIntent = new Intent(this, MainActivity.class);
		   startActivity(jumpIntent);
	}
	public void ListClickable(String address,final int pass){
		//variable needed
		final Context context = this;
		//once activated, bring up a window to set the passphrase connected to that number
		final EditText AlertDialogText;
		AlertDialogText = new EditText(this);
		//bring up a window, any window
		//title of window
		alertPassPhrase.setTitle("Set Pass Phrase");
		//message to show what window is about
		alertPassPhrase.setMessage("A simple test to see if a message appears.");
		//edit text section to use as pass phrase for this individual
		alertPassPhrase.setView(AlertDialogText);
		//positive answer to accept new pass phrase for the individual selected (set by pass)
		alertPassPhrase.setPositiveButton("Accept", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				final String NewPhrase;
				//Once clicked, it should input a value made in the save stream, and keep until overwrite
				NewPhrase = AlertDialogText.getText().toString();
				
				 try{
				 	 FileOutputStream PhraseOut = openFileOutput("PhraseSave" + pass,Context.MODE_WORLD_READABLE);
				 	 PhraseOut.write(NewPhrase.getBytes());
			       	 PhraseOut.close();
				 }
				 catch(Exception e){
					
				 }
				
				//Toast.makeText(context, NewPhrase, Toast.LENGTH_LONG).show();				
			}
		});
		alertPassPhrase.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog,int id){
			dialog.cancel();
			}
		});
		alertPassPhrase.show();
	}
}

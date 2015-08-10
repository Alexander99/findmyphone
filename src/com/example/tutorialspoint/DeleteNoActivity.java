package com.example.tutorialspoint;

import android.support.v7.app.ActionBarActivity;
import android.widget.*;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

@SuppressWarnings("unused")
public class DeleteNoActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_no);
	}
	
	public void GOBACK(View view){
		
		//use to return to main screen
		 Intent jumpIntent = new Intent(this, MainActivity.class);
		   startActivity(jumpIntent);
	}
	
}

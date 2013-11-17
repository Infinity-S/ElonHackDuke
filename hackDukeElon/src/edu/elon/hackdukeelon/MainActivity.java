package edu.elon.hackdukeelon;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import com.netcompss.ffmpeg4android_client.BaseWizard;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends BaseWizard {
	private ListView songSegements; 
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		songSegements = (ListView) findViewById(R.id.list); 
		songSegements.setOnItemClickListener(clickListener); 
		
		updateList();
	}
	
	private OnItemClickListener clickListener = new OnItemClickListener() {
		/*Clicking on list items */
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			
		}
	};
	
	private void updateList() {
		String[] secondsList = {"1 Second", "5 Second", "10 second"}; 
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, secondsList); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

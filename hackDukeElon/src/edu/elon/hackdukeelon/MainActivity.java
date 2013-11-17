package edu.elon.hackdukeelon;

import com.netcompss.ffmpeg4android_client.BaseWizard;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends BaseWizard {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	/**
	 * Thomas needs his own Javadoc, because he is great.
	 * 
	 * @param greatness
	 * @return moreGreatness
	 */
	/*
	 * public moreGreatness thomasMethod(Greatness g){
	 * 	return g+1;
	 * }
	 */
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

package edu.elon.hackdukeelon;

import com.netcompss.ffmpeg4android_client.BaseWizard;
import com.netcompss.ffmpeg4android_client.Prefs;

import android.os.Bundle;
import android.os.RemoteException;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends BaseWizard {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button b = (Button) findViewById(R.id.button1);

		//mergeTest();

		b.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				compileVideos();
			}

		});
	}

	private void compileVideos(){

		copyLicenseAndDemoFilesFromAssetsToSDIfNeeded();

		String commandStr = "ffmpeg -f concat -i /sdcard/mylist.txt -c copy /sdcard/merge.mp4";
		

		setCommand(commandStr);

		///optional////
		setProgressDialogTitle("Compiling your music video!");
		setProgressDialogMessage("Depends on your video size, it can take a few minutes");
		//setNotificationIcon(R.drawable.icon2);
		setNotificationTitle("HackDuke Application");
		setNotificationfinishedMessageTitle("Compiling finished");
		setNotificationfinishedMessageDesc("Click to play demo");
		setNotificationStoppedMessage("Demo Transcoding stopped");
		///////////////

		runTranscoing();
		///////////////////////////////////////////////////////////////////////////////
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

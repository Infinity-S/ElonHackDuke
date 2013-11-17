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
	private final String AUDIO_REMOVED = "/sdcard/noAudio.mp4";
	private final String SONG_ADDED = "/sdcard/withSong.mp4";
	private final String MERGED_VIDEO = "/sdcard/merge.mp4";
	private final String COMPILATION = "/sdcard/mylist.txt";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button b = (Button) findViewById(R.id.button1);

		b.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				compileVideos();
			}
		});
		
		
		Button remove = (Button) findViewById(R.id.button2);
		remove.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				dropAudio();
			}
		});
		
		Button add = (Button) findViewById(R.id.button3);
		add.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				addAudio();
			}
		});
		
	}
	
	private void addAudio(){
		copyLicenseAndDemoFilesFromAssetsToSDIfNeeded();
		//setOutputFilePath("/sdcard/withSong.mp4");
		String commandStr = "ffmpeg -i " + AUDIO_REMOVED + " -i /sdcard/song.mp3 -map 0 -map 1 -codec copy -shortest " + SONG_ADDED;
		
		setCommand(commandStr);
		
		setProgressDialogTitle("Adding audio!");
		setProgressDialogMessage("Depends on your video size, it can take a few minutes");
		setNotificationTitle("HackDuke Application");
		setNotificationfinishedMessageTitle("Audio added!");

		runTranscoing();
	}
	
	private void dropAudio(){
		copyLicenseAndDemoFilesFromAssetsToSDIfNeeded();

		String commandStr = "ffmpeg -i " + MERGED_VIDEO + "-vcodec copy -an " + AUDIO_REMOVED;
	
		setCommand(commandStr);
		
		setProgressDialogTitle("Removing audio!");
		setProgressDialogMessage("Depends on your video size, it can take a few minutes");
		setNotificationTitle("HackDuke Application");
		setNotificationfinishedMessageTitle("Audio all removed");

		runTranscoing();
		
	}

	private void compileVideos(){

		copyLicenseAndDemoFilesFromAssetsToSDIfNeeded();

		String commandStr = "ffmpeg -f concat -i " + COMPILATION + " -c copy " + MERGED_VIDEO;
		

		setCommand(commandStr);

	
		setProgressDialogTitle("Compiling your music video!");
		setProgressDialogMessage("Depends on your video size, it can take a few minutes");

		setNotificationTitle("HackDuke Application");
		setNotificationfinishedMessageTitle("Compiling finished");
		setNotificationfinishedMessageDesc("Click to play demo");
		setNotificationStoppedMessage("Demo Transcoding stopped");

		runTranscoing();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

package edu.elon.hackdukeelon;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.netcompss.ffmpeg4android_client.BaseWizard;




public class MainActivity extends BaseWizard {
	private final String AUDIO_REMOVED = "/sdcard/videokit/noAudio.mp4";
	private final String SONG_ADDED = "/sdcard/videokit/withSong.mp4";
	private final String MERGED_VIDEO = "/sdcard/videokit/merge.mp4";
	private final String COMPILATION = "/sdcard/videokit/mylist.txt";
	private final String SONG = "/sdcard/videokit/song.mp3";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		copyLicenseAndDemoFilesFromAssetsToSDIfNeeded();

		try {
			saveRawToSD();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
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

		Button threefer = (Button) findViewById(R.id.button4);
		threefer.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				AsyncCompile c = new AsyncCompile();
				//c.execute("execute");
				compileVideos();
			}
		});


	}

	private class AsyncCompile extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			System.out.println("Compiling");
			compileVideos();
			return "done";
		}

		@Override
		protected void onPostExecute(String result){
			super.onPostExecute(result);
			dropAudio();
		}

	}

	
	private void compileVideos(){

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
	
	private void addAudio(){
		//copyLicenseAndDemoFilesFromAssetsToSDIfNeeded();

		String commandStr = "ffmpeg -i " + AUDIO_REMOVED + " -i " + SONG + " -map 0 -map 1 -codec copy -shortest " + SONG_ADDED;

		setCommand(commandStr);
	

		setProgressDialogTitle("Adding audio!");
		setProgressDialogMessage("Depends on your video size, it can take a few minutes");
		setNotificationTitle("HackDuke Application");
		setNotificationfinishedMessageTitle("Audio added!");

		runTranscoing();

	}

	private void saveRawToSD() throws IOException {

		InputStream in = getResources().openRawResource(R.raw.song);
		FileOutputStream out = new FileOutputStream(SONG);
		byte[] buff = new byte[1024];
		int read = 0;
		try{
			while((read = in.read(buff)) > 0){
				out.write(buff, 0, read);
			}
		}finally{
			in.close();
			out.close();
		}
		
	}

	private void dropAudio(){
		//copyLicenseAndDemoFilesFromAssetsToSDIfNeeded();

		String commandStr = "ffmpeg -i " + MERGED_VIDEO + " -vcodec copy -an " + AUDIO_REMOVED;

		setCommand(commandStr);

		setProgressDialogTitle("Removing audio!");
		setProgressDialogMessage("Depends on your video size, it can take a few minutes");
		setNotificationTitle("HackDuke Application");
		setNotificationfinishedMessageTitle("Audio all removed");

		runTranscoing();

	}

	




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

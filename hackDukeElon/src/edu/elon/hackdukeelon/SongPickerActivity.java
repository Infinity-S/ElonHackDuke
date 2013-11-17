package edu.elon.hackdukeelon;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SongPickerActivity extends Activity {
	private ListView songView; 
	private ArrayList<Song> songs = new ArrayList<Song>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_song_picker);
		
		songView = (ListView) findViewById(R.id.songs); 
		songView.setOnItemClickListener(clickListener); 
		buildList(); 
	}
	
	private OnItemClickListener clickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			Bundle b = new Bundle(); 
			b.putStringArrayList("durationsStr", songs.get(position).getClips()); 
			b.putString("filePath", songs.get(position).getFileName()); 
			
			Intent i = new Intent(SongPickerActivity.this, MainActivity.class); 
			i.putExtras(b); 
			startActivity(i); 
			
		}
	};
	
	private void buildList() {
		Song song1 = new Song("San Francisco Noise","/sdcard/videokit/song.mp3", new int[] {5,10,15,5,10}); 
		songs.add(song1); 
		
		ArrayList<String> songNames = new ArrayList<String>(); 
		
		for(Song s : songs) {
			songNames.add(s.getTitle()); 
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1, songNames);
		songView.setAdapter(adapter); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.song_picker, menu);
		return true;
	}

}

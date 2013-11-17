/**
 * MainActivity.java 1.0 Nov 16, 2013
 * 
 * Copyright (c) 2013 Schuyler Goodwin, Miles Camp, Thomas Robbins and Evan Walmer
 */
package edu.elon.hackdukeelon;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.netcompss.ffmpeg4android_client.BaseWizard;

public class MainActivity extends BaseWizard {
	private final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	public final int MEDIA_TYPE_IMAGE = 1;
	public final int MEDIA_TYPE_VIDEO = 2;
	private Uri fileUri;
	
	private ListView songSegements;
	private ArrayList<Clip> clipList = new ArrayList<Clip>();
	private Button finalize; 
	private boolean cancelled = false; 
	private String clipName = ""; 
	private int currClipPos = 0; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		songSegements = (ListView) findViewById(R.id.list);
		songSegements.setOnItemClickListener(clickListener);
		songSegements.setOnItemLongClickListener(longClickListener); 
		
		finalize = (Button) findViewById(R.id.finalizeButton); 
		finalize.setOnClickListener(finalizeListener); 
		updateList();
		

	}
	
	public void showDialog(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this); 
	    builder.setTitle(title);
	    builder.setMessage(message); 
	    builder.setCancelable(false); 
	    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}); 
	    builder.show(); 
	}
	
	public void nameClip() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this); 
	    builder.setTitle("Name Your Clip"); 
	    final EditText input = new EditText(this);
	    input.setText("Clip "+(currClipPos+1)); 
	    input.setInputType(InputType.TYPE_CLASS_TEXT); 
	    builder.setView(input);
	    
	 // Set up the buttons
	    builder.setPositiveButton("Record", new DialogInterface.OnClickListener() { 
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	        	clipName = input.getText().toString();  
	            //m_Text = input.getText().toString();
	        	Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				
				fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);  // create a file to save the video
			    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name
			    //intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, clipList.get(currClipPos).getDuration());
			    intent.putExtra("android.intent.extra.durationLimit", clipList.get(currClipPos).getDuration()); 
			    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high

			    // start the Video Capture Intent
			    startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
			    
			    
	        }
	    });
	    
	    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	        	clipName = ""; 
	            dialog.cancel();
	        }
	    });
	    
	    builder.show();
	 
	}

	private OnItemClickListener clickListener = new OnItemClickListener() {

		/* Clicking on list items */

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			currClipPos = position; 
			nameClip(); 		 
		}
	};
	
	private OnItemLongClickListener longClickListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
			String path = clipList.get(position).getPath(); 
			if(clipList.get(position).isRecorded()) {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
			intent.setDataAndType(Uri.parse(path), "video/mp4");
			startActivity(intent);
			} else {
				//Toast.makeText(getApplicationContext(), "Cannot Preview File. You haven't recorded one yet!", Toast.LENGTH_SHORT).show();
				showDialog("Cannot Preview Clip", "You must have recorded a clip first, in order for it to be previewed!"); 
			}
			
			return false; 
		}
	};

	private void updateList() { 
		if(clipList.size() < 1) {
		clipList.add(new Clip()); 
		clipList.add(new Clip()); 
		clipList.add(new Clip()); 
		}
		boolean enable = true; 
		ArrayList<String> clipNames = new ArrayList<String>(); 
		for(Clip c : clipList) {
			clipNames.add(c.toString()); 
			if(!c.isRecorded()) {
				enable = false; 
			}
		}
		
		finalize.setEnabled(enable); 
		
		
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_list_item_1, clipNames);
		ColorAdapter adapter = new ColorAdapter(this, android.R.layout.simple_list_item_1, clipNames); 
		songSegements.setAdapter(adapter);
	}
	
	private OnClickListener finalizeListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "Finalize Clicked!", Toast.LENGTH_SHORT).show();
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	        	Clip current = clipList.get(currClipPos); 
	        	String fileName = data.getDataString(); 
	        	if(isGoodRecord(fileName, current.getDuration())) {
	        	current.setTitle(clipName); 
	        	current.setPath(data.getDataString()); 
	        	updateList(); 
	        	} else {
	        		//Toast.makeText(this, "Invalid Length! Please record again!", Toast.LENGTH_LONG).show();
	        		showDialog("Clip Not Saved", "You recorded a clip of an invaild length. \nPlease try again!"); 
	        	}
	            // Video captured and saved to fileUri specified in the Intent
//	            Toast.makeText(this, "Video saved to:\n" +
//                     data.getDataString(), Toast.LENGTH_LONG).show();
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the video capture
	        } else {
	            // Video capture failed, advise user
	        }
	    }
	}
	
	private boolean isGoodRecord(String uri, int expectedDuration) { 
		
		MediaPlayer mp = MediaPlayer.create(this, Uri.parse(uri)); 
		int duration = mp.getDuration(); 
		mp.release(); 
		
		duration = duration / 1000; 
		
		if(duration == expectedDuration) {
			return true;
		} else {
			return false;
		}
	}
	
	private Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}
	
	/** Create a File for saving an image or video */
	private File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "HackDukeVideo");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("HackDukeVideo", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }

	    return mediaFile;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class ColorAdapter extends ArrayAdapter<String> {
		
		public ColorAdapter(Context context, int resID, ArrayList<String> items) {
	        super(context, resID, items);                       
	    }
		
		@Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View v = super.getView(position, convertView, parent);
	        Clip current = clipList.get(position); 
	        if (current.isRecorded()) {
	        	((TextView) v).setBackgroundColor(Color.GREEN);
	        } else {
	        	((TextView) v).setBackgroundColor(Color.RED); 
	        }
	        return v;
	    }
	}

}

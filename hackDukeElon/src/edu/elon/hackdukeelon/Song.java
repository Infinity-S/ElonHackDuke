package edu.elon.hackdukeelon;

import java.util.ArrayList;

public class Song {
	private String title;
	private String fileName; 
	private ArrayList<String> clips = new ArrayList<String>(); 
	
	public Song(String title, String fileName, int[] durations){
		this.title = title; 
		this.fileName = fileName; 
		for(int i : durations) {
			clips.add(""+i); 
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<String> getClips() {
		return clips;
	}

	public void setClips(ArrayList<String> clips) {
		this.clips = clips;
	}

}

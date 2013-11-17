/**
 * Clip.java 1.0 Nov 16, 2013
 * 
 * Copyright (c) 2013 Schuyler Goodwin, Miles Camp, Thomas Robbins and Evan Walmer
 */
package edu.elon.hackdukeelon;

import java.io.File;

public class Clip{
        private int duration; 
        private String title; 
        private String path; 
        private boolean recorded = false; 
        
        public Clip() {
                duration = 5; 
                title = "Empty Clip"; 
                path = ""; 
        }
        
        public Clip(int dur) {
        	this.duration = dur; 
        	this.title = "Empty Clip";
        }
        
        public Clip(int dur, String title) {
                this.duration = dur; 
                this.title = title;  
        }

        public int getDuration() {
                return duration;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getPath() {
                return path;
        }

        public void setPath(String path) {
                this.path = path;
        }
        
        public boolean isRecorded() {
                if(title.equals("Empty Clip")) {
                        recorded = false; 
                } else {
                        recorded = true; 
                }
                return recorded; 
        }
        
        public String toString() {
                return title + " ("+duration+" seconds)"; 
        }

}
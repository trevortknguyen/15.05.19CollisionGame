package audio;

import java.awt.Component;
import java.io.*;

import javax.swing.JFileChooser;

import sun.audio.*;

public class Audio {
	
	AudioStream boing;
	AudioStream click;
	AudioStream background;
	
	public Audio(){
	try{
		String st = "C:\\Users\\Ryan\\workspace\\Project\\click.wav";
		InputStream in = new FileInputStream(st);
		click = new AudioStream(in);
		st = "C:\\Users\\Ryan\\workspace\\Project\\boing.wav";
		in = new FileInputStream(st);
		boing = new AudioStream(in);
		st = "C:\\Users\\Ryan\\workspace\\Project\\background.wav";
		in = new FileInputStream(st);
		background = new AudioStream(in);
	}
	catch(Exception e){}
	}
	
	public void playboing(){
		AudioPlayer.player.start(boing);
	}
	
	public void playclick(){
		AudioPlayer.player.start(click);
	}
	
	public void playbackground(){
		//AudioPlayer.player.start(background);
	}
	
}
 
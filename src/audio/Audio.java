package audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Audio {
	
	AudioStream boing; //creates AudioStream Objects for all sound effects
	AudioStream click;
	AudioStream background;
	
	public Audio(){
	try{
		String st = "C:\\Users\\Thai Nguyen\\Documents\\LWJGL\\15.05.12 CollisionGame\\res\\click.wav"; //creates strings for file locations
		InputStream in = new FileInputStream(new File(st));                                             //creates an input stream for the data
		click = new AudioStream(in);                                                                    //initialize the AudioStream object
		st = "C:\\Users\\Thai Nguyen\\Documents\\LWJGL\\15.05.12 CollisionGame\\res\\boing.wav";
		in = new FileInputStream(new File(st));
		boing = new AudioStream(in);
		st = "C:\\Users\\Thai Nguyen\\Documents\\LWJGL\\15.05.12 CollisionGame\\res\\background.wav";
		in = new FileInputStream(new File(st));
		background = new AudioStream(in);
	}
	catch(Exception e){}
	}
	
	public void playboing(){           //allows the audio clip to be called from different classes
		AudioPlayer.player.start(boing);
	}
	
	public void playclick(){
		AudioPlayer.player.start(click);
	}
	
	public void playbackground(){
		//AudioPlayer.player.start(background);
	}
	
}
 
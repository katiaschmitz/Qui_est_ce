import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FenetreGird Fenetre = new FenetreGird();
		
		
		//playSound("mario.wav");
	}

	/*public static synchronized void playSound(final String url) {
		  new Thread(new Runnable() {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		    public void run() {
		      try {
		        Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(("/path/to/sounds/" + url));
		        clip.open(inputStream);
		        clip.start(); 
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
		}*/
	
}

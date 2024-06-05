package game;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {

  Clip clip;
  URL soundUrl[] = new URL[30];

  public SoundManager() {

    soundUrl[0] =  getClass().getResource("/assets/Sound/BlueBoyAdventure.wav");
    soundUrl[1] =  getClass().getResource("/assets/Sound/coin.wav");
    soundUrl[2] =  getClass().getResource("/assets/Sound/unlock.wav");
    soundUrl[3] =  getClass().getResource("/assets/Sound/powerup.wav");
    soundUrl[4] =  getClass().getResource("/assets/Sound/receivedamage.wav");
    soundUrl[5] =  getClass().getResource("/assets/Sound/hitmonster.wav");
    soundUrl[6] =  getClass().getResource("/assets/Sound/cuttree.wav");
    soundUrl[7] =  getClass().getResource("/assets/Sound/levelup.wav");
    soundUrl[8] =  getClass().getResource("/assets/Sound/cursor.wav");
    soundUrl[9] =  getClass().getResource("/assets/Sound/burning.wav");

  }

  public void setFile(int i) {

    try {

      AudioInputStream  ais = AudioSystem.getAudioInputStream(soundUrl[i]);
      clip = AudioSystem.getClip();
      clip.open(ais);

    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void play() {
    clip.start();
  }

  public void loop() {
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }
  
  public void stop() {
    clip.stop();
  }
}

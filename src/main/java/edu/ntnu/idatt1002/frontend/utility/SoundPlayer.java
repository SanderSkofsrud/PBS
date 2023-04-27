package edu.ntnu.idatt1002.frontend.utility;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.logging.Logger;

/**
 * A class that plays a sound.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.1 - 26.04.2023
 */
public class SoundPlayer {

  /**
   * A method that plays a sound.
   *
   * @param filename the name of the sound file
   */
  public static void play(String filename) {
    try {
      File soundFile = new File(filename);
      Clip clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(soundFile));
      clip.start();
    } catch (Exception e) {
      Logger.getGlobal().warning("Could not play sound");
    }
  }
}








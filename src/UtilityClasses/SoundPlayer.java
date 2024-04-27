package UtilityClasses;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

/**
 * Allows for audio playback controls. One AudioInputStream is loaded at a time, and it is played through the Clip.
 * Controls operations include play, pause and resume.
 */
public class SoundPlayer {
    private static SoundPlayer instance; // Singleton instance
    private boolean isPaused = false;
    private long pausedTime = 0;
    private Clip clip;
    private AudioInputStream sound;

    /**
     * Private constructor setting the Clip.
     * @param clip Clip instance
     */
    private SoundPlayer(Clip clip) {
        this.clip = clip;
    }

    /**
     * Attempts to create a SoundPlayer instance. It can fail if the Clip can't be loaded.
     * @return SoundPlayer instance
     * @throws Exception Clip failed to load
     */
    private static SoundPlayer create() throws Exception {
        try {
            Clip clip = AudioSystem.getClip();
            SoundPlayer player = new SoundPlayer(clip);
            return player;
        } catch (LineUnavailableException e) {
            throw new Exception("Unable to use the audio system.");
        }
    }

    /**
     * Sets the sound being played. Attempts to load the sound file at a given file path.
     * If that fails, an exception is thrown. If it's successful, the sound is loaded and the Clip's
     * frame position is set to 0.
     * @param filePath the file path
     * @throws Exception audio failed to load
     */
    public void setSound(String filePath) throws Exception {
        AudioInputStream audio = FileLoader.loadSoundFromFile(filePath);
        sound = audio;
        clip.stop();
        clip.setFramePosition(0);
        clip.open(sound);
    }

    /**
     * Returns the SoundPlayer instance. There can only be one at a time.
     * @return SoundPlayer instance
     * @throws Exception SoundPlayer creation failed
     */
    public static SoundPlayer getInstance() throws Exception {
        if(instance == null) {
            instance = create();
        }
        return instance;
    }

    /**
     * Starts playback on the Clip.
     */
    public void play() {
        clip.start();
    }

    /**
     * Pauses playback on the Clip.
     */
    public void pause() {
        if(clip.isRunning()) {
            isPaused = true;
            pausedTime = clip.getMicrosecondPosition();
            clip.stop();
        }
    }

    /**
     * Resumes playback on the Clip.
     */
    public void resume() {
        if(isPaused) {
            isPaused = false;
            clip.setMicrosecondPosition(pausedTime);
            clip.start();
        }
    }

    /**
     * Returns a boolean indicating whether playback is paused or not.
     * @return true if playback is paused, false otherwise
     */
    public boolean isPaused() {
        return isPaused;
    }
}

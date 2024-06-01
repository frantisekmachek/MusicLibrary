package UtilityClasses;

import MusicClasses.Library;
import MusicClasses.Song;
import UserInterface.Player.PlaybackState;
import UserInterface.Player.UpdateSliderThread;
import UserInterface.UserInterface;

import javax.sound.sampled.*;

/**
 * Allows for audio playback controls. One AudioInputStream is loaded at a time, and it is played through the Clip.
 * Controls operations include play, pause and resume.
 */
public class SoundPlayer {
    private static SoundPlayer instance; // Singleton instance
    private boolean isPaused = true;
    private long pausedTime = 0;
    private Clip clip;
    private AudioInputStream sound;
    private Song currentSong;
    private PlaybackThread thread;
    private UpdateSliderThread sliderThread = new UpdateSliderThread();

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
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    if(!player.isPaused()) {
                        System.out.println("Playback completed.");
                        try {
                            Song songInQueue = Library.getInstance().getSongInQueue();
                            player.setCurrentSong(songInQueue, false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
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

        isPaused = true;
        pausedTime = 0;
        clip.stop();
        clip.close();

        if(filePath != null) {
            AudioInputStream audio = FileLoader.loadSoundFromFile(filePath);
            sound = audio;

            clip.open(sound);
            clip.setFramePosition(0);
            clip.start();

            isPaused = false;
        } else {
            sound = null;
        }
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
            System.out.println("Sound playback paused.");

            UserInterface.getInstance().getPlayButton().setState(PlaybackState.PAUSED);
            sliderThread.pauseThread();
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
            System.out.println("Sound playback resumed.");

            UserInterface.getInstance().getPlayButton().setState(PlaybackState.PLAYING);
            sliderThread.resumeThread();
        }
    }

    /**
     * Sets the sound playback volume.
     * @param volume float where 0 means 0% volume and 1 means 100% volume
     */
    public void setVolume(float volume) {
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        if(volume < 0f) {
            volume = 0f;
        } else if (volume > 1f) {
            volume = 1f;
        }
        volumeControl.setValue(volume);
    }

    /**
     * Returns a boolean indicating whether playback is paused or not.
     * @return true if playback is paused, false otherwise
     */
    public boolean isPaused() {
        return isPaused;
    }

    public void setCurrentSong(Song song, boolean addToHistory) throws Exception {
        this.currentSong = song;
        if(currentSong != null) {
            setSound(currentSong.getFilePath());
            System.out.println("Song called " + currentSong.getTitle() + " by " + currentSong.getArtist() + " is now playing.");

            if(addToHistory) {
                Library.getInstance().addSongToHistory(getCurrentSong());
            }

            UserInterface.getInstance().getPlayButton().setState(PlaybackState.PLAYING);

            if (!sliderThread.started()) {
                sliderThread.start();
            } else {
                sliderThread.resumeThread();
            }
        } else {
            setSound(null);
            System.out.println("No song is being played.");

            UserInterface.getInstance().getPlayButton().setState(PlaybackState.STOPPED);
        }
        UserInterface.getInstance().loadSoundOnSlider();
    }

    public double getDurationInMilliseconds() {
        long microseconds = clip.getMicrosecondLength();
        double milliseconds = microseconds / 1000.0;
        return milliseconds;
    }

    public Clip getClip() {
        return clip;
    }

    public void setThread(PlaybackThread thread) {
        this.thread = thread;
    }

    public void setMillisecondPosition(int position) {
        long microsecondPosition = position * 1000L;
        pausedTime = microsecondPosition;
        clip.setMicrosecondPosition(microsecondPosition);
    }

    public Song getCurrentSong() {
        return currentSong;
    }
}

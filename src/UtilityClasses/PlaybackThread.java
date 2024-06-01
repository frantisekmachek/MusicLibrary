package UtilityClasses;

/**
 * Controls playback on the SoundPlayer instance on a separate thread.
 */
public class PlaybackThread extends Thread {
    private SoundPlayer player;

    /**
     * A private constructor setting the SoundPlayer.
     * @param player the SoundPlayer instance
     */
    private PlaybackThread(SoundPlayer player) {
        this.player = player;
    }

    /**
     * Attempts to create a PlaybackThread instance.
     * @return a new PlaybackThread
     * @throws Exception SoundPlayer couldn't be loaded
     */
    public static PlaybackThread create() throws Exception {
        SoundPlayer player = SoundPlayer.getInstance();
        PlaybackThread thread = new PlaybackThread(player);
        return thread;
    }

    /**
     * An overridden run() method of the Thread class. Makes the PlaybackThread run continuously and sleep while playback is paused.
     */
    @Override
    public void run() {
        player.play();
    }

    /**
     * Starts playback on the SoundPlayer.
     */
    public void startPlayback() {
        player.play();
    }

    /**
     * Pauses playback on the SoundPlayer.
     */
    public void pausePlayback() {
        player.pause();
    }

    /**
     * Resumes playback on the SoundPlayer.
     */
    public void resumePlayback() {
        player.resume();
    }

    /**
     * Gets the SoundPlayer instance.
     * @return SoundPlayer instance
     */
    public SoundPlayer getPlayer() {
        return player;
    }

}

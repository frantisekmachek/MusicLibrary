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
     * Gets the SoundPlayer instance.
     * @return SoundPlayer instance
     */
    public SoundPlayer getPlayer() {
        return player;
    }

}

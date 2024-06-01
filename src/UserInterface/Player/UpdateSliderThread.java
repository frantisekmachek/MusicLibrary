package UserInterface.Player;

import UserInterface.UserInterface;
import UtilityClasses.SoundPlayer;

import javax.sound.sampled.Clip;

/**
 * This thread updates the slider value every so often to visualize how far
 * the sound is in its playback.
 */
public class UpdateSliderThread extends Thread {
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private volatile boolean started = false;

    public void pauseThread() {
        paused = true;
    }

    /**
     * Resumes the thread.
     */
    public void resumeThread() {
        paused = false;
        synchronized (this) {
            notify();
        }
    }

    @Override
    public void run() {
        started = true;
        while (running) {
            synchronized (this) {
                while (paused) {
                    try {
                        wait(); // Wait until resumed
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }

            // Check if the thread is still running after being resumed
            if (running) {
                try {
                    // Update the slider
                    updateSlider();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    /**
     * Updates the slider value to visualize how far the sound is in its playback.
     */
    public void updateSlider() {
        try {
            Clip clip = SoundPlayer.getInstance().getClip();
            if(!UserInterface.getInstance().isSliderAdjusting()) {
                long microsecondPosition = clip.getMicrosecondPosition();
                double millisecondPosition = microsecondPosition / 1000.0;
                UserInterface.getInstance().setSliderValue(millisecondPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean started() {
        return started;
    }
}

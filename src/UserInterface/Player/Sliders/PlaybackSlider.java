package UserInterface.Player.Sliders;

import UtilityClasses.SoundPlayer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Represents the playback slider where you can skip to different parts of a song.
 */
public class PlaybackSlider extends JSlider {
    private boolean programmaticChange = false;
    public PlaybackSlider() {
        super();
        loadProperties();
    }

    /**
     * Loads the slider properties.
     */
    private void loadProperties() {
        setPaintLabels(false);
        setFocusable(false);
        setBackground(new Color(156, 156, 156));
        setMajorTickSpacing(10);
        setMinorTickSpacing(1);
        setValueProgrammatically(0);
        setEnabled(true);

        loadListeners();
    }

    /**
     * Loads the slider listeners.
     */
    private void loadListeners() {
        addChangeListener(new ChangeListener() {
            /**
             * When the slider value changes, this method checks if it is a user change or program change
             * (programmaticChange variable). It also checks if the value is adjusting or not. If it's a user
             * change and the value stopped adjusting, the sound position is set to the slider value.
             * @param e  a ChangeEvent object
             */
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!programmaticChange && !getValueIsAdjusting()) {
                    getParent().getParent().repaint();
                    int value = getValue();
                    try {
                        SoundPlayer.getInstance().setMillisecondPosition(value);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            /**
             * When pressed, the slider's value is set precisely to where they clicked.
             * @param e the event to be processed
             */
            @Override
            public void mousePressed(MouseEvent e) {
                JSlider sourceSlider = (JSlider)e.getSource();
                BasicSliderUI ui = (BasicSliderUI)sourceSlider.getUI();
                int value = ui.valueForXPosition(e.getX());
                setValueProgrammatically(value);
            }
        });
    }

    /**
     * Adjusts the slider properties to accommodate the new sound being played. It sets the minimum and maximum
     * value and the current value is set to 0.
     */
    public void loadNewSound() {
        try {
            setValueProgrammatically(0);
            setMinimum((int) SoundPlayer.getInstance().getClip().getMicrosecondPosition() / 1000);
            setMaximum((int) Math.ceil(SoundPlayer.getInstance().getDurationInMilliseconds()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used when the slider value is being changed by the program. Otherwise,
     * the setValue method is used. This allows for easy detection of user value changes.
     * @param value new slider value
     */
    public synchronized void setValueProgrammatically(int value) {
        programmaticChange = true;
        setValue(value);
        programmaticChange = false;
    }
}

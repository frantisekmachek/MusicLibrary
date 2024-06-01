package UserInterface.Player.ControlLabels;

import UserInterface.Player.PlaybackState;
import UserInterface.UserInterface;
import UtilityClasses.SoundPlayer;

import javax.swing.*;
import java.awt.*;

/**
 * Starts or stops sound playback when clicked.
 */
public class PlayButton extends ControlLabel {
    private PlaybackState state = PlaybackState.STOPPED;

    public PlayButton() {
        super(new Dimension(32,32));
    }

    /**
     * When clicked, the sound playback is paused or resumed based on its current state.
     * If a song wasn't selected, the user is prompted to do so.
     */
    @Override
    protected void clickAction() {
        try {
            SoundPlayer player = SoundPlayer.getInstance();
            if(player.getCurrentSong() != null) {
                switch(state) {
                    case STOPPED:
                        player.play();
                        setState(PlaybackState.PLAYING);
                        break;
                    case PAUSED:
                        player.resume();
                        setState(PlaybackState.PLAYING);
                        break;
                    case PLAYING:
                        player.pause();
                        setState(PlaybackState.PAUSED);
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(UserInterface.getInstance().getWindow(), "Please choose a song first.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void enterAction() {
        updateIcon();
    }

    @Override
    protected void exitAction() {
        updateIcon();
    }

    /**
     * Updates the icon based on whether it is being hovered over and also based on its state.
     */
    @Override
    protected void updateIcon() {
        if(state != null) {
            if(state == PlaybackState.STOPPED || state == PlaybackState.PAUSED) {
                if(hovering) {
                    loadIcon("play-blue");
                } else {
                    loadIcon("play");
                }
            } else {
                if(hovering) {
                    loadIcon("pause-blue");
                } else {
                    loadIcon("pause");
                }
            }
        } else {
            if(hovering) {
                loadIcon("play-blue");
            } else {
                loadIcon("play");
            }
        }
    }

    /**
     * Sets the playback state.
     * @param state playback state
     */
    public void setState(PlaybackState state) {
        this.state = state;
        updateIcon();
    }
}

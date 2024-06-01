package UserInterface.Player.ControlLabels;

import MusicClasses.Library;
import MusicClasses.Song;
import UtilityClasses.SoundPlayer;

import java.awt.*;

/**
 * The button allowing for playing the next song in the history.
 */
public class ForwardButton extends ControlLabel {

    public ForwardButton() {
        super(new Dimension(18,24));
    }

    @Override
    protected void updateIcon() {
        if(hovering) {
            loadIcon("forward-blue");
        } else {
            loadIcon("forward");
        }
    }

    /**
     * Plays the next song in the history.
     */
    @Override
    protected void clickAction() {
        Song nextSong = Library.getInstance().getNextSong();
        try {
            SoundPlayer player = SoundPlayer.getInstance();
            if(nextSong != null) {
                player.setCurrentSong(nextSong, false);
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
}

package UserInterface.Player.ControlLabels;

import MusicClasses.Library;
import MusicClasses.Song;
import UtilityClasses.SoundPlayer;

import java.awt.*;

/**
 * The button allowing for going back to songs played previously.
 */
public class BackwardButton extends ControlLabel {

    public BackwardButton() {
        super(new Dimension(18,24));
    }

    @Override
    protected void updateIcon() {
        if(hovering) {
            loadIcon("backward-blue");
        } else {
            loadIcon("backward");
        }
    }

    /**
     * If there is a song in the history, it is played. If not, the current song is played again.
     */
    @Override
    protected void clickAction() {
        Song prevSong = Library.getInstance().getPreviousSong();
        try {
            SoundPlayer player = SoundPlayer.getInstance();
            Song currentSong = player.getCurrentSong();
            if(prevSong != null) {
                player.setCurrentSong(prevSong, false); // Don't add the song to the history
            } else {
                player.setCurrentSong(currentSong, false); // Just play the current song again and don't add it to the history
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

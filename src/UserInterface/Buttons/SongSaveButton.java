package UserInterface.Buttons;

import UserInterface.Dialogs.SongDialog;
import java.awt.event.ActionEvent;

/**
 * A save button for the song details dialog.
 */
public class SongSaveButton extends SaveButton {

    public SongSaveButton(SongDialog dialog) {
        super(dialog);
    }

    /**
     * Attempts to save the user's dialog input.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        SongDialog songDialog = (SongDialog)dialog;
        boolean updated = songDialog.updateSong();
        if(updated) {
            songDialog.dispose();
        }
    }
}

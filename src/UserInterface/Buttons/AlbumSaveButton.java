package UserInterface.Buttons;

import UserInterface.Dialogs.AlbumDialog;

import java.awt.event.ActionEvent;

/**
 * Save button for the album creation and edit dialog.
 */
public class AlbumSaveButton extends SaveButton {

    public AlbumSaveButton(AlbumDialog dialog) {
        super(dialog);
    }

    /**
     * When clicked, tells the dialog to save the user input.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        AlbumDialog dialog = (AlbumDialog)this.dialog;
        boolean updated = dialog.updateAlbum();
        if(updated) {
            dialog.dispose();
        }
    }
}

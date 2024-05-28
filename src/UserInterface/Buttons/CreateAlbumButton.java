package UserInterface.Buttons;

import UserInterface.Dialogs.AlbumDialog;
import UserInterface.UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Button which opens an album creation dialog.
 */
public class CreateAlbumButton extends CustomButton {

    public CreateAlbumButton() {
        super(new Dimension(250,30), "Create Album", new Color(156, 156, 156),
                new Color(172, 172, 172), Color.BLACK, new Font("Urbana", Font.BOLD, 16));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        openDialog();
    }

    /**
     * Opens the album creation dialog.
     */
    private void openDialog() {
        JFrame mainWindow = UserInterface.getInstance().getWindow();
        AlbumDialog dialog = new AlbumDialog(mainWindow);
    }
}

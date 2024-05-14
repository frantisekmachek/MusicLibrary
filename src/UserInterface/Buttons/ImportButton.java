package UserInterface.Buttons;

import UserInterface.UserInterface;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Allows the user to import songs.
 */
public class ImportButton extends CustomButton {

    /**
     * Constructor that creates a custom button.
     */
    public ImportButton() {
        super(new Dimension(250,30), "Import New", new Color(156, 156, 156),
                new Color(172, 172, 172), Color.BLACK, new Font("Urbana", Font.BOLD, 16));
    }

    /**
     * Opens a file chooser when clicked and the user is prompted to choose a sound file.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Sound Files (*.wav, *.mp3, *.ogg, *.flac)", "mp3", "wav", "ogg", "flac");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnValue = fileChooser.showOpenDialog(UserInterface.getInstance().getWindow());
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = fileChooser.getSelectedFile();
            // TODO: implement something
        }
    }

}

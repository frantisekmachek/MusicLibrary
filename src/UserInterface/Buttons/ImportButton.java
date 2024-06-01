package UserInterface.Buttons;

import UserInterface.Dialogs.SongDialog;
import UserInterface.UserInterface;
import UtilityClasses.CustomFileFilter;
import UtilityClasses.FileLoader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Path;

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
     * Then, a dialog is opened where the user chooses the song title and artist.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        FileNameExtensionFilter extFilter = new FileNameExtensionFilter("Sound Files (*.wav)", "wav");
        CustomFileFilter filter = new CustomFileFilter(extFilter);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnValue = fileChooser.showOpenDialog(UserInterface.getInstance().getWindow());
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getPath();
            try {
                Path newPath = FileLoader.copySong(filePath);
                openDialog(newPath.toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(UserInterface.getInstance().getWindow(), "File couldn't be copied.");
            }
        }
    }

    /**
     * Opens the song dialog.
     * @param filePath file path chosen in the file chooser
     */
    private void openDialog(String filePath) {
        JFrame mainWindow = UserInterface.getInstance().getWindow();
        SongDialog dialog = new SongDialog(mainWindow, filePath);
    }

}

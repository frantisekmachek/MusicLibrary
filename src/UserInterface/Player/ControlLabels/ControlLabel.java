package UserInterface.Player.ControlLabels;

import UserInterface.UserInterface;
import UtilityClasses.FileLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Represents a playback control label (which act like buttons).
 */
public abstract class ControlLabel extends JLabel {
    protected Dimension defaultSize;
    protected boolean hovering = false;

    public ControlLabel(Dimension defaultSize) {
        super();
        this.defaultSize = defaultSize;
        loadProperties();
        loadListeners();
    }

    /**
     * Loads some of the label properties and loads the icon.
     */
    private void loadProperties() {
        setPreferredSize(defaultSize);
        updateIcon();
    }

    protected abstract void updateIcon();

    /**
     * Loads the icon.
     * @param fileName icon file name
     */
    protected void loadIcon(String fileName) {
        String filePath = "res\\icons\\" + fileName + ".png";
        try {
            ImageIcon icon = FileLoader.loadImageFromFile(filePath);
            ImageIcon resizedIcon = UserInterface.resizeIcon(icon, defaultSize);
            setIcon(resizedIcon);
            repaint();
            Component container = getParent();
            if(container != null) {
                container.getParent().getParent().repaint(); // Repaint the playback control panel
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the label listeners.
     */
    private void loadListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickAction();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                hovering = true;
                enterAction();
                ((JComponent) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovering = false;
                exitAction();
                ((JComponent) e.getSource()).setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    /**
     * Does something when the label is clicked on.
     */
    protected abstract void clickAction();

    /**
     * Does something when the mouse enters the label.
     */
    protected abstract void enterAction();

    /**
     * Does something when the mouse exits the label.
     */
    protected abstract void exitAction();
}

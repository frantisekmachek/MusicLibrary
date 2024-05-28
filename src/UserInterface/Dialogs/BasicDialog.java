package UserInterface.Dialogs;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog with a GridLayout which allows for simple extension for other dialog classes.
 */
public abstract class BasicDialog extends JDialog {
    protected JPanel panel;

    public BasicDialog(JFrame window, String title, int rows, int height) {
        super(window, title, true);
        load(window, rows, height);
    }

    /**
     * Sets the font of all components in a container.
     * @param container the container component
     */
    protected void setComponentFonts(JComponent container) {
        int defaultFontSize = 12;
        for(Component component : container.getComponents()) {
            component.setFont(new Font("Urbana", Font.BOLD, defaultFontSize));
        }
    }

    /**
     * Sets the foreground on all labels in a container.
     * @param container the container component
     */
    protected void setLabelForeground(JComponent container) {
        for(Component component : container.getComponents()) {
            if(component instanceof JLabel) {
                component.setForeground(Color.WHITE);
            }
        }
    }

    /**
     * Loads some of the dialog properties.
     * @param window main app window
     * @param rows GridLayout rows
     * @param height dialog window height
     */
    private void load(JFrame window, int rows, int height) {
        setSize(350, height);
        setResizable(false);
        setLocationRelativeTo(window);

        panel = new JPanel();
        panel.setSize(350, 200);
        panel.setLayout(new GridLayout(rows, 2, 0, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.setBackground(new Color(37, 37, 37));
        add(panel);

        loadContent();

        setComponentFonts(panel);
        setLabelForeground(panel);
    }

    /**
     * Loads the dialog content.
     */
    protected abstract void loadContent();

    /**
     * Preloads the values in input fields.
     */
    protected abstract void preloadValues();

    /**
     * Checks if the user input is valid.
     * @return true if the input is valid, false otherwise
     */
    protected abstract boolean inputIsValid();
}

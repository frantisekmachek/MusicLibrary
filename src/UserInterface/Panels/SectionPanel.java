package UserInterface.Panels;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a section on the left panel.
 */
public class SectionPanel extends JPanel {
    private Dimension size = new Dimension(250, 0);
    public SectionPanel() {
        super();
        loadProperties();
    }

    /**
     * Loads the panel properties.
     */
    private void loadProperties() {
        setPreferredSize(size);
        setBackground(new Color(37, 37, 37));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

}

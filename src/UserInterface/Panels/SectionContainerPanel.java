package UserInterface.Panels;

import javax.swing.*;
import java.awt.*;

/**
 * A JPanel which always contains one SectionPanel. Sections can be switched between.
 */
public class SectionContainerPanel extends JPanel {
    private SectionPanel currentSection;
    private Dimension size = new Dimension(250, 0);

    /**
     * Constructor which loads the panel properties.
     */
    public SectionContainerPanel() {
        super();
        loadProperties();
    }

    /**
     * Closes the current section.
     */
    private void closeSection() {
        removeAll();
        revalidate();
        repaint();
        currentSection = null;
    }

    /**
     * Adds a SectionPanel, effectively "opening" it.
     * @param section section to be opened
     */
    private void loadSection(SectionPanel section) {
        currentSection = section;
        add(currentSection);
        resize();
        repaint();
    }

    /**
     * Switches to another section.
     * @param section section to be opened
     */
    public void openSection(SectionPanel section) {
        if(currentSection != null) { // Check if the section is null
            if(!currentSection.equals(section)) { // If the section isn't already open, open another one
                closeSection();
                loadSection(section);
            }
        } else { // Load the new section
            loadSection(section);
        }
    }

    /**
     * Loads the panel properties such as size, background color and layout.
     */
    private void loadProperties() {
        setPreferredSize(size);
        setBackground(new Color(37, 37, 37));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * Resizes the panel based on the preferred size of all the components inside the currently
     * selected section.
     */
    public void resize() {
        Component[] components = currentSection.getComponents();
        int totalSize = 0;
        for (Component component : components) {
            totalSize += (int)component.getPreferredSize().getHeight();
        }
        size = new Dimension(250, totalSize);
        setPreferredSize(size);
    }
}

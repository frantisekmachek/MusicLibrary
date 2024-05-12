package UserInterface.Panels;

import UserInterface.Buttons.SectionButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A JPanel where you can navigate between different sections via buttons.
 */
public class SectionChoicePanel extends JPanel {
    private Dimension prefSize = new Dimension(250, 40);
    private Color bgColor = new Color(79, 79, 79);
    private ArrayList<SectionButton> sectionButtons = new ArrayList<>();
    private ArrayList<SectionPanel> sections;

    /**
     * Constructor which injects an ArrayList of all the sections and loads the panel properties and buttons.
     * @param sections all sections
     */
    public SectionChoicePanel(ArrayList<SectionPanel> sections) {
        super();
        this.sections = sections;
        loadProperties();
        loadButtons();
    }

    /**
     * Loads some of the panel properties such as size, background color, etc.
     */
    private void loadProperties() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(layout);
        setPreferredSize(prefSize);
        setSize(prefSize);
        setBackground(bgColor);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    /**
     * Loads the section buttons. The buttons are for these sections: Songs, Albums and Playlists.
     * By default, the first section and thus the first button is selected.
     */
    private void loadButtons() {
        String[] buttonText = {"Songs", "Albums", "Playlists"};
        for(int i = 0; i < buttonText.length; i++) {
            boolean selected;
            if(i == 0) {
                selected = true;
            } else {
                selected = false;
            }
            String text = buttonText[i];
            SectionButton button = new SectionButton(text, sectionButtons, selected, sections.get(i));
            sectionButtons.add(button);
            add(button);
            if(i != buttonText.length - 1) {
                add(Box.createHorizontalStrut(5));
            }
        }
    }
}

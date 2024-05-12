package UserInterface.Buttons;

import UserInterface.Panels.SectionPanel;
import UserInterface.UserInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * A button used for navigating between different sections.
 */
public class SectionButton extends CustomButton {
    private boolean selected;
    private ArrayList<SectionButton> sectionButtons;
    private SectionPanel assignedSection;

    /**
     * Constructor which sets some of the button properties and also the 'selected' variable.
     * @param text button text
     * @param sectionButtons all section buttons
     * @param selected true if the button is currently selected, false otherwise
     * @param section the section this button belongs to
     */
    public SectionButton(String text, ArrayList<SectionButton> sectionButtons, boolean selected, SectionPanel section) {
        super(new Dimension(76,30), text, new Color(156, 156, 156), new Color(172, 172, 172), Color.BLACK, new Font("Urbana", Font.BOLD, 10));
        this.sectionButtons = sectionButtons;
        this.selected = selected;
        assignedSection = section;
        if(selected) {
            select();
        }
    }

    /**
     * Makes the button change its background color when hovered over.
     * It is an overridden method from the CustomButton class, and it operates the same way,
     * but the background color is only changed when the button isn't selected.
     */
    @Override
    protected void addHoverListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!selected) {
                    setBackground(hoverBgColor);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!selected) {
                    setBackground(bgColor);
                }
            }
        });
    }

    /**
     * When clicked, the button is selected and all the other section buttons are deselected.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        select();
        for (SectionButton button : sectionButtons) {
            if(!button.equals(SectionButton.this)) {
                button.deselect();
            }
        }
    }

    /**
     * Deselects the button. Also sets the background color to the default one.
     */
    public void deselect() {
        selected = false;
        setBackground(bgColor);
    }

    /**
     * Selects the button. Also sets the background color to white and opens the
     * section the button belongs to.
     */
    public void select() {
        selected = true;
        setBackground(Color.WHITE);
        UserInterface ui = UserInterface.getInstance();
        ui.openSection(assignedSection);
    }

}

package UserInterface.Buttons;

import javax.swing.*;
import java.awt.*;

/**
 * A save button for dialogs.
 */
public abstract class SaveButton extends CustomButton {

    protected JDialog dialog;

    public SaveButton(JDialog dialog) {
        super(new Dimension(250,30), "Save", new Color(156, 156, 156),
                new Color(172, 172, 172), Color.BLACK, new Font("Urbana", Font.BOLD, 16));
        this.dialog = dialog;
    }
}

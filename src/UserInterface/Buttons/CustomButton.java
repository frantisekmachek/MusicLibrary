package UserInterface.Buttons;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class is used for this application's custom buttons and sets the properties automatically such as
 * the size, color, font, etc.
 */
public abstract class CustomButton extends JButton implements ActionListener {
    protected Dimension defaultSize;
    protected Color bgColor;
    protected Color hoverBgColor;
    protected Color fgColor;
    protected String text;
    protected Font font;

    /**
     * Constructor setting the default properties.
     * @param size button size
     * @param text button text
     * @param bgColor background color
     * @param hoverBgColor hovering background color
     * @param fgColor foreground color
     * @param font button text font
     */
    public CustomButton(Dimension size, String text, Color bgColor, Color hoverBgColor, Color fgColor, Font font) {
        this.defaultSize = size;
        this.text = text;
        this.bgColor = bgColor;
        this.hoverBgColor = hoverBgColor;
        this.fgColor = fgColor;
        this.font = font;
        loadProperties();
        addHoverListener();
        preventBlueSelect();
    }

    /**
     * Sets some of the button's properties, such as size and text, but also disables Rollover etc.
     */
    protected void loadProperties() {
        setText(text);
        setPreferredSize(defaultSize);
        setMaximumSize(defaultSize);
        setFont(font);
        setFocusable(false);
        setBackground(bgColor);
        setForeground(fgColor);
        setRolloverEnabled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        addActionListener(this);
    }

    /**
     * Makes the button change its background color when hovered over.
     */
    protected void addHoverListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBgColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(bgColor);
            }
        });
    }

    /**
     * Prevents the button from turning blue when clicked (default JButton behavior).
     * That is realized by overriding the paintButtonPressed method in the BasicButtonUI (and setting the
     * button's UI to the new BasicButtonUI).
     */
    protected void preventBlueSelect() {
        setUI(new BasicButtonUI() {
            @Override
            protected void paintButtonPressed(Graphics g, AbstractButton b) {
                // Do nothing (stops the blue select color)
            }
        });
    }
}

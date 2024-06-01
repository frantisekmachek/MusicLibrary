package UserInterface.Panels;

import UserInterface.UserInterface;
import UtilityClasses.FileLoader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Used for extension by classes such as SongPanel and AlbumPanel, where those panels are "items"
 * in a section. The ItemPanel consists of an icon and some labels, as well as a popup menu
 * which shows up when the user right-clicks on it.
 */
public abstract class ItemPanel extends JPanel {
    protected ImageIcon icon;
    protected JLabel iconLabel;
    protected JPanel labelPanel;
    protected JLabel titleLabel; // always has a title but might not have an artist (playlist)
    protected SectionPanel section;
    protected JPopupMenu popupMenu;

    public ItemPanel(SectionPanel section) {
        super(new FlowLayout(FlowLayout.LEFT));
        this.section = section;
    }

    /**
     * Loads the panel properties and popup menu.
     */
    protected void loadAll() {
        load();
        loadPopupMenu();
        revalidate();
        repaint();
    }

    /**
     * Loads the panel properties, components and mouse listeners.
     */
    protected void load() {
        setPreferredSize(new Dimension(250,60));
        setMaximumSize(new Dimension(250,60));
        setBackground(new Color(0, 0, 0, 0));
        setBorder(null);
        setAlignmentX(Component.LEFT_ALIGNMENT);

        loadIcon();
        loadLabelPanel();
        loadLabels();

        addMouseListeners();
    }

    /**
     * Loads the mouse listener which changes the cursor when the mouse hovers over it.
     */
    private void addMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(45, 45, 45));
                ((JComponent) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                section.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(0,0,0,0));
                ((JComponent) e.getSource()).setCursor(Cursor.getDefaultCursor());
                section.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                clickAction();
            }
        });
    }

    /**
     * Loads the popup menu that appears when you right-click on the panel.
     */
    private void loadPopupMenu() {
        popupMenu = new JPopupMenu();
        JMenuItem menuItem1 = new JMenuItem("Edit");
        JMenuItem menuItem2 = new JMenuItem("Remove");
        popupMenu.add(menuItem1);
        popupMenu.add(menuItem2);

        menuItem1.addActionListener(e -> editAction());
        menuItem2.addActionListener(e -> removeAction());

        setComponentPopupMenu(popupMenu);
    }

    /**
     * Loads the panel which contains the labels.
     */
    private void loadLabelPanel() {
        labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBackground(new Color(0,0,0,0));
        add(labelPanel);
    }

    /**
     * Loads the cover label.
     * @return cover label
     */
    protected JLabel loadIconLabel() {
        Dimension size = new Dimension(50,50);

        JLabel label = new JLabel();
        label.setPreferredSize(size);
        label.setMaximumSize(size);
        label.setBorder(new LineBorder(Color.WHITE));
        return label;
    }

    /**
     * Loads the default song cover icon.
     */
    protected void loadDefaultIcon() {
        try {
            ImageIcon icon = FileLoader.loadImageFromFile("res\\covers\\placeholder.jpg");
            setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the icon on the cover label.
     * @param icon new cover
     */
    public void setIcon(ImageIcon icon) {
        this.icon = icon;
        Dimension size = new Dimension(50,50);
        ImageIcon resizedIcon = UserInterface.resizeIcon(icon, size);
        iconLabel.setIcon(resizedIcon);
    }

    /**
     * Loads the labels.
     */
    protected abstract void loadLabels();

    /**
     * Loads the icon.
     */
    protected abstract void loadIcon();

    /**
     * Does something when the user chooses the edit option in the popup menu.
     */
    protected abstract void editAction();

    /**
     * Does something when the user chooses the remove option in the popup menu.
     */
    protected abstract void removeAction();

    /**
     * Does something when the user clicks on the panel.
     */
    protected abstract void clickAction();
}

package UserInterface.Panels.SongLists;

import javax.swing.*;
import java.awt.*;

public class SongListPanel extends JPanel {
    private Dimension size = new Dimension(550, 0);

    public SongListPanel() {
        super();
        loadProperties();
    }

    private void loadProperties() {
        setPreferredSize(size);
        setBackground(new Color(37, 37, 37));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}

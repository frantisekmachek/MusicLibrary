package UserInterface.Panels.SongLists;

import javax.swing.*;
import java.awt.*;

public class SongListContainer extends JPanel {
    private Dimension size = new Dimension(550, 530);

    public SongListContainer() {
        super();
        loadProperties();
    }

    private void loadProperties() {
        setBackground(new Color(79, 79, 79));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(size);
        setSize(size);
    }
}

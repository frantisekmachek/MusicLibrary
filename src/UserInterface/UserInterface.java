package UserInterface;

import UserInterface.Panels.CustomVerticalScrollPane;
import UserInterface.Panels.SectionChoicePanel;
import UserInterface.Panels.SectionContainerPanel;
import UserInterface.Panels.SectionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Represents the user interface the user interacts with while using the application. There
 * can only be one instance of UserInterface at a time.
 */
public class UserInterface {
    private static UserInterface instance;
    private boolean loaded = false;
    private JFrame window;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel sectionChoicePanel;
    private SectionContainerPanel sectionPanel;
    private JScrollPane sectionScrollPane;
    private ArrayList<SectionPanel> sections = new ArrayList<>();

    /**
     * Starts the UI.
     */
    public void start() {
        loadInterface();
    }

    /**
     * A private constructor which forbids instance creation outside of this class.
     */
    private UserInterface() {
    }

    /**
     * Returns the UserInterface instance. If it hasn't been initialized yet, it is created and returned.
     * @return UserInterface instance
     */
    public static UserInterface getInstance() {
        if(instance == null) {
            instance = new UserInterface();
        }
        return instance;
    }

    /**
     * Loads the UI (window, panels, etc.).
     */
    private void loadInterface() {
        if (!loaded) {
            loaded = true;

            // UIManager.put( "ScrollBar.maximumThumbSize", new Dimension(15, 100));

            loadWindow();
            loadMainPanel();
            loadLeftPanel();
            window.setVisible(true);
        }
    }

    /**
     * Loads the main application window (JFrame).
     */
    private void loadWindow() {
        JFrame window = new JFrame("MusicLibrary");
        window.setSize(800, 639); // 639 because of the top bar... otherwise it overflows
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.getContentPane().setLayout(null);
        this.window = window;
    }

    /**
     * Loads the main panel, being the main content container.
     */
    private void loadMainPanel() {
        JPanel mainPanel = new JPanel(null);
        mainPanel.setSize(800, 600);
        mainPanel.setAlignmentX(0);
        mainPanel.setAlignmentY(0);
        mainPanel.setBackground(Color.BLACK);
        this.mainPanel = mainPanel;
        window.add(mainPanel);
    }

    /**
     * Loads the left panel where you can see songs, albums and playlists.
     */
    private void loadLeftPanel() {
        JPanel leftPanel = new JPanel(null);
        leftPanel.setPreferredSize(new Dimension(250, 600));
        leftPanel.setBounds(0,0,250, 600);
        this.leftPanel = leftPanel;
        mainPanel.add(leftPanel);

        loadSectionPanel();
        loadSections();
        loadSectionChoicePanel();
        loadSectionScrollPane();
    }

    /**
     * Loads the panel at the top of the left panel where you can navigate between different sections
     * via buttons.
     */
    private void loadSectionChoicePanel() {
        sectionChoicePanel = new SectionChoicePanel(sections);
        leftPanel.add(sectionChoicePanel);
    }

    // Placeholder section loader method, to be reworked
    private void loadSections() {
        SectionPanel section1 = new SectionPanel();
        SectionPanel section2 = new SectionPanel();
        SectionPanel section3 = new SectionPanel();
        loadDummyComponents(section1, "Song");
        loadDummyComponents(section2, "Album");
        loadDummyComponents(section3, "Playlist");
        sections.add(section1);
        sections.add(section2);
        sections.add(section3);
    }

    /**
     * Loads the panel which contains a selected section.
     */
    private void loadSectionPanel() {
        this.sectionPanel = new SectionContainerPanel();
    }

    /**
     * Opens a section on the left panel.
     * @param section the section to be opened
     */
    public void openSection(SectionPanel section) {
        sectionPanel.openSection(section);
    }

    /**
     * Loads the section scroll pane where you can see different songs, albums and playlists.
     */
    private void loadSectionScrollPane() {
        Dimension prefSize = new Dimension(250, 560);
        Point location = new Point(0, 40);
        sectionScrollPane = new CustomVerticalScrollPane(sectionPanel, prefSize, location, 20);
        leftPanel.add(sectionScrollPane);
    }

    // Method for testing purposes, will be removed later. Loads a few placeholder components in a container.
    private void loadDummyComponents(JPanel container, String text) {
        for(int i = 0; i < 10; i++) {
            JPanel panel = loadDummy(text + " Placeholder " + (i + 1));
            container.add(panel);
        }
        sectionPanel.revalidate();
        sectionPanel.repaint();
    }

    // Method for testing purposes, will be removed later. Creates a placeholder component.
    private JPanel loadDummy(String innerText) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setPreferredSize(new Dimension(250,60));
        panel.setMaximumSize(new Dimension(250, 60));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setBackground(new Color(156, 156, 156));

        JLabel text = new JLabel();
        text.setText(innerText);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("Urbana", Font.BOLD, 20));
        panel.add(text);
        return panel;
    }
}

package UserInterface;

import MusicClasses.Song;
import UserInterface.Panels.*;

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
            UIManager.put("ScrollBar.width", 15);

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
        SongSectionPanel songSection = new SongSectionPanel();
        SectionPanel section2 = new SectionPanel();
        SectionPanel section3 = new SectionPanel();
        sections.add(songSection);
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

    /**
     * Returns the main app window.
     * @return main app window
     */
    public JFrame getWindow() {
        return window;
    }

    /**
     * Creates a new panel for a song.
     * @param song the song being added
     */
    public void createSongElement(Song song) {
        SongSectionPanel songSectionPanel = (SongSectionPanel)sections.get(0);
        songSectionPanel.createSongPanel(song);
    }

    /**
     * Removes a panel belonging to a given song.
     * @param song the song being removed
     */
    public void removeSongElement(Song song) {
        SongSectionPanel songSectionPanel = (SongSectionPanel)sections.get(0);
        songSectionPanel.removeSongPanel(song);
    }

}

import UserInterface.UserInterface;
import UtilityClasses.FileLoader;
import UtilityClasses.PlaybackThread;

public class Main {
    public static void main(String[] args) {
        FileLoader.createResourceDirectory();

        try {
            UserInterface ui = UserInterface.getInstance();
            ui.start();
            PlaybackThread playbackThread = PlaybackThread.create();
            playbackThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
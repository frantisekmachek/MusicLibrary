import MusicClasses.Library;
import UserInterface.UserInterface;
import UtilityClasses.FileLoader;
import UtilityClasses.PlaybackThread;
import UtilityClasses.SoundPlayer;

public class Main {
    public static void main(String[] args) {
        FileLoader.createResourceDirectory();

        try {
            Library lib = Library.getInstance();
            UserInterface ui = UserInterface.getInstance();
            ui.start();
            PlaybackThread playbackThread = PlaybackThread.create();
            playbackThread.start();
            SoundPlayer.getInstance().setThread(playbackThread);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
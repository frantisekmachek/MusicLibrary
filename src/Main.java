import UserInterface.UserInterface;
import UtilityClasses.FileLoader;

public class Main {
    public static void main(String[] args) {
        // Initialize the resource folder if it doesn't exist
        FileLoader.createResourceDirectory();

        UserInterface ui = UserInterface.getInstance();
        ui.start();
    }
}
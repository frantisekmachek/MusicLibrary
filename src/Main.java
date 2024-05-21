import UserInterface.UserInterface;
import UtilityClasses.FileLoader;

public class Main {
    public static void main(String[] args) {
        FileLoader.createResourceDirectory();
        // Initialize the resource folder if it doesn't exist

        UserInterface ui = UserInterface.getInstance();
        ui.start();
    }
}
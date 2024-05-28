import UserInterface.UserInterface;
import UtilityClasses.FileLoader;

public class Main {
    public static void main(String[] args) {
        FileLoader.createResourceDirectory();

        UserInterface ui = UserInterface.getInstance();
        ui.start();
    }
}
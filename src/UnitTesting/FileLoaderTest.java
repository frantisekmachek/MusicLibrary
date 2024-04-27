package UnitTesting;
import UtilityClasses.FileLoader;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains test methods for the FileLoader class.
 */
class FileLoaderTest {

    @org.junit.jupiter.api.Test
    void getFileExtensionTest() {
        String filePath = "file.png";
        String expectedExtension = "png";
        String extension = FileLoader.getFileExtension(filePath);
        assertEquals(expectedExtension, extension);
    }

    @org.junit.jupiter.api.Test
    void isSoundFileTest() {
        String imageFilePath = "file.png";
        String soundFilePath = "sound.mp3";
        assertFalse(FileLoader.isSoundFile(imageFilePath));
        assertTrue(FileLoader.isSoundFile(soundFilePath));
    }

    @org.junit.jupiter.api.Test
    void isImageFileTest() {
        String imageFilePath = "file.png";
        String soundFilePath = "sound.mp3";
        assertFalse(FileLoader.isImageFile(soundFilePath));
        assertTrue(FileLoader.isImageFile(imageFilePath));
    }
}
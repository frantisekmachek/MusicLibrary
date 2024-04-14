package UtilityClasses;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileLoader {
    /**
     * Returns the extension of a given file path.
     * @param filePath the file path
     * @return the file extension
     */
    public static String getFileExtension(String filePath) { // ChatGPT code block
        int lastIndex = filePath.lastIndexOf('.');
        if (lastIndex == -1 || lastIndex == filePath.length() - 1) {
            return null;
        }
        return filePath.substring(lastIndex + 1);
    }

    /**
     * Checks if a file at a given path is a sound file. That means it has one of these extensions: mp3, wav, ogg or flac.
     * @param filePath the file path
     * @return true if the file is a sound file, false if not
     */
    public static boolean isSoundFile(String filePath) {
        String extension = FileLoader.getFileExtension(filePath);
        List<String> soundExtensions = Arrays.asList("mp3", "wav", "ogg", "flac");

        return soundExtensions.contains(extension);
    }

    /**
     * Checks if a file at a given path is an image file. That means it has one of these extensions: jpg, jpeg, png, gif or bmp.
     * @param filePath the file path
     * @return true if the file is a sound file, false if not
     */
    public static boolean isImageFile(String filePath) {
        String extension = FileLoader.getFileExtension(filePath);
        List<String> imageExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp");

        return imageExtensions.contains(extension);
    }

    /**
     * Loads an ImageIcon from a file.
     * @param filePath the image file path
     * @return a new ImageIcon
     * @throws Exception file provided is not an image file
     */
    public static ImageIcon loadImageFromFile(String filePath) throws Exception {
        boolean imageExists = isImageFile(filePath);
        if(imageExists) {
            ImageIcon image = new ImageIcon(filePath);
            return image;
        } else {
            throw new Exception("The file provided is not an image file.");
        }
    }

    /**
     * Copies an image file to the 'covers' folder in the 'res' directory.
     * @param filePath the image file path
     * @throws Exception file is not an image file or an error occurred while moving the file
     */
    public static void copyCover(String filePath) throws Exception {
        if(isImageFile(filePath)) {
            copyToResources(filePath, "covers");
        } else {
            throw new Exception("File attempted to copy to the 'covers' folder is not an image file.");
        }
    }

    /**
     * Copies an image file to the 'songs' folder in the 'res' directory.
     * @param filePath the sound file path
     * @throws Exception file is not a sound file or an error occurred while moving the file
     */
    public static void copySong(String filePath) throws Exception {
        if(isSoundFile(filePath)) {
            copyToResources(filePath, "songs");
        } else {
            throw new Exception("File attempted to copy to the 'songs' folder is not a sound file.");
        }
    }

    /**
     * Copies a file at a given file path to a given folder in the 'res' directory.
     * @param filePath the file path
     * @param resFolderName the resource folder name
     * @throws Exception error occurred while moving the file
     */
    public static void copyToResources(String filePath, String resFolderName) throws Exception  {
        File file = new File(filePath);
        if(file.exists()) {
            Path sourcePath = Paths.get(filePath);
            Path destinationPath = Paths.get("res\\" + resFolderName);
            try {
                Files.move(sourcePath, destinationPath.resolve(sourcePath.getFileName()));
            } catch (IOException e) {
                throw new Exception("Error moving file: " + e.getMessage());
            }
        } else {
            throw new FileNotFoundException("File not found.");
        }
    }
}

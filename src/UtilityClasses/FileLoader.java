package UtilityClasses;

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
        if (extension != null && (extension.equalsIgnoreCase("mp3") || extension.equalsIgnoreCase("wav") || extension.equalsIgnoreCase("ogg") || extension.equalsIgnoreCase("flac"))) {
            return true;
        } else {
            System.out.println("File is not a sound file.");
            return false;
        }
    }
}

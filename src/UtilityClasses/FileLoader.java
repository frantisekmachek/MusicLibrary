package UtilityClasses;

import MusicClasses.Album;
import MusicClasses.Library;
import MusicClasses.Song;
import UserInterface.UserInterface;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
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

    public static AudioInputStream loadSoundFromFile(String filePath) throws Exception {
        boolean soundExists = isSoundFile(filePath);
        if(soundExists) {
            File soundFile = new File(filePath);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundFile);
            return audioInput;
        } else {
            throw new Exception("The file provided is not a sound file.");
        }
    }

    /**
     * Copies an image file to the 'covers' folder in the 'res' directory.
     * @param filePath the image file path
     * @throws Exception file is not an image file or an error occurred while moving the file
     */
    public static Path copyCover(String filePath) throws Exception {
        if(isImageFile(filePath)) {
            Path newPath = copyToResources(filePath, "covers");
            return newPath;
        } else {
            throw new Exception("File attempted to copy to the 'covers' folder is not an image file.");
        }
    }

    /**
     * Copies an image file to the 'songs' folder in the 'res' directory.
     * @param filePath the sound file path
     * @throws Exception file is not a sound file or an error occurred while moving the file
     * @returns new file path
     */
    public static Path copySong(String filePath) throws Exception {
        if(isSoundFile(filePath)) {
            Path newPath = copyToResources(filePath, "songs");
            return newPath;
        } else {
            throw new Exception("File attempted to copy to the 'songs' folder is not a sound file.");
        }
    }

    /**
     * Copies a file at a given file path to a given folder in the 'res' directory.
     * @param filePath the file path
     * @param resFolderName the resource folder name
     * @throws Exception error occurred while moving the file
     * @returns new file path
     */
    public static Path copyToResources(String filePath, String resFolderName) throws Exception  {
        File file = new File(filePath);
        if(file.exists()) {
            Path sourcePath = Paths.get(filePath);
            Path destinationPath = Paths.get("res\\" + resFolderName);
            try {
                Path newPath = destinationPath.resolve(sourcePath.getFileName());
                Files.move(sourcePath, newPath);
                return newPath;
            } catch (IOException e) {
                throw new Exception("Error moving file: " + e.getMessage());
            }
        } else {
            throw new FileNotFoundException("File not found.");
        }
    }

    /**
     * Creates the 'res' directory along with all of its subdirectories.
     */
    public static void createResourceDirectory() {
        String resPath = "res";
        File res = new File(resPath);

        if(!res.exists()) {
            res.mkdirs();
        }

        // Define subdirectory paths
        String songPath = "res\\songs";
        String coverPath = "res\\covers";
        String albumPath = "res\\albums";
        String playlistPath = "res\\playlists";

        // Make sure that all subdirectories exist
        if(res.exists()) {
            createFolder(songPath);
            createFolder(coverPath);
            createFolder(albumPath);
            createFolder(playlistPath);
        }

        createFolder(songPath + "\\songdata"); // Also create the songdata folder if it doesn't exist
    }

    /**
     * Tries to find a folder (File) at a given file path and if it doesn't exist, it is created.
     * @param path the folder path
     * @return true if the folder was created, false otherwise
     */
    public static boolean createFolder(String path) {
        File folder = new File(path);
        if(!folder.exists()) { // If the folder doesn't exist, try creating it and return true/false
            return folder.mkdirs();
        } else {
            return false;
        }
    }

    /**
     * Checks if a file exists at a given file path. It excludes directories.
     * @param filePath the file path
     * @return true if the file exists, false otherwise (or if it's a directory)
     */
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        if(file.exists()) {
            return !file.isDirectory();
        } else {
            return false;
        }
    }

    /**
     * Loads songs from the 'res\songs\songdata' directory.
     * @return all songs in a HashSet
     */
    public static HashSet<Song> loadSongsFromResources() {
        HashSet<Song> songs = new HashSet<>();
        Serializer<Song> serializer = new Serializer<>();
        String directoryPath = "res\\songs\\songdata";
        File directory = new File(directoryPath);
        if(directory.exists()) {
            if(directory.isDirectory()) {
                File[] files = directory.listFiles();
                if(files != null) {
                    for (File file : files) {
                        if(file.isFile()) {
                            Song song = serializer.deserializeObject(file.getPath());
                            if(song != null) {
                                songs.add(song);
                            }
                        }
                    }
                }
            }
        }
        return songs;
    }

    /**
     * Extracts the file name (gets rid of the extension).
     * @param file the file
     * @return file name without an extension
     */
    public static String getFileNameWithoutExtension(File file) {
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex != -1 && lastDotIndex != 0) {
            return fileName.substring(0, lastDotIndex);
        } else {
            return fileName;
        }
    }

    /**
     * Saves a song object.
     * @param song song object being saved
     */
    public static void saveSong(Song song) {
        String dataPath = getSongDataPath(song);
        Serializer<Song> songSer = new Serializer<>();
        songSer.serializeObject(song, dataPath);
    }

    /**
     * Finds a song's data file path.
     * @param song song
     * @return song data file path
     */
    private static String getSongDataPath(Song song) {
        File songFile = new File(song.getFilePath());
        String fileName = FileLoader.getFileNameWithoutExtension(songFile);
        String dataFilePath = "res\\songs\\songdata\\" + fileName + ".ser";
        return dataFilePath;
    }

    /**
     * Removes a song from the Library as well as the files.
     * @param song song being removed
     */
    public static void removeSong(Song song) {
        File songFile = new File(song.getFilePath());
        String dataPath = getSongDataPath(song);
        File dataFile = new File(dataPath);

        songFile.delete();
        dataFile.delete();

        Library.getInstance().removeSong(song);
    }

    /**
     * Removes an album from the files.
     * @param album album being removed
     */
    public static void removeAlbum(Album album) {
        File albumFile = new File(album.getFilePath());

        if(albumFile.exists()) {
            albumFile.delete();
        }

        Library.getInstance().removeAlbum(album);
    }

    /**
     * Saves an Album object.
     * @param album album being saved
     */
    public static void saveAlbum(Album album) {
        String filePath = album.getFilePath();
        if(filePath == null) {
            filePath = "res\\albums\\" + album.getArtist() + "-" + album.getTitle() + ".ser";
            album.setFilePath(filePath);
        }
        Serializer<Album> ser = new Serializer<>();
        ser.serializeObject(album, filePath);
    }

    /**
     * Loads all albums from the 'res' directory.
     * @return HashSet of all Album objects loaded from the 'res' directory
     */
    public static HashSet<Album> loadAlbumsFromResources() {
        HashSet<Album> albums = new HashSet<>();
        Serializer<Album> serializer = new Serializer<>();
        String directoryPath = "res\\albums";
        File directory = new File(directoryPath);
        if(directory.exists()) {
            if(directory.isDirectory()) {
                File[] files = directory.listFiles();
                if(files != null) {
                    for (File file : files) {
                        if(file.isFile()) {
                            Album album = serializer.deserializeObject(file.getPath());
                            if(album != null) {
                                albums.add(album);
                            }
                        }
                    }
                }
            }
        }
        return albums;
    }
}

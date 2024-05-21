package UtilityClasses;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Custom file filter which accepts certain extensions and ignores the "res" directory.
 */
public class CustomFileFilter extends FileFilter {
    private final FileNameExtensionFilter extensionFilter;

    public CustomFileFilter(FileNameExtensionFilter extensionFilter) {
        this.extensionFilter = extensionFilter;
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory() && "res".equalsIgnoreCase(file.getName())) {
            return false;
        }
        return file.isDirectory() || extensionFilter.accept(file);
    }

    @Override
    public String getDescription() {
        return extensionFilter.getDescription();
    }
}

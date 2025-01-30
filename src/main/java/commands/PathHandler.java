package commands;

import exception.JessicaException;
import jessica.Jessica;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathHandler {
    public static String findStoragePath() throws JessicaException, URISyntaxException {
        String currentPath = Jessica.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();

        // Fix the path for Windows
        if (System.getProperty("os.name").toLowerCase().contains("win") && currentPath.startsWith("/")) {
            currentPath = currentPath.substring(1);  // Remove leading slash
        }

        Path path = Paths.get(currentPath);
        Path parentPath = path.getParent();

        // Construct the storage path and normalize separators
        String storagePath = parentPath.toString() + "/.data/jessica.txt";
        storagePath = storagePath.replace("\\", "/");  // Ensure forward slashes for consistency

        return storagePath;
    }
}

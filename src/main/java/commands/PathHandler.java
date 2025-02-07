package commands;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import exception.JessicaException;
import jessica.Jessica;

/**
 * Handles operations related to determining the storage path for the application.
 * This class constructs and returns the path to the storage file where tasks are saved.
 */
public class PathHandler {

    /**
     * Finds and returns the storage path for the application data file.
     * The method constructs the path relative to the location of the application's executable or JAR file.
     * It also handles path adjustments for different operating systems, particularly Windows.
     *
     * @return The normalized storage path to the data file as a string.
     * @throws JessicaException    If an error occurs while processing the path (application-specific error).
     * @throws URISyntaxException  If the URI syntax of the path is invalid.
     */
    public static String findStoragePath() throws JessicaException, URISyntaxException {
        String currentPath = Jessica.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();

        // Fix the path for Windows
        if (System.getProperty("os.name").toLowerCase().contains("win") && currentPath.startsWith("/")) {
            currentPath = currentPath.substring(1); // Remove leading slash
        }

        Path path = Paths.get(currentPath);
        Path parentPath = path.getParent();

        // Construct the storage path and normalize separators
        String storagePath = parentPath.toString() + "/.data/jessica.txt";
        storagePath = storagePath.replace("\\", "/"); // Ensure forward slashes for consistency

        return storagePath;
    }
}

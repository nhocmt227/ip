package commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.DosFileAttributeView;
import java.util.List;
import java.util.Scanner;

import exception.JessicaException;
import tasks.Task;

/**
 * Handles storage operations such as loading and saving tasks to and from disk.
 * It ensures proper file handling, including file creation, data reading, and writing.
 */
public class StorageHandler {

    private final String filePath;

    /**
     * Constructor to initialize the storage handler with a file path.
     *
     * @param filePath The path to the file where tasks will be stored.
     */
    public StorageHandler(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the disk file into memory.
     * If the file or parent directories do not exist, they are created.
     *
     * @param list The list where tasks will be loaded.
     * @throws IOException       If an I/O error occurs.
     * @throws JessicaException  If an error occurs while parsing task data.
     */
    public void loadDiskToMem(List<Task> list) throws IOException, JessicaException {
        Path filePath = Paths.get(this.filePath);
        Path parentDir = filePath.getParent();

        // Ensure the parent directory exists
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);

            // Make the parent directory hidden (Windows only)
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                Files.getFileAttributeView(parentDir, DosFileAttributeView.class).setHidden(true);
            } else {
                // Rename for Linux/macOS to start with `.`
                Path hiddenParentDir = parentDir.resolveSibling("." + parentDir.getFileName());
                Files.move(parentDir, hiddenParentDir, StandardCopyOption.REPLACE_EXISTING);
                parentDir = hiddenParentDir;
                filePath = hiddenParentDir.resolve(filePath.getFileName()); // Update filePath
            }
        }

        // Create the file if it doesn't exist
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);

        }

        // Read and load file content
        try (Scanner scanner = new Scanner(filePath.toFile())) {
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                loadLineToMem(list, data);
            }
        }
    }

    /**
     * Parses a line of data from the file and adds the corresponding task to the list.
     *
     * @param list The list to which the task will be added.
     * @param line A line of task data from the file.
     * @throws JessicaException If an error occurs while parsing the line.
     */
    public void loadLineToMem(List<Task> list, String line) throws JessicaException {
        if (line.isEmpty()) {
            return;
        }
        Task task = Converter.dataLineToTask(line);
        list.add(task);
    }

    /**
     * Stores all tasks from memory to the disk file.
     * This method overwrites the file content.
     *
     * @param list The list of tasks to be stored.
     * @throws IllegalArgumentException If the list is null or invalid.
     * @throws IOException              If an I/O error occurs during file writing.
     */
    public void storeMemToDisk(List<Task> list) throws IOException {
        Path filePath = Paths.get(this.filePath);
        Path parentDir = filePath.getParent();

        // Ensure the parent directory exists
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);

            // Make the parent directory hidden (Windows only)
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                Files.getFileAttributeView(parentDir, DosFileAttributeView.class).setHidden(true);
            } else {
                // Rename for Linux/macOS to start with `.`
                Path hiddenParentDir = parentDir.resolveSibling("." + parentDir.getFileName());
                Files.move(parentDir, hiddenParentDir, StandardCopyOption.REPLACE_EXISTING);
                parentDir = hiddenParentDir;
                filePath = hiddenParentDir.resolve(filePath.getFileName()); // Update filePath
            }
        }

        // Ensure the file exists and is hidden
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);

            // Make the file hidden (Windows only)
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                Files.getFileAttributeView(filePath, DosFileAttributeView.class).setHidden(true);
            } else {
                // Rename for Linux/macOS to start with `.`
                Path hiddenFilePath = filePath.resolveSibling("." + filePath.getFileName());
                Files.move(filePath, hiddenFilePath, StandardCopyOption.REPLACE_EXISTING);
                filePath = hiddenFilePath;
            }
        }

        // Write data to the hidden file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            for (Task task : list) {
                writer.write(Converter.taskToDataLine(task));
                writer.newLine(); // Add newline after each task for readability
            }
        }
    }

    /**
     * Appends a single task to the disk file.
     *
     * @param task The task to be stored.
     * @throws IOException If an I/O error occurs during file writing.
     */
    public void storeTaskToDisk(Task task) throws IOException {
        String line = Converter.taskToDataLine(task);
        // todo store to file
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath, true));
        writer.write(line);
        writer.close();
    }
}

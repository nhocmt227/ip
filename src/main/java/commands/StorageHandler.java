package commands;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

        // Ensure the parent directory (.data) exists
        Path parentDir = filePath.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);  // Create directories if they don't exist
        }

        // Create the file if it doesn't exist
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }

        Scanner scanner = new Scanner(filePath.toFile());
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            loadLineToMem(list, data);
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
    public void storeMemToDisk(List<Task> list) throws IllegalArgumentException, IOException {
        FileWriter fw = new FileWriter(this.filePath);
        StringBuilder sb = new StringBuilder();
        for (Task task : list) {
            String s = Converter.taskToDataLine(task);
            sb.append(s);
        }
        fw.write(sb.toString());
        fw.close();
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

    /**
     * Refactors the task storage file by clearing and rewriting all tasks.
     * This removes unnecessary or malformed lines in the file.
     *
     * @throws IOException       If an I/O error occurs during file operations.
     * @throws JessicaException  If an error occurs while loading or parsing tasks.
     */
    public void refactor() throws IOException, JessicaException {
        List<Task> list = new ArrayList<>();
        loadDiskToMem(list);
        clearFileContent();
        storeMemToDisk(list);
    }

    /**
     * Clears all content in the task storage file.
     *
     * @throws IOException If an I/O error occurs during file writing.
     */
    public void clearFileContent() throws IOException {
        FileWriter writer = new FileWriter(this.filePath);
        writer.write("");
        writer.close();
    }
}

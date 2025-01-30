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

public class StorageHandler {

    private final String filePath;

    public StorageHandler(String filePath) {
        this.filePath = filePath;
    }

    // Methods to load
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

    public void loadLineToMem(List<Task> list, String line) throws JessicaException {
        if (line.isEmpty()) {
            return;
        }
        Task task = Converter.dataLineToTask(line);
        list.add(task);
    }

    // Methods to store
    // Erasing all content in the file and write from the start
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

    // Method to insert a new line to the end of file
    public void storeTaskToDisk(Task task) throws IOException {
        String line = Converter.taskToDataLine(task);
        // todo store to file
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath, true));
        writer.write(line);
        writer.close();
    }

    // This method is used to compact the jessica.txt file, removing all the unnecessary lines
    public void refactor() throws IOException, JessicaException {
        List<Task> list = new ArrayList<>();
        loadDiskToMem(list);
        clearFileContent();
        storeMemToDisk(list);
    }

    public void clearFileContent() throws IOException {
        FileWriter writer = new FileWriter(this.filePath);
        writer.write("");
        writer.close();
    }
}

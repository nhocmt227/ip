package commands;

import java.io.*;
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
        File file = new File(this.filePath);
        // Check if the file exists
        if (!file.exists()) {
            file.createNewFile();
        }
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            System.out.println("Data: [" + data + "]");
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

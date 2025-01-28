package commands;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.io.File;

import exception.JessicaException;
import tasks.Task;
import tasks.ToDo;
import tasks.Deadline;
import tasks.Event;

public class StorageHandler {
    public static void loadDiskToMem(String filePath, List<Task> list) throws IOException, JessicaException {
        File file = new File(filePath);
        // Check if the file exists
        if (!file.exists()) {
            file.createNewFile();
        }
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            loadLineToMem(list, data);
        }
    }

    public static void loadLineToMem(List<Task> list, String line) throws JessicaException {
        if (line.isEmpty()) { // EOF
            return;
        }
        String[] parts = line.split("\\|");

        // Validate input format
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid line format: " + line);
        }

        char taskType = line.charAt(0); // Task type (T, D, E)
        boolean isDone = parts[1].equals("1");
        String message = parts[2];

        switch (taskType) {
            case 'T': // To-Do
                list.add(new ToDo(message, isDone));
                break;
            case 'D': { // Deadline
                if (parts.length < 4) {
                    throw new IllegalArgumentException("Invalid deadline format: " + line);
                }
                String deadline = parts[3];
                list.add(new Deadline(message, Converter.stringToDate(deadline), isDone));
                break;
            }
            case 'E': { // Event
                if (parts.length < 5) {
                    throw new IllegalArgumentException("Invalid event format: " + line);
                }
                String begin = parts[3];
                String end = parts[4];
                list.add(new Event(message, Converter.stringToDate(begin), Converter.stringToDate(end), isDone));
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown task type: " + taskType);
        }
    }


    public static void storeMemToDisk(String filePath, List<Task> list) throws IllegalArgumentException, IOException {
        FileWriter fw = new FileWriter(filePath);
        StringBuilder sb = new StringBuilder();
        for (Task task : list) {
            String s = "";
            if (task instanceof ToDo) {
                if (task.getDescription().contains("|")) {
                    throw new IllegalArgumentException("message cannot contain |");
                }
                s += "T|";
                s += task.getDone() ? "1|" : "0|";
                s += task.getDescription();
                s += "\n";
            } else if (task instanceof Deadline) {
                if (task.getDescription().contains("|") || ((Deadline) task).getDeadline().toString().contains("|")) {
                    throw new IllegalArgumentException("message cannot contain |");
                }
                s += "D|";
                s += task.getDone() ? "1|" : "0|";
                s += task.getDescription() + "|";
                s += ((Deadline) task).getDeadline();
                s += "\n";
            } else if (task instanceof Event) {
                if (task.getDescription().contains("|")) {
                    throw new IllegalArgumentException("message cannot contain |");
                }
                if (((Event) task).getBegin().toString().contains("|")) {
                    throw new IllegalArgumentException("message cannot contain |");
                }
                if (((Event) task).getEnd().toString().contains("|")) {
                    throw new IllegalArgumentException("message cannot contain |");
                }
                s += "T|";
                s += task.getDone() ? "1|" : "0|";
                s += task.getDescription() + "|";
                s += ((Event) task).getBegin() + "|";
                s += ((Event) task).getEnd();
                s += "\n";
            } else {
                throw new IllegalArgumentException("Unknown error");
            }
            sb.append(s);
        }
        fw.write(sb.toString());
        fw.close();
    }
}

package commands;

import exception.JessicaException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Converter {
    // Example: 2022-11-11 -> new LocalDate()
    public static LocalDate stringToDate(String deadline) throws DateTimeParseException {
        deadline = deadline.strip();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return LocalDate.parse(deadline, formatter);
    }

    // Example: LocalDate ld -> Oct 12 2022
    public static String dateToFormattedString(LocalDate date) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);
        return date.format(outputFormatter);
    }

    public static Task dataLineToTask(String line) throws JessicaException {
        if (line.isEmpty()) {
            return null;
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
            case 'T':// To-Do
                return new ToDo(message, isDone);
            case 'D': // Deadline
                if (parts.length < 4) {
                    throw new IllegalArgumentException("Invalid deadline format: " + line);
                }
                String deadline = parts[3];
                return new Deadline(message, Converter.stringToDate(deadline), isDone);
            case 'E': // Event
                if (parts.length < 5) {
                    throw new IllegalArgumentException("Invalid event format: " + line);
                }
                String begin = parts[3];
                String end = parts[4];
                return new Event(message, Converter.stringToDate(begin), Converter.stringToDate(end), isDone);
            default:
                throw new IllegalArgumentException("Unknown task type: " + taskType);
        }
    }

    public static String taskToDataLine(Task task) {
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
            s += "E|";
            s += task.getDone() ? "1|" : "0|";
            s += task.getDescription() + "|";
            s += ((Event) task).getBegin() + "|";
            s += ((Event) task).getEnd();
            s += "\n";
        } else {
            throw new IllegalArgumentException("Unknown error");
        }
        return s;
    }
}

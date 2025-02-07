package commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import exception.JessicaException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;



/**
 * Handles conversions between strings, dates, and tasks for storage and display purposes.
 * This class helps with parsing and formatting task data from and to storage format.
 */
public class Converter {

    /**
     * Converts a date string in ISO format (yyyy-MM-dd) to a {@code LocalDate} object.
     *
     * @param deadline The date string to convert.
     * @return The parsed {@code LocalDate} object.
     * @throws DateTimeParseException If the date string is not in a valid format.
     */
    public static LocalDate stringToDate(String deadline) throws DateTimeParseException {
        deadline = deadline.strip();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return LocalDate.parse(deadline, formatter);
    }

    /**
     * Formats a {@code LocalDate} object to a string in the format "MMM dd yyyy" (e.g., Oct 12 2022).
     *
     * @param date The {@code LocalDate} to format.
     * @return The formatted date string.
     */
    public static String dateToFormattedString(LocalDate date) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);
        return date.format(outputFormatter);
    }

    /**
     * Converts a line of data from storage format to a {@code Task} object.
     *
     * @param line The data line to convert.
     * @return The corresponding {@code Task} object.
     * @throws JessicaException         If a task-specific error occurs.
     * @throws IllegalArgumentException If the line format is invalid or unknown.
     */
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

    /**
     * Converts a {@code Task} object to a string formatted for storage.
     *
     * @param task The task to convert.
     * @return The formatted data line representing the task.
     * @throws IllegalArgumentException If the task contains invalid characters (e.g., the '|' character)
     *      or if the task type is unknown.
     */
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
            if (((Event) task).getStartDate().toString().contains("|")) {
                throw new IllegalArgumentException("message cannot contain |");
            }
            if (((Event) task).getEndDate().toString().contains("|")) {
                throw new IllegalArgumentException("message cannot contain |");
            }
            s += "E|";
            s += task.getDone() ? "1|" : "0|";
            s += task.getDescription() + "|";
            s += ((Event) task).getStartDate() + "|";
            s += ((Event) task).getEndDate();
            s += "\n";
        } else {
            throw new IllegalArgumentException("Unknown error");
        }
        return s;
    }
}

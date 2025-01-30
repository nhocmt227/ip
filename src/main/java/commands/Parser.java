package commands;

import exception.JessicaException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

// Methods to identify or get input patterns
public class Parser {

    // No index checking with the list length
    public static int getMarkIndex(String input) throws JessicaException {
        try {
            String[] parts = input.split("mark\\s+", 2);
            if (parts.length == 1) {
                throw new JessicaException("Mark index must not be empty, try again");
            }
            int index = Integer.parseInt(parts[1].trim(), 10);
            if (index <= 0) {
                throw new JessicaException("Mark index must be a positive number, try again");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new JessicaException("Mark index must be a number, try again");
        }
    }

    public static int getUnmarkIndex(String input) throws JessicaException {
        try {
            String[] parts = input.split("unmark\\s+", 2);
            if (parts.length == 1) {
                throw new JessicaException("Unmark index must not be empty, try again");
            }
            int index = Integer.parseInt(parts[1].trim(), 10);
            if (index <= 0) {
                throw new JessicaException("Unmark index must be a positive number, try again");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new JessicaException("Unmark index must be a number, try again");
        }
    }

    public static int getDeleteIndex(String input) throws JessicaException {
        try {
            String[] parts = input.split("delete\\s+", 2);
            if (parts.length == 1) {
                throw new JessicaException("Delete index must not be empty, try again");
            }
            int index = Integer.parseInt(parts[1].trim(), 10);
            if (index <= 0) {
                throw new JessicaException("Delete index must be a positive number, try again");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new JessicaException("Delete index must be a number, try again");
        }
    }

    public static String getToDoDescription(String input) throws JessicaException {
        String[] parts = input.split("todo\\s+", 2);
        if (parts.length == 1) {
            throw new JessicaException("Todo description cannot be empty");
        }
        String description = parts[1];
        if (description.trim().isEmpty()) {
            throw new JessicaException("Todo description cannot be empty");
        } else {
            return description.strip();
        }
    }

    public static String getDeadlineDescription(String input) throws JessicaException {
        String[] parts = input.split("deadline\\s+", 2);
        if (parts.length == 1) {
            throw new JessicaException("Wrong deadline format, try again");
        }
        String[] bodyParts = parts[1].split("\\s+/by\\s+", 2);
        if (bodyParts[0].trim().isEmpty()) {
            throw new JessicaException("Wrong deadline format, try again");
        }
        return bodyParts[0].strip();
    }

    public static String getDeadlineDate(String input) throws JessicaException {
        String[] parts = input.split("deadline\\s+", 2);
        if (parts.length == 1) {
            throw new JessicaException("Wrong deadline format, try again");
        }
        String[] bodyParts = parts[1].split("\\s+/by\\s+", 2);
        if (bodyParts.length == 1 || bodyParts[1].trim().isEmpty()) {
            throw new JessicaException("Wrong deadline format, try again");
        }
        return bodyParts[1].strip();
    }

    public static String getEventDescription(String input) throws JessicaException {
        String[] parts = input.split("event\\s+", 2);
        if (parts.length == 1) {
            throw new JessicaException("Wrong event format, try again");
        }
        String[] bodyParts = parts[1].split("\\s+/from\\s+", 2);
        if (bodyParts[0].trim().isEmpty()) {
            throw new JessicaException("Wrong event format, try again");
        }
        return bodyParts[0].strip();
    }

    public static String getEventBeginDate(String input) throws JessicaException {
        String[] parts = input.split("event\\s+", 2);
        if (parts.length == 1) {
            throw new JessicaException("Wrong event format, try again");
        }
        String[] bodyParts = parts[1].split("\\s+/from\\s+", 2);
        if (bodyParts.length == 1 || bodyParts[1].trim().isEmpty()) {
            throw new JessicaException("Wrong event format, try again");
        }
        bodyParts = bodyParts[1].split("\\s+/to\\s+", 2);
        if (bodyParts[0].trim().isEmpty()) {
            throw new JessicaException("Wrong event format, try again");
        }
        return bodyParts[0].strip();
    }

    public static String getEventEndDate(String input) throws JessicaException {
        String[] parts = input.split("event\\s+", 2);
        if (parts.length == 1) {
            throw new JessicaException("Wrong event format, try again");
        }
        String[] bodyParts = parts[1].split("\\s+/from\\s+", 2);
        if (bodyParts.length == 1 || bodyParts[1].trim().isEmpty()) {
            throw new JessicaException("Wrong event format, try again");
        }
        bodyParts = bodyParts[1].split("\\s+/to\\s+", 2);
        if (bodyParts.length == 1 || bodyParts[1].trim().isEmpty()) {
            throw new JessicaException("Wrong event format, try again");
        }
        return bodyParts[1].strip();
    }

    public static String getFindDescription(String input) throws JessicaException {
        input = input.strip();  // Remove leading and trailing whitespace
        if (!input.startsWith("find ")) {
            throw new JessicaException("Wrong find description, try again");
        }

        String description = input.substring(5).strip();  // Get everything after "find "
        if (description.isEmpty()) {
            throw new JessicaException("No description provided, try again");
        }

        return description;
    }

    public static String[] parse(String input) {
        /*
         * This method parses all the tag in that input
         * Example use: input = "mark 1  2"
         *              result = ["mark", "1", "2"]
         * */
        input = input.trim();
        // "\\s+" is a regular expression for one or more spaces
        String[] parts = input.split("\\s+");
        return parts;
    }

    public static boolean detectBye(String input) {
        return input.trim().equals("bye");
    }

}

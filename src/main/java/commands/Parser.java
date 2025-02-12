package commands;

import exception.JessicaException;

/**
 * Handles parsing of user input for various task commands.
 * Provides methods to extract and validate command parameters such as task descriptions and indices.
 */
public class Parser {

    /**
     * Extracts and returns the index for the mark command from the user input.
     *
     * @param input The user's input command.
     * @return The task index to be marked.
     * @throws JessicaException If the index is missing, not a number, or not positive.
     */
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

    /**
     * Extracts and returns the index for the unmark command from the user input.
     *
     * @param input The user's input command.
     * @return The task index to be unmarked.
     * @throws JessicaException If the index is missing, not a number, or not positive.
     */
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

    /**
     * Extracts and returns the index for the delete command from the user input.
     *
     * @param input The user's input command.
     * @return The task index to be deleted.
     * @throws JessicaException If the index is missing, not a number, or not positive.
     */
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

    /**
     * Extracts and returns the description for the ToDo command from the user input.
     *
     * @param input The user's input command.
     * @return The ToDo task description.
     * @throws JessicaException If the description is missing or empty.
     */
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

    /**
     * Extracts and returns the description for the Deadline command from the user input.
     *
     * @param input The user's input command.
     * @return The Deadline task description.
     * @throws JessicaException If the description or format is incorrect.
     */
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

    /**
     * Extracts and returns the deadline date from the user input.
     *
     * @param input The user's input command.
     * @return The deadline date as a string.
     * @throws JessicaException If the date is missing or the format is incorrect.
     */
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

    /**
     * Extracts and returns the description for the Event command from the user input.
     *
     * @param input The user's input command.
     * @return The Event task description.
     * @throws JessicaException If the description or format is incorrect.
     */
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

    /**
     * Extracts and returns the event start date from the user input.
     *
     * @param input The user's input command.
     * @return The event start date as a string.
     * @throws JessicaException If the date is missing or the format is incorrect.
     */
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

    /**
     * Extracts and returns the event end date from the user input.
     *
     * @param input The user's input command.
     * @return The event end date as a string.
     * @throws JessicaException If the date is missing or the format is incorrect.
     */
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

    /**
     * Extracts and returns the description from the "find" command input.
     * This method validates the input format and ensures that a description is provided.
     *
     * @param input The user's input command string.
     * @return The extracted description string.
     * @throws JessicaException If the input does not start with "find " or if the description is missing.
     */
    public static String getFindDescription(String input) throws JessicaException {
        input = input.strip(); // Remove leading and trailing whitespace
        if (!input.startsWith("find ")) {
            throw new JessicaException("Wrong find description, try again");
        }

        String description = input.substring(5).strip(); // Get everything after "find "
        if (description.isEmpty()) {
            throw new JessicaException("No description provided, try again");
        }

        return description;
    }

    /**
     * Detects if the input command is the "bye" command.
     *
     * @param input The user's input command.
     * @return True if the command is "bye", otherwise false.
     */
    public static boolean detectBye(String input) {
        return input.trim().equals("bye");
    }

}

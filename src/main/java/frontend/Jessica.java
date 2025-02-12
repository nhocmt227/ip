package frontend;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import commands.LogicHandler;
import commands.Parser;
import commands.PathHandler;
import commands.StorageHandler;
import commands.UI;
import exception.JessicaException;
import tasks.Task;

/**
 * The main logic class for the Jessica chatbot application.
 */
public class Jessica {

    /**
     * The list of tasks used to store task information in memory.
     */
    private static final List<Task> list = new ArrayList<>();
    private final StorageHandler storageHandler;
    private final LogicHandler logicHandler;

    /**
     * Enum representing the different command tags that the chatbot recognizes.
     */
    public enum Tag {
        LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND
    }

    /**
     * Constructor that initializes the chatbot and handles storage.
     */
    public Jessica() {
        // Get path to storage
        String storagePath = "";
        try {
            storagePath = PathHandler.findStoragePath();
        } catch (JessicaException e) {
            System.out.println("Error finding storage path: " + e.getMessage());
        } catch (URISyntaxException e) {
            System.out.println("Unknown error in PathHandler: " + e.getMessage());
        }

        // Initialization
        this.storageHandler = new StorageHandler(storagePath);
        this.logicHandler = new LogicHandler(storageHandler, list);

        // Load data from hard disk to list
        try {
            storageHandler.loadDiskToMem(list);
        } catch (JessicaException e) {
            String s1 = "Error: " + e.getMessage();
            String s2 = "The storage file has been corrupted.";
            UI.prettyPrintArray(new String[] { s1, s2 });
        } catch (Exception e) {
            String s1 = "Error: " + e.getMessage();
            String s2 = "Error in storage handling.";
            UI.prettyPrintArray(new String[] { s1, s2 });
        }
        System.out.println("Log init path: " + storagePath);
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {

        if (input.trim().isEmpty()) { // Empty input
            return UI.getPrettyArray(new String[] { "Cannot add an empty task, try again" });
        }

        try {
            String output = "";
            Tag tag = getFirstTag(input);

            // Process commands based on tag
            output = switch (tag) {
                case LIST -> logicHandler.handleList(input);
                case FIND -> logicHandler.handleFind(input);
                case MARK -> logicHandler.handleMark(input);
                case UNMARK -> logicHandler.handleUnmark(input);
                case TODO -> logicHandler.handleToDo(input);
                case DEADLINE -> logicHandler.handleDeadline(input);
                case EVENT -> logicHandler.handleEvent(input);
                case DELETE -> logicHandler.handleDelete(input);
                default -> "Unknown command, try again.";
            };

            // Save tasks to storage
            try {
                storageHandler.storeMemToDisk(list);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                output = "Unable to save to storage.";
            } catch (Exception e) {
                System.out.println(e.getMessage());
                output = "Error saving to storage: " + e.getMessage();
            }

            return output;

        } catch (IllegalArgumentException e) {
            // Unknown tag, prompt user to try again
            System.out.println(e.getMessage());
            return UI.getPrettyArray(new String[] { "Please specify the type of task." });
        }
    }

    /**
     * Parses the user's input to determine the command type.
     *
     * @param input The user's input string.
     * @return The corresponding {@code Tag} enum representing the command.
     * @throws IllegalArgumentException If the command is not recognized.
     */
    public static Tag getFirstTag(String input) {
        input = input.trim();

        // Ensure input is not empty
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty.");
        }

        // Extract the first word as the tag
        String tagStr = input.split("\\s+", 2)[0];

        try {
            return Tag.valueOf(tagStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid command: " + tagStr);
        }
    }
}





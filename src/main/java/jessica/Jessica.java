package jessica;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exception.JessicaException;
import tasks.Task;
import commands.UI;
import commands.LogicHandler;
import commands.Parser;
import commands.StorageHandler;
import commands.PathHandler;

/**
 * The main class for the Jessica chatbot application.
 * This class initializes the chatbot, handles user input, manages tasks, and interacts with storage.
 */
public class Jessica {

    /**
     * The list of tasks used to store task information in memory.
     */
    private static List<Task> list = new ArrayList<>();

    /**
     * Enum representing the different command tags that the chatbot recognizes.
     */
    public enum Tag {
        LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND
    }

    /**
     * The main method of the Jessica application.
     * It initializes storage, loads tasks, and continuously handles user input until the "bye" command is issued.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        // get path to storage
        String storagePath = "";
        try {
            storagePath = PathHandler.findStoragePath();
        } catch (JessicaException e) {
            System.out.println(e.getMessage());
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
            System.out.println("Unknown error in PathHandler");
        }

        // Initialization
        StorageHandler storageHandler = new StorageHandler(storagePath);
        Scanner scanner = new Scanner(System.in);
        LogicHandler logicHandler = new LogicHandler(storageHandler, list);


        // Load data from hard disk to list
        try {
            storageHandler.loadDiskToMem(list);
        } catch (JessicaException e) {
            String s1 = "Error: " + e;
            String s2 = "The storage file has been corrupted";
            UI.prettyPrintArray(new String[] {s1, s2});
            return;
        } catch (Exception e) {
            String s1 = "Error: " + e;
            String s2 = "Error in storage handling";
            UI.prettyPrintArray(new String[] {s1, s2});
            return;
        }

        // Main application logic
        UI.chatbotHello();
        while (true) {
            String input = scanner.nextLine();
            if (Parser.detectBye(input)) { // Terminate the program
                break;
            }
            if (input.trim().isEmpty()) { // Empty input
                String s = "Cannot add an empty task, try again";
                UI.prettyPrintArray(new String[] {s});
                continue;
            }
            try {
                Tag tag = getFirstTag(input);
                switch (tag) {
                    case LIST:
                        logicHandler.handleList(input);
                        break;
                    case FIND:
                        logicHandler.handleFind(input);
                        break;
                    case MARK:
                        logicHandler.handleMark(input);
                        break;
                    case UNMARK:
                        logicHandler.handleUnmark(input);
                        break;
                    case TODO:
                        logicHandler.handleToDo(input);
                        break;
                    case DEADLINE:
                        logicHandler.handleDeadline(input);
                        break;
                    case EVENT:
                        logicHandler.handleEvent(input);
                        break;
                    case DELETE:
                        logicHandler.handleDelete(input);
                        break;
                    default:
                        System.out.println("Unknown command, try again");
                        break;
                }
            } catch (IllegalArgumentException e) { // Unknown tag, try again
                String s1 = "Please specify the type of task";
                UI.prettyPrintArray(new String[] {s1});
            }
        }
        UI.chatbotGoodbye();

        // Store data from list to the hard disk
        try {
            storageHandler.storeMemToDisk(list);
        } catch (IOException e) {
            System.out.println("Unable to save to storage");
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        String tagStr = input.split("\\s+", 2)[0];
        return Jessica.Tag.valueOf(tagStr.toUpperCase());
    }

}

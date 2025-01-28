import java.io.File;
import java.io.IOException;
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

public class Jessica {

    // An arraylist of tasks.Task to store the Tasks information
    private static List<Task> list = new ArrayList<>();

    public enum Tag {
        LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE
    }



    public static void main(String[] args) {
        // get path to storage
        File currentFile = new File("");
        String storagePath = "";
        try {
            String currentDirPath = currentFile.getCanonicalPath();
            storagePath = PathHandler.findStoragePath(currentDirPath);
        } catch (JessicaException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Unknown error: " + e.getMessage());
        }

        StorageHandler storageHandler = new StorageHandler(storagePath);
        Scanner scanner = new Scanner(System.in);
        LogicHandler logicHandler = new LogicHandler(storageHandler);


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

        // main logic
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
                        logicHandler.handleList(input, list);
                        break;
                    case MARK:
                        logicHandler.handleMark(input, list);
                        break;
                    case UNMARK:
                        logicHandler.handleUnmark(input, list);
                        break;
                    case TODO:
                        logicHandler.handleToDo(input, list);
                        break;
                    case DEADLINE:
                        logicHandler.handleDeadline(input, list);
                        break;
                    case EVENT:
                        logicHandler.handleEvent(input, list);
                        break;
                    case DELETE:
                        logicHandler.handleDelete(input, list);
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

        // store data from list to the hard disk
        try {
            storageHandler.storeMemToDisk(list);
        } catch (IOException e) {
            System.out.println("Unable to save to storage");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Tag getFirstTag(String input) {
        input = input.trim();
        String tagStr = input.split("\\s+", 2)[0];
        return Jessica.Tag.valueOf(tagStr.toUpperCase());
    }

}

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
        LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, BYE
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

        // Load data from hard disk to list
        try {
            StorageHandler.loadDiskToMem(storagePath, list);
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
        Scanner scanner = new Scanner(System.in);
        UI.chatbotHello();
        while (true) {
            String input = scanner.nextLine();
            if (Parser.detectBye(input)) {
                break;
            }
            if (input.trim().isEmpty()) {
                String s = "Cannot add an empty task, try again";
                UI.prettyPrintArray(new String[] {s});
                continue;
            }
            try {
                Tag tag = getFirstTag(input);
                switch (tag) {
                    case LIST:
                        LogicHandler.handleList(input, list);
                        break;
                    case MARK:
                        LogicHandler.handleMark(input, list);
                        break;
                    case UNMARK:
                        LogicHandler.handleUnmark(input, list);
                        break;
                    case TODO:
                        LogicHandler.handleToDo(input, list);
                        break;
                    case DEADLINE:
                        LogicHandler.handleDeadline(input, list);
                        break;
                    case EVENT:
                        LogicHandler.handleEvent(input, list);
                        break;
                    case DELETE:
                        LogicHandler.handleDelete(input, list);
                        break;
                    default:
                        System.out.println("Unknown command, try again");
                        break;
                }
            } catch (IllegalArgumentException e) { // just add a simple task
                String s1 = "Please specify the type of task";
                UI.prettyPrintArray(new String[] {s1});
            }
        }
        UI.chatbotGoodbye();

        // store data from list to the hard disk
        try {
            StorageHandler.storeMemToDisk(storagePath, list);
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

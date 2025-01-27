import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exception.JessicaException;
import tasks.Task;
import commands.UI;
import commands.Handler;
import commands.Parser;
import commands.StorageHandler;
import commands.PathHandler;

public class Jessica {

    // An arraylist of tasks.Task to store the Tasks information
    private static List<Task> list = new ArrayList<>();

    public static enum Tag {
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
            StorageHandler.loadTaskFromFile(storagePath, list);
        } catch (IOException e) {
            System.out.println("Unable to open the file storage");
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
                System.out.println("Cannot add an empty task, try again");
                continue;
            }
            try {
                Tag tag = getFirstTag(input);
                switch (tag) {
                    case LIST:
                        Handler.handleList(input, list);
                        break;
                    case MARK:
                        Handler.handleMark(input, list);
                        break;
                    case UNMARK:
                        Handler.handleUnmark(input, list);
                        break;
                    case TODO:
                        Handler.handleToDo(input, list);
                        break;
                    case DEADLINE:
                        Handler.handleDeadline(input, list);
                        break;
                    case EVENT:
                        Handler.handleEvent(input, list);
                        break;
                    case DELETE:
                        Handler.handleDelete(input, list);
                        break;
                    default:
                        System.out.println("Unknown command, try again");
                        break;
                }
            } catch (IllegalArgumentException e) { // just add a simple task
                System.out.println("Please specify the type of task");
            }
        }
        UI.chatbotGoodbye();

        // store data from list to the hard disk
        try {
            StorageHandler.storeTaskToFile(storagePath, list);
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

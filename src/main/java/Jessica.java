import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import tasks.Task;
import commands.UI;
import commands.Handler;
import commands.Parser;

public class Jessica {

    // An arraylist of tasks.Task to store the Tasks information
    private static List<Task> list = new ArrayList<>();
    public static enum Tag {
        LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, BYE
    }

    public static void main(String[] args) {
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
            } catch (IllegalArgumentException e) {
                list.add(new Task(input));
                UI.prettyPrint("added: " + input);
                continue;
            }
        }
        UI.chatbotGoodbye();
    }

    public static Tag getFirstTag(String input) {
        input = input.trim();
        String tagStr = input.split("\\s+", 2)[0];
        return Jessica.Tag.valueOf(tagStr.toUpperCase());
    }

}

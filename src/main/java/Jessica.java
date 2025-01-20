import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jessica {

    // An arraylist of Task to store the Tasks information
    private static List<Task> list = new ArrayList<>();

    // Methods to decorate input
    public static String decorateInput(String input) {
        return "    ____________________________________________________________\n"
                + "     "
                + input
                + "\n"
                + "    ____________________________________________________________\n";
    }
    public static String decorateListOfInput(List<?> list) {
        String result = "    ____________________________________________________________\n";
        for (int i = 0; i < list.size(); i++) {
            String temp = "     " + (i + 1) + ". " + list.get(i).toString() + "\n";
            result += temp;
        }
        result += "    ____________________________________________________________\n";
        return result;
    }
    public static void prettyPrint(String input) {
        System.out.println(decorateInput(input));
    }
    public static void prettyPrintList(List<?> list) {
        System.out.println(decorateListOfInput(list));
    }
    public static void printAddedTask(Task task) {
        String content = "Got it. I've added this task:\n"
                + "       "
                + task.toString() + "\n"
                + "     Now you have " + printSize() + " in the list.";
        prettyPrint(content);
    }

    // Methods to handle logics
    public static void handleMark(int index) {
        Task task = list.get(index - 1);
        task.setDone(true);
        String input = "Nice! I've marked this task as done:\n" + "       " + task.toString();
        System.out.println(decorateInput(input));
    }
    public static void handleUnmark(int index) {
        Task task = list.get(index - 1);
        task.setDone(false);
        String input = "OK, I've marked this task as not done yet:\n" + "       " + task.toString();
        System.out.println(decorateInput(input));
    }
    public static void handleToDo(String input) {
        String description = getToDoDescription(input);
        Task newTask = new ToDo(description);
        list.add(newTask);
        printAddedTask(newTask);
    }
    public static void handleDeadline(String input) {
        String description = getDeadlineDescription(input);
        String deadline = getDeadlineDate(input);
        Task newTask = new Deadline(description, deadline);
        list.add(newTask);
        printAddedTask(newTask);
    }
    public static void handleEvent(String input) {
        String description = getEventDescription(input);
        String begin = getEventBeginDate(input);
        String end = getEventEndDate(input);
        Task newTask = new Event(description, begin, end);
        list.add(newTask);
        printAddedTask(newTask);
    }

    // Methods to detect input patterns
    public static boolean detectBye(String input) {
        return input.equals("bye");
    }
    public static boolean detectList(String input) {
        return input.equals("list");
    }
    public static boolean detectMark(String input) {
        if (input.length() <= 5) {
            return false;
        }
        String header = input.substring(0, 5);
        return header.equals("mark ");
    }
    public static boolean detectUnmark(String input) {
        if (input.length() <= 7) {
            return false;
        }
        String header = input.substring(0, 7);
        return header.equals("unmark ");
    }
    public static boolean detectToDo(String input) {
        if (input.length() <= 5) {
            return false;
        }
        String header = input.substring(0, 5);
        return header.equals("todo ");
    }
    public static boolean detectDeadline(String input) {
        if (input.length() <= 9) {
            return false;
        }
        String header = input.substring(0, 9);
        return header.equals("deadline ");
    }
    public static boolean detectEvent(String input) {
        if (input.length() <= 6) {
            return false;
        }
        String header = input.substring(0, 6);
        return header.equals("event ");
    }

    // Method to get input patterns
    public static int getMarkIndex(String input) {
        String strIndex = input.substring(5);
        return Integer.parseInt(strIndex, 10);
    }
    public static int getUnmarkIndex(String input) {
        String strIndex = input.substring(7);
        return Integer.parseInt(strIndex, 10);
    }
    public static String getToDoDescription(String input) {
        return input.substring(5);
    }
    public static String getDeadlineDescription(String input) {
        String subStr = input.split(" /by ")[0];
        return subStr.substring(9);
    }
    public static String getDeadlineDate(String input) {
        return input.split(" /by ")[1];
    }
    public static String getEventDescription(String input) {
        String subStr = input.split(" /from ")[0];
        return subStr.substring(6);
    }
    public static String getEventBeginDate(String input) {
        String s1 = input.split(" /from ")[1];
        return s1.split(" /to ")[0];
    }
    public static String getEventEndDate(String input) {
        return input.split(" /to ")[1];
    }

    public static String printSize() {
        int size = list.size();
        if (size <= 1) {
            return size + " task";
        } else {
            return size + " tasks";
        }
    }

    public static void main(String[] args) {
        String logo = "____________________________________________________________\n"
                + " Hello! I'm Jessica\n"
                + " What can I do for you?\n"
                + "____________________________________________________________\n";
        String goodbye = "____________________________________________________________\n"
                + " Bye. Hope to see you again soon!\n"
                + "____________________________________________________________\n";
        Scanner scanner = new Scanner(System.in);

        System.out.println(decorateInput("Hello! I'm Jessica\n" +
                "     What can I do for you?"));
        while (true) {
            String input = scanner.nextLine();
            if (detectBye(input)) {
                break;
            }
            if (detectList(input)) {
                prettyPrintList(list);
            } else if (detectMark(input)) {
                handleMark(getMarkIndex(input));
            } else if (detectUnmark(input)) {
                handleUnmark(getUnmarkIndex(input));
            } else if (detectToDo(input)) {
                handleToDo(input);
            } else if (detectDeadline(input)) {
                handleDeadline(input);
            } else if (detectEvent(input)) {
                handleEvent(input);
            } else {
                list.add(new Task(input));
                prettyPrint("added: " + input);
            }
        }
        prettyPrint("Bye. Hope to see you again soon!");
    }
}

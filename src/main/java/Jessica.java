import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jessica {

    private static List<Task> list = new ArrayList<>();

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

    public static String mark(int index) {
        Task task = list.get(index - 1);
        task.setDone(true);
        String input = "Nice! I've marked this task as done:\n" + "       " + task.toString();
        return decorateInput(input);
    }

    public static String unmark(int index) {
        Task task = list.get(index - 1);
        task.setDone(false);
        String input = "OK, I've marked this task as not done yet:\n" + "       " + task.toString();
        return decorateInput(input);
    }

    public static boolean detectBye(String input) {
        return input.equals("bye") || input.equals("Bye");
    }

    public static boolean detectList(String input) {
        return input.equals("list") || input.equals("List");
    }

    public static boolean detectMark(String input) {
        String header = input.substring(0, 5);
        return header.equals("mark ");
    }

    public static boolean detectUnmark(String input) {
        String header = input.substring(0, 7);
        return header.equals("unmark ");
    }

    public static int getMarkIndex(String input) throws NumberFormatException {
        String strIndex = input.substring(5);
        return Integer.parseInt(strIndex, 10);
    }

    public static int getUnmarkIndex(String input) throws NumberFormatException {
        String strIndex = input.substring(7);
        return Integer.parseInt(strIndex, 10);
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
                System.out.println(decorateListOfInput(list));
            } else if (detectMark(input)) {
                System.out.println(mark(getMarkIndex(input)));
            } else if (detectUnmark(input)) {
                System.out.println(unmark(getUnmarkIndex(input)));
            } else {
                list.add(new Task(input, false));
                System.out.println(decorateInput("added: " + input));
            }
        }
        System.out.println(decorateInput("Bye. Hope to see you again soon!"));
    }
}

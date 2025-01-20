import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jessica {

    public static String decorateInput(String input) {
        return "    ____________________________________________________________\n"
                + "     "
                + input
                + "\n"
                + "    ____________________________________________________________\n";
    }

    public static String decorateListOfInput(List<String> list) {
        String result = "    ____________________________________________________________\n";
        for (int i = 0; i < list.size(); i++) {
            String temp = "     " + (i + 1) + ". " + list.get(i) + "\n";
            result += temp;
        }
        result += "    ____________________________________________________________\n";
        return result;
    }

    public static boolean detectBye(String input) {
        return input.equals("bye") || input.equals("Bye");
    }

    public static boolean detectList(String input) {
        return input.equals("list") || input.equals("List");
    }

    private static List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        String logo = "____________________________________________________________\n"
                + " Hello! I'm Jessica\n"
                + " What can I do for you?\n"
                + "____________________________________________________________\n";
        String goodbye = "____________________________________________________________\n"
                + " Bye. Hope to see you again soon!\n"
                + "____________________________________________________________\n";
        Scanner scanner = new Scanner(System.in);

        System.out.println(decorateInput("Hello! I'm [YOUR CHATBOT NAME]\n" +
                "     What can I do for you?"));
        while (true) {
            String input = scanner.nextLine();
            if (detectBye(input)) {
                break;
            }
            if (detectList(input)) {
                System.out.println(decorateListOfInput(list));
            } else {
                list.add(input);
                System.out.println(decorateInput("added: " + input));
            }
        }
        System.out.println(decorateInput("Bye. Hope to see you again soon!"));
    }
}

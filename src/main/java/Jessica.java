import java.util.Scanner;

public class Jessica {

    public static String decorateInput(String input) {
        return "    ____________________________________________________________\n"
                + "     "
                + input
                + "\n"
                + "    ____________________________________________________________\n";
    }

    public static boolean detectBye(String input) {
        return input.equals("bye") || input.equals("Bye");
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

        System.out.println(decorateInput("Hello! I'm [YOUR CHATBOT NAME]\n" +
                "     What can I do for you?"));
        while (true) {
            String input = scanner.nextLine();
            if (detectBye(input)) {
                break;
            }
            System.out.println(decorateInput(input));
        }
        System.out.println(decorateInput("Bye. Hope to see you again soon!"));
    }
}

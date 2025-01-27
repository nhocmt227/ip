package commands;

import tasks.Task;
import java.util.List;

// Methods to decorate input
public class UI {
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

    public static void printAddedTask(Task task, List<?> list) {
        String content = "Got it. I've added this task:\n"
                + "       "
                + task.toString() + "\n"
                + "     Now you have " + getTaskCountMessage(list) + " in the list.";
        prettyPrint(content);
    }

    public static void printPrettyName() {
        String s = "      _               _\n" +
                "     | | ___  ___ ___(_) ___ __ _\n" +
                "  _  | |/ _ \\/ __/ __| |/ __/ _` |\n" +
                " | |_| |  __/\\__ \\__ \\ | (_| (_| |\n" +
                "  \\___/ \\___||___/___/_|\\___\\__,_|\n";
        System.out.println(s);
    }

    public static String getTaskCountMessage(List<?> list) {
        int size = list.size();
        return size + " task" + (size == 1 ? "" : "s");
    }

    public static void chatbotHello() {
        UI.printPrettyName();
        System.out.println(UI.decorateInput("Hello! I'm Jessica\n" +
                "     What can I do for you?"));
    }

    public static void chatbotGoodbye() {
        UI.prettyPrint("Bye. Hope to see you again soon!");
    }

}

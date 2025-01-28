package commands;

import tasks.Task;
import java.util.List;

// Methods to decorate input
public class UI {

    public static void printBorder() {
        System.out.println("    ____________________________________________________________");
    }

    public static void printArray(String[] items) {
        for (String item : items) {
            System.out.println("     " + item);
        }
    }

    public static void prettyPrintArray(String[] items) {
        printBorder();
        printArray(items);
        printBorder();
    }

    public static void prettyPrintList(List<?> list) {
        printBorder();
        for (int i = 0; i < list.size(); i++) {
            System.out.println("     " + (i + 1) + ". " + list.get(i).toString());
        }
        printBorder();
    }

    public static void printAddedTask(Task task, List<?> list) {
        String s1 = "Got it. I've added this task:";
        String s2 = "  " + task.toString();
        String s3 = "Now you have " + getTaskCountMessage(list) + " in the list.";
        prettyPrintArray(new String[] {s1, s2, s3});
    }

    public static void printPrettyName() {
        String s = "      _               _\n" +
                "     | | ___  ___ ___(_) ___ __ _\n" +
                "  _  | |/ _ \\/ __/ __| |/ __/ _` |\n" +
                " | |_| |  __/\\__ \\__ \\ | (_| (_| |\n" +
                "  \\___/ \\___||___/___/_|\\___\\__,_|\n";
        System.out.println(s);
    }

    public static void chatbotHello() {
        UI.printPrettyName();
        String s1 = "Hello! I'm jessica.Jessica";
        String s2 = "What can I do for you?";
        prettyPrintArray(new String[] {s1, s2});
    }

    public static void chatbotGoodbye() {
        String s1 = "Bye. Hope to see you again soon!";
        prettyPrintArray(new String[] {s1});
    }

    public static String getTaskCountMessage(List<?> list) {
        int size = list.size();
        return size + " task" + (size == 1 ? "" : "s");
    }
}

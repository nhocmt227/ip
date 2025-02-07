package commands;

import java.util.List;

import tasks.Task;

/**
 * Handles user interface operations such as printing messages, task lists, and decorated output.
 * This class helps improve user experience by displaying formatted messages and task information.
 */
public class UI {

    public static String getPrettyBorder() {
        return "    ____________________________________________________________\n";
    }

    public static String getArray(String[] items) {
        String result = "";
        for (String item : items) {
            String temp = "  " + item + "\n";
            result += temp;
        }
        return result;
    }

    public static String getPrettyArray(String[] items) {
        return getArray(items);
    }

    public static String getPrettyList(List<?> list) {
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            String temp = "  " + (i + 1) + ". " + list.get(i).toString() + "\n";
            result += temp;
        }
        return result;
    }

    public static String getAddedTask(Task task, List<?> list) {
        String s1 = "Got it. I've added this task:\n";
        String s2 = "  " + task.toString() + "\n";
        String s3 = "Now you have " + getTaskCountMessage(list) + " in the list." + "\n";
        return getPrettyArray(new String[] {s1, s2, s3});
    }

    public static String getPrettyName() {
        String s = "      _               _\n"
                + "     | | ___  ___ ___(_) ___ __ _\n"
                + "  _  | |/ _ \\/ __/ __| |/ __/ _` |\n"
                + " | |_| |  __/\\__ \\__ \\ | (_| (_| |\n"
                + "  \\___/ \\___||___/___/_|\\___\\__,_|\n";
        return s;
    }

    /**
     * Prints a greeting message when the chatbot starts.
     */
    public static String getChatbotHello() {
        String result = UI.getPrettyName();
        String s1 = "Hello! I'm jessica.Jessica";
        String s2 = "What can I do for you?";
        result += getPrettyArray(new String[] {s1, s2});
        return result;
    }

    /**
     * Prints a farewell message when the chatbot shuts down.
     */
    public static String getChatbotGoodbye() {
        String s1 = "Bye. Hope to see you again soon!";
        return getPrettyArray(new String[] {s1});
    }












    /**
     * Prints a horizontal border line.
     */
    public static void printBorder() {
        System.out.println("    ____________________________________________________________");
    }

    /**
     * Prints each item in the given array with indentation.
     *
     * @param items The array of strings to print.
     */
    public static void printArray(String[] items) {
        for (String item : items) {
            System.out.println("     " + item);
        }
    }

    /**
     * Prints an array of strings enclosed between two border lines.
     *
     * @param items The array of strings to print.
     */
    public static void prettyPrintArray(String[] items) {
        printBorder();
        printArray(items);
        printBorder();
    }

    /**
     * Prints a list of objects, each preceded by its index, enclosed between two border lines.
     *
     * @param list The list of objects to print.
     */
    public static void prettyPrintList(List<?> list) {
        printBorder();
        for (int i = 0; i < list.size(); i++) {
            System.out.println("     " + (i + 1) + ". " + list.get(i).toString());
        }
        printBorder();
    }

    /**
     * Prints a message indicating that a task has been added, along with the current task count.
     *
     * @param task The task that was added.
     * @param list The current list of tasks.
     */
    public static void printAddedTask(Task task, List<?> list) {
        String s1 = "Got it. I've added this task:";
        String s2 = "  " + task.toString();
        String s3 = "Now you have " + getTaskCountMessage(list) + " in the list.";
        prettyPrintArray(new String[] {s1, s2, s3});
    }

    /**
     * Prints the ASCII art name of the chatbot.
     */
    public static void printPrettyName() {
        String s = "      _               _\n"
                + "     | | ___  ___ ___(_) ___ __ _\n"
                + "  _  | |/ _ \\/ __/ __| |/ __/ _` |\n"
                + " | |_| |  __/\\__ \\__ \\ | (_| (_| |\n"
                + "  \\___/ \\___||___/___/_|\\___\\__,_|\n";
        System.out.println(s);
    }

    /**
     * Prints a greeting message when the chatbot starts.
     */
    public static void chatbotHello() {
        UI.printPrettyName();
        String s1 = "Hello! I'm jessica.Jessica";
        String s2 = "What can I do for you?";
        prettyPrintArray(new String[] {s1, s2});
    }

    /**
     * Prints a farewell message when the chatbot shuts down.
     */
    public static void chatbotGoodbye() {
        String s1 = "Bye. Hope to see you again soon!";
        prettyPrintArray(new String[] {s1});
    }

    /**
     * Returns a message indicating the current number of tasks in the list.
     *
     * @param list The list of tasks.
     * @return A string representing the task count message.
     */
    public static String getTaskCountMessage(List<?> list) {
        int size = list.size();
        return size + " task" + (size == 1 ? "" : "s");
    }
}

package commands;

import java.util.List;

import tasks.Task;

/**
 * Handles user interface operations such as printing messages, task lists, and decorated output.
 * This class helps improve user experience by displaying formatted messages and task information.
 */
public class UI {

    public static String getArray(String[] items) {
        StringBuilder result = new StringBuilder();
        for (String item : items) {
            String temp = " " + item + "\n";
            result.append(temp);
        }
        return result.toString();
    }

    public static String getPrettyArray(String[] items) {
        return getArray(items);
    }

    public static String getPrettyList(List<?> list) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String temp = "  " + (i + 1) + ". " + list.get(i).toString() + "\n";
            result.append(temp);
        }
        return result.toString();
    }

    public static String getAddedTask(Task task, List<?> list) {
        String s1 = "Got it. I've added this task:\n";
        String s2 = "  " + task.toString() + "\n";
        String s3 = "Now you have " + getTaskCountMessage(list) + " in the list." + "\n";
        return getPrettyArray(new String[] {s1, s2, s3});
    }






    /**
     * Prints a horizontal borderline.
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
     * Prints an array of strings enclosed between two borderlines.
     *
     * @param items The array of strings to print.
     */
    public static void prettyPrintArray(String[] items) {
        printBorder();
        printArray(items);
        printBorder();
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

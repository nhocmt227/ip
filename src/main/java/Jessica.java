import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import exception.*;

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

    // Methods to detect input patterns
    public static boolean detectBye(String input) {
        return input.trim().equals("bye");
    }
    public static boolean detectList(String input) {
        return input.trim().equals("list");
    }
    public static boolean detectMark(String input) {
        String[] tags = parse(input);
        return tags[0].equals("mark");
    }
    public static boolean detectUnmark(String input) {
        String[] tags = parse(input);
        return tags[0].equals("unmark");
    }
    public static boolean detectToDo(String input) {
        String[] tags = parse(input);
        return tags[0].equals("todo");
    }
    public static boolean detectDeadline(String input) {
        String[] tags = parse(input);
        return tags[0].equals("deadline");
    }
    public static boolean detectEvent(String input) {
        String[] tags = parse(input);
        return tags[0].equals("event");
    }

    // Method to get input patterns
    public static int getMarkIndex(String input) throws JessicaException {
        try {
            String[] parts = input.split("mark\\s+", 2);
            if (parts.length == 1) {
                throw new JessicaException("Mark index must not be empty, try again");
            }
            int index = Integer.parseInt(parts[1].trim(), 10);
            if (index <= 0) {
                throw new JessicaException("Index must be a positive number, try again");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new JessicaException("Invalid syntax, try again");
        }
    }
    public static int getUnmarkIndex(String input) throws JessicaException {
        try {
            String[] parts = input.split("mark\\s+", 2);
            if (parts.length == 1) {
                throw new JessicaException("Unmark index must not be empty, try again");
            }
            int index = Integer.parseInt(parts[1].trim(), 10);
            if (index <= 0) {
                throw new JessicaException("Index must be a positive number, try again");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new JessicaException("Invalid syntax, try again");
        }
    }
    public static String getToDoDescription(String input) throws JessicaException {
        String[] parts = input.split("todo\\s+", 2);
        if (parts.length == 1) {
            throw new JessicaException("Todo description cannot be empty");
        }
        String description = parts[1];
        if (description.trim().isEmpty()) {
            throw new JessicaException("Todo description cannot be empty");
        } else {
            return description;
        }
    }
    public static String getDeadlineDescription(String input) throws JessicaException {
        String[] parts = input.split("deadline\\s+", 2);
        if (parts.length == 1) {
            throw new JessicaException("Deadline description cannot be empty");
        }
        String body = parts[1];
        String[] bodyParts = body.split("\\s+/by\\s+", 2);
        if (bodyParts[0].trim().isEmpty()) {
            throw new JessicaException("Deadline description cannot be empty");
        } else {
            return bodyParts[0];
        }
    }
    public static String getDeadlineDate(String input) throws JessicaException {
        String[] parts = input.split("deadline\\s+", 2);
        if (parts.length == 1) {
            throw new JessicaException("Deadline description cannot be empty");
        }
        String body = parts[1];
        String[] bodyParts = body.split("\\s+/by\\s+", 2);
        if (bodyParts.length == 1 || bodyParts[1].trim().isEmpty()) {
            throw new JessicaException("Deadline end date cannot be empty");
        } else {
            return bodyParts[1];
        }
    }
    public static String getEventDescription(String input) throws JessicaException {
        String[] parts = input.split("event\\s+", 2);
        if (parts.length == 1) {
            throw new JessicaException("Event description cannot be empty");
        }
        String body = parts[1];
        String[] bodyParts = body.split("\\s+/from\\s+", 2);
        if (bodyParts[0].trim().isEmpty()) {
            throw new JessicaException("Event description cannot be empty");
        } else {
            return bodyParts[0];
        }
    }
    public static String getEventBeginDate(String input) throws JessicaException {
        String[] parts = input.split("event\\s+", 2);
        if (parts.length == 1) {
            throw new JessicaException("Event description cannot be empty");
        }
        String body = parts[1];
        String[] bodyParts = body.split("\\s+/from\\s+", 2);
        if (bodyParts.length == 1 || bodyParts[1].trim().isEmpty()) {
            throw new JessicaException("Event begin time cannot be empty");
        }
        body = bodyParts[1];
        bodyParts = body.split("\\s+/to\\s+", 2);
        if (bodyParts[0].trim().isEmpty()) {
            throw new JessicaException("Event begin time cannot be empty");
        }
        return bodyParts[0];
    }
    public static String getEventEndDate(String input) throws JessicaException {
        String[] parts = input.split("event\\s+", 2);
        if (parts.length == 1) {
            throw new JessicaException("Event description cannot be empty");
        }
        String body = parts[1];
        String[] bodyParts = body.split("\\s+/from\\s+", 2);
        if (bodyParts.length == 1 || bodyParts[1].trim().isEmpty()) {
            throw new JessicaException("Event begin time cannot be empty");
        }
        body = bodyParts[1];
        bodyParts = body.split("\\s+/to\\s+", 2);
        if (bodyParts.length == 1 || bodyParts[1].trim().isEmpty()) {
            throw new JessicaException("Event end time cannot be empty");
        }
        return bodyParts[1];
    }

    // Methods to handle logics
    public static void handleMark(String input) {
        try {
            int index = getMarkIndex(input);
            Task task = list.get(index - 1);
            task.setDone(true);
            String output = "Nice! I've marked this task as done:\n" + "       " + task.toString();
            System.out.println(decorateInput(output));
        } catch (JessicaException e) {
            System.out.println(e.getMessage());
            System.out.println("Usage: mark [index]");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bound, try again");
            System.out.println("Usage: mark [index]");
        }
    }
    public static void handleUnmark(String input) {
        try {
            int index = getUnmarkIndex(input);
            Task task = list.get(index - 1);
            task.setDone(false);
            String output = "OK, I've marked this task as not done yet:\n" + "       " + task.toString();
            System.out.println(decorateInput(output));
        } catch (JessicaException e) {
            System.out.println(e.getMessage());
            System.out.println("Usage: unmark [index]");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bound, try again");
            System.out.println("Usage: unmark [index]");
        }
    }
    public static void handleToDo(String input) {
        try {
            String description = getToDoDescription(input);
            Task newTask = new ToDo(description);
            list.add(newTask);
            printAddedTask(newTask);
        } catch (JessicaException e) {
            System.out.println(e.getMessage());
            System.out.println("Usage: todo [description]");
        }
    }
    public static void handleDeadline(String input) {
        try {
            String description = getDeadlineDescription(input);
            String deadline = getDeadlineDate(input);
            Task newTask = new Deadline(description, deadline);
            list.add(newTask);
            printAddedTask(newTask);
        } catch (JessicaException e) {
            System.out.println(e.getMessage());
            System.out.println("Usage: deadline [description] /by [end date]");
        }
    }
    public static void handleEvent(String input) {
        try {
            String description = getEventDescription(input);
            String begin = getEventBeginDate(input);
            String end = getEventEndDate(input);
            Task newTask = new Event(description, begin, end);
            list.add(newTask);
            printAddedTask(newTask);
        } catch (JessicaException e) {
            System.out.println(e.getMessage());
            System.out.println("Usage: event [description] /from [begin time] /to [end time]");
        }

    }

    // Other helper methods
    public static String printSize() {
        int size = list.size();
        if (size <= 1) {
            return size + " task";
        } else {
            return size + " tasks";
        }
    }
    public static String[] parse(String input) {
        /*
        * This method parses all the tag in that input
        * Example use: input = "mark 1  2"
        *              result = ["mark", "1", "2"]
        * */
        input = input.trim();
        // "\\s+" is a regular expression for one or more spaces
        String[] parts = input.split("\\s+");
        return parts;
    }
    public static void chatbotHello() {
        System.out.println(decorateInput("Hello! I'm Jessica\n" +
                "     What can I do for you?"));
    }
    public static void chatbotGoodbye() {
        prettyPrint("Bye. Hope to see you again soon!");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        chatbotHello();

        while (true) {
            String input = scanner.nextLine();
            if (detectBye(input)) {
                break;
            }
            if (input.trim().isEmpty()) {
                System.out.println("Cannot add an empty task, try again");
            } else if (detectList(input)) {
                prettyPrintList(list);
            } else if (detectMark(input)) {
                handleMark(input);
            } else if (detectUnmark(input)) {
                handleUnmark(input);
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
        chatbotGoodbye();
    }
}

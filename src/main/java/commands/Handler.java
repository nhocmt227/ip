package commands;

import exception.JessicaException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;
import java.util.List;

// Methods to handle logics given the input
public class Handler {
    public static void handleList(String input, List<Task> list) {
        if (input.trim().equals("list")) {
            UI.prettyPrintList(list);
        } else {
            System.out.println("Invalid list syntax, try again");
            System.out.println("Usage: list");
        }
    }

    public static void handleMark(String input, List<Task> list) {
        try {
            int index = Parser.getMarkIndex(input);
            Task task = list.get(index - 1);
            task.setDone(true);
            String output = "Nice! I've marked this task as done:\n" + "       " + task.toString();
            System.out.println(UI.decorateInput(output));
        } catch (JessicaException e) {
            System.out.println(e.getMessage());
            System.out.println("Usage: mark [index]");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bound, try again");
            System.out.println("Usage: mark [index]");
        }
    }

    public static void handleUnmark(String input, List<Task> list) {
        try {
            int index = Parser.getUnmarkIndex(input);
            Task task = list.get(index - 1);
            task.setDone(false);
            String output = "OK, I've marked this task as not done yet:\n" + "       " + task.toString();
            System.out.println(UI.decorateInput(output));
        } catch (JessicaException e) {
            System.out.println(e.getMessage());
            System.out.println("Usage: unmark [index]");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bound, try again");
            System.out.println("Usage: unmark [index]");
        }
    }

    public static void handleToDo(String input, List<Task> list) {
        try {
            String description = Parser.getToDoDescription(input);
            Task newTask = new ToDo(description);
            list.add(newTask);
            UI.printAddedTask(newTask, list);
        } catch (JessicaException e) {
            System.out.println(e.getMessage());
            System.out.println("Usage: todo [description]");
        }
    }

    public static void handleDeadline(String input, List<Task> list) {
        try {
            String description = Parser.getDeadlineDescription(input);
            String deadline = Parser.getDeadlineDate(input);
            Task newTask = new Deadline(description, deadline);
            list.add(newTask);
            UI.printAddedTask(newTask, list);
        } catch (JessicaException e) {
            System.out.println(e.getMessage());
            System.out.println("Usage: deadline [description] /by [end date]");
        }
    }

    public static void handleEvent(String input, List<Task> list) {
        try {
            String description = Parser.getEventDescription(input);
            String begin = Parser.getEventBeginDate(input);
            String end = Parser.getEventEndDate(input);
            Task newTask = new Event(description, begin, end);
            list.add(newTask);
            UI.printAddedTask(newTask, list);
        } catch (JessicaException e) {
            System.out.println(e.getMessage());
            System.out.println("Usage: event [description] /from [begin time] /to [end time]");
        }
    }

    public static void handleDelete(String input, List<Task> list) {
        try {
            int index = Parser.getDeleteIndex(input);
            Task task = list.get(index - 1);
            list.remove(index - 1);
            String output = "Noted. I've removed this task:\n       "
                    + task.toString()
                    + "\n     "
                    + "Now you have "
                    + UI.getTaskCountMessage(list)
                    + " in the list.";
            UI.prettyPrint(output);
        } catch (JessicaException e) {
            System.out.println(e.getMessage());
            System.out.println("Usage: delete [index]");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bound, try again");
            System.out.println("Usage: delete [index]");
        }
    }

}

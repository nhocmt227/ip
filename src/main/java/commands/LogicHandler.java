package commands;

import exception.JessicaException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.time.LocalDate;

// Methods to handle logics given the input
public class LogicHandler {
    public static void handleList(String input, List<Task> list) {
        if (input.trim().equals("list")) {
            UI.prettyPrintList(list);
        } else {
            String s1 = "Invalid list syntax, try again";
            String s2 = "Usage: list";
            UI.prettyPrintArray(new String[] {s1, s2});
        }
    }

    public static void handleMark(String input, List<Task> list) {
        try {
            int index = Parser.getMarkIndex(input);
            Task task = list.get(index - 1);
            task.setDone(true);
            String s1 = "Nice! I've marked this task as done:";
            String s2 = "  " + task.toString();
            UI.prettyPrintArray(new String[] {s1, s2});
        } catch (JessicaException e) {
            String s1 = e.getMessage();
            String s2 = "Usage: mark [index]";
            UI.prettyPrintArray(new String[] {s1, s2});
        } catch (IndexOutOfBoundsException e) {
            String s1 = "Index out of bound, try again";
            String s2 = "Usage: mark [index]";
            UI.prettyPrintArray(new String[] {s1, s2});
        }
    }

    public static void handleUnmark(String input, List<Task> list) {
        try {
            int index = Parser.getUnmarkIndex(input);
            Task task = list.get(index - 1);
            task.setDone(false);
            String s1 = "OK, I've marked this task as not done yet:";
            String s2 = "  " + task.toString();
            UI.prettyPrintArray(new String[] {s1, s2});
        } catch (JessicaException e) {
            String s1 = e.getMessage();
            String s2 = "Usage: unmark [index]";
            UI.prettyPrintArray(new String[] {s1, s2});
        } catch (IndexOutOfBoundsException e) {
            String s1 = "Index out of bound, try again";
            String s2 = "Usage: unmark [index]";
            UI.prettyPrintArray(new String[] {s1, s2});
        }
    }

    public static void handleToDo(String input, List<Task> list) {
        try {
            String description = Parser.getToDoDescription(input);
            Task newTask = new ToDo(description);
            list.add(newTask);
            UI.printAddedTask(newTask, list);
        } catch (JessicaException e) {
            String s1 = e.getMessage();
            String s2 = "Usage: todo [description]";
            UI.prettyPrintArray(new String[] {s1, s2});
        }
    }

    public static void handleDeadline(String input, List<Task> list) {
        try {
            String description = Parser.getDeadlineDescription(input);
            String deadline = Parser.getDeadlineDate(input);
            LocalDate ld = Converter.stringToDate(deadline);
            Task newTask = new Deadline(description, ld);
            list.add(newTask);
            UI.printAddedTask(newTask, list);
        } catch (JessicaException e) {
            String s1 = e.getMessage();
            String s2 = "Usage: deadline [description] /by [end date]";
            UI.prettyPrintArray(new String[] {s1, s2});
        } catch (DateTimeParseException e) {
            String s1 = e.getMessage();
            String s2 = "Usage: deadline [description] /by [end date]";
            String s3 = "Date format: yyyy-mm-dd";
            UI.prettyPrintArray(new String[] {s1, s2, s3});
        }
    }

    public static void handleEvent(String input, List<Task> list) {
        try {
            String description = Parser.getEventDescription(input);
            String begin = Parser.getEventBeginDate(input);
            String end = Parser.getEventEndDate(input);
            Task newTask = new Event(description, Converter.stringToDate(begin), Converter.stringToDate(end));
            list.add(newTask);
            UI.printAddedTask(newTask, list);
        } catch (JessicaException e) {
            String s1 = e.getMessage();
            String s2 = "Usage: event [description] /from [begin time] /to [end time]";
            UI.prettyPrintArray(new String[] {s1, s2});
        } catch (DateTimeParseException e) {
            String s1 = e.getMessage();
            String s2 = "Usage: deadline [description] /by [end date]";
            String s3 = "Date format: yyyy-mm-dd";
            UI.prettyPrintArray(new String[] {s1, s2, s3});
        }
    }

    public static void handleDelete(String input, List<Task> list) {
        try {
            int index = Parser.getDeleteIndex(input);
            Task task = list.get(index - 1);
            list.remove(index - 1);
            String s1 = "Noted. I've removed this task:";
            String s2 = task.toString();
            String s3 = "Now you have " + UI.getTaskCountMessage(list) + " in the list.";
            UI.prettyPrintArray(new String[] {s1, s2, s3});
        } catch (JessicaException e) {
            String s1 = e.getMessage();
            String s2 = "Usage: delete [index]";
            UI.prettyPrintArray(new String[] {s1, s2});
        } catch (IndexOutOfBoundsException e) {
            String s1 = "Index out of bound, try again";
            String s2 = "Usage: delete [index]";
            UI.prettyPrintArray(new String[] {s1, s2});
        }
    }
}

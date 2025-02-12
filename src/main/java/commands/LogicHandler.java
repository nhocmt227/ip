package commands;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import exception.JessicaException;
import jessica.Help;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

/**
 * Handles various task-related operations, such as adding, deleting, marking tasks,
 * and managing task deadlines and events. This class processes input and interacts
 * with the storage and UI components.
 */

public class LogicHandler {

    private final StorageHandler storageHandler;
    private final List<Task> tasksList;

    /**
     * Constructor to initialize LogicHandler with a storage handler.
     *
     * @param storageHandler The handler to manage storage operations.
     * @param list to store the list object.
     */
    public LogicHandler(StorageHandler storageHandler, List<Task> list) {
        this.storageHandler = storageHandler;
        this.tasksList = list;
    }

    /**
     * Handles the list command by displaying all tasks in the list.
     *
     * @param input The user's input command.
     */
    public String handleList(String input) {
        if (input.trim().equals("list")) {
            return UI.getPrettyList(tasksList);
        } else {
            String s1 = "Invalid list syntax, try again";
            String s2 = Help.LIST_USAGE;
            return UI.getPrettyArray(new String[] {s1, s2});
        }
    }

    /**
     * Handles marking a task as completed.
     *
     * @param input The user's input command.
     */
    public String handleMark(String input) {
        try {
            int index = Parser.getMarkIndex(input);
            Task task = tasksList.get(index - 1);
            task.setDone(true);
            storageHandler.storeMemToDisk(tasksList);
            String s1 = "Nice! I've marked this task as done:";
            String s2 = "  " + task;
            return UI.getPrettyArray(new String[] {s1, s2});
        } catch (JessicaException e) {
            String s1 = e.getMessage();
            String s2 = Help.MARK_USAGE;
            return UI.getPrettyArray(new String[] {s1, s2});
        } catch (IndexOutOfBoundsException e) {
            String s1 = "Index out of bound, try again";
            String s2 = Help.MARK_USAGE;
            return UI.getPrettyArray(new String[] {s1, s2});
        } catch (IOException e) {
            String s1 = e.getMessage();
            String s2 = "Error when store data to file";
            return UI.getPrettyArray(new String[] {s1, s2});
        }
    }

    /**
     * Handles unmarking a task as incomplete.
     *
     * @param input The user's input command.
     */
    public String handleUnmark(String input) {
        try {
            int index = Parser.getUnmarkIndex(input);
            Task task = tasksList.get(index - 1);
            task.setDone(false);
            storageHandler.storeMemToDisk(tasksList);
            String s1 = "OK, I've marked this task as not done yet:";
            String s2 = "  " + task;
            return UI.getPrettyArray(new String[] {s1, s2});
        } catch (JessicaException e) {
            String s1 = e.getMessage();
            String s2 = Help.UNMARK_USAGE;
            return UI.getPrettyArray(new String[] {s1, s2});
        } catch (IndexOutOfBoundsException e) {
            String s1 = "Index out of bound, try again";
            String s2 = Help.UNMARK_USAGE;
            return UI.getPrettyArray(new String[] {s1, s2});
        } catch (IOException e) {
            String s1 = e.getMessage();
            String s2 = "Error when store data to file";
            return UI.getPrettyArray(new String[] {s1, s2});
        }
    }

    /**
     * Handles adding a new ToDo task.
     *
     * @param input The user's input command.
     */
    public String handleToDo(String input) {
        try {
            String description = Parser.getToDoDescription(input);
            Task newTask = new ToDo(description);
            tasksList.add(newTask);
            storageHandler.storeTaskToDisk(newTask);
            return UI.getAddedTask(newTask, tasksList);
        } catch (JessicaException e) {
            String s1 = e.getMessage();
            String s2 = Help.TODO_USAGE;
            return UI.getPrettyArray(new String[] {s1, s2});
        } catch (IOException e) {
            String s1 = e.getMessage();
            String s2 = "Error when store data to file";
            return UI.getPrettyArray(new String[] {s1, s2});
        }
    }

    /**
     * Handles adding a new Deadline task.
     *
     * @param input The user's input command.
     */
    public String handleDeadline(String input) {
        try {
            String description = Parser.getDeadlineDescription(input);
            String deadline = Parser.getDeadlineDate(input);
            LocalDate ld = Converter.stringToDate(deadline);
            Task newTask = new Deadline(description, ld);
            tasksList.add(newTask);
            storageHandler.storeTaskToDisk(newTask);
            return UI.getAddedTask(newTask, tasksList);
        } catch (JessicaException e) {
            String s1 = e.getMessage();
            String s2 = Help.DEADLINE_USAGE;
            return UI.getPrettyArray(new String[] {s1, s2});
        } catch (DateTimeParseException e) {
            String s1 = e.getMessage();
            String s2 = Help.DEADLINE_USAGE;
            String s3 = "Date format: yyyy-mm-dd";
            return UI.getPrettyArray(new String[] {s1, s2, s3});
        } catch (IOException e) {
            String s1 = e.getMessage();
            String s2 = "Error when store data to file";
            return UI.getPrettyArray(new String[] {s1, s2});
        }
    }

    /**
     * Handles adding a new Event task.
     *
     * @param input The user's input command.
     */
    public String handleEvent(String input) {
        try {
            String description = Parser.getEventDescription(input);
            String begin = Parser.getEventBeginDate(input);
            String end = Parser.getEventEndDate(input);
            Task newTask = new Event(description, Converter.stringToDate(begin), Converter.stringToDate(end));
            tasksList.add(newTask);
            storageHandler.storeTaskToDisk(newTask);
            return UI.getAddedTask(newTask, tasksList);
        } catch (JessicaException e) {
            String s1 = e.getMessage();
            String s2 = Help.EVENT_USAGE;
            return UI.getPrettyArray(new String[] {s1, s2});
        } catch (DateTimeParseException e) {
            String s1 = e.getMessage();
            String s2 = Help.EVENT_USAGE;
            String s3 = "Date format: yyyy-mm-dd";
            return UI.getPrettyArray(new String[] {s1, s2, s3});
        } catch (IOException e) {
            String s1 = e.getMessage();
            String s2 = "Error when store data to file";
            return UI.getPrettyArray(new String[] {s1, s2});
        }
    }

    /**
     * Handles deleting a task from the list.
     *
     * @param input The user's input command.
     */
    public String handleDelete(String input) {
        try {
            int index = Parser.getDeleteIndex(input);
            Task task = tasksList.get(index - 1);
            tasksList.remove(index - 1);
            storageHandler.storeMemToDisk(tasksList);
            String s1 = "Noted. I've removed this task:";
            String s2 = task.toString();
            String s3 = "Now you have " + UI.getTaskCountMessage(tasksList) + " in the list.";
            return UI.getPrettyArray(new String[] {s1, s2, s3});
        } catch (JessicaException e) {
            String s1 = e.getMessage();
            String s2 = Help.DELETE_USAGE;
            return UI.getPrettyArray(new String[] {s1, s2});
        } catch (IndexOutOfBoundsException e) {
            String s1 = "Index out of bound, try again";
            String s2 = Help.DELETE_USAGE;
            return UI.getPrettyArray(new String[] {s1, s2});
        } catch (IOException e) {
            String s1 = e.getMessage();
            String s2 = "Error when store data to file";
            return UI.getPrettyArray(new String[] {s1, s2});
        }
    }

    /**
     * Handles finding tasks from the list based on a pattern.
     *
     * @param input The user's input command.
     */
    public String handleFind(String input) {
        try {
            String description = Parser.getFindDescription(input);
            List<Task> listToFind = new ArrayList<>();
            for (Task t : this.tasksList) {
                if (t.toString().contains(description)) {
                    listToFind.add(t);
                }
            }
            return UI.getPrettyList(listToFind);
        } catch (JessicaException e) {
            String s1 = e.getMessage();
            String s2 = Help.FIND_USAGE;
            return UI.getPrettyArray(new String[] {s1, s2});
        }
    }

    public String handleHelp() {
        return Help.help();
    }
}

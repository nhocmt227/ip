package tasks;

/**
 * Represents a to-do task.
 * A to-do task has a description and a completion status.
 * This class extends the {@link Task} class.
 */
public class ToDo extends Task {
    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }

    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

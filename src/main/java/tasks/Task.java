package tasks;

/**
 * Represents a general task.
 * A task has a description and a completion status.
 * This is an abstract class to be extended by more specific task types.
 */
public abstract class Task {
    private String description;
    private boolean isDone;

    /**
     * Constructs a new {@code Task} with a specified description and completion status.
     *
     * @param description the description of the task
     * @param isDone the completion status of the task
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Constructs a new {@code Task} with a specified description.
     * The task is not marked as done by default.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getDone() {
        return this.isDone;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        if (this.isDone) {
            return "[X] " + this.description;
        } else {
            return "[ ] " + this.description;
        }
    }
}

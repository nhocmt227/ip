package tasks;

import java.time.LocalDate;

import commands.Converter;

/**
 * Represents a task with a deadline.
 * A Deadline task has a description, a due date, and a completion status.
 * It extends the {@link Task} class and adds a deadline attribute.
 * The deadline is stored as a {@link LocalDate} object.
 */
@SuppressWarnings("checkstyle:Regexp")
public class Deadline extends Task {
    private LocalDate deadline;

    /**
     * Constructs a new {@code Deadline} task with a specified description, deadline, and completion status.
     *
     * @param description the description of the task
     * @param deadline the deadline date of the task
     * @param isDone the completion status of the task
     */
    public Deadline(String description, LocalDate deadline, boolean isDone) {
        super(description, isDone);
        this.deadline = deadline;
    }

    /**
     * Constructs a new {@code Deadline} task with a specified description and deadline.
     * The task is not marked as done by default.
     *
     * @param description the description of the task
     * @param deadline the deadline date of the task
     */
    public Deadline(String description, LocalDate deadline) {
        super(description);
        this.deadline = deadline;
    }

    /**
     * Gets the deadline of the task.
     *
     * @return the deadline date of the task
     */
    public LocalDate getDeadline() {
        return this.deadline;
    }

    /**
     * Sets a new deadline for the task.
     *
     * @param deadline the new deadline date to be set
     */
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + Converter.dateToFormattedString(this.deadline) + ")";
    }
}

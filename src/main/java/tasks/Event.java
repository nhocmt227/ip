package tasks;

import java.time.LocalDate;

import commands.Converter;
import exception.JessicaException;

/**
 * Represents an event task with a start and end date.
 * An Event task has a description, a start date, an end date, and a completion status.
 * The task extends the {@link Task} class.
 */
public class Event extends Task {
    private final LocalDate begin;
    private final LocalDate end;

    /**
     * Constructs a new {@code Event} task with the specified description, start date, end date,
     * and completion status. Throws a {@link JessicaException} if the start date is after the end date.
     *
     * @param description the description of the task
     * @param begin the start date of the event
     * @param end the end date of the event
     * @param isDone the completion status of the task
     * @throws JessicaException if the start date is after the end date
     */
    public Event(String description, LocalDate begin, LocalDate end, boolean isDone) throws JessicaException {
        super(description, isDone);
        if (begin.isAfter(end)) {
            throw new JessicaException("Start date must be before end date");
        }
        this.begin = begin;
        this.end = end;
    }

    /**
     * Constructs a new {@code Event} task with the specified description, start date, and end date.
     * The task is not marked as done by default. Throws a {@link JessicaException} if the start date
     * is after the end date.
     *
     * @param description the description of the task
     * @param begin the start date of the event
     * @param end the end date of the event
     * @throws JessicaException if the start date is after the end date
     */
    public Event(String description, LocalDate begin, LocalDate end) throws JessicaException {
        super(description);
        if (begin.isAfter(end)) {
            throw new JessicaException("Start date must be before end date");
        }
        this.begin = begin;
        this.end = end;
    }

    /**
     * Gets the start date of the event.
     *
     * @return the start date of the event
     */
    public LocalDate getStartDate() {
        return this.begin;
    }

    /**
     * Gets the end date of the event.
     *
     * @return the end date of the event
     */
    public LocalDate getEndDate() {
        return this.end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + Converter.dateToFormattedString(this.begin)
                + " to: " + Converter.dateToFormattedString(this.end) + ")";
    }
}

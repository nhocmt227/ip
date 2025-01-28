package tasks;

import commands.Converter;
import exception.JessicaException;

import java.time.LocalDate;

public class Event extends Task {
    private LocalDate begin;
    private LocalDate end;

    public Event(String description, LocalDate begin, LocalDate end, boolean isDone) throws JessicaException {
        super(description, isDone);
        if (begin.isAfter(end)) {
            throw new JessicaException("Start date must be before end date");
        }
        this.begin = begin;
        this.end = end;
    }

    public Event(String description, LocalDate begin, LocalDate end) throws JessicaException {
        super(description);
        if (begin.isAfter(end)) {
            throw new JessicaException("Start date must be before end date");
        }
        this.begin = begin;
        this.end = end;
    }

    public LocalDate getBegin() {
        return this.begin;
    }

    public LocalDate getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + Converter.dateToFormattedString(this.begin) +
                " to: " + Converter.dateToFormattedString(this.end) + ")";
    }
}

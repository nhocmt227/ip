package tasks;

import commands.Converter;

import java.time.LocalDate;

public class Event extends Task {
    private LocalDate begin;
    private LocalDate end;

    public Event(String description, LocalDate begin, LocalDate end, boolean isDone) {
        super(description, isDone);
        this.begin = begin;
        this.end = end;
    }

    public Event(String description, LocalDate begin, LocalDate end) {
        super(description);
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

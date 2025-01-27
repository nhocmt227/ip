package tasks;

public class Event extends Task {
    private String begin;
    private String end;

    public Event(String description, String begin, String end, boolean isDone) {
        super(description, isDone);
        this.begin = begin;
        this.end = end;
    }

    public Event(String description, String begin, String end) {
        super(description);
        this.begin = begin;
        this.end = end;
    }

    public String getBegin() {
        return this.begin;
    }

    public String getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.begin + " to: " + this.end + ")";
    }
}

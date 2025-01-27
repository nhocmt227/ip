package tasks;

public class Event extends Task {
    private String begin;
    private String end;

    public Event(String description, boolean isDone, String begin, String end) {
        super(description, isDone);
        this.begin = begin;
        this.end = end;
    }

    public Event(String description, String begin, String end) {
        super(description);
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.begin + " to: " + this.end + ")";
    }
}

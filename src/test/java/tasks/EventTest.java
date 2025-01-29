package tasks;


import commands.Converter;
import exception.JessicaException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Warning:
 * This class is not a pure Unit-test class
 * because it is dependent on other classes
 */

public class EventTest {
    // ✅ Test default isDone state (should be false)
    @Test
    public void event_defaultNotDone() throws JessicaException {
        Event event = new Event("Conference",
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 3));
        assertFalse(event.getDone());
    }

    // ✅ Test marking an Event as done
    @Test
    public void event_markAsDone() throws JessicaException {
        Event event = new Event("Hackathon",
                LocalDate.of(2024, 7, 5),
                LocalDate.of(2024, 7, 7));
        event.setDone(true);
        assertTrue(event.getDone());
    }

    // ✅ Test marking an Event as not done
    @Test
    public void event_markAsNotDone() throws JessicaException {
        Event event = new Event("Company Retreat",
                LocalDate.of(2024, 8, 10),
                LocalDate.of(2024, 8, 12),
                true);
        event.setDone(false);
        assertFalse(event.getDone());
    }

    // ✅ Test getting description
    @Test
    public void event_getDescription() throws JessicaException {
        Event event = new Event("Music Festival",
                LocalDate.of(2024, 9, 1),
                LocalDate.of(2024, 9, 3));
        assertEquals("Music Festival", event.getDescription());
    }

    // ✅ Test getting event start date
    @Test
    public void event_getBeginDate() throws JessicaException {
        LocalDate startDate = LocalDate.of(2024, 10, 15);
        Event event = new Event("Science Fair", startDate, LocalDate.of(2024, 10, 17));
        assertEquals(startDate, event.getBegin());
    }

    // ✅ Test getting event end date
    @Test
    public void event_getEndDate() throws JessicaException {
        LocalDate endDate = LocalDate.of(2024, 11, 20);
        Event event = new Event("Business Summit", LocalDate.of(2024, 11, 18), endDate);
        assertEquals(endDate, event.getEnd());
    }

    // ✅ Test invalid date range (start date is after end date → should throw an exception)
    @Test
    public void event_invalidDateRange_exceptionThrown() {
        assertThrows(JessicaException.class,
                () -> new Event("Workshop",
                        LocalDate.of(2024, 6, 5),
                        LocalDate.of(2024, 6, 3)));
    }

    // ✅ Test toString() output for not done event
    @Test
    public void event_toString_notDone() throws JessicaException {
        LocalDate startDate = LocalDate.of(2024, 6, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 3);
        Event event = new Event("Networking Meetup", startDate, endDate);
        assertEquals("[E][ ] Networking Meetup (from: "
                        + Converter.dateToFormattedString(startDate)
                        + " to: " + Converter.dateToFormattedString(endDate)
                        + ")",
                event.toString());
    }

    // ✅ Test toString() output for done event
    @Test
    public void event_toString_done() throws JessicaException {
        LocalDate startDate = LocalDate.of(2024, 7, 20);
        LocalDate endDate = LocalDate.of(2024, 7, 22);
        Event event = new Event("Annual Sports Meet", startDate, endDate, true);
        assertEquals("[E][X] Annual Sports Meet (from: "
                + Converter.dateToFormattedString(startDate) + " to: "
                + Converter.dateToFormattedString(endDate)
                + ")",
                event.toString());
    }
}

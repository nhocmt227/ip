package tasks;

import commands.Converter;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Warning:
 * This class is not a pure Unit-test class
 * because it is dependent on other classes
 */

public class DeadlineTest {
    // ✅ Test default isDone state (should be false)
    @Test
    public void deadline_defaultNotDone() {
        Deadline deadline = new Deadline("Submit report",
                LocalDate.of(2024, 6, 1));
        assertFalse(deadline.getDone());
    }

    // ✅ Test marking a Deadline as done
    @Test
    public void deadline_markAsDone() {
        Deadline deadline = new Deadline("Complete assignment",
                LocalDate.of(2024, 12, 31));
        deadline.setDone(true);
        assertTrue(deadline.getDone());
    }

    // ✅ Test marking a Deadline as not done
    @Test
    public void deadline_markAsNotDone() {
        Deadline deadline = new Deadline("Finish project",
                LocalDate.of(2024, 8, 15), true);
        deadline.setDone(false);
        assertFalse(deadline.getDone());
    }

    // ✅ Test getting description
    @Test
    public void deadline_getDescription() {
        Deadline deadline = new Deadline("Buy groceries",
                LocalDate.of(2024, 5, 10));
        assertEquals("Buy groceries", deadline.getDescription());
    }

    // ✅ Test setting description
    @Test
    public void deadline_setDescription() {
        Deadline deadline = new Deadline("Old description",
                LocalDate.of(2024, 7, 20));
        deadline.setDescription("New description");
        assertEquals("New description", deadline.getDescription());
    }

    // ✅ Test getting deadline date
    @Test
    public void deadline_getDeadline() {
        LocalDate date = LocalDate.of(2024, 9, 30);
        Deadline deadline = new Deadline("Submit research paper", date);
        assertEquals(date, deadline.getDeadline());
    }

    // ✅ Test setting deadline date
    @Test
    public void deadline_setDeadline() {
        Deadline deadline = new Deadline("Submit application",
                LocalDate.of(2024, 10, 5));
        LocalDate newDate = LocalDate.of(2024, 11, 10);
        deadline.setDeadline(newDate);
        assertEquals(newDate, deadline.getDeadline());
    }

    // ✅ Test toString() output for not done task
    @Test
    public void deadline_toString_notDone() {
        LocalDate date = LocalDate.of(2024, 6, 1);
        Deadline deadline = new Deadline("Walk the dog", date);
        assertEquals("[D][ ] Walk the dog (by: "
                        + Converter.dateToFormattedString(date)
                        + ")",
                deadline.toString());
    }

    // ✅ Test toString() output for done task
    @Test
    public void deadline_toString_done() {
        LocalDate date = LocalDate.of(2024, 12, 25);
        Deadline deadline = new Deadline("Christmas shopping", date, true);
        assertEquals("[D][X] Christmas shopping (by: "
                        + Converter.dateToFormattedString(date)
                        + ")",
                deadline.toString());
    }
}

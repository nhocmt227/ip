package commands;

import exception.JessicaException;
import org.junit.jupiter.api.Test;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Warning:
 * This class is not a pure Unit-test class
 * because it is dependent on other classes
 */

public class ConverterTest {

    // Unit test for stringToDate method
    @Test
    public void stringToDate_validInput() {
        assertEquals(LocalDate.of(2024, 6, 1), Converter.stringToDate("2024-06-01"));
        assertEquals(LocalDate.of(2023, 12, 31), Converter.stringToDate("2023-12-31"));
    }

    @Test
    public void stringToDate_invalidFormat_exceptionThrown() {
        assertThrows(DateTimeParseException.class, () -> Converter.stringToDate("06-01-2024")); // MM-DD-YYYY
        assertThrows(DateTimeParseException.class, () -> Converter.stringToDate("2024/06/01")); // YYYY/MM/DD
        assertThrows(DateTimeParseException.class, () -> Converter.stringToDate("June 1, 2024")); // Long format
        assertThrows(DateTimeParseException.class, () -> Converter.stringToDate("01-06-2024")); // DD-MM-YYYY
        assertThrows(DateTimeParseException.class, () -> Converter.stringToDate("2024-13-01")); // Invalid month
        assertThrows(DateTimeParseException.class, () -> Converter.stringToDate("2024-06-32")); // Invalid day
        assertThrows(DateTimeParseException.class, () -> Converter.stringToDate("abc-def-ghi")); // Non-numeric input
        assertThrows(DateTimeParseException.class, () -> Converter.stringToDate("")); // Empty input
        assertThrows(DateTimeParseException.class, () -> Converter.stringToDate("  ")); // Empty input
    }

    @Test
    public void stringToDate_extraSpaces_validInput() {
        assertEquals(LocalDate.of(2024, 6, 1), Converter.stringToDate("  2024-06-01  "));
        assertEquals(LocalDate.of(2023, 12, 31), Converter.stringToDate("\t2023-12-31\n"));
    }

    // Unit test for dateToFormattedString method
    @Test
    public void dateToFormattedString_validInput() {
        assertEquals("Oct 12 2022", Converter.dateToFormattedString(LocalDate.of(2022, 10, 12)));
        assertEquals("Jan 01 2024", Converter.dateToFormattedString(LocalDate.of(2024, 1, 1)));
        assertEquals("Dec 31 2023", Converter.dateToFormattedString(LocalDate.of(2023, 12, 31)));
    }

    @Test
    public void dateToFormattedString_leapYear() {
        assertEquals("Feb 29 2024", Converter.dateToFormattedString(LocalDate.of(2024, 2, 29)));
    }

    @Test
    public void dateToFormattedString_singleDigitDayMonth() {
        assertEquals("Mar 03 2023", Converter.dateToFormattedString(LocalDate.of(2023, 3, 3)));
        assertEquals("Apr 09 2021", Converter.dateToFormattedString(LocalDate.of(2021, 4, 9)));
    }


    // Unit test for dataLineToTask method
    @Test
    public void dataLineToTask_validToDo() throws JessicaException {
        Task task = Converter.dataLineToTask("T|1|Buy groceries");
        assertInstanceOf(ToDo.class, task);
        assertEquals("Buy groceries", task.getDescription());
        assertTrue(task.getDone());
    }

    @Test
    public void dataLineToTask_validDeadline() throws JessicaException {
        Task task = Converter.dataLineToTask("D|0|Submit report|2024-06-01");
        assertInstanceOf(Deadline.class, task);
        assertEquals("Submit report", task.getDescription());
        assertEquals(LocalDate.of(2024, 6, 1), ((Deadline) task).getDeadline());
        assertFalse(task.getDone());
    }

    @Test
    public void dataLineToTask_validEvent() throws JessicaException {
        Task task = Converter.dataLineToTask("E|1|Company retreat|2024-08-10|2024-08-12");
        assertInstanceOf(Event.class, task);
        assertEquals("Company retreat", task.getDescription());
        assertEquals(LocalDate.of(2024, 8, 10), ((Event) task).getBegin());
        assertEquals(LocalDate.of(2024, 8, 12), ((Event) task).getEnd());
        assertTrue(task.getDone());
    }

    @Test
    public void dataLineToTask_emptyLine_nullReturned() throws JessicaException {
        assertNull(Converter.dataLineToTask(""));
    }

    @Test
    public void dataLineToTask_invalidFormat_exceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> Converter.dataLineToTask("T|1")); // Missing description
        assertThrows(IllegalArgumentException.class, () -> Converter.dataLineToTask("D|0|Submit report")); // Missing deadline
        assertThrows(IllegalArgumentException.class, () -> Converter.dataLineToTask("E|1|Company retreat|2024-08-10")); // Missing end date
    }

    @Test
    public void dataLineToTask_unknownFormat_exceptionThrown() {
        assertThrows(Exception.class, () -> Converter.dataLineToTask("|||")); // Invalid
        assertThrows(Exception.class, () -> Converter.dataLineToTask("T|T")); // Invalid
        assertThrows(Exception.class, () -> Converter.dataLineToTask("|T |")); // Invalid
        assertThrows(Exception.class, () -> Converter.dataLineToTask("TDE")); // Invalid
        assertThrows(Exception.class, () -> Converter.dataLineToTask("1|T|October|")); // Invalid
        assertThrows(Exception.class, () -> Converter.dataLineToTask("E|1||Company retreat|2024-08-10|2024-09-11")); // Invalid
        assertThrows(Exception.class, () -> Converter.dataLineToTask("|1|Company")); // Invalid
    }


    @Test
    public void dataLineToTask_invalidTaskType_exceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> Converter.dataLineToTask("X|1|Mystery Task"));
    }

    // Unit test for taskToDataLine method
    @Test
    public void taskToDataLine_validToDo() {
        Task todo = new ToDo("Buy groceries", true);
        assertEquals("T|1|Buy groceries\n", Converter.taskToDataLine(todo));

        Task todoNotDone = new ToDo("Read book", false);
        assertEquals("T|0|Read book\n", Converter.taskToDataLine(todoNotDone));
    }

    @Test
    public void taskToDataLine_validDeadline() {
        Task deadline = new Deadline("Submit report", LocalDate.of(2024, 6, 1), false);
        assertEquals("D|0|Submit report|2024-06-01\n", Converter.taskToDataLine(deadline));

        Task deadlineDone = new Deadline("Pay bills", LocalDate.of(2023, 12, 31), true);
        assertEquals("D|1|Pay bills|2023-12-31\n", Converter.taskToDataLine(deadlineDone));
    }

    @Test
    public void taskToDataLine_validEvent() throws JessicaException {
        Task event = new Event("Company retreat", LocalDate.of(2024, 8, 10), LocalDate.of(2024, 8, 12), false);
        assertEquals("E|0|Company retreat|2024-08-10|2024-08-12\n", Converter.taskToDataLine(event));

        Task eventDone = new Event("Music festival", LocalDate.of(2025, 7, 15), LocalDate.of(2025, 7, 16), true);
        assertEquals("E|1|Music festival|2025-07-15|2025-07-16\n", Converter.taskToDataLine(eventDone));
    }

    @Test
    public void taskToDataLine_invalidCharacter_exceptionThrown() throws JessicaException {
        Task todo = new ToDo("Invalid | Description", false);
        assertThrows(IllegalArgumentException.class, () -> Converter.taskToDataLine(todo));

        Task deadline = new Deadline("Wrong | Deadline", LocalDate.of(2024, 10, 10), false);
        assertThrows(IllegalArgumentException.class, () -> Converter.taskToDataLine(deadline));

        Task event = new Event("Bad | Event", LocalDate.of(2024, 9, 10), LocalDate.of(2024, 9, 12), false);
        assertThrows(IllegalArgumentException.class, () -> Converter.taskToDataLine(event));
    }

    @Test
    public void taskToDataLine_unknownTaskType_exceptionThrown() {
        class MockTask extends Task {
            public MockTask(String description) {
                super(description);
            }
        }

        Task unknownTask = new MockTask("Unknown task");
        assertThrows(IllegalArgumentException.class, () -> Converter.taskToDataLine(unknownTask));
    }
}

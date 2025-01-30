package commands;

import exception.JessicaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    // unit test for getMarkIndex method
    @Test
    public void getMarkIndex_validInput() throws JessicaException {
        assertEquals(1, Parser.getMarkIndex("mark 1"));
        assertEquals(3, Parser.getMarkIndex("  mark  3  "));
        assertEquals(22, Parser.getMarkIndex(" mark 22"));
    }

    @Test
    public void getMarkIndex_invalidInput_exceptionThrown() {
        JessicaException e = null;
        e = assertThrows(JessicaException.class, () -> Parser.getMarkIndex("mark mark"));
        assertEquals("Mark index must be a number, try again", e.getMessage());
        e = assertThrows(JessicaException.class, () -> Parser.getMarkIndex("mark 0"));
        assertEquals("Mark index must be a positive number, try again", e.getMessage());
        e = assertThrows(JessicaException.class, () -> Parser.getMarkIndex("mark -1"));
        assertEquals("Mark index must be a positive number, try again", e.getMessage());
    }

    @Test
    public void getMarkIndex_unknownInput_exceptionThrown() {
        assertThrows(JessicaException.class, () -> Parser.getMarkIndex("mark4"));
        assertThrows(JessicaException.class, () -> Parser.getMarkIndex("4 mark"));
        assertThrows(JessicaException.class, () -> Parser.getMarkIndex("0 mark"));
    }

    @Test
    public void getMarkIndex_inputTooBig_exceptionThrown() {
        JessicaException e1 = assertThrows(JessicaException.class, () -> Parser.getMarkIndex(" mark 999999999999"));
        assertEquals("Mark index must be a number, try again", e1.getMessage());
    }


    // unit test for getUnmarkIndex method
    @Test
    public void getUnmarkIndex_validInput() throws JessicaException {
        assertEquals(1, Parser.getUnmarkIndex("unmark 1"));
        assertEquals(3, Parser.getUnmarkIndex("  unmark  3  "));
        assertEquals(22, Parser.getUnmarkIndex(" unmark 22"));
    }

    @Test
    public void getUnmarkIndex_invalidInput_exceptionThrown() {
        JessicaException e = null;
        e = assertThrows(JessicaException.class, () -> Parser.getUnmarkIndex("unmark mark"));
        assertEquals("Unmark index must be a number, try again", e.getMessage());
        e = assertThrows(JessicaException.class, () -> Parser.getUnmarkIndex("unmark 0"));
        assertEquals("Unmark index must be a positive number, try again", e.getMessage());
        e = assertThrows(JessicaException.class, () -> Parser.getUnmarkIndex("unmark -1"));
        assertEquals("Unmark index must be a positive number, try again", e.getMessage());
    }

    @Test
    public void getUnmarkIndex_unknownInput_exceptionThrown() {
        assertThrows(JessicaException.class, () -> Parser.getUnmarkIndex("unmark4"));
        assertThrows(JessicaException.class, () -> Parser.getUnmarkIndex("4 mark"));
        assertThrows(JessicaException.class, () -> Parser.getUnmarkIndex("0 unmark"));
    }

    @Test
    public void getUnmarkIndex_inputTooBig_exceptionThrown() {
        JessicaException e1 = assertThrows(JessicaException.class, () -> Parser.getUnmarkIndex(" unmark 999999999999"));
        assertEquals("Unmark index must be a number, try again", e1.getMessage());
    }

    // Unit test for getDeleteIndex method
    @Test
    public void getDeleteIndex_validInput() throws JessicaException {
        assertEquals(1, Parser.getDeleteIndex("delete 1"));
        assertEquals(100, Parser.getDeleteIndex("delete 100"));
        assertEquals(5, Parser.getDeleteIndex("  delete   5  "));
    }

    @Test
    public void getDeleteIndex_invalidInput_exceptionThrown() {
        assertThrows(JessicaException.class, () -> Parser.getDeleteIndex("delete"));
        assertThrows(JessicaException.class, () -> Parser.getDeleteIndex(" delete delete"));
        assertThrows(JessicaException.class, () -> Parser.getDeleteIndex("delete   12e"));
        assertThrows(JessicaException.class, () -> Parser.getDeleteIndex(" delete -1"));
        assertThrows(JessicaException.class, () -> Parser.getDeleteIndex("delete 0"));
    }

    @Test
    public void getDeleteIndex_unknownInput_exceptionThrown() {
        assertThrows(JessicaException.class, () -> Parser.getDeleteIndex("delete1"));
        assertThrows(JessicaException.class, () -> Parser.getDeleteIndex("1 delete"));
    }

    @Test
    public void getDeleteIndex_inputTooBig_exceptionThrown() {
        assertThrows(JessicaException.class, () -> Parser.getDeleteIndex("delete 999999999999"));
    }

    // Unit test for getToDoDescription method
    @Test
    public void getToDoDescription_validInput() throws JessicaException {
        assertEquals("buy groceries", Parser.getToDoDescription("todo buy groceries"));
        assertEquals("finish project", Parser.getToDoDescription("  todo   finish project   "));
    }

    @Test
    public void getToDoDescription_invalidDescription_exceptionThrown() {
        assertThrows(JessicaException.class, () -> Parser.getToDoDescription("  todo"));
        assertThrows(JessicaException.class, () -> Parser.getToDoDescription("todo "));
        assertThrows(JessicaException.class, () -> Parser.getToDoDescription(""));
        assertThrows(JessicaException.class, () -> Parser.getToDoDescription("deadline read books"));
        assertThrows(JessicaException.class, () -> Parser.getToDoDescription("todoreadbooks"));
        assertThrows(JessicaException.class, () -> Parser.getToDoDescription("910991todo"));
        assertThrows(JessicaException.class, () -> Parser.getToDoDescription("read books todo"));
    }

    // Unit test for getDeadlineDescription method
    @Test
    public void getDeadlineDescription_validInput() throws JessicaException {
        assertEquals("submit report", Parser.getDeadlineDescription("deadline submit report /by 2024-06-01"));
        assertEquals("finish project", Parser.getDeadlineDescription("  deadline   finish project   /by 2024-06-01"));
        assertEquals("run 5km", Parser.getDeadlineDescription("deadline run 5km /by 2021-22-11"));
    }

    // Unit test for getDeadlineDate method
    @Test
    public void getDeadlineDate_validInput() throws JessicaException {
        assertEquals("2024-06-01", Parser.getDeadlineDate("deadline submit report /by 2024-06-01"));
        assertEquals("2023-12-31", Parser.getDeadlineDate("  deadline   finish project   /by 2023-12-31"));
        assertEquals("tomorrow", Parser.getDeadlineDate("deadline run 5km /by tomorrow"));
    }

    @Test
    public void getDeadlineDate_missingDate_exceptionThrown() {
        assertThrows(JessicaException.class, () -> Parser.getDeadlineDate("deadline submit report /by"));
        assertThrows(JessicaException.class, () -> Parser.getDeadlineDate("deadline homework /by   "));
    }

    @Test
    public void getDeadlineDate_missingByKeyword_exceptionThrown() {
        assertThrows(JessicaException.class, () -> Parser.getDeadlineDate("deadline submit report"));
        assertThrows(JessicaException.class, () -> Parser.getDeadlineDate("deadline project due tomorrow"));
    }

    // Unit test for getEventDescription method
    @Test
    public void getEventDescription_validInput() throws JessicaException {
        assertEquals("team meeting", Parser.getEventDescription("event team meeting /from 2024-06-01"));
        assertEquals("concert night", Parser.getEventDescription("  event   concert night   /from 2023-12-31"));
        assertEquals("birthday party", Parser.getEventDescription("event birthday party /from next week"));
    }

    // Unit test for getEventBeginDate method
    @Test
    public void getEventBeginDate_validInput() throws JessicaException {
        assertEquals("2024-06-01", Parser.getEventBeginDate("event team meeting /from 2024-06-01 /to 2024-06-02"));
        assertEquals("Monday", Parser.getEventBeginDate("  event   workshop   /from Monday /to Tuesday"));
        assertEquals("next week", Parser.getEventBeginDate("event trip /from next week /to next month"));
    }

    @Test
    public void getEventBeginDate_missingFromKeyword_exceptionThrown() {
        assertThrows(JessicaException.class, () -> Parser.getEventBeginDate("event seminar /to 2024-06-02"));
        assertThrows(JessicaException.class, () -> Parser.getEventBeginDate("event hackathon"));
    }

    // Unit test for getEventEndDate method
    @Test
    public void getEventEndDate_validInput() throws JessicaException {
        assertEquals("2024-06-02", Parser.getEventEndDate("event team meeting /from 2024-06-01 /to 2024-06-02"));
        assertEquals("Tuesday", Parser.getEventEndDate("  event   workshop   /from Monday /to Tuesday"));
        assertEquals("next month", Parser.getEventEndDate("event trip /from next week /to next month"));
    }

    @Test
    public void getEventEndDate_missingEndDate_exceptionThrown() {
        assertThrows(JessicaException.class, () -> Parser.getEventEndDate("event conference /from 2024-06-01 /to"));
        assertThrows(JessicaException.class, () -> Parser.getEventEndDate("event party /from 2024-06-01 /to   "));
    }

    @Test
    public void getEventEndDate_missingFromKeyword_exceptionThrown() {
        assertThrows(JessicaException.class, () -> Parser.getEventEndDate("event seminar /to 2024-06-02"));
        assertThrows(JessicaException.class, () -> Parser.getEventEndDate("event hackathon"));
    }

    @Test
    public void getEventEndDate_missingToKeyword_exceptionThrown() {
        assertThrows(JessicaException.class, () -> Parser.getEventEndDate("event concert /from 2024-06-01"));
        assertThrows(JessicaException.class, () -> Parser.getEventEndDate("event company retreat /from next Monday"));
    }

    // Unit test for getFindDescription method
    @Test
    public void getFindDescription_validInput() throws JessicaException {
        assertEquals("aaa", Parser.getFindDescription("find aaa"));
        assertEquals("bbb", Parser.getFindDescription("  find  bbb"));
        assertEquals("ccc", Parser.getFindDescription("find  ccc "));
    }

    @Test
    public void getFindDescription_inValidInput() {
        assertThrows(JessicaException.class, () -> Parser.getFindDescription("find"));
        assertThrows(JessicaException.class, () -> Parser.getFindDescription(" find   "));
        assertThrows(JessicaException.class, () -> Parser.getFindDescription("2find"));
        assertThrows(JessicaException.class, () -> Parser.getFindDescription("findmessage"));
        assertThrows(JessicaException.class, () -> Parser.getFindDescription("findfind"));
        assertThrows(JessicaException.class, () -> Parser.getFindDescription("3 find"));
    }

    // Unit test for detectBye method
    @Test
    public void detectBye_exactMatch() {
        assertTrue(Parser.detectBye("bye"));
    }

    @Test
    public void detectBye_extraSpaces_true() {
        assertTrue(Parser.detectBye(" bye "));
        assertTrue(Parser.detectBye("   bye   "));
    }

    @Test
    public void detectBye_caseInsensitive_false() {
        assertFalse(Parser.detectBye("Bye"));
        assertFalse(Parser.detectBye("BYE"));
        assertFalse(Parser.detectBye("bYe"));
    }

    @Test
    public void detectBye_partialMatch_false() {
        assertFalse(Parser.detectBye("goodbye"));
        assertFalse(Parser.detectBye("say bye"));
        assertFalse(Parser.detectBye("bye now"));
    }
}

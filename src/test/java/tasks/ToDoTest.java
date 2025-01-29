package tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Warning:
 * This class is not a pure Unit-test class
 * because it is dependent on other classes
 */

public class ToDoTest {

    // ✅ Test default isDone state (should be false)
    @Test
    public void todo_defaultNotDone() {
        ToDo todo = new ToDo("Read a book");
        assertFalse(todo.getDone());
    }

    // ✅ Test marking a ToDo as done
    @Test
    public void todo_markAsDone() {
        ToDo todo = new ToDo("Complete homework");
        todo.setDone(true);
        assertTrue(todo.getDone());
    }

    // ✅ Test marking a ToDo as not done
    @Test
    public void todo_markAsNotDone() {
        ToDo todo = new ToDo("Finish project", true);
        todo.setDone(false);
        assertFalse(todo.getDone());
    }

    // ✅ Test getting description
    @Test
    public void todo_getDescription() {
        ToDo todo = new ToDo("Buy groceries");
        assertEquals("Buy groceries", todo.getDescription());
    }

    // ✅ Test setting description
    @Test
    public void todo_setDescription() {
        ToDo todo = new ToDo("Old description");
        todo.setDescription("New description");
        assertEquals("New description", todo.getDescription());
    }

    // ✅ Test toString() output for not done task
    @Test
    public void todo_toString_notDone() {
        ToDo todo = new ToDo("Walk the dog");
        assertEquals("[T][ ] Walk the dog", todo.toString());
    }

    // ✅ Test toString() output for done task
    @Test
    public void todo_toString_done() {
        ToDo todo = new ToDo("Exercise", true);
        assertEquals("[T][X] Exercise", todo.toString());
    }
}
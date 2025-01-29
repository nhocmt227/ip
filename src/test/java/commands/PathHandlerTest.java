package commands;

import exception.JessicaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PathHandlerTest {
    // ✅ Test valid "ip" directory at the end
    @Test
    public void findStoragePath_validIpDirectory() throws JessicaException {
        assertEquals("/Users/test/ip/data/jessica.txt", PathHandler.findStoragePath("/Users/test/ip"));
        assertEquals("/home/user/ip/data/jessica.txt", PathHandler.findStoragePath("/home/user/ip"));
    }

    // ✅ Test valid "ip" directory nested inside another folder
    @Test
    public void findStoragePath_nestedIpDirectory() throws JessicaException {
        assertEquals("/Users/test/ip/data/jessica.txt", PathHandler.findStoragePath("/Users/test/ip/project"));
        assertEquals("/home/user/ip/data/jessica.txt", PathHandler.findStoragePath("/home/user/ip/code"));
    }

    // ✅ Test when "ip" directory is not present (should throw exception)
    @Test
    public void findStoragePath_noIpDirectory_exceptionThrown() {
        assertThrows(JessicaException.class, () -> PathHandler.findStoragePath("/Users/test/documents"));
        assertThrows(JessicaException.class, () -> PathHandler.findStoragePath("/home/user/projects"));
    }

    // ✅ Test Windows-style path conversion
    @Test
    public void findStoragePath_windowsPathConversion() throws JessicaException {
        assertEquals("C:/Users/test/ip/data/jessica.txt", PathHandler.findStoragePath("C:\\Users\\test\\ip"));
        assertEquals("D:/workspace/ip/data/jessica.txt", PathHandler.findStoragePath("D:\\workspace\\ip"));
    }
}

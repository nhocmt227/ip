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

    // ✅ Test Windows-style path conversion
    @Test
    public void findStoragePath_windowsPathConversion() throws JessicaException {
        assertEquals("C:/Users/test/ip/data/jessica.txt", PathHandler.findStoragePath("C:\\Users\\test\\ip"));
        assertEquals("D:/workspace/ip/data/jessica.txt", PathHandler.findStoragePath("D:\\workspace\\ip"));
    }

    // ✅ Test path outside the directory
    @Test
    public void findStoragePath_outsideDirectory() throws JessicaException {
        assertEquals("C:/Users/test/ip/data/jessica.txt", PathHandler.findStoragePath("C:/Users/test"));
        assertEquals("D:/workspace/ip/data/jessica.txt", PathHandler.findStoragePath("D:/workspace"));
    }
//    // ✅ Test path at random location
//    @Test
//    public void findStoragePath_randomLocation() throws JessicaException {
//        assertEquals("C:/Users/test/ip/data/jessica.txt", PathHandler.findStoragePath("C:/Users/test"));
//        assertEquals("D:/workspace/ip/data/jessica.txt", PathHandler.findStoragePath("D:/workspace"));
//    }

}

package commands;

import exception.JessicaException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Warning:
 * This test class only work in the local developer's computer
 * To make this test class to work in a different environment,
 * modify the CORRECT_PATH to point to the storage location in your computer
 */

public class PathHandlerTest {

    private static final String CORRECT_PATH = "/C:/Users/phong/Documents/Local Computer/NUS/Y2S2/CS2103T/Individual Project/ip/data/jessica.txt";

    // ✅ Test valid "ip" directory at the end
    @Test
    public void findStoragePath_validIpDirectory() throws JessicaException, URISyntaxException {
        assertEquals(CORRECT_PATH, PathHandler.findStoragePath());
    }

}

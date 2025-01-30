package commands;

import exception.JessicaException;
import jessica.Jessica;

import java.io.File;
import java.net.URISyntaxException;

public class PathHandler {
    public static String findStoragePath() throws JessicaException, URISyntaxException {
        String path = Jessica.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        // Handle the case when current location is inside the project
        path = path.replace("\\", "/");
        for (int i = path.length() - 1; i >= 0; i--) {
            if (path.charAt(i) == '/') {
                String s = path.substring(i + 1);
                if (s.equals("ip")) {
                    return path + "/data/jessica.txt";
                }
                path = path.substring(0, i);
            }
        }
        throw new JessicaException("Unknown error in findStoragePath");
    }
}

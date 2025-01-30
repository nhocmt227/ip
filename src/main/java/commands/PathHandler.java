package commands;

import exception.JessicaException;

public class PathHandler {
    public static String findStoragePath(String currentDirPath) throws JessicaException {
        String originalPath = currentDirPath;
        // Handle the case when current location is inside the project
        currentDirPath = currentDirPath.replace("\\", "/");
        for (int i = currentDirPath.length() - 1; i >= 0; i--) {
            if (currentDirPath.charAt(i) == '/') {
                String s = currentDirPath.substring(i + 1);
                if (s.equals("ip")) {
                    return currentDirPath + "/data/jessica.txt";
                }
                currentDirPath = currentDirPath.substring(0, i);
            }
        }

        // Handle the case when current location is outside the project
        originalPath = originalPath + "/ip/data/jessica.txt";
        return originalPath;
    }
}

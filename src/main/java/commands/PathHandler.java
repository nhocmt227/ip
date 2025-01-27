package commands;

import exception.JessicaException;

public class PathHandler {
    public static String findStoragePath(String currentDirPath) throws JessicaException {
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
        throw new JessicaException("Unknown error when find path");
    }
}

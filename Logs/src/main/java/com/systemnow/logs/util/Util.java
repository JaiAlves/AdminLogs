package com.systemnow.logs.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Util {

    public static String getCurrentPath() {
        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString();
    }
}

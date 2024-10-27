package me.kammoun.utils.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BJFileUtils {
    public static String readFileToString(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public static void writeStringToFile(String path, String content) throws IOException {
        Files.write(Paths.get(path), content.getBytes());
    }

    public static boolean fileExists(String path) {
        return new File(path).exists();
    }
}

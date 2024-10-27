package me.kammoun.utils.utils;

public class BJSystemUtils {

    public static String getOSName() {
        return System.getProperty("os.name");
    }

    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    public static long getAvailableMemory() {
        return Runtime.getRuntime().freeMemory();
    }
}

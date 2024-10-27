package me.kammoun.utils.utils;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class BJFontUtils {

    public static Font loadFont(String path, float size) {
        try (InputStream is = BJFontUtils.class.getResourceAsStream(path)) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, (int) size);
        }
    }

    public static Font setFontStyle(Font font, int style) {
        return font.deriveFont(style);
    }
}

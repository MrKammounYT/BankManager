package me.kammoun.utils.utils;

import javax.swing.*;
import java.awt.*;

public class BJIconUtils {
    public static Icon createIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}

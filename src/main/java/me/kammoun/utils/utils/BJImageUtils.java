package me.kammoun.utils.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BJImageUtils {
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image scaleImage(BufferedImage image, int width, int height) {
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}

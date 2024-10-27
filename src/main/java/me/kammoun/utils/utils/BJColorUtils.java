package me.kammoun.utils.utils;

import java.awt.*;

public class BJColorUtils {

    public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float) ratio;
        float ir = (float) 1.0 - r;

        float[] rgb1 = color1.getComponents(null);
        float[] rgb2 = color2.getComponents(null);

        return new Color(rgb1[0] * r + rgb2[0] * ir,
                rgb1[1] * r + rgb2[1] * ir,
                rgb1[2] * r + rgb2[2] * ir);
    }

    public static Color fromHex(String hex) {
        return Color.decode(hex);
    }
}

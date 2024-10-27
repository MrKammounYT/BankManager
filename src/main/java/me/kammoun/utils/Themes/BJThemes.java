package me.kammoun.utils.Themes;

import java.awt.*;

public enum BJThemes {
    LIGHT(new Color(255, 255, 255), new Color(0, 0, 0)),
    DARK(new Color(34, 34, 34), new Color(255, 255, 255));

    private final Color background;
    private final Color foreground;

    BJThemes(Color background, Color foreground) {
        this.background = background;
        this.foreground = foreground;
    }

    public Color getBackground() {
        return background;
    }

    public Color getForeground() {
        return foreground;
    }
}

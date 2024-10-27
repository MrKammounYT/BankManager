package me.kammoun.utils.Layouts;

import java.awt.*;

public class BJBorderLayout {
    public static void addToNorth(Container parent, Component component) {
        parent.add(component, BorderLayout.NORTH);
    }

    public static void addToSouth(Container parent, Component component) {
        parent.add(component, BorderLayout.SOUTH);
    }

    public static void addToEast(Container parent, Component component) {
        parent.add(component, BorderLayout.EAST);
    }

    public static void addToWest(Container parent, Component component) {
        parent.add(component, BorderLayout.WEST);
    }

    public static void addToCenter(Container parent, Component component) {
        parent.add(component, BorderLayout.CENTER);
    }
}

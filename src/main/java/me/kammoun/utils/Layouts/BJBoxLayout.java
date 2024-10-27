package me.kammoun.utils.Layouts;

import javax.swing.*;
import java.awt.*;

public class BJBoxLayout {
    public static Box createVerticalBox(Component... components) {
        Box box = Box.createVerticalBox();
        for (Component component : components) {
            box.add(component);
            box.add(Box.createVerticalStrut(5)); // Add space between components
        }
        return box;
    }

    public static Box createHorizontalBox(Component... components) {
        Box box = Box.createHorizontalBox();
        for (Component component : components) {
            box.add(component);
            box.add(Box.createHorizontalStrut(5));
        }
        return box;
    }
}

package me.kammoun.utils.Layouts;

import javax.swing.*;
import java.awt.*;

public class BJGroupLayout {

    public static GroupLayout createGroupLayout(Container container) {
        GroupLayout layout = new GroupLayout(container);
        container.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        return layout;
    }

    public static void configureHorizontalGroup(GroupLayout layout, GroupLayout.Group group) {
        layout.setHorizontalGroup(group);
    }

    public static void configureVerticalGroup(GroupLayout layout, GroupLayout.Group group) {
        layout.setVerticalGroup(group);
    }
}

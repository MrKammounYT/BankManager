package me.kammoun.utils.Core;

import javax.swing.*;
import java.awt.*;

public class BJFrame extends JFrame {

    public BJFrame(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public BJFrame setBackgroundColor(Color color) {
        getContentPane().setBackground(color);
        return this;
    }

    public BJFrame addComponent(Component component) {
        add(component);
        return this;
    }

    public BJFrame useLayout(LayoutManager layout) {
        setLayout(layout);
        return this;
    }

    public void showFrame() {
        setVisible(true);
    }
}

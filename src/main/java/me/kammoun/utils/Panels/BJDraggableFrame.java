package me.kammoun.utils.Panels;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class BJDraggableFrame extends JFrame {
    private int mouseX, mouseY;

    public BJDraggableFrame(String title) {
        super(title);
        setUndecorated(true);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen() - mouseX, e.getYOnScreen() - mouseY);
            }
        });
    }

}

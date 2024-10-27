package me.kammoun.utils.Animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BJAnimation {

    public static void fadeIn(JComponent component, int duration) {
        new Timer(30, new ActionListener() {
            float alpha = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += 0.05;
                component.setOpaque(true);
                component.setBackground(new Color(1, 1, 1, Math.min(alpha, 1)));
                if (alpha >= 1) ((Timer) e.getSource()).stop();
                component.repaint();
            }
        }).start();
    }
}

package me.kammoun.utils.utils;

import javax.swing.*;
import java.awt.*;

public class BJToastNotification extends JWindow {

    public BJToastNotification(String message, int duration, Color bgColor) {
        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);

        getContentPane().add(panel);
        setSize(300, 50);
        setLocationRelativeTo(null);  // Centered on the screen

        new Timer(duration, e -> dispose()).start();
    }

    public void showToast() {
        setVisible(true);
    }
}

package me.kammoun.utils.Animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BJAnimatedButton extends JButton {

    private Color hoverColor;
    private Color defaultColor;

    public BJAnimatedButton(String text, Color defaultColor, Color hoverColor) {
        super(text);
        this.defaultColor = defaultColor;
        this.hoverColor = hoverColor;
        setBackground(defaultColor);
        setFocusPainted(false);
        setBorderPainted(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                animateColorChange(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                animateColorChange(defaultColor);
            }
        });
    }

    private void animateColorChange(Color targetColor) {
        Timer timer = new Timer(10, null);
        timer.addActionListener(e -> {
            setBackground(interpolateColor(getBackground(), targetColor, 0.1f));
            if (getBackground().equals(targetColor)) timer.stop();
        });
        timer.start();
    }

    private Color interpolateColor(Color c1, Color c2, float fraction) {
        int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * fraction);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * fraction);
        int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * fraction);
        return new Color(r, g, b);
    }
}
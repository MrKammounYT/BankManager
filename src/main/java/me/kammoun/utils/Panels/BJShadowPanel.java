package me.kammoun.utils.Panels;

import javax.swing.*;
import java.awt.*;

public class BJShadowPanel extends JPanel {

    private final int shadowSize;
    private final Color shadowColor;

    public BJShadowPanel(int shadowSize, Color shadowColor) {
        this.shadowSize = shadowSize;
        this.shadowColor = shadowColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(shadowColor);
        g2d.fillRoundRect(shadowSize, shadowSize, getWidth() - shadowSize, getHeight() - shadowSize, 20, 20);

        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth() - shadowSize, getHeight() - shadowSize, 20, 20);
        g2d.dispose();
    }
}

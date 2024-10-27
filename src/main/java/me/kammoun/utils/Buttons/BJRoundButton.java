package me.kammoun.utils.Buttons;

import javax.swing.JButton;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BJRoundButton extends JButton {
    private Color startColor = Color.BLUE;
    private Color endColor = Color.CYAN;
    private boolean hovered = false;

    public BJRoundButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Additional click effects can be added here
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();

        if (hovered) {
            g2.setPaint(new GradientPaint(0, 0, endColor, width, height, startColor));
        } else {
            g2.setPaint(new GradientPaint(0, 0, startColor, width, height, endColor));
        }

        g2.fillRoundRect(0, 0, width, height, height, height);

        g2.setColor(getForeground());
        FontMetrics fm = g2.getFontMetrics();
        int stringWidth = fm.stringWidth(getText());
        int stringHeight = fm.getAscent();
        g2.drawString(getText(), (width - stringWidth) / 2, (height + stringHeight) / 2 - 3);

        g2.dispose();
        super.paintComponent(g);
    }

    public void setStartColor(Color startColor) {
        this.startColor = startColor;
        repaint();
    }

    public void setEndColor(Color endColor) {
        this.endColor = endColor;
        repaint();
    }
}

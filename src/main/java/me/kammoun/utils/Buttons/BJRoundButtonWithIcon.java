package me.kammoun.utils.Buttons;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.ToolTipManager;
import java.awt.*;

public class BJRoundButtonWithIcon extends JButton {
    private ImageIcon icon;
    private String tooltipText;

    public BJRoundButtonWithIcon(String text, ImageIcon icon) {
        super(text);
        this.icon = icon;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);

        // Enable tooltips
        ToolTipManager.sharedInstance().registerComponent(this);
    }

    @Override
    public String getToolTipText() {
        return tooltipText;
    }

    public void setToolTipText(String tooltipText) {
        this.tooltipText = tooltipText;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();

        g2.setPaint(new GradientPaint(0, 0, Color.BLUE, width, height, Color.CYAN));
        g2.fillRoundRect(0, 0, width, height, height, height);

        if (icon != null) {
            int iconX = (width - icon.getIconWidth()) / 2;
            int iconY = (height - icon.getIconHeight()) / 2;
            icon.paintIcon(this, g2, iconX, iconY);
        }

        g2.setColor(getForeground());
        FontMetrics fm = g2.getFontMetrics();
        int stringWidth = fm.stringWidth(getText());
        int stringHeight = fm.getAscent();
        g2.drawString(getText(), (width - stringWidth) / 2, height - 5);

        g2.dispose();
        super.paintComponent(g);
    }
}


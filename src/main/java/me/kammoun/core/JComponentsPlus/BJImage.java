package me.kammoun.core.JComponentsPlus;

import javax.swing.*;
import java.awt.*;

public class BJImage extends JLabel {

    public BJImage(ImageIcon icon) {
        super(icon);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    public static BJImage of(String imagePath) {
        return new BJImage(new ImageIcon(imagePath));
    }

    public BJImage setImageSize(int width, int height) {
        ImageIcon icon = (ImageIcon) getIcon();
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledImage));
        return this;
    }

    public BJImage alignHorizontally(int alignment) {
        setHorizontalAlignment(alignment);
        return this;
    }

    public BJImage alignVertically(int alignment) {
        setVerticalAlignment(alignment);
        return this;
    }

    public BJImage setTooltip(String text) {
        setToolTipText(text);
        return this;
    }
}

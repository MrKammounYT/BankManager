package me.kammoun.core.JComponentsPlus;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickableText extends JLabel {

    public ClickableText(String text, Color normalColor, Color hoverColor) {
        super(text);
        setForeground(normalColor);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFont(new Font("Arial", Font.PLAIN, 14));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(normalColor);
            }


        });
    }


}
package me.kammoun.utils.TextField;

import javax.swing.*;
import java.awt.*;

public class BJPasswordTextField extends JPasswordField {
    private Color normalColor = Color.BLACK;
    private Color focusColor = Color.BLUE;

    public BJPasswordTextField() {
        super();
        setEchoChar('*');

        addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                setBorder(BorderFactory.createLineBorder(focusColor));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                setBorder(BorderFactory.createLineBorder(normalColor));
            }
        });
    }

    public void setNormalColor(Color normalColor) {
        this.normalColor = normalColor;
        setBorder(BorderFactory.createLineBorder(normalColor));
    }

    public void setFocusColor(Color focusColor) {
        this.focusColor = focusColor;
    }
}
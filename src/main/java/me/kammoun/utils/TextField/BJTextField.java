package me.kammoun.utils.TextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class BJTextField extends JTextField {
    private String placeholder;
    private Color normalColor = Color.BLACK;
    private Color errorColor = Color.RED;

    public BJTextField(String placeholder) {
        this.placeholder = placeholder;
        setForeground(Color.GRAY);
        setText(placeholder);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(normalColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setForeground(Color.GRAY);
                    setText(placeholder);
                }
            }
        });
    }

    public void setNormalColor(Color normalColor) {
        this.normalColor = normalColor;
    }

    public void setErrorColor(Color errorColor) {
        this.errorColor = errorColor;
    }

    public void validateInput(boolean isValid) {
        if (isValid) {
            setBorder(BorderFactory.createLineBorder(normalColor));
        } else {
            setBorder(BorderFactory.createLineBorder(errorColor));
        }
    }
}

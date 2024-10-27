package me.kammoun.utils.Buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BJSimpleButton extends JButton {

    private Color hoverColor;
    private Color defaultColor;
    private Color ClickColor;
    final String placeholder;

    public BJSimpleButton(String placeholder, Color hoverColor,Color ClickColor) {
        super(placeholder);
        this.hoverColor = hoverColor;
        this.placeholder = placeholder;
        this.defaultColor = getBackground();
        this.ClickColor = ClickColor;
        addHoverEffect();
    }
    public BJSimpleButton(String placeholder) {
        super(placeholder);
        this.placeholder = placeholder;
    }
    public BJSimpleButton(String placeholder, Color hoverColor, Color ClickColor, Color BackgroundColor) {
        this(placeholder,hoverColor,ClickColor);
        this.defaultColor = BackgroundColor;
        setBackground(BackgroundColor);
        addHoverEffect();
    }
    public BJSimpleButton(String placeholder, Color BackgroundColor) {
        this(placeholder);
        this.defaultColor = BackgroundColor;
        setBackground(BackgroundColor);
    }

    private void addHoverEffect() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                setBackground(ClickColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultColor);
            }

        });
    }


}

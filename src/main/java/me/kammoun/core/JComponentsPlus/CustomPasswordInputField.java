package me.kammoun.core.JComponentsPlus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPasswordField;
import javax.swing.border.Border;

public class CustomPasswordInputField extends JPasswordField {

	private String placeholder;
    private Icon icon;

    public CustomPasswordInputField(String placeholder, Icon icon) {
        this.placeholder = placeholder;
        this.icon = icon;

        setPreferredSize(new Dimension(300, 40));
        setForeground(Color.GRAY);
        setFont(new Font("Arial", Font.PLAIN, 14));
        setBorder(createCustomBorder());

        setText(placeholder);

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            	if(new String(getPassword()).equals(placeholder)) {
	               setText("");
	               setForeground(Color.BLACK);
            	 }
            }

            @Override
            public void focusLost(FocusEvent e) {
            	if(new String(getPassword()).isEmpty()) {
            		setText(placeholder);
                    setForeground(Color.GRAY);
            	}         
            }
        });
    }

    private Border createCustomBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED), // Red underline
                BorderFactory.createEmptyBorder(0, 30, 0, 0) // Padding for the icon
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (icon != null) {
            int iconY = (getHeight() - icon.getIconHeight()) / 2;
            icon.paintIcon(this, g, 0, iconY); 
        }
    }

}

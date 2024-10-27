package me.kammoun.core.JComponentsPlus;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class ButtonWithHoverEvent extends JButton {
	
	
	 private final Color hoverColor;
	    private final Color defaultColor;
	    private final String placeholder;

	    public ButtonWithHoverEvent(String placeholder, Color hoverColor) {
	        super(placeholder);
	        this.hoverColor = hoverColor;
	        this.placeholder = placeholder;
	        this.defaultColor = getBackground(); 

	        setFont(new Font("Arial", Font.BOLD, 14));
	        setFocusPainted(false);
	        setContentAreaFilled(true);
	        setOpaque(true);

	        addHoverEffect();
	    }

	    private void addHoverEffect() {
	        addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                setBackground(hoverColor);
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	                setBackground(defaultColor);
	            }
	        });
	    }

}

package me.kammoun.utils.TextField;

import javax.swing.*;
import java.awt.*;

public class BJTextFieldWithIcon extends JTextField {
    private JLabel iconLabel;

    public BJTextFieldWithIcon(ImageIcon icon,BorderLayout iconPosition) {
        super();
        iconLabel = new JLabel(icon);
        setLayout(new BorderLayout());
        add(iconLabel, iconPosition);
    }
}

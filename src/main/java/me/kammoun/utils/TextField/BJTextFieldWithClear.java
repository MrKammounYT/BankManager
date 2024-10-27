package me.kammoun.utils.TextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BJTextFieldWithClear extends JTextField {
    private JButton clearButton;

    public BJTextFieldWithClear() {
        super();
        clearButton = new JButton("X");
        clearButton.setBorder(BorderFactory.createEmptyBorder());
        clearButton.setContentAreaFilled(false);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setText("");
            }
        });

        setLayout(new BorderLayout());
        add(clearButton, BorderLayout.EAST);
    }
}
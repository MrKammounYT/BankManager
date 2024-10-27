package me.kammoun.utils.Labels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BJAnimatedLabel extends JLabel {
    private String text;
    private int index = 0;
    private Timer timer;

    public BJAnimatedLabel(String text, int delay) {
        this.text = text;
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < text.length()) {
                    setText(getText() + text.charAt(index));
                    index++;
                } else {
                    timer.stop();
                }
            }
        });
    }

    public void startAnimation() {
        setText("");
        index = 0;
        timer.start();
    }

    public void stopAnimation() {
        timer.stop();
    }
}


package me.kammoun.utils.Animation;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class BJAnimatedProgressBar extends JProgressBar {

    private int targetValue = 0;
    private final Timer timer;

    public BJAnimatedProgressBar(int min, int max,Color foregroundColor,Color backgroundColor) {
        super(min, max);
        setStringPainted(true);  // Display percentage
        setForeground(foregroundColor);  // Green color
        setBackground(backgroundColor);
        timer = new Timer();
    }

    public void setProgressSmoothly(int value, int speed) {
        targetValue = Math.min(value, getMaximum());
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (getValue() < targetValue) {
                    setValue(getValue() + 1);
                } else {
                    timer.cancel();
                }
            }
        }, 0, speed);
    }
}
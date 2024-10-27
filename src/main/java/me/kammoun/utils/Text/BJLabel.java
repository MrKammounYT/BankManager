package me.kammoun.utils.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class BJLabel extends JLabel {

    private Color hoverColor;
    private Color defaultColor;
    private boolean isClickable = false;
    private Consumer<BJLabel> onClickAction;

    public BJLabel(String text) {
        super(text);
    }

    public BJLabel(Icon icon) {
        super(icon);
    }

    public BJLabel(String text, Icon icon) {
        super(text, icon, CENTER);
    }
    public BJLabel(String text, Color textColor, Font font) {
        super(text);
        setForeground(textColor);
        setFont(font);
        setCursor(Cursor.getDefaultCursor());
        defaultColor = textColor;
        hoverColor = textColor.brighter();
    }

    public BJLabel(String text, Font font, Color textColor, boolean clickable, Consumer<BJLabel> action) {
        this(text, textColor, font);
        setClickable(clickable, action);
    }

    public static BJLabel of(String text) {
        return new BJLabel(text);
    }

    public static BJLabel of(Icon icon) {
        return new BJLabel(icon);
    }

    public static BJLabel of(String text, Icon icon) {
        return new BJLabel(text, icon);
    }

    public BJLabel setTextColor(Color color) {
        setForeground(color);
        return this;
    }

    public BJLabel setFontStyle(Font font) {
        setFont(font);
        return this;
    }

    public BJLabel alignHorizontally(int alignment) {
        setHorizontalAlignment(alignment);
        return this;
    }

    public BJLabel setTextIconPosition(int position) {
        setHorizontalTextPosition(position);
        return this;
    }

    public BJLabel applyDefaultStyle() {
        setFont(new Font("Arial", Font.PLAIN, 14));
        setForeground(Color.BLACK);
        return this;
    }
    public void setClickable(boolean clickable, Consumer<BJLabel> action) {
        isClickable = clickable;
        onClickAction = action;
        if (clickable) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addHoverEffect();
            addClickListener();
        }
    }

    private void addHoverEffect() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(defaultColor);
            }
        });
    }

    private void addClickListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onClickAction != null) onClickAction.accept(BJLabel.this);
            }
        });
    }
}

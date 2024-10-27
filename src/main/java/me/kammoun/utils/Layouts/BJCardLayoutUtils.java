package me.kammoun.utils.Layouts;

import java.awt.*;

public class BJCardLayoutUtils {
    public static CardLayout createCardLayout() {
        return new CardLayout();
    }

    public static void showNext(Container container) {
        ((CardLayout) container.getLayout()).next(container);
    }

    public static void showPrevious(Container container) {
        ((CardLayout) container.getLayout()).previous(container);
    }

    public static void showComponent(Container container, String name) {
        ((CardLayout) container.getLayout()).show(container, name);
    }
}

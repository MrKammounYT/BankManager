package me.kammoun.utils.Layouts;

import java.awt.*;

public class BJFlowLayout {

    public static FlowLayout createFlowLayout(int alignment, int hgap, int vgap) {
        return new FlowLayout(alignment, hgap, vgap);
    }

    public static FlowLayout createCenterAlignedLayout() {
        return new FlowLayout(FlowLayout.CENTER, 10, 10);
    }

    public static FlowLayout createLeftAlignedLayout() {
        return new FlowLayout(FlowLayout.LEFT, 5, 5);
    }

    public static FlowLayout createRightAlignedLayout() {
        return new FlowLayout(FlowLayout.RIGHT, 5, 5);
    }
}

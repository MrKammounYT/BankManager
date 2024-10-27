package me.kammoun.utils.Layouts;

import java.awt.*;

public class BJGridBagUtils {


    public static GridBagConstraints createConstraints(int gridx, int gridy) {
        return configure(new GridBagConstraints(), gridx, gridy, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
    }

    public static GridBagConstraints createConstraints(int gridx, int gridy, int gridwidth, int gridheight) {
        return configure(new GridBagConstraints(), gridx, gridy, gridwidth, gridheight, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    public static GridBagConstraints configure(
            GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        return gbc;
    }

    public static GridBagConstraints withInsets(GridBagConstraints gbc, int top, int left, int bottom, int right) {
        gbc.insets = new Insets(top, left, bottom, right);
        return gbc;
    }
}

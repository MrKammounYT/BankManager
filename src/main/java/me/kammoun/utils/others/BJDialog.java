package me.kammoun.utils.others;

import javax.swing.*;

public class BJDialog {

    public static void showInfo(String title, String message) {
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(title);
        dialog.setAlwaysOnTop(true);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    public static void showError(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
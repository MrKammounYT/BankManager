package me.kammoun.utils.Panels;

import javax.swing.*;
import java.awt.*;

public class BJTrayFrame extends JFrame {
    private SystemTray tray;
    private TrayIcon trayIcon;

    public BJTrayFrame() {
        super("BJTray Frame");
        if (SystemTray.isSupported()) {
            tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("icon.png");
            trayIcon = new TrayIcon(image, "Tray Demo");
            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(e -> setVisible(true));
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            tray.remove(trayIcon);
        } else {
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                throw new RuntimeException(e);
            }
        }
        super.setVisible(b);
    }
}


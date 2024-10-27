package me.kammoun.core;

import me.kammoun.core.DataBase.MySQLManager;
import me.kammoun.core.Pages.DashBoardPageAdmin;
import me.kammoun.core.Pages.LoginPage;

import javax.swing.*;

public class Main {


	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MySQLManager mySQLManager = new MySQLManager();
                new LoginPage(mySQLManager).setVisible(true);
            }
        });
    }
	
}

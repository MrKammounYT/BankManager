package me.kammoun.core;

import me.kammoun.core.DataBase.MySQLManager;
import me.kammoun.core.Pages.LoginPage;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class Main {
    public  static  NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MySQLManager mySQLManager = new MySQLManager();
                new LoginPage(mySQLManager);
            }
        });
    }
	
}

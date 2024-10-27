package me.kammoun.Events;

import javax.swing.*;

public class LoginButtonClickEvent {


    final String username;
    final String password;
    final JPanel panel;


    public LoginButtonClickEvent(String username, String password, JPanel panel) {
        this.username = username;
        this.password = password;
        this.panel = panel;
        run();
    }

    protected void run(){
        if(isValid()){
            JOptionPane.showMessageDialog(panel, "Login Successful!");
            return;
        }
        JOptionPane.showMessageDialog(panel, "Invalid Username or Password!");
    }

    private boolean isValid(){
        return false;//valid logic here
    }
}

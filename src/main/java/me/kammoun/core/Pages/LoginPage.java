package me.kammoun.core.Pages;

import javax.swing.*;

import me.kammoun.Events.ForgetPasswordClickEvent;
import me.kammoun.Events.LoginButtonClickEvent;
import me.kammoun.core.JComponentsPlus.ClickableText;
import me.kammoun.core.JComponentsPlus.CustomInputField;
import me.kammoun.core.JComponentsPlus.CustomPasswordInputField;
import me.kammoun.core.JComponentsPlus.RoundButton;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class LoginPage extends JFrame {
	
	final ImageIcon FrameIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/Images/lockImg.png")));
	final ImageIcon BackGroundIMG = new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/Images/bg.png")));
	final ImageIcon userImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/Images/user.png")));
	final ImageIcon lockImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/Images/lockImg.png")));
	
	
	 public LoginPage() {
		 	setIconImage(FrameIcon.getImage());
	        setTitle("Login Page");
	        setSize(1000, 600); 
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the frame is closed
	        setResizable(false);
	        JPanel panel = new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                g.drawImage(BackGroundIMG.getImage(), 0, 0, getWidth(), getHeight(), this);
	            }
	        };
	        panel.setLayout(null);
	        JLabel welcomeLabel = new JLabel("WELCOME");
	        welcomeLabel.setBounds(700, 100, 200, 30); 
	        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
	        panel.add(welcomeLabel);

	        CustomInputField userText = new CustomInputField("UserName",userImg);
	        userText.setBounds(600, 150, 300, 30); 
	        panel.add(userText);


	        CustomPasswordInputField passwordText = new CustomPasswordInputField("*******************",lockImg);
	        passwordText.setBounds(600, 250, 300, 30); 
	        panel.add(passwordText);

			 JLabel outputLabel = new JLabel();
			 outputLabel.setBounds(650,280,200,50);

	        RoundButton loginButton = new RoundButton("Login");
	        loginButton.setBounds(650, 300, 200, 30);
			loginButton.addMouseListener(new MouseAdapter() {
				 @Override
				 public void mouseClicked(MouseEvent e) {
					 new LoginButtonClickEvent(userText.getName(), new String(passwordText.getPassword()),panel);
				 }
			});
	        panel.add(loginButton);

	        JLabel forgetPasswordLabel = new ClickableText("Forget password",Color.black,Color.red);
	        forgetPasswordLabel.setBounds(700, 330, 150, 30);
			forgetPasswordLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					new ForgetPasswordClickEvent();//password Reset Logic Here
				}
			});
	        panel.add(forgetPasswordLabel);


	        
	        
	        add(panel);
	        setLocationRelativeTo(null);
	    }

    
}

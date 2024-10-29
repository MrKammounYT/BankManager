package me.kammoun.core.Pages;

import javax.imageio.ImageIO;
import javax.swing.*;

import me.kammoun.Events.ForgetPasswordClickEvent;
import me.kammoun.core.DataBase.MySQLManager;
import me.kammoun.core.Enums.Roles;
import me.kammoun.core.JComponentsPlus.ClickableText;
import me.kammoun.core.JComponentsPlus.CustomInputField;
import me.kammoun.core.JComponentsPlus.CustomPasswordInputField;
import me.kammoun.core.JComponentsPlus.RoundButton;
import me.kammoun.core.utils.Holder.User;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class LoginPage extends JFrame {

	ImageIcon FrameIcon = new ImageIcon();
	ImageIcon BackGroundIMG = new ImageIcon();
	ImageIcon userImg = new ImageIcon();
	 ImageIcon lockImg = new ImageIcon();

	private CustomInputField userText;
	private CustomPasswordInputField passwordText;
	private JLabel outputLabel;
	private final MySQLManager mySQLManager;

	void setupIMAGES() throws IOException {
		FrameIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Images/lockImg.png")));
		BackGroundIMG = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Images/bg.png")));
		userImg = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Images/user.png")));
		lockImg = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Images/lockImg.png")));
	}
	public LoginPage(MySQLManager mySQLManager) {
		this.mySQLManager = mySQLManager;
		setVisible(true);
        setIconImage(FrameIcon.getImage());
		setTitle("Login Page");
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setupUI();
	}

	private void setupUI() {
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

		userText = new CustomInputField("UserName", userImg);
		userText.setBounds(600, 150, 300, 30);
		panel.add(userText);

		passwordText = new CustomPasswordInputField("*******************", lockImg);
		passwordText.setBounds(600, 250, 300, 30);
		panel.add(passwordText);

		outputLabel = new JLabel();
		outputLabel.setBounds(650, 280, 300, 50);
		outputLabel.setForeground(Color.RED);
		panel.add(outputLabel);

		RoundButton loginButton = new RoundButton("Login");
		loginButton.setBounds(650, 450, 200, 30);
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loginLogic();
			}
		});
		panel.add(loginButton);

		JLabel forgetPasswordLabel = new ClickableText("Forget password", Color.black, Color.red);
		forgetPasswordLabel.setBounds(700, 330, 150, 30);
		forgetPasswordLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ForgetPasswordClickEvent(); //password reset logic here
			}
		});
		panel.add(forgetPasswordLabel);

		add(panel);
		setLocationRelativeTo(null);
	}

	private void loginLogic() {
		String username = userText.getText().trim();
		String password = new String(passwordText.getPassword()).trim();

		if (username.isEmpty() || password.isEmpty()) {
			outputLabel.setText("Username and password cannot be empty.");
			return;
		}

		if (!mySQLManager.isConnected()) {
			outputLabel.setText("Server is down. Please try again later.");
			return;
		}

		User user = mySQLManager.getUserTable().getLoginUserByUsername(username);

		if (user == null) {
			outputLabel.setText("Invalid username.");
			return;
		}
		if (!user.getPassword().equals(mySQLManager.getUserTable().hashPassword(password))) {
			outputLabel.setText("Incorrect password.");
			return;
		}
		outputLabel.setForeground(Color.GREEN);
		outputLabel.setText("Login successful!");
		if(user.getUserRole().equals(Roles.ADMIN) || user.getUserRole().equals(Roles.ADMIN)){
			new DashBoardPageAdmin(mySQLManager).setVisible(true);
		}else{
			new DashBoardPageUser(mySQLManager, mySQLManager.getUserTable().getUserByUsername(username)).setVisible(true);
		}
		dispose(); // Close the login window

	}
    
}

package me.kammoun.core.Pages;

import me.kammoun.core.DataBase.UserTable;
import me.kammoun.core.Enums.Roles;
import me.kammoun.utils.Buttons.BJRoundButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserPage extends JFrame {

    private final UserTable userTable;
    private JTextField usernameField;
    private JTextField balanceField;
    private JPasswordField passwordField;
    private JComboBox<Roles> roleComboBox;
    private BJRoundButton addButton;
    private JLabel statusLabel;
    private final DashBoardPageAdmin dashboardPageAdmin;

    public AddUserPage(UserTable userTable, DashBoardPageAdmin dashboardPageAdmin) {
        this.userTable = userTable;
        this.dashboardPageAdmin = dashboardPageAdmin;
        setTitle("Add User");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        setLayout(new BorderLayout());

        add(createFormPanel(), BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    private void initializeComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        balanceField = new JTextField(20);
        roleComboBox = new JComboBox<>(Roles.values());
        addButton = new BJRoundButton("Add User");
        addButton.setStartColor(Color.orange);
        statusLabel = new JLabel(" ");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddUser();
            }
        });
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        panel.add(roleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Balance:"), gbc);

        gbc.gridx = 1;
        panel.add(balanceField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addButton, gbc);

        return panel;
    }

    private void handleAddUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String number = balanceField.getText().trim();
        Roles role = (Roles) roleComboBox.getSelectedItem();  // Get selected role

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Username and password cannot be empty.");
            return;
        }
        if (!isValidNumber(number)) {
            statusLabel.setText("Invalid balance number!");
            return;
        }

        if (!userTable.isUsernameAvailable(username)) {
            statusLabel.setText("Username is already taken.");
            return;
        }

        userTable.addUser(username, role, password, Integer.parseInt(number));
        statusLabel.setText("User added successfully!");
        clearFields();
        dashboardPageAdmin.refreshList();
        dispose();
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        balanceField.setText("");
        roleComboBox.setSelectedIndex(0);
    }

    private boolean isValidNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

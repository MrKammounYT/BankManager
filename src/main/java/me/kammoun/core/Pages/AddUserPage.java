package me.kammoun.core.Pages;

import me.kammoun.core.DataBase.MySQLManager;
import me.kammoun.core.DataBase.UserTable;
import me.kammoun.core.Enums.Roles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserPage extends JFrame {

    private final UserTable userTable;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<Roles> roleComboBox;
    private JButton addButton;
    private JLabel statusLabel;
    private final DashBoardPageAdmin dashboardPageAdmin;

    public AddUserPage(UserTable userTable,DashBoardPageAdmin dashboardPageAdmin) {
        this.userTable = userTable;
        this.dashboardPageAdmin = dashboardPageAdmin;
        setTitle("Add User");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        setLayout(new BorderLayout());

        // Add components to the frame
        add(createFormPanel(), BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    private void initializeComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        roleComboBox = new JComboBox<>(Roles.values());
        addButton = new JButton("Add User");
        statusLabel = new JLabel(" ");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddUser();
            }
        });
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        panel.add(new JLabel("Role:"));
        panel.add(roleComboBox);

        panel.add(new JLabel());
        panel.add(addButton);

        return panel;
    }

    private void handleAddUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        Roles role = (Roles) roleComboBox.getSelectedItem();  // Get selected role

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Username and password cannot be empty.");
            return;
        }

        if (!userTable.isUsernameAvailable(username)) {
            statusLabel.setText("Username is already taken.");
            return;
        }

        userTable.addUser(username, role, password);
        statusLabel.setText("User added successfully!");
        clearFields();
        dashboardPageAdmin.refreshList();
        dispose();  // Close the frame after adding the user
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        roleComboBox.setSelectedIndex(0);
    }


}

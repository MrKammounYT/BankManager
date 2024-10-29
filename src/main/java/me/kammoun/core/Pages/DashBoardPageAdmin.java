package me.kammoun.core.Pages;

import me.kammoun.core.DataBase.MySQLManager;
import me.kammoun.core.JComponentsPlus.CustomInputField;
import me.kammoun.core.Main;
import me.kammoun.core.utils.Holder.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DashBoardPageAdmin extends JFrame {

    private final String[] columnNames = {"ID", "Name", "Role", "Creation Date", "Balance"};
    private Object[][] data;
    private JTable table;
    private final MySQLManager mySQLManager;

    public DashBoardPageAdmin(MySQLManager mySQLManager) {
        this.mySQLManager = mySQLManager;
        setTitle("Admin Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setupData();
        initializeUI();
    }

    private void initializeUI() {
        table = new JTable(createNonEditableModel(data, columnNames));
        styleTable();

        JPanel headerPanel = createHeaderPanel();
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBorder(new EmptyBorder(20, 20, 20, 20));

        add(headerPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        setLocationRelativeTo(null);  // Center the frame on the screen
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 20));
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        headerPanel.setBackground(new Color(237, 118, 88));

        CustomInputField searchField = createSearchField();
        JButton addUserButton = createAddUserButton();
        JPanel sortPanel = createSortPanel();
        JButton logoutButton = createLogoutButton();

        JPanel westPanel = new JPanel(new BorderLayout(10, 0));
        westPanel.setOpaque(false);
        westPanel.add(searchField, BorderLayout.WEST);
        westPanel.add(addUserButton, BorderLayout.EAST);

        headerPanel.add(westPanel, BorderLayout.WEST);
        headerPanel.add(sortPanel, BorderLayout.CENTER);
        headerPanel.add(logoutButton, BorderLayout.EAST);

        return headerPanel;
    }

    private CustomInputField createSearchField() {
        CustomInputField searchField = new CustomInputField("Search", null);
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable(searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable(searchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable(searchField.getText());
            }
        });

        return searchField;
    }

    private JButton createAddUserButton() {
        JButton addUserButton = new JButton("Add User +");
        addUserButton.setBackground(new Color(233, 66, 66));
        addUserButton.setForeground(Color.WHITE);
        addUserButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        addUserButton.setPreferredSize(new Dimension(120, 40));

        addUserButton.addActionListener(e -> {
            if (!mySQLManager.isConnected()) {
                JOptionPane.showMessageDialog(table, "Server is down! Cannot create an account.");
                return;
            }
            new AddUserPage(mySQLManager.getUserTable(), this).setVisible(true);
        });

        return addUserButton;
    }

    private JPanel createSortPanel() {
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        sortPanel.setOpaque(false);

        JLabel sortLabel = new JLabel("Sort by:");
        sortLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JComboBox<String> sortComboBox = new JComboBox<>(new String[]{"Name", "Date", "Balance"});
        sortComboBox.setPreferredSize(new Dimension(100, 30));
        sortComboBox.addActionListener(sortComboBoxListener(sortComboBox));

        sortPanel.add(sortLabel);
        sortPanel.add(sortComboBox);

        return sortPanel;
    }

    private JButton createLogoutButton() {
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(233, 66, 66));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        logoutButton.setPreferredSize(new Dimension(120, 40));

        logoutButton.addActionListener(e -> {
            // Handle logout action here
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                new LoginPage(mySQLManager);
                dispose();
            }
        });

        return logoutButton;
    }

    private void styleTable() {
        table.setRowHeight(40);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(239, 244, 250));
        table.getTableHeader().setPreferredSize(new Dimension(100, 40));
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(false);

        // Center align text in the table
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private DefaultTableModel createNonEditableModel(Object[][] data, String[] columnNames) {
        return new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private ActionListener sortComboBoxListener(JComboBox<String> sortComboBox) {
        return e -> {
            String selectedItem = (String) sortComboBox.getSelectedItem();
            switch (selectedItem) {
                case "Name" -> table.setModel(sortDataByName());
                case "Date" -> table.setModel(sortDataByDate());
                case "Balance" -> table.setModel(sortDataByBalance());
            }
        };
    }

    private DefaultTableModel sortDataByName() {
        List<Object[]> sortedData = Arrays.stream(data)
                .sorted(Comparator.comparing(a -> String.valueOf(a[1])))
                .collect(Collectors.toList());

        return createNonEditableModel(sortedData.toArray(new Object[0][]), columnNames);
    }

    private DefaultTableModel sortDataByDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        List<Object[]> sortedData = Arrays.stream(data)
                .sorted((a, b) -> {
                    try {
                        Date dateA = dateFormat.parse(String.valueOf(a[3]));
                        Date dateB = dateFormat.parse(String.valueOf(b[3]));
                        return dateA.compareTo(dateB);
                    } catch (ParseException e) {
                        return 0;  // Treat invalid dates as equal
                    }
                })
                .collect(Collectors.toList());

        return createNonEditableModel(sortedData.toArray(new Object[0][]), columnNames);
    }

    private DefaultTableModel sortDataByBalance() {
        List<Object[]> sortedData = Arrays.stream(data)
                .sorted((a, b) -> {
                    try {
                        double balance1 = NumberFormat.getCurrencyInstance(Locale.US)
                                .parse(String.valueOf(a[4])).doubleValue();
                        double balance2 = NumberFormat.getCurrencyInstance(Locale.US)
                                .parse(String.valueOf(b[4])).doubleValue();
                        return Double.compare(balance1, balance2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return 0;
                    }
                })
                .collect(Collectors.toList());

        return createNonEditableModel(sortedData.toArray(new Object[0][]), columnNames);
    }

    private void filterTable(String query) {
        String lowerCaseQuery = query.toLowerCase();
        Object[][] filteredData = Arrays.stream(data)
                .filter(row -> String.valueOf(row[1]).toLowerCase().contains(lowerCaseQuery))
                .toArray(Object[][]::new);

        table.setModel(createNonEditableModel(filteredData, columnNames));
    }

    public void refreshList() {
        setupData();
        table.setModel(createNonEditableModel(data, columnNames));
    }

    private void setupData() {
        if (!mySQLManager.isConnected()) return;
        List<User> users = mySQLManager.getUserTable().getAccountList();
        data = new Object[users.size()][columnNames.length];
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            data[i][0] = user.getId();
            data[i][1] = user.getName();
            data[i][2] = user.getUserRole().toString();
            data[i][3] = user.getAccountCreationDate();
            data[i][4] = Main.currencyFormat.format(user.getBalance());

        }
    }
}

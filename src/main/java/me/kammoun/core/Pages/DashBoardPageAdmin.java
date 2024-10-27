package me.kammoun.core.Pages;

import me.kammoun.core.DataBase.MySQLManager;
import me.kammoun.core.JComponentsPlus.CustomInputField;
import me.kammoun.core.utils.User;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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

    private final String[] columnNames = {"ID", "Name", "Role", "CreationDate", "Balance"};
    private Object[][] data;
    private JTable table;
    private final MySQLManager mySQLManager;

    public DashBoardPageAdmin(MySQLManager mySQLManager) {
        this.mySQLManager = mySQLManager;
        setTitle("Users Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setupData();

        initializeUI();
    }

    private void initializeUI() {
        table = new JTable(new DefaultTableModel(data, columnNames));
        styleTable();

        JPanel headerPanel = createHeaderPanel();
        JScrollPane tableScrollPane = new JScrollPane(table);

        add(headerPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
    }
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());

        CustomInputField searchField = createSearchField();
        JButton addUserButton = createAddUserButton();
        JPanel sortPanel = createSortPanel();

        headerPanel.add(searchField, BorderLayout.WEST);
        headerPanel.add(addUserButton, BorderLayout.CENTER);
        headerPanel.add(sortPanel, BorderLayout.EAST);

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
                JOptionPane.showMessageDialog(table, "You can't create an account right now. The server is down!");
                return;
            }
            new AddUserPage(mySQLManager.getUserTable(), this).setVisible(true);
        });

        return addUserButton;
    }
    private JPanel createSortPanel() {
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        JLabel sortLabel = new JLabel("Sort by:");
        sortLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JComboBox<String> sortComboBox = new JComboBox<>(new String[]{"Name", "Date","Balance"});
        sortComboBox.setPreferredSize(new Dimension(100, 30));
        sortComboBox.addActionListener(sortComboBoxListener(sortComboBox));

        sortPanel.add(sortLabel);
        sortPanel.add(sortComboBox);

        return sortPanel;
    }
    private void styleTable() {
        table.setRowHeight(50);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(239, 244, 250));
        table.getTableHeader().setPreferredSize(new Dimension(100, 40));
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(false);
    }
    private ActionListener sortComboBoxListener(JComboBox<String> sortComboBox) {
        return e -> {
            String selectedItem = (String) sortComboBox.getSelectedItem();
            if ("Name".equals(selectedItem)) {
                table.setModel(sortDataByName());
            } else if ("Date".equals(selectedItem)) {
                table.setModel(sortDataByDate());
            }else if("Balance".equals(selectedItem)) {
                table.setModel(sortDataByBalance());
            }
        };
    }
    private DefaultTableModel sortDataByName() {
        List<Object[]> sortedData = Arrays.stream(data)
                .sorted(Comparator.comparing(a -> String.valueOf(a[1])))
                .collect(Collectors.toList());

        return new DefaultTableModel(sortedData.toArray(new Object[0][]), columnNames);
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
                        return 0; // Treat invalid dates as equal
                    }
                })
                .collect(Collectors.toList());

        return new DefaultTableModel(sortedData.toArray(new Object[0][]), columnNames);
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
                        return 0; //balances as equal
                    }
                })
                .collect(Collectors.toList());

        return new DefaultTableModel(sortedData.toArray(new Object[0][]), columnNames);
    }
    private void filterTable(String query) {
        String lowerCaseQuery = query.toLowerCase();
        Object[][] filteredData = Arrays.stream(data)
                .filter(row -> String.valueOf(row[1]).toLowerCase().contains(lowerCaseQuery))
                .toArray(Object[][]::new);

        table.setModel(new DefaultTableModel(filteredData, columnNames));
    }
    public void refreshList() {
        setupData();
        table.setModel(new DefaultTableModel(data, columnNames));
    }
    private void setupData() {
        if (!mySQLManager.isConnected()) return;

        List<User> users = mySQLManager.getUserTable().getAccountList();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

        data = users.stream()
                .map(user -> new Object[]{
                        user.getId(),
                        user.getName(),
                        user.getUserRole().toString(),
                        user.getAccountCreationDate(),
                        currencyFormat.format(user.getBalance())
                })
                .toArray(Object[][]::new);
    }
}

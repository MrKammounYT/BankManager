package me.kammoun.core.Pages;

import me.kammoun.core.DataBase.MySQLManager;
import me.kammoun.core.JComponentsPlus.CustomInputField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DashBoardPageAdmin extends JFrame {

    public HashMap<Integer, Boolean> editing = new HashMap<>();

    String[] columnNames = {"ID","Name", "Role", "Create Date"};
    Object[][] data = {
            {"David Wagner", "24 Oct, 2015", "Admin", ""},
            {"Ina Hogan", "24 Oct, 2015", "Admin", ""},
            {"Devin Harmon", "18 Dec, 2015", "Employee", ""},
            {"Lena Page", "8 Oct, 2016", "User", ""},
            {"Eula Horton", "15 Jun, 2017", "User", ""},
            {"Victoria Perez", "12 Jan, 2019", "User", ""},
            {"Cora Medina", "21 July, 2020", "User", ""}
    };
    JTable table;
    private DashBoardPageAdmin instance;
    private final MySQLManager mySQLManager;
    public DashBoardPageAdmin(MySQLManager mySQLManager) {
        instance = this;
        this.mySQLManager = mySQLManager;
        if(mySQLManager.isConnected()){
            data = mySQLManager.getUserTable().getAccountList();
        }
        setTitle("Users Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        table = new JTable(data, columnNames);
        JPanel headerPanel = new JPanel();
        CustomInputField searchField = new CustomInputField("Search", null);
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        addSearchFieldListener(searchField);
        JButton addUserButton = new JButton("Add User +");
        addUserButton.setBackground(new Color(233, 66, 66));
        addUserButton.setForeground(Color.WHITE);
        addUserButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        addUserButton.setPreferredSize(new Dimension(120, 40));
        addUserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!mySQLManager.isConnected()){
                    JOptionPane.showMessageDialog(table, "You cant create an account at the moment server is down !");
                    return;
                }
                new AddUserPage(mySQLManager.getUserTable(),instance).setVisible(true);
            }
        });

        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JLabel sortLabel = new JLabel("Sort by:");
        sortLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JComboBox<String> sortComboBox = new JComboBox<>(new String[]{"Name", "Date"});
        sortComboBox.setPreferredSize(new Dimension(100, 30));
        sortComboBox.addActionListener(sortComboBoxListener(sortComboBox));
        sortPanel.add(sortLabel);
        sortPanel.add(sortComboBox);

        headerPanel.add(searchField, BorderLayout.WEST);
        headerPanel.add(addUserButton, BorderLayout.CENTER);
        headerPanel.add(sortPanel, BorderLayout.EAST);

        table.setRowHeight(50);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(239, 244, 250));
        table.getTableHeader().setPreferredSize(new Dimension(100, 40));
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(false);

        JScrollPane tableScrollPane = new JScrollPane(table);
        add(headerPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    private DefaultTableModel sortDataByDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        Arrays.sort(data, (a, b) -> {
            try {
                Date dateA = dateFormat.parse(String.valueOf(a[4]));
                Date dateB = dateFormat.parse(String.valueOf(b[4]));
                return dateA.compareTo(dateB);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        });
        return new DefaultTableModel(data, columnNames);
    }


    private DefaultTableModel sortDataByName() {
        Arrays.sort(data, Comparator.comparing(a -> String.valueOf(a[1])));
        return new DefaultTableModel(data, columnNames);
    }

    private ActionListener sortComboBoxListener(JComboBox<String> sortComboBox) {
        return e -> {
            String selectedItem = (String) sortComboBox.getSelectedItem();
            if (selectedItem.equals("Name")) {
                table.setModel(sortDataByName());
            } else if (selectedItem.equals("Date")) {
                table.setModel(sortDataByDate());
            }
        };
    }

    private void addSearchFieldListener(CustomInputField customInputField) {
        if(customInputField.getText().isEmpty())return;
        customInputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable(customInputField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable(customInputField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable(customInputField.getText());
            }
        });
    }

    private void filterTable(String query) {
        String lowerCaseQuery = query.toLowerCase();
        Object[][] filteredData = Arrays.stream(data)
                .filter(row -> (String.valueOf(row[1])).toLowerCase().contains(lowerCaseQuery))
                .toArray(Object[][]::new);
        table.setModel(new DefaultTableModel(filteredData, columnNames));
    }

    public void refreshList(){
        data = mySQLManager.getUserTable().getAccountList();
        table.setModel(new DefaultTableModel(data, columnNames));

    }

}

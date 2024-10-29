package me.kammoun.core.Pages;

import me.kammoun.core.DataBase.MySQLManager;
import me.kammoun.core.Enums.TransactionType;
import me.kammoun.core.Main;
import me.kammoun.core.utils.Holder.User;
import me.kammoun.core.utils.Holder.UserTransaction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

public class DashBoardPageUser extends JFrame {

    private final MySQLManager mySQLManager;
    private final User currentUser;
    private JTable transactionTable;
    List<UserTransaction> transactions;


    public DashBoardPageUser(MySQLManager mySQLManager, User user) {
        this.mySQLManager = mySQLManager;
        this.currentUser = user;
        if(!mySQLManager.isConnected()){
            JOptionPane.showMessageDialog(null, "Server is Down! Please try again later.");
            return;
        }
        setTitle("Dashboard - " + user.getName());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        currentUser.setTransactions(mySQLManager.getUserTransactionsTable().getTransactionsForUser(currentUser.getId()));
        transactions = currentUser.getTransactions();
        setupUI();
        setLocationRelativeTo(null);
    }

    private void setupUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = createTransactionTablePanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        headerPanel.setBackground(new Color(237, 118, 88));

        // Welcome message on the left
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);

        // Display balance on the right
        JLabel balanceLabel = new JLabel("Balance: " + Main.currencyFormat.format(currentUser.getBalance()));
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        balanceLabel.setForeground(Color.WHITE);

        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(balanceLabel, BorderLayout.EAST);
        return headerPanel;
    }
    private JPanel createTransactionTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel tableTitle = new JLabel("Transaction History");
        tableTitle.setFont(new Font("Arial", Font.BOLD, 20));
        tablePanel.add(tableTitle, BorderLayout.NORTH);

        // Initialize the transaction table
        transactionTable = new JTable();
        updateTransactionTable();

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // "Transfer Money" Button
        JButton transferButton = new JButton("Transfer Money");
        transferButton.setPreferredSize(new Dimension(150, 40));
        transferButton.setBackground(new Color(76, 175, 80)); // Green
        transferButton.setForeground(Color.WHITE);
        transferButton.setFont(new Font("Arial", Font.BOLD, 14));
        transferButton.addActionListener(new TransferButtonListener());

        // "Logout" Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(100, 40));
        logoutButton.setBackground(new Color(233, 66, 66)); // Red
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.addActionListener(e -> logout());

        footerPanel.add(transferButton);
        footerPanel.add(logoutButton);

        return footerPanel;
    }



    private void updateTransactionTable() {
        String[] columns = {"TransactionType","Recipient","Date", "Description", "Amount"};
        Object[][] rowData = new Object[transactions.size()][columns.length];
        for (int i = 0; i < transactions.size(); i++) {
            UserTransaction transaction = transactions.get(i);
            rowData[i][0] = transaction.getTransactionType().toString(); // Transaction Type
            rowData[i][1] = transaction.getReceiverName(); // Receiver Name
            rowData[i][2] = transaction.getDate();          // Date
            rowData[i][3] = transaction.getDescription();   // Description
            rowData[i][4] = transaction.getAmount();        // Amount
        }
        DefaultTableModel model = new DefaultTableModel(rowData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transactionTable.setModel(model);
    }


    private void logout() {
        dispose(); // Close the dashboard window
        new LoginPage(mySQLManager).setVisible(true);
    }

    //Transfer Money button clicks
    private class TransferButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String recipient = JOptionPane.showInputDialog("Enter recipient username:");
            String amountStr = JOptionPane.showInputDialog("Enter amount to transfer:");
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(null, "Invalid amount. Amount must be greater than zero.");
                    return;
                }
                if (amount > currentUser.getBalance()) {
                    JOptionPane.showMessageDialog(null, "Insufficient balance. You only have " + Main.currencyFormat.format(currentUser.getBalance()) + ".");
                    return;
                }
                if(!mySQLManager.getUserTable().isValidUser(recipient)){
                    JOptionPane.showMessageDialog(null, "This recipient does not exist !");
                    return;
                }
                mySQLManager.getUserTable().addBalance(recipient,amount);
                UserTransaction transaction = new UserTransaction(
                        TransactionType.WITHDRAW.toString(),
                        currentUser.getId(),
                        recipient,
                        "Transfer to " + recipient,
                        amount,
                        LocalDateTime.now()
                );
                mySQLManager.getUserTransactionsTable().insertTransaction(transaction);
                currentUser.getTransactions().add(transaction);
                updateTransactionTable();
                JOptionPane.showMessageDialog(null, "Transfer successful!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a valid number.");
            }
        }
    }
}

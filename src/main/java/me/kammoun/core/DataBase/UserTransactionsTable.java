package me.kammoun.core.DataBase;

import me.kammoun.core.utils.Holder.UserTransaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserTransactionsTable {
    private final MySQLManager mySQLManager;

    public UserTransactionsTable(MySQLManager mySQLManager) {
        this.mySQLManager = mySQLManager;
        CreateTransactionTable();
    }

    private void CreateTransactionTable(){
        String query = "CREATE TABLE IF NOT EXISTS transactions (" +
                "    id INT AUTO_INCREMENT PRIMARY KEY," +
                "    TransactionType VARCHAR(10),"+
                "    user_id INT NOT NULL," +
                "    ReceiverName VARCHAR(255) NOT NULL,"+
                "    description VARCHAR(255)," +
                "    amount DOUBLE NOT NULL," +
                "    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" +
                ");";
        try{
            mySQLManager.executeUpdate(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<UserTransaction> getTransactionsForUser(int userId) {
        List<UserTransaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE user_id = ? ORDER BY date DESC";
        try (Connection conn = mySQLManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UserTransaction transaction = new UserTransaction(
                        rs.getInt("id"),
                        rs.getString("TransactionType"),
                        rs.getInt("user_id"),
                        rs.getString("ReceiverName"),
                        rs.getString("description"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("date").toLocalDateTime()
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public boolean insertTransaction(UserTransaction transaction) {
        String query = "INSERT INTO transactions (user_id,TransactionType, ReceiverName, description, amount, date) VALUES (?,?, ?, ?, ?, ?)";
        try (Connection conn = mySQLManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, transaction.getUserId());
            stmt.setString(2, transaction.getTransactionType().toString());
            stmt.setString(3, transaction.getReceiverName());
            stmt.setString(4, transaction.getDescription());
            stmt.setDouble(5, transaction.getAmount());
            stmt.setTimestamp(6, Timestamp.valueOf(transaction.getDate()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserTransaction getTransactionById(int transactionId) {
        String query = "SELECT * FROM transactions WHERE id = ?";
        try (Connection conn = mySQLManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, transactionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserTransaction(
                        rs.getInt("id"),
                        rs.getString("TransactionType"),
                        rs.getInt("user_id"),
                        rs.getString("ReceiverName"),
                        rs.getString("description"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("date").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}

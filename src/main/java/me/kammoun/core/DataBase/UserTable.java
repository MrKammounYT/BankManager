package me.kammoun.core.DataBase;

import me.kammoun.core.Enums.Roles;
import me.kammoun.core.utils.Holder.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class UserTable {


    private final MySQLManager mySQLManager;

    public UserTable(MySQLManager mySQLManager){
        this.mySQLManager = mySQLManager;
        CreateUserTable();
        addUser("kammoun",Roles.ADMIN,"admin",1000);
    }

    protected void CreateUserTable(){
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Users (id INT AUTO_INCREMENT PRIMARY KEY,UserName VARCHAR(255)" +
                    ",UserRole VARCHAR(100),creationDate date,password varchar(255),balance int NOT NULL)";
        try {
            mySQLManager.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addUser(String userName, Roles roles,String password,int balance) {
        if(!isUsernameAvailable(userName))return;
        // Encrypt password before storing it in the database.
        String insertUserQuery = "INSERT INTO Users (UserName, UserRole, creationDate,password,balance) VALUES (?,?,CURDATE(),?,?)";
        try (PreparedStatement preparedStatement = mySQLManager.getConnection().prepareStatement(insertUserQuery)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, roles.toString());
            preparedStatement.setString(3, hashPassword(password));
            preparedStatement.setInt(4,balance);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String hashPassword(String plainPassword) {
        try {
            //hash Code I got from the internet :=)
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(plainPassword.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public ArrayList<User> getAccountList() {
        ArrayList<User> userList = new ArrayList<>();
        try (Connection connection = mySQLManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String userName = resultSet.getString("UserName");
                String userRole = resultSet.getString("UserRole");
                Date creationDate = resultSet.getDate("creationDate");
                String password = resultSet.getString("password");
                int balance = resultSet.getInt("balance");
                User user = new User(id, userName, password, balance, creationDate,Roles.valueOf(userRole.toUpperCase()));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user data", e);
        }

        return userList;
    }
    public boolean isUsernameAvailable(String userName) {
        String query = "SELECT COUNT(*) FROM Users WHERE UserName = ?";

        try (PreparedStatement preparedStatement = mySQLManager.getConnection().prepareStatement(query)) {

            preparedStatement.setString(1, userName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
    public User getLoginUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE UserName = ?";  // prevent SQL injection
        try (PreparedStatement stmt = mySQLManager.getConnection().prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String user = rs.getString("UserName");
                    String password = rs.getString("password");
                    Roles userRole = Roles.valueOf(rs.getString("UserRole").toUpperCase());

                    return new User(id,user, password,userRole);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // user is not found or an error occurs
    }

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE UserName = ?";  // prevent SQL injection
        try (PreparedStatement stmt = mySQLManager.getConnection().prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String user = rs.getString("UserName");
                    String password = rs.getString("password");
                    int balance = rs.getInt("balance");
                    Date creationDate = rs.getDate("creationDate");
                    Roles userRole = Roles.valueOf(rs.getString("UserRole").toUpperCase());
                    return new User(id, user, password, balance, creationDate, userRole);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getBalance(String username){
        int balance = 0;
        String query = "SELECT balance FROM users WHERE UserName =?";
        try (PreparedStatement stmt = mySQLManager.getConnection().prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    balance = rs.getInt("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public void addBalance(String recipient, double amount) {
        String query = "UPDATE users SET balance = balance + ? WHERE UserName = ?";
        try (PreparedStatement stmt = mySQLManager.getConnection().prepareStatement(query)) {
            stmt.setDouble(1, amount);
            stmt.setString(2, recipient);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private boolean userExists(String username) {
        String query = "SELECT 1 FROM users WHERE UserName = ?";
        try (PreparedStatement stmt = mySQLManager.getConnection().prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isValidUser(String username) {
        if(!userExists(username))return false;
        String query = "SELECT UserRole FROM users WHERE UserName = ?";
        try (PreparedStatement stmt = mySQLManager.getConnection().prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Roles role = Roles.valueOf(rs.getString("UserRole"));
                    return role == Roles.USER;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

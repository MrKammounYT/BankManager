package me.kammoun.core.DataBase;

import me.kammoun.core.Enums.Roles;

import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserTable {


    private final MySQLManager mySQLManager;

    public UserTable(MySQLManager mySQLManager){
        this.mySQLManager = mySQLManager;
        CreateUserTable();
        addUser("kammoun",Roles.ADMIN,"admin");
    }

    protected void CreateUserTable(){
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Users (id INT AUTO_INCREMENT PRIMARY KEY,UserName VARCHAR(255)" +
                    ",UserRole VARCHAR(100),creationDate date,password varchar(255))";
        try {
            mySQLManager.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addUser(String userName, Roles roles,String password) {
        if(!isUsernameAvailable(userName))return;
        // Encrypt password before storing it in the database.
        String insertUserQuery = "INSERT INTO Users (UserName, UserRole, creationDate,password) VALUES (?,?,CURDATE(),?)";
        try (PreparedStatement preparedStatement = mySQLManager.getConnection().prepareStatement(insertUserQuery)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, roles.toString());
            preparedStatement.setString(3, hashPassword(password));
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
    public Object[][] getAccountList() {
        ArrayList<Object[]> dataList = new ArrayList<>();

        try (Connection connection = mySQLManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                dataList.add(row);
            }//result set get the password before the account creation date
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataList.toArray(new Object[0][0]);
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

}

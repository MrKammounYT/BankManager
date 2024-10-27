package me.kammoun.core.DataBase;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLManager {

    private HikariDataSource dataSource;
    private final String url = "jdbc:mysql://localhost:3306/kammoun";
    private final String user = "root";
    private final String password = "";

    private UserTable userTable;

    public MySQLManager() {
        setupDataSource();
        if (isConnected()) {
            userTable = new UserTable(this);
            System.out.println("Database connected and UserTable initialized.");
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }

    private void setupDataSource() {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(password);
            config.setMaximumPoolSize(10);  // Adjust as needed
            config.setConnectionTimeout(30000);  // 30 seconds
            config.setIdleTimeout(60000);  // 1 minute

            dataSource = new HikariDataSource(config);
            System.out.println("HikariCP DataSource initialized.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error initializing HikariCP DataSource.");
        }
    }

    public Connection getConnection() throws SQLException {
        if (dataSource != null) {
            return dataSource.getConnection();
        }
        throw new SQLException("DataSource is not initialized.");
    }

    public void executeUpdate(String query) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();  // Don't close here; call `closeResultSet` later.
    }

    public void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.getStatement().getConnection().close();  // Close the connection along with the result set.
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        if (dataSource != null) {
            dataSource.close();
            System.out.println("Disconnected from the database.");
        }
    }

    public boolean isConnected() {
        try (Connection connection = getConnection()) {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public UserTable getUserTable() {
        return userTable;
    }
}

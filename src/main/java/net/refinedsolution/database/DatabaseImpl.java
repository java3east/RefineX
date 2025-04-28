package net.refinedsolution.database;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class allows to connect to a database and that way perform MySQL related tests.
 * @author Java3east
 */
public class DatabaseImpl implements Database {
    private static int nextId = 0;
    private final String databaseName = "refx_" + nextId++;
    private Connection connection = null;

    public DatabaseImpl() {
        Connection con = connect();
        try {
            con.prepareCall("CREATE DATABASE IF NOT EXISTS " + this.databaseName).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull String getDatabaseName() {
        return this.databaseName;
    }

    @Override
    public @NotNull String getConnectionUrl() {
        return "jdbc:mysql://localhost:3306/";
    }

    @Override
    public @NotNull Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = java.sql.DriverManager.getConnection(getConnectionUrl(), "refinex", "refinex");
            return this.connection;
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to the database with username 'refinex' and password 'refinex': " + e, e);
        }
    }

    @Override
    public void closeConnection() {
        if (this.connection == null) return;
        try {
            this.connection.close();
        } catch (SQLException ignored) { }
        this.connection = null;
    }

    @Override
    public boolean testConnection() {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                connect();
            }
            return this.connection != null && !this.connection.isClosed();
        } catch (Exception e) {
            System.out.println("Failed to connect to the database with username 'refinex': " + e.getMessage());
            return false;
        }
    }

    @Override
    public void execute(String sql, Object[] args) {
        Connection con = connect();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

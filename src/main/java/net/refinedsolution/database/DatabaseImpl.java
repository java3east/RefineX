package net.refinedsolution.database;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

/**
 * This class allows to connect to a database and that way perform MySQL related tests.
 * @author Java3east
 */
public class DatabaseImpl implements Database {
    private static int nextId = 0;
    private final String databaseName = "refx_" + nextId++;

    @Override
    public @NotNull String getDatabaseName() {
        return this.databaseName;
    }

    @Override
    public @NotNull String getConnectionUrl() {
        return "jdbc:mysql://localhost:3306/" + this.databaseName;
    }

    @Override
    public @NotNull Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return java.sql.DriverManager.getConnection(getConnectionUrl());
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }
}

package net.refinedsolution.database;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

/**
 * This interface allows to connect to a database and that way perform MySQL related tests.
 * @author Java3east
 */
public interface Database {
    /**
     * Returns the name of the database that should be used
     * @return the name of the database
     */
    @NotNull String getDatabaseName();

    /**
     * Returns the string that can be used to connect to the database
     * @return the connection string
     */
    @NotNull String getConnectionUrl();

    /**
     * Connects to the database and returns the connection
     * @return the connection to the database
     */
    @NotNull Connection connect();

    /**
     * Closes the current established connection to the database.
     * This will do nothing if there is no open connection.
     */
    void closeConnection();

    /**
     * Attempts to connect to the database.
     * @return true if the connection was successful, false otherwise
     */
    boolean testConnection();

    /**
     * Runs the given SQL statement with the given arguments.
     * This is a simple way to execute SQL statements without returning any results.
     * @param sql the sql statement to execute
     * @param args the arguments to pass to the statement
     */
    void execute(String sql, Object[] args);
}

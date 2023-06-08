package hw8;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * The ConnectionManager class manages JDBC connections to a database.
 * 
 * This class provides methods for opening and closing database connections, 
 * as well as retrieving a Connection object for use in executing database queries 
 * and updates. It also provides functionality for handling exceptions that may occur 
 * during connection management.
 * 
 * Usage: 
 * To use this class, first create an instance of the ConnectionManager, passing in 
 * the necessary parameters for connecting to the database. Then, call the getConnection() 
 * method to retrieve a Connection object, and use it to execute your database queries 
 * and updates. When finished, call the close() method to release the connection resources.
 * 
 * Example usage:
 * ConnectionManager connectionManager = new ConnectionManager("jdbc:mysql://localhost/mydatabase", "root", "password");
 * try {
 *     Connection connection = connectionManager.getConnection();
 *     // execute database queries and updates using the connection object
 * } catch (SQLException ex) {
 *     // handle any exceptions that may occur during connection management
 * } finally {
 *     connectionManager.close();
 * }
 * 
 * @author Evangeli Silva (esilva2@albany.edu)
 */
public class ConnectionManager {
	
	/*
	 * The JDBC URL for the database connection
	 */
	private String url;
	
	/*
	 * The username for the database connection
	 */
    private String username;
    
    /*
     * The password for the database connection
     */
    private String password;
    
    /*
     * The Connection object for the database connection
     */
    private Connection connection;
    
    /**
     * Creates a new instance of the ConnectionManager class.
     * 
     * @param url
     * 		The JDBC URL for the database connection
     * @param username 
     * 		The username for the database connection
     * @param password 
     * 		The password for the database connection
     * @throws SQLException 
     */
    public ConnectionManager(String url, String username, String password) throws SQLException {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    /**
     * Retrieves a Connection object for use in executing database queries 
     * and updates.
     * 
     * @return A Connection object for the database connection
     * @throws SQLException 
     * 		If a database access error occurs
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
        	connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }
    
    /**
     * returns the DatabaseMetaData object associated with 
     * the database connection
     * 
     * @return The DatabaseMetaData object associated with the database connection
     * @throws SQLException 
     * 		If a database access error occurs
     */
    public DatabaseMetaData getDatabaseMetaData() throws SQLException {
        return getConnection().getMetaData();
    }
    
    /**
     * Executes a SQL query and returns a ResultSet object containing the results.
     * 
     * @param query 
     * 		The SQL query to execute
     * @return A ResultSet object containing the results of the query
     * @throws SQLException 
     * 		If a database access error occurs
     */
    public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }
    
    /**
     * Closes the database connection and releases any resources associated with it.
     * 
     * @throws SQLException 
     * 		If a database access error occurs
     */
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
        	connection.close();
        }
    }
}

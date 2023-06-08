package hw8;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;


/**
 * The MetaData class represents the metadata information for a database connection, 
 * including the DatabaseMetaData object and the Connection object. 
 * 
 * It provides methods to retrieve information such as column names, primary keys, 
 * tuple count, and distinct count for a given relation in the database.
 * 
 * @author Evangeli Silva (esilva2@albany.edu)
 */
public class MetaData {
	
    /*
     * The Connection object for the database connection
     */
    private Connection connection;
    
    /*
     * The DatabaseMetaData object associated with the database connection
     */
    private DatabaseMetaData dbmd;
  
	/**
	 * Constructs a new instance of the MetaData class with the given ConnectionManager object.
	 * @param connectionManager
	 * 		The ConnectionManager object to use for database connectivity 
	 * @throws SQLException
	 */
	public MetaData(ConnectionManager connectionManager) throws SQLException {
		this.connection = connectionManager.getConnection();
		this.dbmd = connectionManager.getConnection().getMetaData();
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
	 * Returns an ArrayList of table names in the connected database
	 * 
	 * @return ArrayList of table names
	 * @throws SQLException 
	 * 	        If there is an error accessing the database metadata
	 */
	public ArrayList<String> getTables() throws SQLException {
		String[] types = {"TABLE"};
	    ArrayList<String> tables = new ArrayList<>(); 
	    try {
			ResultSet rs = dbmd.getTables(null, null, "%", types);
			while (rs.next())
				tables.add(rs.getString("TABLE_NAME"));
		} catch (SQLException e) {
			throw new SQLException("Error accessing the database metadata", e);
		}
		return tables;
	}
	
	/**
	 * Retrieves the column names for the specified relation
	 * 
	 * @param relation
	 * 		The name of the relation for which to retrieve the column names (must not be null)
	 * @return An ArrayList containing the names of the columns in the specified relation
	 * @throws SQLException 
	 * 		If a database access error occurs
	 * @throws IllegalArgumentException 
	 * 		If dbmd or relation is null
	 */
	public ArrayList<String> getColumns(String relation) throws SQLException {
	    Objects.requireNonNull(relation, "Relation name cannot be null");
	    
	    ArrayList<String> columns = new ArrayList<>(); 
	    try {
			ResultSet rs = dbmd.getColumns(null, null, relation, null);
			while (rs.next())
				columns.add(rs.getString("COLUMN_NAME"));
		} catch (SQLException e) {
			throw new SQLException("Error accessing the database metadata for relation: " + relation, e);
		}
		return columns;
	}
	
	/**
	 * Retrieves the primary keys for the specified relation
	 * 
	 * @param relation
	 * 		The name of the relation for which to retrieve the column names (must not be null)
	 * @throws SQLException 
	 * 		If a database access error occurs
	 * @throws IllegalArgumentException 
	 * 		If dbmd or relation is null
	 */
	public ArrayList<String> getPrimaryKeys(String relation) throws SQLException {
	    Objects.requireNonNull(relation, "Relation name cannot be null");
		
		ArrayList<String> primaryKeys = new ArrayList<String>(); 
		try {
			ResultSet rs = dbmd.getPrimaryKeys(null, null, relation);
			while (rs.next())
				primaryKeys.add(rs.getString("COLUMN_NAME"));
		} catch (SQLException e) {
			throw new SQLException("Error accessing the database metadata for relation: " + relation, e);
		}
		return primaryKeys;	
	}
	
	/**
	 * Returns the tuple count of the given relation
	 * 
	 * @param relation
	 * 		The name of the relation to query
	 * @return The tuple count of the specified relation
	 * @throws SQLException
	 * 		If query execution fails
	 */
	public int getTupleCount(String relation) throws SQLException {
		Objects.requireNonNull(relation, "Relation name cannot be null");
		
		String query = "SELECT COUNT(*) FROM " + relation + ";";
		int tupleCount = 0;
		
		try {
			ResultSet rs = executeQuery(query);
			while (rs.next()) {
				tupleCount = rs.getInt("count");
			}
		} catch (SQLException e) {
			throw new SQLException("Error executing query: " + query + ". " + e);
		}
		return tupleCount;
	}
	
	/**
	 * Returns the count of distinct values in the specified columns of the given relation
	 * 
	 * @param columns
	 * 		A comma-separated list of column names
	 * @param relation
	 * 		The name of the relation to query
	 * @return The count of distinct values in the specified columns
	 * @throws SQLException
	 * 		If query execution fails
	 */
	public int getDistinctCount(String columns, String relation) throws SQLException {
		Objects.requireNonNull(relation, "Relation name cannot be null");
		
		String query = "SELECT COUNT(DISTINCT " + columns + ") FROM " +  relation + ";";
		int distictCount = 0;
		
		try {
			ResultSet rs = executeQuery(query);
			while (rs.next())
				distictCount = rs.getInt("count");
		} catch (SQLException e) {
			throw new SQLException("Error executing query: " + query + ". " + e);
		}
		return distictCount;
	}
}

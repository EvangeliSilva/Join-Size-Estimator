package hw8;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * An application that takes two table names of the university dataset
 * as command-line arguments and calculates and prints the estimated join size,
 * actual join size and the estimation error.
 * 
 * Run-time arguments:
 *	url: The JDBC URL for the database connection 
 *	username: The username for the database connection
 *	password: The password for the database connection
 * 	firstReation: The First relation for the natural join
 * 	secondRelation: The Second relation for the natural join
 * 
 * @author Evangeli Silva (esilva2@albany.edu)
 *
 */
public class Application {
	
	/*
	 * A static instance of the ConnectionManager class to manage connections to the database
	 */
	public static ConnectionManager connectionManager; 
	
	/*
	 * A static instance of the SizeEstimator class
	 */
	public static SizeEstimator estimator;
	
	/*
	 * A static instance of the MetaData class
	 */
	public static MetaData metadata; 
	
	public static void main(String[] args) throws Exception {
		
		// Checks if all the required input arguments are provided
		if (args.length != 5) {
			String errorMessage = "Missing required argument(s). "
		            + "Please provide the following arguments: "
		            + "<database connection url, database connection username, database connection password, "
		            + "first relation name, second relation name>.";
			throw new MissingArgumentException(errorMessage);
		} 
		
		String url = args[0]; // The JDBC URL for the database connection 
		String username = args[1]; // The username for the database connection
		String password = args[2]; // The password for the database connection
		
		try {
			connectionManager = new ConnectionManager(url, username, password);
			metadata = new MetaData(connectionManager);
			
			// Checks if the given relations exist in the database
			ArrayList<String> tables = metadata.getTables();
			
			String firstRelation = args[3]; // The First relation for the natural join
			if (!tables.contains(firstRelation)) {
				throw new IllegalArgumentException("Relation '" + firstRelation + "' does not exist");
			}

			String secondRelation = args[4]; // The Second relation for the natural join
			if (!tables.contains(secondRelation)) {
				throw new IllegalArgumentException("Relation '" + secondRelation + "' does not exist");
			}
			
			estimator = new SizeEstimator(connectionManager);
			
			// Estimates the join size
			int estimatedJoinSize = estimator.getEstimatedJoinSize(firstRelation, secondRelation);
			System.out.println("Estimated Join Size: " + estimatedJoinSize);
			
			// Retrieves the actual join size
			int actualJoinSize = estimator.getActualJoinSize(firstRelation, secondRelation);
			System.out.println("Actual Join Size: " + actualJoinSize);
			
			// Calculates the estimation error (Estimated join size - Actual join size)
			System.out.println("Estimation Error: " + (estimatedJoinSize - actualJoinSize));
			
		} catch (SQLException e) {
			throw new SQLException("Error establishing the connection: ", e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new SQLException(e.getMessage());
		} catch (Exception e) {
			System.err.println("An error occured while calculating the join size: " + e.getMessage());
		}	
	}
}

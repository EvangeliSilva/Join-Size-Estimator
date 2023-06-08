package hw8;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The SizeEstimator class provides methods to estimate and retrieve the size of 
 * join operations between two relations in a database. The class uses metadata 
 * from the database to estimate the size of the join operations and also provides 
 * a method to retrieve the actual size of a join operation by executing a 
 * SQL query to count the number of tuples in the natural join of two relations.
 * 
 * @author Evangeli Silva (esilva2@albany.edu)
 *
 */
public class SizeEstimator {
	
	/*
     * The metadata associated with the ConnectionManager object
     */
	private MetaData metadata;
	
	/**
	 * Constructs a new instance of the SizeEstimator class with the given ConnectionManager object.
	 * @param connectionManager
	 * 			The ConnectionManager object to use for database connectivity 
	 * @throws SQLException
	 */
	public SizeEstimator(ConnectionManager connectionManager) throws SQLException {
		this.metadata = new MetaData(connectionManager);
	}
	
	/**
	 * Returns the size of the join of the given relation with another relation
	 * @param relation 
	 * 			The name of the relation for which to estimate the size of join
	 * @param intersection
	 * 			The list of attributes that the given relation shares with another relation
	 * @param firstRelationSize
	 * 			The size of the first relation in the join
	 * @param secondRelationSize
	 * 			The size of the second relation in the join
	 * @return The size of the join of the given relation with another relation
	 * @throws SQLException
	 * 			If there is an error retrieving metadata from the database
	 */
	public int getJoinTupleCount(String relation, ArrayList<String> intersection, 
			int firstRelationSize, int secondRelationSize) throws SQLException {
		int distinctCount = metadata.getDistinctCount(Utils.toString(intersection), relation);
		return (firstRelationSize * secondRelationSize)/distinctCount;
	}
	
	/**
	 * Calculates the estimated size of a natural join operation between two relations
	 * 
	 * @param firstRelation
	 * 			The first relation to join
	 * @param secondRelation
	 * 			The second relation to join
	 * @return An integer representing the estimated size of the natural join operation
	 * @throws SQLException
	 * 			If an SQL exception occurs while querying the metadata of the relations
	 */
	public int getEstimatedJoinSize(String firstRelation, String secondRelation) throws SQLException {
		int estimatedSize = 0;

		ArrayList<String> intersection = Utils.getIntersection(
				metadata.getColumns(firstRelation), metadata.getColumns(secondRelation));
		
		int firstRelationSize = metadata.getTupleCount(firstRelation);
		int secondRelationSize = metadata.getTupleCount(secondRelation);
		 
		if (intersection.isEmpty()) {
			estimatedSize = firstRelationSize * secondRelationSize;
		} else {
			if (intersection == metadata.getPrimaryKeys(firstRelation)) {
				estimatedSize = secondRelationSize;
			} else if (intersection == metadata.getPrimaryKeys(secondRelation)) {
				estimatedSize = firstRelationSize;
			} else {
				estimatedSize = Math.min(
						getJoinTupleCount(firstRelation, intersection, firstRelationSize, secondRelationSize),
						getJoinTupleCount(secondRelation, intersection, firstRelationSize, secondRelationSize));
			}
		}
		return estimatedSize;
	}
	
	/**
	 * Returns the actual size of the join between two relations by executing a SQL query 
	 * to count the number of tuples in the natural join of the given two relations.
	 * @param firstRelation
	 * 			The first relation to join
	 * @param secondRelation
	 * 			The second relation to join
	 * @return An integer representing the actual size of the natural join operation
	 * @throws SQLException 
	 * 			if there is an error executing the SQL query
	 */
	public int getActualJoinSize(String firstRelation, String secondRelation) throws SQLException {
		int actualSize = 0;
		String query = "SELECT COUNT(*) FROM " + firstRelation + " NATURAL JOIN " + secondRelation + ";";
		
		try {
			ResultSet rs = metadata.executeQuery(query);
			while (rs.next())
				actualSize = rs.getInt("count");
		} catch (SQLException e) {
			throw new SQLException("Error executing query: " + query + ". " + e);
		}
		return actualSize;
	}
}

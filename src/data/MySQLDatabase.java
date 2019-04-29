package data;

import java.sql.*;
import java.util.*;

/**
 * @author Xin Liu
 *
 * Data layer for connecting to the database
 *
 * Contains methods for MySQL
 */
public class MySQLDatabase {
	private static final String URI = "jdbc:mysql://localhost/faculty_research?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String USER = "root";
	private static final String PASSWORD = "password";

	private static final String DEFAULT_ERROR = "Failed to complete this operation at this time!";

	public Connection conn = null; // DB connection object

	/**
	 * Attempts to connect to a database
	 * @return Boolean true/false
	 */
	public boolean connect() throws DLException {
		try {
			Class.forName( DRIVER );
			conn = DriverManager.getConnection( URI, USER, PASSWORD );
		}
		catch(ClassNotFoundException cnfe) {
			throw new DLException( cnfe, DEFAULT_ERROR, "Driver: " + DRIVER + " not found" );
		}
		catch(SQLException sqle) {
			throw new DLException( sqle, DEFAULT_ERROR, "MySQL authentication failed, Could not connect", "Attempted username: " + USER );
		}

		// Check if connection has been established and return Boolean
		if(conn != null) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Attempts to close the connection to a database
	 * @return Boolean true/false
	 */
	public boolean close() throws DLException {
		try {
			// Check if connection is still opened before closing
			if(!conn.isClosed()) {
				conn.close();
				return true;
			}
		}
		catch(NullPointerException npe) {
			throw new DLException( npe, DEFAULT_ERROR, "MySQL Connection is not established");
		}
		catch(SQLException sqle) {
			throw new DLException( sqle, DEFAULT_ERROR, "Could not close MySQL" );
		}

		return false;
	}

	/**
	 * Uses the 'select' sql query to retrieve data from a database and store it in a 2D ArrayList
	 * @param query SQL string
	 * @return 2D array containing results from MySQL query
	 */
	public ArrayList<ArrayList<String>> getData(String query) throws DLException {
		// Create an ArrayList to store ALL results retrieved
		ArrayList<ArrayList<String>> fullResultList = new ArrayList<>();

		int numFields = 0; // Holds the number of column counts

		try {
			// Create Statement object
			Statement stmt = conn.createStatement();

			// Execute the query, and retrieve the results as ResultSet object
			ResultSet res = stmt.executeQuery( query );

			// Get the metadata for res
			ResultSetMetaData rsmd = res.getMetaData();

			// Get the column count from the metadata
			numFields = rsmd.getColumnCount();

			// Iterate through the res for the values retrieved and add it to queryResultList
			while(res.next()) {
				// Create an ArrayList to store query results
				ArrayList<String> queryResultList = new ArrayList<>();

				for(int i=1; i<=numFields; i++) { // Starts at 1, not 0
					queryResultList.add( res.getString(i) );
				}

				fullResultList.add( queryResultList );
			}
		}
		catch(NullPointerException npe) {
			throw new DLException( npe, DEFAULT_ERROR, "Could not create Statement object", "Attempted query: " + query, "numFields: " + numFields );
		}
		catch(SQLException sqle) {
			throw new DLException( sqle, DEFAULT_ERROR, "Could not execute query", "Attempted query: " + query, "numFields: " + numFields );
		}

		return fullResultList;
	}

	/**
	 * Uses the 'select' sql query to retrieve data from a database and store it in a 2D ArrayList
	 *
	 * Depending on the boolean value passed into this method, if true, the first row of the 2D array will contain the column names, and the second row(s) will contain the data
	 *
	 * @param query SQL string
	 * @param hasColumnNames true or false to determine whether or not the 2D array will contain the column names
	 * @return 2D array containing results from MySQL query
	 */
	public ArrayList<ArrayList<String>> getData(String query, boolean hasColumnNames) throws DLException {
		// Check the boolean value
		if(hasColumnNames) {
			int numFields = 0; // Holds the number of column counts

			// Call the default getData method and store the result ArrayList
			ArrayList<ArrayList<String>> fullResultList = getData(query); // contains all data in table

			// Get the column names from table
			try {
				// Create Statement Object
				Statement stmt = conn.createStatement();

				// Execute the query, and retrieve the results as ResultSet object
				ResultSet res = stmt.executeQuery( query );

				// Get the metadata for res
				ResultSetMetaData rsmd = res.getMetaData();

				// Get the column count from the metadata
				numFields = rsmd.getColumnCount();

				// Get the column names for the data returned and add it to columnNamesList
				ArrayList<String> columnNameList = new ArrayList<>();

				for(int i=1; i<=numFields; i++) { // Starts at 1, not 0
					columnNameList.add( rsmd.getColumnName(i) );
				}

				// Add to front of fullResultList
				fullResultList.add( 0, columnNameList );
			}
			catch(NullPointerException npe) {
				throw new DLException( npe, DEFAULT_ERROR, "Could not create Statement object", "Attempted query: " + query, "numFields: " + numFields);
			}
			catch(SQLException sqle) {
				throw new DLException( sqle, DEFAULT_ERROR, "Could not execute query", "Attempted query: " + query, "numFields: " + numFields );
			}

			return fullResultList;

		}
		// If the boolean is false, simply call the getData() method without the boolean argument
		else {
			return getData(query);
		}
	}

	/**
	 * FOR PREPARED STATEMENTS
	 *
	 * This method calls the prepare() method, binds the values, executes the statement, and returns a 2D ArrayList with the first row being the column names, followed the data
	 *
	 * @param query SQL string
	 * @param values List of values to be prepared into SQL string
	 * @return 2D list of column names and result values
	 */
	public ArrayList<ArrayList<String>> getData(String query, ArrayList<String> values) throws DLException {
		// Get the PrepareStatement object
		PreparedStatement stmt = prepare( query, values );

		// Create an ArrayList that will hold other ArrayLists
		ArrayList<ArrayList<String>> fullResultList = new ArrayList<>();

		int numFields = 0; // Holds the number of columns

		try {
			// Bind values
			for(int i=0; i<values.size(); i++) { // setString() starts at 1
				stmt.setString( i+1, values.get(i) );
			}

			// Use execute() to test if the PreparedStatement works
			if(!stmt.execute()) {
				return null;
			}
			else {
				// Get results
				ResultSet res = stmt.executeQuery();

				// Get the metadata
				ResultSetMetaData rsmd = res.getMetaData();

				// Set numFields
				numFields = rsmd.getColumnCount();

				// Create an ArrayList containing the column names of the results
				ArrayList<String> columnNameList = new ArrayList<>();

				for(int i=1; i<=numFields; i++) { // Starts at 1
					columnNameList.add( rsmd.getColumnName(i) );
				}

				fullResultList.add( columnNameList );

				// Iterate through the result and add to fullResultList
				while(res.next()) {
					// Create an ArrayList to store results data
					ArrayList<String> queryResultList = new ArrayList<>();

					for(int i=1; i<=numFields; i++) {
						queryResultList.add( res.getString(i) );
					}

					fullResultList.add( queryResultList );
				}
			}
		}
		catch(SQLException sqle) {
			throw new DLException( sqle, DEFAULT_ERROR, "Could not execute PreparedStatement query", "Attempted query: " + query, "numFields: " + numFields);
		}

		return fullResultList;
	}

	/**
	 * Uses 'update', 'delete, or 'insert' queries to manipulate data in database
	 * @params The query string
	 * @return Number of records affected
	 */
	public int setData(String query) throws DLException {
		int resCount = 0; // Holds rows modified

		try {
			// Create statement object
			Statement stmt = conn.createStatement();

			// Execute update
			resCount = stmt.executeUpdate( query );
		}
		catch(NullPointerException npe) {
			throw new DLException( npe, DEFAULT_ERROR, "Could not create Statement object", "Attempted query: " + query );
		}
		catch(SQLException sqle) {
			throw new DLException( sqle, DEFAULT_ERROR, "Could not execute query", "Attempted query: " + query );
		}

		return resCount;
	}

	/**
	 * FOR PREPARED STATEMENTS
	 *
	 * This method calls the executeStmt() method. Uses 'update', 'delete', or 'insert' queries to manipulate data in database
	 * @param query SQL string
	 * @param values List of values
	 * @return Number of records affected
	 */
	public int setData(String query, ArrayList<String> values) throws DLException {
		return executeStmt( query, values );
	}

	/**
	 * FOR PREPARED STATEMENTS
	 *
	 * Accepts a SQL query and a list of string values, prepares it and returns a prepared statement
	 * @param query SQL string
	 * @param values List of values
	 * @return PreparedStatement object
	 */
	public PreparedStatement prepare(String query, ArrayList<String> values) throws DLException {
		PreparedStatement stmt = null;

		try {
			// Create prepared statement
			stmt = conn.prepareStatement( query );
		}
		catch(NullPointerException npe) {
			throw new DLException( npe, DEFAULT_ERROR, "Could not create PreparedStatement object", "Attempted query: " + query );
		}
		catch(SQLException sqle) {
			throw new DLException( sqle, DEFAULT_ERROR, "Could not create PreparedStatement object", "Attempted query: " + query );
		}

		return stmt;
	}

	/**
	 * FOR PREPARED STATEMENTS
	 *
	 * This method calls the prepare() method, binds it and execute it. Used with setData() method
	 * @param query SQL string
	 * @param values List of values
	 * @return Count of rows modified
	 */
	public int executeStmt(String query, ArrayList<String> values) throws DLException {
		// Get PreparedStatement object
		PreparedStatement stmt = prepare( query, values );

		int resCount = 0; // Holds rows modified

		try {
			// Bind values
			for(int i=0; i<values.size(); i++) {
				stmt.setString( i+1, values.get(i) );
			}

			// Execute the query
			resCount = stmt.executeUpdate();
		}
		catch(SQLException sqle) {
			throw new DLException( sqle, DEFAULT_ERROR, "Could not execute PreparedStatement", "Attempted query: " + query);
		}

		return resCount;
	}

	/**
	 * FOR TRANSACTIONS
	 *
	 * Starts a database transaction
	 */
	public void startTrans() throws DLException {
		try {
			// Start transaction
			conn.setAutoCommit( false );
		}
		catch(NullPointerException npe) {
			throw new DLException( npe, DEFAULT_ERROR, "Connection object does not exist, could not start transaction");
		}
		catch(SQLException sqle) {
			throw new DLException( sqle, DEFAULT_ERROR, "Could not start transaction");
		}
	}

	/**
	 * FOR TRANSACTIONS
	 *
	 * Ends a database transaction
	 */
	public void endTrans() throws DLException {
		try {
			// End transaction
			conn.commit();
			conn.setAutoCommit( true );
		}
		catch(NullPointerException npe) {
			throw new DLException( npe, DEFAULT_ERROR, "Connection object does not exist, could not end transaction");
		}
		catch(SQLException sqle) {
			throw new DLException( sqle, DEFAULT_ERROR, "Could not end transaction");
		}
	}

	/**
	 * FOR TRANSACTIONS
	 *
	 * Rollbacks a database transaction
	 */
	public void rollbackTrans() throws DLException {
		try {
			// Rollback
			conn.rollback();
		}
		catch(NullPointerException npe) {
			throw new DLException( npe, DEFAULT_ERROR, "Connection object does not exist, could rollback transaction");
		}
		catch(SQLException sqle) {
			throw new DLException( sqle, DEFAULT_ERROR, "Could not rollback transaction");
		}
	}

 }
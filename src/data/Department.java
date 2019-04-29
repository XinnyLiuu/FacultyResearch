package data;

import java.util.ArrayList;

/**
 * Data layer of Department
 */
public class Department {
	// Fields
	private String id;
	private String name;
	private String officeRoomNumber;

	// MySQL
	MySQLDatabase mysql = new MySQLDatabase();

	// Constructor
	public Department(String id) {
		this.id = id;
	}

	public Department(String id, String name, String officeNum) {
		this.id = id;
		this.name = name;
		this.officeRoomNumber = officeNum;
	}

	/**
	 * Retrieve the values from the db using the department's id and update other attributes
	 *
	 * @throws DLException if any MySQLDatabase methods errors
	 * @return size of the data fetched
	 */
	public int get() throws DLException {
		// Connect to Mysql
		mysql.connect();

		// Add the equipment id to an ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getId());

		// Create query string
		String query = "select departmentname, officeroomnumber from department where departmentid = ?";

		// Retrieve data from db using .getData()
		ArrayList<ArrayList<String>> queryData = mysql.getData(query, values);

		// Iterate through queryData and set the object's attributes
		if (queryData.size() > 1) {
			// Get the second row as the first row will be the column names
			this.name = queryData.get(1).get(0);
			this.officeRoomNumber = queryData.get(1).get(1);
		}

		// Close mysql
		mysql.close();
		return queryData.size();
	}

	/**
	 * Update the department in the db using the department's id as a selector
	 *
	 * @throws DLException if any MySQLDatabase methods errors
	 * @return number of rows update
	 */
	public int put() throws DLException {
		//connect to MySQL
		mysql.connect();

		//set up query string
		String query = "update department set DepartmentName = ?, OfficeRoomNumber = ? where DepartmentID = ?;";

		//add values to the ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getName());
		values.add(this.getOfficeRoomNumber());
		values.add(this.getId());

		int modified = mysql.setData(query, values);

		// Close mysql
		mysql.close();
		return modified;
	}

	/**
	 * Insert the new department in the db using the department's id as a selector
	 *
	 * @throws DLException if any MySQLDatabase methods errors
	 * @return number of rows updated
	 */
	public int post() throws DLException {
		// Connect to MySQL
		mysql.connect();

		// Create query
		String query = "insert into department values(?, ?, ?);";

		// Add properties to values ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getId());
		values.add(this.getName());
		values.add(this.getOfficeRoomNumber());

		int modified = mysql.setData(query, values);

		// Close mysql
		mysql.close();
		return modified;
	}

	/**
	 * Delete the department in the db using the department's id as a selector
	 *
	 * @throws DLException if any MySQLDatabase methods errors
	 * @return number of rows changed
	 */
	public int delete() throws DLException {
		// Connect to Mysql
		mysql.connect();

		// Connect to Mysql
		String query = "delete from department where DepartmentID = ?;";

		// Add properties to values ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getId());

		int modified = mysql.setData(query, values);

		// Close mysql
		mysql.close();
		return modified;
	}

	// Getters
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getOfficeRoomNumber() {
		return officeRoomNumber;
	}
}

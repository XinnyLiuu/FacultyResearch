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

	/**
	 * Retrieve the values from the db using the department's id and update other attributes
	 *
	 * @returns size of the data fetched
	 */
	public int fetch() throws DLException {
		// Connect to Mysql
		mysql.connect();

		// Add the equipment id to an ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add( this.getId() );

		// Create query string
		String query = "select departmentname, officeroomnumber from department where departmentid = ?";

		// Retrieve data from db using .getData()
		ArrayList<ArrayList<String>> queryData = mysql.getData( query, values );

		// Iterate through queryData and set the object's attributes
		if(queryData.size() > 0) {
			// Get the second row as the first row will be the column names
			this.name = queryData.get(1).get(0);
			this.officeRoomNumber = queryData.get(1).get(1);
		}

		// Close mysql
		mysql.close();
		return queryData.size();
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

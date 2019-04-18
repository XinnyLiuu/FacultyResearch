package data;

import java.util.ArrayList;

/**
 * Data layer of Faculty
 */
public class Faculty {
	// Fields
	private String id;
	private String departmentid;
	private String fName;
	private String lName;
	private String email;
	private String interest;
	private String officeBuilding;
	private String officePhoneNumber;
	private String officeRoomNumber;

	// MySQL
	MySQLDatabase mysql = new MySQLDatabase();

	// Constructor
	public Faculty(String id) {
		this.id = id;
	}

	/**
	 * Retrieve the values from the db using the faculty's id and update other attributes
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
		String query = "select departmentid, firstname, lastname, email, interest, officebuilding, officephonenumber, officeroomnumber from faculty where facultyid = ?";

		// Retrieve data from db using .getData()
		ArrayList<ArrayList<String>> queryData = mysql.getData( query, values );

		// Iterate through queryData and set the object's attributes
		if(queryData.size() > 0) {
			// Get the second row as the first row will be the column names
			this.departmentid = queryData.get(1).get(0);
			this.fName = queryData.get(1).get(1);
			this.lName = queryData.get(1).get(2);
			this.email = queryData.get(1).get(3);
			this.interest = queryData.get(1).get(4);
			this.officeBuilding = queryData.get(1).get(5);
			this.officePhoneNumber = queryData.get(1).get(6);
			this.officeRoomNumber = queryData.get(1).get(7);
		}

		// Close mysql
		mysql.close();
		return queryData.size();
	}

	// Getters
	public String getId() {
		return id;
	}

	public String getDepartmentid() {
		return departmentid;
	}

	public String getfName() {
		return fName;
	}

	public String getlName() {
		return lName;
	}

	public String getEmail() {
		return email;
	}

	public String getInterest() {
		return interest;
	}

	public String getOfficeBuilding() {
		return officeBuilding;
	}

	public String getOfficePhoneNumber() {
		return officePhoneNumber;
	}

	public String getOfficeRoomNumber() {
		return officeRoomNumber;
	}
}

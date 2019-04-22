package data;

import java.util.ArrayList;

/**
 * Data layer of Faculty
 */
public class Faculty {
	// Fields
	private String id;
	private String departmentId;
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

	public Faculty(String id, String departmentId, String fName, String lName, String email, String interest, String officeBuilding, String officePhoneNumber, String officeRoomNumber) {
		this.id = id;
		this.departmentId = departmentId;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.interest = interest;
		this.officeBuilding = officeBuilding;
		this.officePhoneNumber = officePhoneNumber;
		this.officeRoomNumber = officeRoomNumber;
	}

	/**
	 * Retrieve the values from the db using the faculty's id and update other attributes
	 *
	 * @returns size of the data fetched
	 */
	public int get() throws DLException {
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
			this.departmentId = queryData.get(1).get(0);
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

	/**
	 * Update the attributes in the db
	 *
	 * @returns num of rows affected
	 */
	public int put() throws DLException {
		// Connect to Mysql
		mysql.connect();

		// Create query
		String query = "update faculty set departmentId = ?, firstname = ?, lastname = ?, email = ?, interest = ?, officebuilding = ?, officephonenumber = ?, officeroomnumber where facultyid = ?";

		// Add properties to values ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getDepartmentId());
		values.add(this.getfName());
		values.add(this.getlName());
		values.add(this.getEmail());
		values.add(this.getInterest());
		values.add(this.getOfficeBuilding());
		values.add(this.getOfficePhoneNumber());
		values.add(this.getOfficeRoomNumber());
		values.add(this.getId());

		int modified = mysql.setData(query, values);

		// Close mysql
		mysql.close();
		return modified;
	}

	/**
	 * Insert the attributes in the db
	 *
	 * @returns num of rows affected
	 */
	public int post() throws DLException {
		// Connect to Mysql
		mysql.connect();

		// Create query
		String query = "insert into faculty values(?, ?, ?, ?, ?, ?, ?, ?, ?);";

		// Add properties to values ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getId());
		values.add(this.getDepartmentId());
		values.add(this.getfName());
		values.add(this.getlName());
		values.add(this.getEmail());
		values.add(this.getInterest());
		values.add(this.getOfficeBuilding());
		values.add(this.getOfficePhoneNumber());
		values.add(this.getOfficeRoomNumber());

		int modified = mysql.setData(query, values);

		// Close mysql
		mysql.close();
		return modified;
	}

	/**
	 * Delete method to delete a record in the database
	 *
	 * @returns num of rows deleted
	 */
	public int delete() throws DLException {
		// Connect to Mysql
		mysql.connect();

		// Connect to Mysql
		String query = "delete from faculty where facultyId = ?;";

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

	public String getDepartmentId() {
		return departmentId;
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

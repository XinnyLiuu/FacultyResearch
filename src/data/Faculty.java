package data;

import java.util.ArrayList;

/**
 * Data layer for Faculty
 */
public class Faculty {
	private String id;
	private String departmentId;
	private String fName;
	private String lName;
	private String email;
	private String interest;
	private String officeBuilding;
	private String officePhoneNumber;
	private String officeRoomNumber;
	private String password;

	// MySQL
	MySQLDatabase mysql = new MySQLDatabase();

	// Constructors
	public Faculty() { }

	public Faculty(String id) {
		this.id = id;
	}

	public Faculty(String id, String password) {
		this.id = id;
		this.password = password;
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
	 * @throws DLException if any MySQLDatabase methods errors
	 * @return size of the data fetched
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
		if(queryData.size() > 1) {
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
	 * @throws DLException if any MySQLDatabase methods errors
	 * @return num of rows affected
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
	 * @throws DLException if any MySQLDatabase methods errors
	 * @return num of rows affected
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
	 * Delete a record in the database
	 *
	 * @throws DLException if any MySQLDatabase methods errors
	 * @return num of rows deleted
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

	/**
	 * Checks the faculty's id and password against the ones in the database
	 *
	 * @throws DLException if any MySQLDatabase methods errors
	 * @return 1 - if the faculty exists based on the id and password given, 0 - if otherwise
	 */
	public int login() throws DLException {
		// Open mysql
		mysql.connect();

		// Create query
		String query = "select * from faculty where password = sha2(?, 256) and facultyid = ?";

		// Prepare values
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getPassword());
		values.add(this.getId());

		ArrayList<ArrayList<String>> data = mysql.getData( query, values );
		data.remove(0); // Remove column names

		// On faculty data found, set the faculty's values and return the number
		if(data.size() == 1) {
			get();
			return data.size();
		}

		// Close mysql
		mysql.close();
		return 0;
	}

	/**
	 * Gets all the faculty's projects
	 *
	 * @throws DLException if any MySQLDatabase methods errors
	 * @return 2D arraylist of all projects associated with the faculty
	 */
	public ArrayList<ArrayList<String>> getAllMyProjects() throws DLException {
		// Open mysql
		mysql.connect();

		// Create query
		String query = "select p.projectid, p.projectname, f.lastname, p.projectdescription, p.budget, p.startdate, p.enddate from project p join faculty f on p.facultyid = ? and f.facultyid = p.facultyid";

		// Prepare values
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getId());

		ArrayList<ArrayList<String>> data = mysql.getData( query, values );
		data.remove(0); // Remove column names

		// Close mysql
		mysql.close();
		return data;
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

	public String getPassword() { return password; }
}

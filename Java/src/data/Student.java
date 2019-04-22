package data;

import java.util.ArrayList;

/**
 * Data layer of Student
 */
public class Student {
	// Fields
	private String id;
	private String departmentId;
	private String fName;
	private String lName;
	private String email;
	private String year;
	private String major;

	// MySQL
	MySQLDatabase mysql = new MySQLDatabase();

	// Constructor
	public Student(String id) {
		this.id = id;
	}

	public Student(String id, String departmentId, String fName, String lName, String email, String year, String major) {
		this.id = id;
		this.departmentId = departmentId;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.year = year;
		this.major = major;
	}

	/**
	 * Retrieve the values from the db using the student's id and update other attributes
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
		String query = "select departmentid, firstname, lastname, email, schoolyear, major from student where studentid = ?";

		// Retrieve data from db using .getData()
		ArrayList<ArrayList<String>> queryData = mysql.getData( query, values );

		// Iterate through queryData and set the object's attributes
		if(queryData.size() > 0) {
			// Get the second row as the first row will be the column names
			this.departmentId = queryData.get(1).get(0);
			this.fName = queryData.get(1).get(1);
			this.lName = queryData.get(1).get(2);
			this.email = queryData.get(1).get(3);
			this.year = queryData.get(1).get(4);
			this.major = queryData.get(1).get(5);
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
		String query = "update student set departmentid = ?, firstname = ?, lastname = ?, email = ?, schoolyear = ?, major = ? where studentid = ?";

		// Add properties to values ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getDepartmentId());
		values.add(this.getfName());
		values.add(this.getlName());
		values.add(this.getEmail());
		values.add(this.getYear());
		values.add(this.getMajor());
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
		String query = "insert into student values(?, ?, ?, ?, ?, ?, ?);";

		// Add properties to values ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getId());
		values.add(this.getDepartmentId());
		values.add(this.getfName());
		values.add(this.getlName());
		values.add(this.getEmail());
		values.add(this.getYear());
		values.add(this.getMajor());

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
		String query = "delete from student where studentid = ?;";

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

	public String getYear() {
		return year;
	}

	public String getMajor() {
		return major;
	}
}

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

	/**
	 * Retrieve the values from the db using the student's id and update other attributes
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

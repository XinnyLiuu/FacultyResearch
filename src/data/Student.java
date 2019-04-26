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
	private String password;

	// MySQL
	MySQLDatabase mysql = new MySQLDatabase();

	// Constructor
	public Student() { }

	public Student(String id) {
		this.id = id;
	}

	public Student(String id, String password) {
		this.id = id;
		this.password = password;
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
		if(queryData.size() > 1) {
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

	/**
	 * Checks the student's id and password against the ones in the database
	 *
	 * @return
	 * @throws DLException
	 */
	public int login() throws DLException {
		// Open mysql
		mysql.connect();

		// Create query
		String query = "select * from student where password = sha2(?, 256) and studentid = ?";

		// Prepare values
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getPassword());
		values.add(this.getId());

		ArrayList<ArrayList<String>> data = mysql.getData( query, values );
		data.remove(0); // Remove column names

		// On student data found, set the student's values and return the number
		if(data.size() == 1) {
			get();
			return data.size();
		}

		// Close mysql
		mysql.close();
		return 0;
	}

	/**
	 * Gets all the student's projects
	 *
	 * @return arraylist of all projects with faculty name associated with the student
	 */
	public ArrayList<ArrayList<String>> getAllMyProjects() throws DLException {
		// Open mysql
		mysql.connect();

		// Create query
		String query = "select p.projectid, p.projectname, f.lastname, p.projectdescription, p.budget, p.startdate, p.enddate from project p join faculty f on p.studentid = ?";

		// Prepare values
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getId());

		ArrayList<ArrayList<String>> data = mysql.getData( query, values );
		data.remove(0); // Remove column names

		// Close mysql
		mysql.close();
		return data;
	}

	/**
	 * Gets all the student's projects by department
	 *
	 * @return arraylist of all projects with faculty name associated with the department
	 */
	public ArrayList<ArrayList<String>> getAllDepartmentProjects() throws DLException {
		// Open mysql
		mysql.connect();

		// Create query
		String query = "select p.projectid, p.projectname, f.lastname, p.projectdescription, p.budget, p.startdate, p.enddate from project p join faculty f on f.departmentid = ? and f.facultyid = p.facultyid";

		// Prepare values
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getDepartmentId());

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

	public String getYear() {
		return year;
	}

	public String getMajor() {
		return major;
	}

	public String getPassword() { return password; }
}

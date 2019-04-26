package data;

import java.util.ArrayList;

/**
 * Data layer of Project
 */
public class Project {
	// Fields
	private String id;
	private String facultyId;
	private String studentId;
	private String name;
	private String desc;
	private String budget;
	private String startDate;
	private String endDate;

	private String faculty;

	// MySQL
	MySQLDatabase mysql = new MySQLDatabase();

	// Constructor
	public Project() { }

	public Project(String id) {
		this.id = id;
	}

	public Project(String id, String facultyId, String studentId, String name, String desc, String budget, String startDate, String endDate) {
		this.id = id;
		this.facultyId = facultyId;
		this.studentId = studentId;
		this.name = name;
		this.desc = desc;
		this.budget = budget;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Project(String id, String name, String faculty, String desc, String budget, String startDate, String endDate) {
		this.id = id;
		this.name = name;
		this.faculty = faculty;
		this.desc = desc;
		this.budget = budget;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Project(String facultyId, String name, String desc, String budget, String startDate, String endDate) {
		this.facultyId = facultyId;
		this.name = name;
		this.desc = desc;
		this.budget = budget;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Retrieve the values from the db using the project's id and update other attributes
	 *
	 * @returns size of the data fetched
	 */
	public int get() throws DLException {
		// Connect to Mysql
		mysql.connect();

		// Add the equipment id to an ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getId());

		// Create query string
		String query = "select facultyid, studentid, projectname, projectdescription, budget, startdate, enddate from project where projectid = ?";

		// Retrieve data from db using .getData()
		ArrayList<ArrayList<String>> queryData = mysql.getData(query, values);

		// Iterate through queryData and set the object's attributes
		if (queryData.size() > 1) {
			// Get the second row as the first row will be the column names
			this.facultyId = queryData.get(1).get(0);
			this.studentId = queryData.get(1).get(1);
			this.name = queryData.get(1).get(2);
			this.desc = queryData.get(1).get(3);
			this.budget = queryData.get(1).get(4);
			this.startDate = queryData.get(1).get(5);
			this.endDate = queryData.get(1).get(6);
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
		String query = "update project set facultyid = ?, studentid = ?, projectname = ?, projectdescription = ?, budget = ?, startdate = ?, enddate = ? where projectid = ?";

		// Add properties to values ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getFacultyId());
		values.add(this.getStudentId());
		values.add(this.getName());
		values.add(this.getDesc());
		values.add(this.getBudget());
		values.add(this.getStartDate());
		values.add(this.getEndDate());
		values.add(this.getId());

		int modified = mysql.setData(query, values);

		// Close mysql
		mysql.close();
		return modified;
	}

	/**
	 * Update the attributes in the db
	 *
	 * @returns num of rows affected
	 */
	public int putNoStudentId() throws DLException {
		// Connect to Mysql
		mysql.connect();

		// Create query
		String query = "update project set projectname = ?, projectdescription = ?, budget = ?, startdate = ?, enddate = ? where projectid = ?";

		// Add properties to values ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getName());
		values.add(this.getDesc());
		values.add(this.getBudget());
		values.add(this.getStartDate());
		values.add(this.getEndDate());
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
		String query = "insert into project values(?, ?, ?, ?, ?, ?, ?, ?);";

		// Add properties to values ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getId());
		values.add(this.getFacultyId());
		values.add(this.getName());
		values.add(this.getDesc());
		values.add(this.getBudget());
		values.add(this.getStartDate());
		values.add(this.getEndDate());
		values.add(this.getStudentId());

		int modified = mysql.setData(query, values);

		// Close mysql
		mysql.close();
		return modified;
	}

	/**
	 * Insert the attributes in the db without projectid
	 *
	 * @returns num of rows affected
	 */
	public int postNoId() throws DLException {
		// Connect to Mysql
		mysql.connect();

		// Create query
		String query = "insert into project (facultyid, projectname, projectdescription, budget, startdate, enddate) values(?, ?, ?, ?, ?, ?);";

		// Add properties to values ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getFacultyId());
		values.add(this.getName());
		values.add(this.getDesc());
		values.add(this.getBudget());
		values.add(this.getStartDate());
		values.add(this.getEndDate());

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
		String query = "delete from project where ProjectID = ?;";

		// Add properties to values ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add(this.getId());

		int modified = mysql.setData(query, values);

		// Close mysql
		mysql.close();
		return modified;
	}

	/**
	 * Gets all the projects with faculty name
	 *
	 * @return arraylist of all projects with faculty name
	 */
	public ArrayList<ArrayList<String>> getAllFullProjects() throws DLException {
		// Open mysql
		mysql.connect();

		// Create query
		String query = "select p.projectid, p.projectname, f.lastname, p.projectdescription, p.budget, p.startdate, p.enddate from project p join faculty f where p.facultyid = f.facultyid";

		ArrayList<ArrayList<String>> data = mysql.getData( query );

		// Close mysql
		mysql.close();
		return data;
	}

	// Getters
	public String getId() {
		return id;
	}

	public String getFacultyId() {
		return facultyId;
	}

	public String getStudentId() { return studentId; }

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public String getBudget() {
		return budget;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getFaculty() {
		return faculty;
	}
}

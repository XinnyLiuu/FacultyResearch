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
   
   public Department(String id, String name, String officeNum){
      this.id = id;
      this.name = name;
      this.officeRoomNumber = officeNum;
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

	/**
	 * Update the department in the db using the department's id as a selector
	 *
	 * @returns number of rows update
	 */
	public int put() throws DLException{
		//connect to tMySQL
		mysql.connect();

		//set up query string
		String query = "Update department set DepartmentName = ?, OfficeRoomNumber = ? where DepartmentID = ?;";

		//add values to the ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add(name);
		values.add(officeRoomNumber);
		values.add(id);

		//Update the department
		int rowsChanged = mysql.setData(query, values);

		//close the connection
		mysql.close();
		return rowsChanged;
	}

	/**
	 * Insert the new department in the db using the department's id as a selector
	 *
	 * @returns number of rows updated
	 */
	public int post() throws DLException{
		//Connect to MySQL
		mysql.connect();

		//Create query
		String query = "Insert into department VALUES(?,?,?);";

		//Add properties to values ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add(id);
		values.add(name);
		values.add(officeRoomNumber);

		//Insert the new department
		int rowsChanged = mysql.setData(query,values);

		//close the connection
		mysql.close();
		return rowsChanged;
	}

	/**
	 * Delete the department in the db using the department's id as a selector
	 *
	 * @returns number of rows changed
	 */
	public int delete() throws DLException{
		//connect to MySQL
		mysql.connect();

		//set up query string
		String query = "Delete from department where DepartmentID = ?;";

		//add values to ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add(id);

		// delete the department form the db
		int rowsChanged = mysql.setData(query,values);

		//close the connection
		mysql.close();
		return rowsChanged;
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

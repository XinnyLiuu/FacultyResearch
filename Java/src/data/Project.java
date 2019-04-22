package data;

import java.util.ArrayList;

/**
 * Data layer of Project
 */
public class Project {
	// Fields
	private String id;
	private String facultyId;
	private String name;
	private String desc;
	private String budget;
	private String startDate;
	private String endDate;

	// MySQL
	MySQLDatabase mysql = new MySQLDatabase();

	// Constructor
	public Project(String id) {
		this.id = id;
	}
   
   //Constructor
   public Project(String id, String facultyId, String name, String desc, String budget, String startDate, String endDate){
      this.id = id;
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
	public int fetch() throws DLException {
		// Connect to Mysql
		mysql.connect();

		// Add the equipment id to an ArrayList
		ArrayList<String> values = new ArrayList<>();
		values.add( this.getId() );

		// Create query string
		String query = "select facultyid, projectname, projectdescription, budget, startdate, enddate from project where projectid = ?";

		// Retrieve data from db using .getData()
		ArrayList<ArrayList<String>> queryData = mysql.getData( query, values );

		// Iterate through queryData and set the object's attributes
		if(queryData.size() > 0) {
			// Get the second row as the first row will be the column names
			this.facultyId = queryData.get(1).get(0);
			this.name = queryData.get(1).get(1);
			this.desc = queryData.get(1).get(2);
			this.budget = queryData.get(1).get(3);
			this.startDate = queryData.get(1).get(4);
			this.endDate = queryData.get(1).get(5);
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
      
       //Create query string
		String query = "UPDATE project SET FacultyID = ?, ProjectName = ?,  ProjectDescription = ?, Budget = ?, StartDate = ?, EndDate = ? WHERE ProjectID = ?;";

      
      //Arraylist to insert data
      ArrayList<String> values = new ArrayList<>();
   		values.add(this.getFacultyId());
   		values.add(this.getName());
   		values.add(this.getDesc());
         values.add(this.getBudget());
         values.add(this.getStartDate());
         values.add(this.getEndDate());
         values.add(this.getId());
         
     		int num = mysql.setData(query, values);
      
      // Close mysql
      mysql.close();
      return num;
      
      }
   
      /**
	 * Insert the attributes in the db
	 *
	 * @returns num of rows affected
	 */
	public int post() throws DLException {
      // Connect to Mysql
		mysql.connect();

         
      //Create query string
      String query = "INSERT INTO project VALUES(?,?,?,?,?,?,?);"; 
      
            
      //Arraylist to insert data
      ArrayList<String> values = new ArrayList<>();
   		values.add(this.getId());
   		values.add(this.getFacultyId());
   		values.add(this.getName());
   		values.add(this.getDesc());
         values.add(this.getBudget());
         values.add(this.getStartDate());
         values.add(this.getEndDate());
        
   		int num = mysql.setData(query, values);
         
         // Close mysql
         mysql.close();
         return num;
      }
      
    /**
	 * Delete method to erase a record in the database
	 *
	 * @returns num of rows deleted
	 */
	public int delete() throws DLException {  
       // Connect to Mysql
		mysql.connect();
      
      //Arraylist to delete data
      ArrayList<String> values = new ArrayList<>();
		   values.add(this.getId());
   		String query = "DELETE FROM project WHERE ProjectID = ?;";
   		int num = mysql.setData(query, values);
         
         // Close mysql
         mysql.close();
         return num;
      

      }
	// Getters
	public String getId() {
		return id;
	}

	public String getFacultyId() {
		return facultyId;
	}

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
}

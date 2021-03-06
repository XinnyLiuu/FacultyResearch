package business;

import data.DLException;
import data.Project;

import java.util.ArrayList;

/**
 * Business layer for the project
 */
public class BLProject extends Project {
	// Constructors
	public BLProject() { }

	public BLProject(String id) {
		super(id);
	}

	public BLProject(String facultyId, String name, String desc, String budget, String startDate, String endDate) {
		super(facultyId, name, desc, budget, startDate, endDate);
	}

	public BLProject(String id, String name, String faculty, String desc, String budget, String startDate, String endDate) {
		super(id, name, faculty, desc, budget, startDate, endDate);
	}

	/**
	 * Calls postNoId()
	 *
	 * @throws DLException if postNoId() fails
	 * @return true - if insert worked, false - if otherwise
	 */
	public boolean doPostNoId() throws DLException {
		if(postNoId() > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Calls putNoStudentId()
	 *
	 * @throws DLException if putNoStudentId() fails
	 * @return true - if inserted, false - if otherwise
	 */
	public boolean doPutNoStudentId() throws DLException {
		if(putNoStudentId() > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Calls doDelete()
     *
	 * @throws DLException if delete() fails
	 * @return true - if deleted, false - if otherwise
	 */
	public boolean doDelete() throws DLException {
		if(delete() > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Calls getAllFullProjects()
	 *
	 * @throws DLException if getAllFullProjects() fails
	 * @return 2D arraylist of all the projects and its relating faculty member's lastname
	 */
	public ArrayList<ArrayList<String>> doGetFullProjects() throws DLException {
		return getAllFullProjects();
	}
}

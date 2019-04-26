package business;

import data.DLException;
import data.Project;

import java.util.ArrayList;

/**
 * Business layer for the project
 */
public class BLProject extends Project {
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
	 * @throws DLException
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
	 * @throws DLException
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
	 * @throws DLException
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
	 * @return
	 * @throws DLException
	 */
	public ArrayList<ArrayList<String>> doGetFullProjects() throws DLException {
		ArrayList<ArrayList<String>> projects = getAllFullProjects();
		return projects;
	}
}

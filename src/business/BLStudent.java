package business;

import data.DLException;
import data.Student;

import java.util.ArrayList;

/**
 * Business layer for Student
 */
public class BLStudent extends Student {
	// Constructors
	public BLStudent() { }

	public BLStudent(String id) {
		super(id);
	}

	public BLStudent(String id, String password) {
		super(id, password);
	}

	/**
	 * Check if the user exists
	 *
	 * @return true / false
	 */
	public boolean doLogin() throws DLException {
		if(login() == 1) {
			return true;
		}

		return false;
	}

	/**
	 * Calls getAllMyProjects()
	 *
	 * @return 2D Arraylist of all the student's projects
	 */
	public ArrayList<ArrayList<String>> doGetMyProjects() throws DLException  {
		return getAllMyProjects();
	}

	/**
	 * Calls getAllDepartmentProjects()
	 *
	 * @return 2D Arraylist of all the projects of the student's department
	 */
	public ArrayList<ArrayList<String>> doGetAllDepartmentProjects() throws DLException {
		return getAllDepartmentProjects();
	}
}

package business;

import data.DLException;
import data.Student;

import java.util.ArrayList;

/**
 * Business layer for Student
 */
public class BLStudent extends Student {
	public BLStudent() { }

	public BLStudent(String id) {
		super(id);
	}

	/**
	 * Check if the user exists
	 *
	 * @return true / false
	 */
	public boolean login() throws DLException {
		if(get() > 1) {
			return true;
		}

		return false;
	}

	/**
	 * Calls getAllMyProjects()
	 *
	 * @return
	 * @throws DLException
	 */
	public ArrayList<ArrayList<String>> doGetMyProjects() throws DLException  {
		ArrayList<ArrayList<String>> projects = getAllMyProjects();
		return projects;
	}

	/**
	 * Calls getAllDepartmentProjects()
	 *
	 * @return
	 * @throws DLException
	 */
	public ArrayList<ArrayList<String>> doGetAllDepartmentProjects() throws DLException {
		ArrayList<ArrayList<String>> projects = getAllDepartmentProjects();
		return projects;
	}
}

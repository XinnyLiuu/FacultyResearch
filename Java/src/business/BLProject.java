package business;

import data.DLException;
import data.Project;

import java.util.ArrayList;

/**
 * Business layer for the project
 */
public class BLProject extends Project {
	public BLProject() { }

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

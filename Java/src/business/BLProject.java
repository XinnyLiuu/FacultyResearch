package business;

import data.DLException;
import data.Project;

import java.util.ArrayList;

/**
 * Business layer for the project
 */
public class BLProject extends Project {
	public BLProject() {

	}

	/**
	 * Calls doGetAllFullProjects() from Project
	 *
	 * @return true / false
	 */
	public ArrayList<ArrayList<String>> doGetAll() throws DLException  {
		// Check size
		if(getAllFullProjects().size() > 0) {
			return getAllFullProjects();
		}

		return null;
	}
}

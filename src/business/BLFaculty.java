package business;

import data.DLException;
import data.Faculty;

import java.util.ArrayList;

public class BLFaculty extends Faculty {
	public BLFaculty() { }

	public BLFaculty(String id) {
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
}

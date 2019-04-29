package business;

import data.DLException;
import data.Faculty;

import java.util.ArrayList;

/**
 * Business layer for Faculty
 */
public class BLFaculty extends Faculty {
	// Constructors
	public BLFaculty() { }

	public BLFaculty(String id) {
		super(id);
	}

	public BLFaculty(String id, String password) {
		super(id, password);
	}

	/**
	 * Calls login()
	 *
	 * @return true - if successful login, false - if otherwise
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
	 * @return 2D ArrayList of all the faculty's projects
	 */
	public ArrayList<ArrayList<String>> doGetMyProjects() throws DLException  {
		return getAllMyProjects();
	}
}

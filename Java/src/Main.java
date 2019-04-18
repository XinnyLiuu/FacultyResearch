import data.*;

/**
 * The presentation layer
 */
public class Main {
	public static void main(String[] args) {
		try {
			// Get faculty
			Faculty f = new Faculty("11");
			if(f.fetch() > 0) {
				System.out.printf("Faculty id %s has attributes: %s %s %s %s %s %s %s %s %n", f.getId(), f.getDepartmentid(), f.getfName(), f.getlName(), f.getEmail(), f.getInterest(), f.getOfficeBuilding(), f.getOfficePhoneNumber(), f.getOfficeRoomNumber());
			}

			// Get project
			Project p = new Project("41");
			if(p.fetch() > 0) {
				System.out.printf("Project id %s has attributes: %s %s %s %s %s %s %n", p.getId(), p.getFacultyId(), p.getName(), p.getDesc(), p.getBudget(), p.getStartDate(), p.getEndDate());
			}

			// Get student
			Student s = new Student("31");
			if(s.fetch() > 0) {
				System.out.printf("Student id %s has attributes: %s %s %s %s %s %s %n", s.getId(), s.getDepartmentId(), s.getfName(), s.getlName(), s.getEmail(), s.getYear(), s.getMajor());
			}

			// Get department
			Department d = new Department("21");
			if(d.fetch() > 0) {
				System.out.printf("Department id %s has attributes: %s %s %n", d.getId(), d.getName(), d.getOfficeRoomNumber());
			}
		}
		catch(DLException dle) {
			dle.printStackTrace();
		}
	}
}

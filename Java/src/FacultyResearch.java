import business.BLProject;
import business.BLStudent;
import data.*;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

/**
 * The presentation layer
 */
public class FacultyResearch extends Application {
	private Stage stage;
	private Scene scene;

	private VBox root = new VBox();

	// PUBLIC
	private TableView publicProjectView;

	// STUDENT
	private TableView studentProjectView; // Table of all student's projects
	private TableView studentDeptProjectView; // Table of all student's department's projects

	public void start(Stage stage) {
		// GUI setup
		this.stage = stage;
		this.stage.setTitle("Faculty Research Project");

		// Authorize user
		String user = this.showUserAuthorization();

		// PUBLIC USERS
		if (user.equals("public")) {
			// Setup table
			this.publicProjectView = setUpPublicTable(publicProjectView);
			this.publicProjectView.prefHeightProperty().bind(stage.heightProperty());
			this.publicProjectView.prefWidthProperty().bind(stage.widthProperty());

			// Setup welcome
			Label welcome = new Label("Welcome, Public User!");
			welcome.setFont(new Font(25));

			// Root configs
			this.root.setSpacing(10);
			this.root.getChildren().addAll(
					welcome,
					publicProjectView
			);

			// Show GUI
			this.scene = new Scene(root, 1500, 800); // W and H
			this.stage.setScene(scene);
			this.stage.show();
		}

		// STUDENT USERS
		else if (user.equals("student")) {
			// Show username login
			TextInputDialog dialog = new TextInputDialog("Your Student ID");
			dialog.setTitle("Student Login");
			dialog.setHeaderText("Enter Your Credentials");
			dialog.setContentText("User ID:");

			Optional<String> result = dialog.showAndWait();
			String userid = null;
			if (result.isPresent()) {
				userid = result.get(); // Get userid
			}

			// Authenticate
			BLStudent student = new BLStudent( userid );
			try {
				if(student.login()) {
					// Setup welcome
					Label welcome = new Label(String.format("Welcome, %s %s!", student.getfName(), student.getlName()));
					welcome.setFont( new Font(25) );

					// Setup major
					Label major = new Label(String.format("Your major: %s", student.getMajor()));
					major.setFont( new Font(20) );

					// Setup tables
					Label mine = new Label("Your Projects");
					mine.setFont( new Font(20) );
					this.studentProjectView = setUpStudentTable(studentProjectView, student);
					this.studentProjectView.prefWidthProperty().bind(stage.widthProperty());

					Label ongoing = new Label("Ongoing Projects in your Department");
					ongoing.setFont( new Font(20) );
					this.studentDeptProjectView = setUpStudentDeptTable(studentDeptProjectView, student);
					this.studentDeptProjectView.prefWidthProperty().bind(stage.widthProperty());

					// Root configs
					this.root.setSpacing( 10 );
					this.root.getChildren().addAll(
							welcome,
							major,
							mine,
							studentProjectView,
							ongoing,
							studentDeptProjectView
					);
				}
				else {
					// User does not exist!
					Alert alert = new Alert(Alert.AlertType.ERROR, "Please try again!");
					alert.showAndWait();
					System.exit(1);
				}
			}
			catch(DLException dle) {
				dle.printStackTrace();
			}

			// Show GUI
			this.scene = new Scene(root, 1500, 800); // W and H
			this.stage.setScene(scene);
			this.stage.show();
		}

		// FACULTY USERS
		else if (user.equals("faculty")) {
			// Setup welcome
			Label welcome = new Label("Welcome, Faculty");
			welcome.setFont(new Font(25));

			// Root configs
			this.root.setSpacing(10);
			this.root.getChildren().addAll(
					welcome
			);

			// Show GUI
			this.scene = new Scene(root, 1500, 800); // W and H
			this.stage.setScene(scene);
			this.stage.show();
		}

		else { // Fallback
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		// Start GUI
		launch(args);
	}

	/**
	 * User authorization
	 *
	 * @return User type
	 */
	private String showUserAuthorization() {
		// Setup alert
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("User Authorization");
		alert.setHeaderText("Select an Authorization Level");

		// Add btn choices
		ButtonType btnPublic = new ButtonType("Public");
		ButtonType btnStudent = new ButtonType("Student");
		ButtonType btnFaculty = new ButtonType("Faculty");
		ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(btnPublic, btnStudent, btnFaculty, btnCancel);

		// Get result
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == btnPublic) {
			return "public";
		} else if (result.get() == btnStudent) {
			return "student";
		} else if (result.get() == btnFaculty) {
			return "faculty";
		} else { // btnCancel
			System.exit(1);
		}

		return "";
	}

	/**
	 * GENERIC project table building
	 *
	 * @param tableView
	 * @param projects
	 */
	private void setUpProjectTable(TableView tableView, ArrayList<ArrayList<String>> projects) {
		// Make columns
		TableColumn<String, Project> id = new TableColumn<>("ID");
		id.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn<String, Project> name = new TableColumn<>("Name");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<String, Project> faculty = new TableColumn<>("Faculty");
		faculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));

		TableColumn<String, Project> desc = new TableColumn<>("Description");
		desc.setCellValueFactory(new PropertyValueFactory<>("desc"));

		TableColumn<String, Project> budget = new TableColumn<>("Budget");
		budget.setCellValueFactory(new PropertyValueFactory<>("budget"));

		TableColumn<String, Project> startDate = new TableColumn<>("Start Date");
		startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));

		TableColumn<String, Project> endDate = new TableColumn<>("End Date");
		endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

		// Add columns to table
		tableView.getColumns().addAll(id, name, faculty, desc, budget, startDate, endDate);

		// Add data to table
		for (int i = 0; i < projects.size(); i++) {
			String pid = projects.get(i).get(0);
			String pname = projects.get(i).get(1);
			String pfaculty = projects.get(i).get(2);
			String pdesc = projects.get(i).get(3);
			String pbudget = projects.get(i).get(4);
			String pstart = projects.get(i).get(5);
			String pend = projects.get(i).get(6);

			tableView.getItems().add(new Project(pid, pname, pfaculty, pdesc, pbudget, pstart, pend));
		}
	}

	/**
	 * FOR PUBLIC USERS
	 *
	 * Prepares the table view of all the projects for PUBLIC
	 */
	private TableView setUpPublicTable(TableView tableView) {
		// Create table view for all projects
		tableView = new TableView();

		// Get Projects
		BLProject publicProject = new BLProject();

		try {
			// Get the projects with faculty name
			ArrayList<ArrayList<String>> projects = publicProject.getAllFullProjects();

			// Build project
			setUpProjectTable( tableView, projects );
		} catch (DLException dle) {
			dle.printStackTrace();
		}

		return tableView;
	}

	/**
	 * FOR STUDENT USERS
	 *
	 * Prepares the table view of all the projects for the current student
	 */
	private TableView setUpStudentTable(TableView tableView, BLStudent student) {
		// Create table view for all projects
		tableView = new TableView();

		try {
			// Get the projects with faculty name
			ArrayList<ArrayList<String>> projects = student.doGetMyProjects();

			// Build project
			setUpProjectTable( tableView, projects );
		} catch (DLException dle) {
			dle.printStackTrace();
		}

		return tableView;
	}

	/**
	 * FOR STUDENT USERS
	 *
	 * Prepares the table view of all the projects for the current student's department
	 */
	private TableView setUpStudentDeptTable(TableView tableView, BLStudent student) {
		// Create table view for all projects
		tableView = new TableView();

		try {
			// Get the projects with faculty name
			ArrayList<ArrayList<String>> projects = student.doGetAllDepartmentProjects();

			// Build project
			setUpProjectTable( tableView, projects );
		} catch (DLException dle) {
			dle.printStackTrace();
		}

		return tableView;
	}
}

import business.BLFaculty;
import business.BLProject;
import business.BLStudent;
import data.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	// FACULTY
	private TableView facultyProjectView; // Table of all the faculty's projects
	private TextField tfName, tfDesc, tfBudget, tfStart, tfEnd, tfDltId, tfUpId, tfUpName, tfUpDesc, tfUpBudget, tfUpStart, tfUpEnd;
	private Button btnAdd, btnUpdate, btnDelete;

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
			String userId = userAuthenticate();
			if(userId.equals("")) {
				// If userId empty, its an error
				Alert alert = new Alert(Alert.AlertType.ERROR, "Please try again!");
				alert.showAndWait();
				System.exit(1);
			}

			// Get Student
			BLStudent student = new BLStudent( userId );

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
			// Show username login
			String userId = userAuthenticate();
			if(userId.equals("")) {
				// If userId empty, its an error
				Alert alert = new Alert(Alert.AlertType.ERROR, "Please try again!");
				alert.showAndWait();
				System.exit(1);
			}

			// Get Faculty
			BLFaculty faculty = new BLFaculty( userId );

			try {
				if(faculty.login()) {
					// Setup welcome
					Label welcome = new Label(String.format("Welcome, %s %s!", faculty.getfName(), faculty.getlName()));
					welcome.setFont( new Font(25) );

					// Setup insert
					Label insert = new Label("Add a new project");
					insert.setFont( new Font(20) );

					// Setup update
					Label update = new Label("Update an existing project");
					update.setFont( new Font(20) );

					// Setup delete
					Label delete = new Label( "Delete a project");
					delete.setFont( new Font(20) );

					// Setup table
					Label mine = new Label("Your Projects");
					mine.setFont( new Font(20) );
					this.facultyProjectView = setUpFacultyTable(facultyProjectView, faculty);
					this.facultyProjectView.prefWidthProperty().bind(stage.widthProperty());

					// INSERT
					FlowPane fpInsert = new FlowPane(10, 10);
					tfName = new TextField("Name");
					tfDesc = new TextField("Description");
					tfBudget = new TextField("Budget");
					tfStart = new TextField("Start Date");
					tfEnd = new TextField("End Date");
					btnAdd = new Button("Add");
					fpInsert.setAlignment(Pos.CENTER);
					fpInsert.getChildren().addAll(
							tfName,
							tfDesc,
							tfBudget,
							tfStart,
							tfEnd,
							btnAdd
					);
					btnAdd.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							doFacultyAdd(faculty);
						}
					});

					// UPDATE
					FlowPane fpUpdate = new FlowPane(10, 10);
					tfUpId = new TextField("Existing Project ID");
					tfUpName = new TextField("New Name");
					tfUpDesc = new TextField("New Description");
					tfUpBudget = new TextField("New Budget");
					tfUpStart = new TextField("New Start Date");
					tfUpEnd = new TextField("New End Date");
					btnUpdate = new Button("Update");
					fpUpdate.setAlignment(Pos.CENTER);
					fpUpdate.getChildren().addAll(
						tfUpId,
						tfUpName,
						tfUpDesc,
						tfUpBudget,
						tfUpStart,
						tfUpEnd,
						btnUpdate
					);
					btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							doFacultyUpdate(faculty);
						}
					});

					// DELETE
					FlowPane fpDelete = new FlowPane(10, 10);
					tfDltId = new TextField("Existing Project ID");
					btnDelete = new Button("Delete");
					fpDelete.setAlignment(Pos.CENTER);
					fpDelete.getChildren().addAll(
						tfDltId,
						btnDelete
					);
					btnDelete.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							doFacultyDelete();
						}
					});

					// Root configs
					this.root.setSpacing( 10 );
					this.root.getChildren().addAll(
							welcome,
							mine,
							facultyProjectView,
							insert,
							fpInsert,
							update,
							fpUpdate,
							delete,
							fpDelete
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
	 * Authenticates user
	 *
	 * @return userId
	 */
	private String userAuthenticate() {
		// Show username login
		TextInputDialog dialog = new TextInputDialog("Your ID");
		dialog.setTitle("User Login");
		dialog.setHeaderText("Enter Your Credentials");
		dialog.setContentText("User ID:");

		Optional<String> result = dialog.showAndWait();
		String id = null;
		if (result.isPresent()) {
			id = result.get(); // Get userid
			return id;
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
		}
		catch (DLException dle) {
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

	/**
	 * FOR FACULTY USERS
	 *
	 * Prepares the table view of all the projects for the faculty
	 */
	private TableView setUpFacultyTable(TableView tableView, BLFaculty faculty) {
		// Create table view for all projects
		tableView = new TableView();

		try {
			// Get the projects with faculty name
			ArrayList<ArrayList<String>> projects = faculty.doGetMyProjects();

			// Build project
			setUpProjectTable( tableView, projects );
		} catch (DLException dle) {
			dle.printStackTrace();
		}

		return tableView;
	}

	/**
	 * FOR FACULTY USERS
	 *
	 * Fires when the add button is clicked
	 */
	private void doFacultyAdd(BLFaculty faculty) {
		// Check inputs
		String name = tfName.getText().trim();
		String desc = tfDesc.getText().trim();
		String budget = tfBudget.getText().trim();
		String start = tfStart.getText().trim();
		String end = tfEnd.getText().trim();

		try {
			if(name.length() == 0) {
				throw new Exception();
			}

			if(desc.length() == 0) {
				throw new Exception();
			}

			if(budget.length() == 0) {
				throw new Exception();
			}
			else {
				try {
					Double.parseDouble(budget);
				}
				catch(NumberFormatException npe) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure the budget is a number!");
					alert.showAndWait();
					return;
				}
			}

			if(start.length() == 0) {
				throw new Exception();
			}
			else {
				try {
					new SimpleDateFormat("yyyy-MM-dd").parse(end);
				}
				catch(ParseException pe) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Please use yyyy-MM-dd for date!");
					alert.showAndWait();
					return;
				}
			}

			if(end.length() == 0) {
				throw new Exception();
			}
			else {
				try {
					new SimpleDateFormat("yyyy-MM-dd").parse(end);
				}
				catch(ParseException pe) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Please use yyyy-MM-dd for date!");
					alert.showAndWait();
					return;
				}
			}
		}
		catch(Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure all inputs are completed!");
			alert.showAndWait();
			return;
		}

		// Add to DB
		BLProject newProject = new BLProject(faculty.getId(), name, desc, budget, start, end);
		try {
			if(newProject.doPostNoId()) {
				// Update table
				newProject.get();
				facultyProjectView.getItems().add(newProject);
				facultyProjectView.refresh();

				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Project created!");
				alert.showAndWait();
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to create project!");
				alert.showAndWait();
			}
		}
		catch(DLException dle) {
			dle.printStackTrace();
		}
	}

	/**
	 * FOR FACULTY USERS
	 *
	 * Fires when the update button is clicked
	 */
	private void doFacultyUpdate(BLFaculty faculty) {
		// Check inputs
		String id = tfUpId.getText().trim();
		String name = tfUpName.getText().trim();
		String desc = tfUpDesc.getText().trim();
		String budget = tfUpBudget.getText().trim();
		String start = tfUpStart.getText().trim();
		String end = tfUpEnd.getText().trim();

		try {
			if(id.length() == 0) {
				throw new Exception();
			}
			else {
				try {
					Integer.parseInt(id);
				}
				catch(NumberFormatException nfe) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure the id is a number!");
					alert.showAndWait();
					return;
				}
			}

			if(name.length() == 0) {
				throw new Exception();
			}

			if(desc.length() == 0) {
				throw new Exception();
			}

			if(budget.length() == 0) {
				throw new Exception();
			}
			else {
				try {
					Double.parseDouble(budget);
				}
				catch(NumberFormatException npe) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure the budget is a number!");
					alert.showAndWait();
					return;
				}
			}

			if(start.length() == 0) {
				throw new Exception();
			}
			else {
				try {
					new SimpleDateFormat("yyyy-MM-dd").parse(end);
				}
				catch(ParseException pe) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Please use yyyy-MM-dd for date!");
					alert.showAndWait();
					return;
				}
			}

			if(end.length() == 0) {
				throw new Exception();
			}
			else {
				try {
					new SimpleDateFormat("yyyy-MM-dd").parse(end);
				}
				catch(ParseException pe) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Please use yyyy-MM-dd for date!");
					alert.showAndWait();
					return;
				}
			}
		}
		catch(Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure all inputs are completed!");
			alert.showAndWait();
			return;
		}

		// Update to DB
		BLProject updateProject = new BLProject(id, name, faculty.getId(), desc, budget, start, end);
		try {
			if(updateProject.doPutNoStudentId()) {
				// Update table
				updateProject.get();
				facultyProjectView.getItems().add(updateProject);
				facultyProjectView.refresh();

				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Project updated!");
				alert.showAndWait();
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to update project!");
				alert.showAndWait();
			}
		}
		catch(DLException dle) {
			dle.printStackTrace();
		}
	}

	/**
	 * FOR FACULTY USERS
	 *
	 * Fires when the delete button is clicked
	 */
	private void doFacultyDelete() {
		// Check inputs
		String id = tfDltId.getText().trim();

		try {
			if(id.length() == 0) {
				throw new Exception();
			}
			else {
				try {
					Integer.parseInt(id);
				}
				catch(NumberFormatException nfe) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure the id is a number!");
					alert.showAndWait();
					return;
				}
			}
		}
		catch(Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure all inputs are completed!");
			alert.showAndWait();
			return;
		}

		// Remove from DB
		BLProject deleteProject = new BLProject(id);
		try {
			if(deleteProject.doDelete()) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Project deleted!");
				alert.showAndWait();
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to delete project!");
				alert.showAndWait();
			}
		}
		catch(DLException dle) {
			dle.printStackTrace();
		}
	}
}

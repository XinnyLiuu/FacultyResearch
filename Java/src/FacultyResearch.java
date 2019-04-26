import data.*;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

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
	private TableView<String> publicProjectView;

	public void start(Stage stage) {
		// GUI setup
		this.stage = stage;
		this.stage.setTitle("Faculty Research Project");

		// Authorize user
		String user = this.showUserAuthorization();
		if(user.equals("public")) { // PUBLIC
			this.publicProjectView = setUpPublicTable(publicProjectView);
			this.publicProjectView.prefHeightProperty().bind(stage.heightProperty());
			this.publicProjectView.prefWidthProperty().bind(stage.widthProperty());
			Label welcome = new Label("Welcome, Public User!");
			welcome.setFont( new Font(25) );


			// Root configs
			this.root.setSpacing( 10 );
			this.root.getChildren().addAll(
					welcome,
					publicProjectView
			);

			// Show GUI
			this.scene = new Scene(root, 1500, 800); // W and H
			this.stage.setScene( scene );
			this.stage.show();
		}
		else if(user.equals("student")) {
			// Show GUI
			this.scene = new Scene(root, 1500, 800); // W and H
			this.stage.setScene( scene );
			this.stage.show();
		}
		else if(user.equals("faculty")) {
			// Show GUI
			this.scene = new Scene(root, 1500, 800); // W and H
			this.stage.setScene( scene );
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
	 * User "LOGIN"
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
		}
		else if (result.get() == btnStudent) {
			return "student";
		}
		else if (result.get() == btnFaculty) {
			return "faculty";
		}
		else { // btnCancel
			System.exit(1);
		}

		return "";
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
		Project publicProject = new Project();

		try {
			// Get the projects with faculty name
			ArrayList<ArrayList<String>> projects = publicProject.getAllFullProjects();

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
			tableView.getColumns().addAll( id, name, faculty, desc, budget, startDate, endDate );

			// Add data to table
			for(int i=0; i<projects.size(); i++) {
				String pid = projects.get(i).get(0);
				String pname = projects.get(i).get(1);
				String pfaculty = projects.get(i).get(2);
				String pdesc = projects.get(i).get(3);
				String pbudget = projects.get(i).get(4);
				String pstart = projects.get(i).get(5);
				String pend = projects.get(i).get(6);

				tableView.getItems().add(new Project( pid, pname, pfaculty, pdesc, pbudget, pstart, pend ));
			}
		}
		catch(DLException dle) { dle.printStackTrace(); }

		return tableView;
	}
}

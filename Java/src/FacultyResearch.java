import data.*;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * The presentation layer
 */
public class FacultyResearch extends Application {
	private Stage stage;
	private Scene scene;

	private VBox root = new VBox();

	private TableView tableView = new TableView();

	public void start(Stage stage) {
		// GUI setup
		this.stage = stage;
		this.stage.setTitle("Faculty Research Project");

		// TODO: Add components


		// Root configs
		this.root.setSpacing( 10 );
		this.root.setAlignment(Pos.CENTER);
		this.root.getChildren().addAll(
				tableView
		);

		// Show GUI
		this.scene = new Scene(root, 800, 800); // W and H
		this.stage.setScene( scene );
		this.stage.show();
	}

	public static void main(String[] args) {
		// Start GUI
		launch(args);
	}

	/**
	 * Prepares the
	 */
	private void setUpTableView() {

	}
}

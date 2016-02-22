package application.view;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddRunwayController {

	private Main mainApp;
	@FXML
	private Button btnSubmitAirport;
	@FXML
	private TextField txtTORA;
	@FXML
	private TextField txtTODA;
	@FXML
	private TextField txtASDA;
	@FXML
	private TextField txtLDA;
	@FXML
	private TextField txtDisplacedThreshold;

	@FXML
	private void handleBtnSubmitRunway() {
		// TODO: get properties and do something with them
		mainApp.toggleAddRunway();
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public final void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
}

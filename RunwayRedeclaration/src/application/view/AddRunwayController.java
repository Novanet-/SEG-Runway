package application.view;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AddRunwayController {

	private Main mainApp;
	@FXML
	private Button btnSubmitAirport;

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

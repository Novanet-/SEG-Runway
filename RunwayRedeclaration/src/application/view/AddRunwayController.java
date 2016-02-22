package application.view;

import application.Main;
import application.model.Runway;
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
		try {
			double TORA = Double.parseDouble(txtTORA.textProperty().getValue());
			double TODA = Double.parseDouble(txtTODA.textProperty().getValue());
			double ASDA = Double.parseDouble(txtASDA.textProperty().getValue());
			double LDA = Double.parseDouble(txtLDA.textProperty().getValue());
			double displacedThreshold = Double.parseDouble(txtDisplacedThreshold.textProperty().getValue());
			
			// TODO: do something with properties
		} catch (NumberFormatException e) {
			// TODO: deal with invalid input
			e.printStackTrace();
		}
		
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

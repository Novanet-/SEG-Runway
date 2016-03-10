package application.controller;

import application.Main;
import application.model.Airport;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class AddAirportController
{

	private       Main                    mainApp;
	@FXML private TextField               txtAirportName;
	@FXML private Button                  btnSubmitAirport;
	private       ObservableList<Airport> airportList;


	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize()
	{
	}


	@FXML
	private void handleBtnSubmitAirport()
	{
		try {
			airportList.add(new Airport(airportList.size(), txtAirportName.getText()));
			txtAirportName.setText(""); //Clears textbox
			mainApp.toggleAddAirport();
		} catch (IllegalArgumentException e) {
			final Alert alert = new Alert(AlertType.ERROR, "Airport name cannot be blank.");
			alert.showAndWait();
		}
	}


	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public final void setMainApp(Main mainApp)
	{
		this.mainApp = mainApp;
	}


	public final void linkToSession()
	{
		airportList = mainApp.getAirportList();
	}
}

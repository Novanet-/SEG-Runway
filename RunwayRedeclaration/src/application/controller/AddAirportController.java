package application.controller;

import application.Main;
import application.model.Airport;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
		airportList.add(new Airport(airportList.size(), txtAirportName.getText()));
		txtAirportName.setText(""); //Clears textbox
		mainApp.toggleAddAirport();
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

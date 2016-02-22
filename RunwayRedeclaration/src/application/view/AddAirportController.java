package application.view;

import application.Main;
import application.model.Airport;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Created by Will on 21/02/2016.
 */
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
		final IntegerProperty airportId = new SimpleIntegerProperty(airportList.size());
		airportList.add(new Airport(airportId, txtAirportName.textProperty()));
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

		/*cmbAirports.setItems(mainApp.getAirports());
		cmbRunways.setItems(mainApp.getAirports().getRunways);
		cmbAirports.setItems(mainApp.getAirports().getRunways.getObjects);*/
	}


	public final void linkToSession()
	{
		airportList = mainApp.getAirportList();
	}
}

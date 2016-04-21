package application.controller;

import application.Main;
import application.model.Airport;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;

public class UpdateAirportController
{

	private       Main                    mainApp;
	@FXML private TextField               txtAirportName;
	@FXML private Button                  btnSubmitAirport;
	private       ObservableList<Airport> airportList;
	private       Airport                 selectedAirport;


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
		try
		{
			final Airport newAirport = new Airport(airportList.size(), txtAirportName.getText());
			for (Airport a : airportList)
			{
				if (a.equals(selectedAirport))
				{
					airportList.set(airportList.indexOf(a), newAirport);
				}
			}
			txtAirportName.setText(""); //Clears textbox
			Notifications.create().title("Airport updated").text("Airport " + txtAirportName.getText() + " updated").showWarning();
			mainApp.toggleUpdateAirport(selectedAirport);
		}
		catch (IllegalArgumentException e)
		{
			final Alert alert = new Alert(AlertType.ERROR, "Airport name cannot be blank.");
			alert.showAndWait();
		}
	}


	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp The main application
	 */
	public final void setMainApp(Main mainApp)
	{
		this.mainApp = mainApp;
	}


	public final void linkToSession()
	{
		airportList = mainApp.getAirportList();
	}


	public void updateSelectedAirport(Airport airport)
	{
		selectedAirport = airport;
	}
}

package application.controller;

import application.Main;
import application.model.Airport;
import application.model.Obstacle;
import application.model.Runway;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddObjectController
{

	@FXML private Label                   lblAirportName;
	@FXML private Label                   lblRunwayID;
	@FXML private TextField               txtObjectName;
	@FXML private ComboBox                cmbCloserTo;
	@FXML private TextField               txtObjectHeight;
	@FXML private TextField               txtObjectDistFromThreshold;
	@FXML private TextField               txtObjectDistFromCentre;
	@FXML private Button                  btnObjectSubmit;
	private       Main                    mainApp;
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
	private void handleObstacleSubmitted()
	{
		final String obstacleName = txtObjectName.getText();
		final double objectHeight = Double.parseDouble(txtObjectHeight.getText());
		final double objectPosition = Double.parseDouble(txtObjectDistFromThreshold.getText());

		final Obstacle obstacle = new Obstacle(obstacleName, objectHeight, objectPosition, 300.0);

		for (final Airport a : airportList)
		{
			if (a.getAirportName().equals(lblAirportName.getText()))
			{
				for (final Runway r : a.getRunways())
				{
					if (r.getAlignment().equals(lblRunwayID.getText()))
					{
						r.addObstacle(obstacle);
						break;
					}
				}
				break;
			}
		}
		mainApp.toggleAddObject(lblAirportName.getText(), lblRunwayID.getText());

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


	public final void updateSelectedAirportRunway(final String airportName, final String runwayID)
	{
		lblAirportName.setText(airportName);
		lblRunwayID.setText(runwayID);
	}
}

package application.controller;

import application.Main;
import application.model.Airport;
import application.model.Obstacle;
import application.model.Runway;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Objects;

public class AddObjectController
{

	@FXML private Label     lblAirportName;
	@FXML private Label     lblRunwayID;
	@FXML private TextField txtPrimObjectName;
	@FXML private TextField txtPrimObjectHeight;
	@FXML private TextField txtPrimObjectDistFromThreshold;
	@FXML private TextField txtPrimObjectDistFromCentre;

	@FXML private Label     lblSecondaryRunwayID;
	@FXML private Label     lblSecObjectName;
	@FXML private Label     lblSecObjectHeight;
	@FXML private TextField txtSecObjectDistFromThreshold;
	@FXML private Label     lblSecObjectDistFromCentre;

	@FXML private Button btnObjectSubmit;

	private Main                    mainApp;
	private ObservableList<Airport> airportList;


	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize()
	{
		txtPrimObjectName.textProperty().addListener((observable, oldValue, newValue) -> {
			lblSecObjectName.setText(newValue);
		});
		txtPrimObjectHeight.textProperty().addListener((observable, oldValue, newValue) -> {
			lblSecObjectHeight.setText(newValue);
		});
		txtPrimObjectName.textProperty().addListener((observable, oldValue, newValue) -> {
			lblSecObjectName.setText(newValue);
		});
	}


	@FXML
	private void handleObstacleSubmitted()
	{
		final String obstacleName = txtPrimObjectName.getText();
		final double objectHeight = Double.parseDouble(txtPrimObjectHeight.getText());
		final double objectPrimaryPosition = Double.parseDouble(txtPrimObjectDistFromThreshold.getText());
		final double objectSecondaryPosition = Double.parseDouble(txtSecObjectDistFromThreshold.getText());

		final Obstacle primaryObstacle = new Obstacle(obstacleName, objectHeight, objectPrimaryPosition, 300.0);
		final Obstacle secondaryObstacle = new Obstacle(obstacleName, objectHeight, objectSecondaryPosition, 300.0);

		for (final Airport a : airportList)
		{
			if (a.getAirportName().equals(lblAirportName.getText()))
			{
				for (final Runway r : a.getRunways())
				{
					if (r.getAlignment().equals(lblRunwayID.getText()))
					{
						r.addObstacle(primaryObstacle);
					}
					if (r.getAlignment().equals(lblSecondaryRunwayID.getText()))
					{
						r.addObstacle(secondaryObstacle);
					}
				}
			}
		}
		mainApp.toggleAddObject(lblAirportName.getText(), lblRunwayID.getText());

	}


	@FXML
	private void handleNameChanged()
	{
		lblSecObjectName.setText(txtPrimObjectName.getText());
	}


	@FXML
	private void handleHeightChanged()
	{
		lblSecObjectHeight.setText(txtPrimObjectHeight.getText());
	}


	@FXML
	private void handleDistCentreChanged()
	{
		lblSecObjectDistFromCentre.setText(txtPrimObjectDistFromCentre.getText());
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
		String primaryID = runwayID.replaceAll("\\D+", "");
		String primaryPosition = runwayID.replaceAll("\\d+", "");
		String secondaryPosition = "";
		if (Objects.equals(primaryPosition, "L")) //TODO: Extract these ifelse blocks into a method in Runway
		{
			secondaryPosition = "R";
		}
		else if (Objects.equals(primaryPosition, "C"))
		{
			secondaryPosition = "C";
		}
		else if (Objects.equals(primaryPosition, "R"))
		{
			secondaryPosition = "L";
		}

		Integer secondaryID = AddRunwayController.calculateSecondPosition(Integer.parseInt(primaryID)); //TODO: Move this method to the Runway class
		lblSecondaryRunwayID.setText(String.format("%02d", secondaryID) + secondaryPosition);
	}
}

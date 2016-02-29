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

public class AddObstacleController
{

	@FXML private Label     lblAirportName;
	@FXML private Label     lblRunwayID;
	@FXML private TextField txtPrimObstacleName;
	@FXML private TextField txtPrimObstacleHeight;
	@FXML private TextField txtPrimObstacleDistFromThreshold;
	@FXML private TextField txtPrimObstacleDistFromCentre;

	@FXML private Label     lblSecondaryRunwayID;
	@FXML private Label     lblSecObstacleName;
	@FXML private Label     lblSecObstacleHeight;
	@FXML private TextField txtSecObstacleDistFromThreshold;
	@FXML private Label     lblSecObstacleDistFromCentre;

	@FXML private Button btnObstacleSubmit;

	private Main                    mainApp;
	private ObservableList<Airport> airportList;


	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize()
	{
		txtPrimObstacleName.textProperty().addListener((observable, oldValue, newValue) -> {
			lblSecObstacleName.setText(newValue);
		});
		txtPrimObstacleHeight.textProperty().addListener((observable, oldValue, newValue) -> {
			lblSecObstacleHeight.setText(newValue);
		});
		txtPrimObstacleName.textProperty().addListener((observable, oldValue, newValue) -> {
			lblSecObstacleName.setText(newValue);
		});
	}


	@FXML
	private void handleObstacleSubmitted()
	{
		final String obstacleName = txtPrimObstacleName.getText();
		final double obstacleHeight = Double.parseDouble(txtPrimObstacleHeight.getText());
		final double obstaclePrimaryPosition = Double.parseDouble(txtPrimObstacleDistFromThreshold.getText());
		final double obstacleSecondaryPosition = Double.parseDouble(txtSecObstacleDistFromThreshold.getText());

		final Obstacle primaryObstacle = new Obstacle(obstacleName, obstacleHeight, obstaclePrimaryPosition, 300.0);
		final Obstacle secondaryObstacle = new Obstacle(obstacleName, obstacleHeight, obstacleSecondaryPosition, 300.0);

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
		mainApp.toggleAddObstacle(lblAirportName.getText(), lblRunwayID.getText());

	}


	@FXML
	private void handleNameChanged()
	{
		lblSecObstacleName.setText(txtPrimObstacleName.getText());
	}


	@FXML
	private void handleHeightChanged()
	{
		lblSecObstacleHeight.setText(txtPrimObstacleHeight.getText());
	}


	@FXML
	private void handleDistCentreChanged()
	{
		lblSecObstacleDistFromCentre.setText(txtPrimObstacleDistFromCentre.getText());
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
		
		//TODO: Extract these into a method in Runway
		switch (primaryPosition)
		{
		case "L":
			secondaryPosition = "R";
			break;
		case "C:":
			secondaryPosition = "C";
			break;
		case "R":
			secondaryPosition = "L";
		}

		Integer secondaryID = AddRunwayController.calculateSecondPosition(Integer.parseInt(primaryID)); //TODO: Move this method to the Runway class
		lblSecondaryRunwayID.setText(String.format("%02d", secondaryID) + secondaryPosition);
	}
}

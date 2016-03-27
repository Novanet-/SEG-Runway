package application.controller;

import application.Main;
import application.model.Airport;
import application.model.Obstacle;
import application.model.Runway;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
		txtPrimObstacleDistFromCentre.textProperty().addListener((observable, oldValue, newValue) -> {
			lblSecObstacleDistFromCentre.setText(newValue);
		});
	}


	@FXML
	private void handleObstacleSubmitted()
	{
		try
		{
			final String obstacleName = txtPrimObstacleName.getText();
			final double obstacleHeight = Double.parseDouble(txtPrimObstacleHeight.getText());
			final double obstaclePrimaryPosition = Double.parseDouble(txtPrimObstacleDistFromThreshold.getText());
			final double obstacleSecondaryPosition = Double.parseDouble(txtSecObstacleDistFromThreshold.getText());
			final double obstacleCentrePosition = Double.parseDouble(txtPrimObstacleDistFromCentre.getText());

			final Obstacle primaryObstacle = new Obstacle(obstacleName, obstacleHeight, obstaclePrimaryPosition, obstacleCentrePosition, 300.0);
			final Obstacle secondaryObstacle = new Obstacle(obstacleName, obstacleHeight, obstacleSecondaryPosition, obstacleCentrePosition, 300.0);

			airportList.stream().filter(a -> a.getAirportName().equals(lblAirportName.getText())).forEach(a -> {
				for (final Runway r : a.getRunways())
				{
					if (r.getAlignment().equals(lblRunwayID.getText()))
					{
						r.setObstacle(primaryObstacle);
					}
					if (r.getAlignment().equals(lblSecondaryRunwayID.getText()))
					{
						r.setObstacle(secondaryObstacle);
					}
				}
			});
			mainApp.toggleAddObstacle(lblAirportName.getText(), lblRunwayID.getText());
		}
		catch (NumberFormatException e)
		{
			final Alert alert = new Alert(AlertType.ERROR, "Please enter only numerical values.");
			alert.showAndWait();
		}
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


	public void updateObstacleParameters(Obstacle obstacle)
	{
		txtPrimObstacleName.setText(obstacle.getName());
		txtPrimObstacleHeight.setText(String.valueOf(obstacle.getHeight()));
		txtPrimObstacleDistFromThreshold.setText(String.valueOf(obstacle.getDisplacementPosition()));
		txtPrimObstacleDistFromCentre.setText(String.valueOf(obstacle.getCentrePosition()));

		//TODO: deal with secondary runway
		txtSecObstacleDistFromThreshold.setText("");
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


	public final void updateSelectedAirportRunway(final String airportName, final String runwayID)
	{
		lblAirportName.setText(airportName);
		lblRunwayID.setText(runwayID);
		String primaryAlignment = runwayID.replaceAll("\\D+", "");
		String primaryPosition = runwayID.replaceAll("\\d+", "");

		final String secondaryPosition = Runway.calculateSecondaryPosition(primaryPosition);

		Integer secondaryAlignment = Runway.calculateSecondaryAlignment(Integer.parseInt(primaryAlignment));
		lblSecondaryRunwayID.setText(String.format("%02d", secondaryAlignment) + secondaryPosition);
	}

}

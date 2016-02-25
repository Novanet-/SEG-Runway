package application.view;

import application.Main;
import application.model.Airport;
import application.model.Obstacle;
import application.model.Runway;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Created by jackclarke on 23/02/2016.
 */
public class AddObjectController
{

	@FXML   Label                   lblAirportName;
	@FXML   Label                   lblRunwayID;
	@FXML   TextField               txtObjectName;
	@FXML   ComboBox                cmbCloserTo;
	@FXML   TextField               txtObjectHeight;
	@FXML   TextField               txtObjectDistFromThreshold;
	@FXML   TextField               txtObjectDistFromCentre;
	@FXML   Button                  btnObjectSubmit;
	private Main                    mainApp;
	private ObservableList<Airport> airportList;


	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize()
	{

	}


	private final void handleObstacleSubmitted()
	{
		StringProperty obstacleName = new SimpleStringProperty(txtObjectName.getText());
		DoubleProperty objectHeight = new SimpleDoubleProperty(Double.parseDouble(txtObjectHeight.getText()));
		DoubleProperty objectPosition = new SimpleDoubleProperty(Double.parseDouble(txtObjectDistFromThreshold.getText()));

		Obstacle obstacle = new Obstacle(obstacleName, objectHeight, objectPosition, new SimpleDoubleProperty(300.0));

		for (Airport a : airportList)
		{
			if (a.getAirportName().equals(lblAirportName.getText()))
			{
				for (Runway r : a.getRunways())
				{
					if (r.getAlignment().equals(lblRunwayID))
					{
						r.addObstacle(obstacle);
						break;
					}
				}
				break;
			}
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


	public void updateSelectedAirportRunway(final String airportName, final String runwayID)
	{
		lblAirportName.setText(airportName);
		lblRunwayID.setText(runwayID);
	}
}

package application.controller;

import application.Main;
import application.model.Airport;
import application.model.Redeclaration;
import application.model.Runway;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class MainScreenController
{

	/**
	 * Used to prevent adding a runway without
	 * an airport selected.
	 */
	boolean airportSelected;
	private       ObservableList<Airport> airportList;
	@FXML private MenuItem                btnAddAirport;
	@FXML private MenuItem  btnAddRunway;
	@FXML private ComboBox  cmbAirports;
	@FXML private ComboBox  cmbRunways;
	@FXML private TextField cmbObjects;
	@FXML private Label     lblOrigTora;
	@FXML private Label     lblOrigToda;
	@FXML private Label     lblOrigAsda;
	@FXML private Label     lblOrigLda;
	@FXML private Label     lblOrigDisplacedThreshold;
	@FXML private Label     lblRecalcTora;
	@FXML private Label     lblRecalcToda;
	@FXML private Label     lblRecalcAsda;
	@FXML private Label     lblRecalcLda;
	@FXML private Label     lblRecalcDisplacedThreshold;
	@FXML private Label     lblResa;
	@FXML private Label     lblStopway;
	@FXML private Label     lblBlastProtection;
	@FXML private Label     lblAngleOfSlope;
	@FXML private Label     lblStripWidth;
	@FXML private Label     lblCAndGWidth;
	@FXML private Button    btnAddObstacle;
	@FXML private Button    btnRemoveObstacle;

	// Reference to the main application.
	private Main mainApp;


	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize()
	{
		updateRunwayDetails();
	}


	/**
	 *
	 */
	private void updateCmbAirports()
	{
		cmbAirports.setItems(airportList);
	}


	/**
	 *
	 */
	@FXML
	private void handleBtnAddAirport()
	{
		openAddAirport();
	}


	/**
	 *
	 */
	@FXML
	private void handleBtnAddRunway()
	{
		if (airportSelected)
		{
			openAddRunway();
		}
		else
		{
			Alert alert = new Alert(AlertType.INFORMATION, "No airport selected. Please select an airport.");
			alert.showAndWait();
		}
	}


	/**
	 *
	 */
	@FXML
	private void handleBtnAddObstacle()
	{
		if (cmbRunways.getValue() != null)
		{
			if (((Runway) cmbRunways.getValue()).getObstacles().size() > 0)
			{
				Alert alert = new Alert(AlertType.INFORMATION, "Obstacle already exists. please remove before adding another");
				alert.showAndWait();
			}
			else
			{
				openAddObject();
			}
		}
		else
		{
			Alert alert = new Alert(AlertType.INFORMATION, "No runway selected. Please select an airport.");
			alert.showAndWait();
		}
	}


	@FXML
	private void handleBtnRemoveObstacle()
	{
		if (cmbRunways.getValue() != null)
		{
			if (((Runway) cmbRunways.getValue()).getObstacles().size() > 0)
			{
				((Runway) cmbRunways.getValue()).getObstacles().remove(0);
				updateObstacleList();
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION, "No obstacle to remove");
				alert.showAndWait();
			}
		}
		else
		{
			Alert alert = new Alert(AlertType.INFORMATION, "No runway selected. Please select an airport.");
			alert.showAndWait();
		}
	}

	@FXML
	private void handleBtnClose()
	{
		mainApp.stop();
	}


	@FXML
	private void handleBtnAbout()
	{
		String message = "Authors:\nJack Clarke\n" + "Maksim Romanovich\nJames Littlefair\n" + "William Davies\nDaniel Oakey";
		Alert alert = new Alert(AlertType.INFORMATION, message);
		alert.showAndWait();
	}


	@FXML
	private void handleAirportSelected()
	{
		airportSelected = cmbAirports.getValue() != null;
		cmbRunways.setItems(((Airport) cmbAirports.getValue()).getRunways());
	}


	@FXML
	private void handleRunwaySelected()
	{
		updateOriginalParameters();
		final Runway currentRunway = (Runway) cmbRunways.getValue();
		Runway newRunway = Redeclaration.redeclareParameters(currentRunway);
		if (!currentRunway.getObstacles().isEmpty())
		{
			updateObstacleList();
		}
		updateNewParameters(newRunway);
	}


	@FXML
	private void handleObjectSelected()

	{
		final Runway currentRunway = (Runway) cmbRunways.getValue();
		updateNewParameters(currentRunway);
	}


	private void updateNewParameters(Runway newRunway)
	{
		lblRecalcTora.setText(Double.toString(newRunway.getTORA()));
		lblRecalcToda.setText(Double.toString(newRunway.getTODA()));
		lblRecalcAsda.setText(Double.toString(newRunway.getASDA()));
		lblRecalcLda.setText(Double.toString(newRunway.getLDA()));
		lblRecalcDisplacedThreshold.setText(Double.toString(newRunway.getDisplacedThreshold()));
	}


	private void updateRunwayDetails()
	{
		lblStopway.setText(Double.toString(Redeclaration.getStopway()));
		lblBlastProtection.setText(Double.toString(Redeclaration.getBlastProtection()));
		lblResa.setText(Double.toString(Redeclaration.getResa()));
	}


	public void updateObstacleList()
	{
		if (((Runway) cmbRunways.getValue()).getObstacles().size() > 0)
		{
			cmbObjects.setText(((Runway) cmbRunways.getValue()).getObstacles().get(0).toString());
		}
		else
		{
			cmbObjects.setText("");
		}
	}


	/**
	 *
	 */
	@FXML
	private void openAddAirport()
	{
		mainApp.toggleAddAirport();
	}


	/**
	 *
	 */
	@FXML
	private void openAddRunway()
	{
		mainApp.toggleAddRunway(((Airport) cmbAirports.getValue()).getAirportName());
	}


	/**
	 *
	 */
	@FXML
	private void openAddObject()
	{
		mainApp.toggleAddObject(((Airport) cmbAirports.getValue()).getAirportName(), ((Runway) cmbRunways.getValue()).getAlignment());
	}


	/**
	 *
	 */
	private void updateOriginalParameters()
	{
		Runway currentRunway = (Runway) cmbRunways.getValue();
		lblOrigTora.setText(Double.toString(currentRunway.getTORA()));
		lblOrigToda.setText(Double.toString(currentRunway.getTODA()));
		lblOrigAsda.setText(Double.toString(currentRunway.getASDA()));
		lblOrigLda.setText(Double.toString(currentRunway.getLDA()));
		lblOrigDisplacedThreshold.setText(Double.toString(currentRunway.getDisplacedThreshold()));
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


	/**
	 *
	 */
	public final void linkToSession()
	{
		airportList = mainApp.getAirportList();
		updateCmbAirports();
		airportList.addListener((ListChangeListener) change -> {
			updateCmbAirports();
		});
	}
}

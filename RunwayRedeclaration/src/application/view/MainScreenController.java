package application.view;

import application.Main;
import application.model.Airport;
import application.model.Redeclaration;
import application.model.Runway;
import application.model.RunwayParameters;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class MainScreenController
{

	private ObservableList<Airport> airportList;

	@FXML private MenuItem  btnAddAirport;
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

	// Reference to the main application.
	private Main mainApp;
	
	/**
	 * Used to prevent adding a runway without
	 * an airport selected.
	 */
	boolean airportSelected = false;


	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize()
	{
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
		if (airportSelected) {
			openAddRunway();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION, "No airport selected. Please select an airport.");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void handleBtnClose() {
		mainApp.stop();
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
		Runway newRunway = Redeclaration.redeclareParameters((Runway) cmbRunways.getValue());
		updateNewParameters(newRunway);
	}


	private void updateNewParameters(Runway newRunway)
	{
		RunwayParameters newRunwayParameters = newRunway.getRunwayParameters();
		lblRecalcTora.setText(Double.toString(newRunwayParameters.getTORA()));
		lblRecalcToda.setText(Double.toString(newRunwayParameters.getTODA()));
		lblRecalcAsda.setText(Double.toString(newRunwayParameters.getASDA()));
		lblRecalcLda.setText(Double.toString(newRunwayParameters.getLDA()));
		lblRecalcDisplacedThreshold.setText(Double.toString(newRunwayParameters.getDisplacedThreshold()));
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
		mainApp.toggleAddObject();
	}


	/**
	 *
	 */
	private void updateOriginalParameters()
	{
		Runway currentRunway = (Runway) cmbRunways.getValue();
		lblOrigTora.setText(Double.toString(currentRunway.getRunwayParameters().getTORA()));
		lblOrigToda.setText(Double.toString(currentRunway.getRunwayParameters().getTODA()));
		lblOrigAsda.setText(Double.toString(currentRunway.getRunwayParameters().getASDA()));
		lblOrigLda.setText(Double.toString(currentRunway.getRunwayParameters().getLDA()));
		lblOrigDisplacedThreshold.setText(Double.toString(currentRunway.getRunwayParameters().getDisplacedThreshold()));
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

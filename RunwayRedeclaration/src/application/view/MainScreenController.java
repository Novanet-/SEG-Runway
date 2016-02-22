package application.view;

import application.Main;
import application.model.Airport;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
	@FXML private Label     lblRecalToda;
	@FXML private Label     lblRecalAsda;
	@FXML private Label     lblRecalLda;
	@FXML private Label     lblRecalDisplacedThreshold;
	@FXML private Label     lblResa;
	@FXML private Label     lblStopway;
	@FXML private Label     lblBlastProtection;
	@FXML private Label     lblAngleOfSlope;
	@FXML private Label     lblStripWidth;
	@FXML private Label     lblCAndGWidth;

	// Reference to the main application.
	private Main mainApp;


	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize()
	{
	}


	private void updateCmbAirports()
	{
		cmbAirports.setItems(airportList);
	}


	@FXML
	private void handleBtnAddAirport()
	{
		openAddAirport();

	}
	
	@FXML
	private void handleBtnAddRunway() {
		openAddRunway();
	}


	@FXML
	private void openAddAirport()
	{
		mainApp.toggleAddAirport();
	}
	
	@FXML
	private void openAddRunway() {
		mainApp.toggleAddRunway();
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
		updateCmbAirports();
		airportList.addListener((ListChangeListener) change -> {
			updateCmbAirports();
		});
	}
}



package application.view;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class MainScreenController
{

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
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public MainScreenController()
	{
	}


	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML private void initialize()
	{

	}


	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(Main mainApp)
	{
		this.mainApp = mainApp;

		/*cmbAirports.setItems(mainApp.getAirports());
		cmbRunways.setItems(mainApp.getAirports().getRunways);
		cmbAirports.setItems(mainApp.getAirports().getRunways.getObjects);*/
	}
}



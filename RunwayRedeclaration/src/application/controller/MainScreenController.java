package application.controller;

import application.Main;
import application.model.Airport;
import application.model.Runway;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class MainScreenController
{

	/**
	 * Used to prevent adding a runway without
	 * an airport selected.
	 */
	private       boolean                 airportSelected;
	private       ObservableList<Airport> airportList;
	@FXML private MenuItem                btnAddAirport;
	@FXML private MenuItem                btnAddRunway;
	@FXML private ComboBox<Airport>       cmbAirports;
	@FXML private ComboBox<Runway>        cmbRunways;
	@FXML private TextField               txtObstacles;
	@FXML private Label                   lblOrigTora;
	@FXML private Label                   lblOrigToda;
	@FXML private Label                   lblOrigAsda;
	@FXML private Label                   lblOrigLda;
	@FXML private Label                   lblOrigDisplacedThreshold;
	@FXML private Label                   lblRecalcTora;
	@FXML private Label                   lblRecalcToda;
	@FXML private Label                   lblRecalcAsda;
	@FXML private Label                   lblRecalcLda;
	@FXML private Label                   lblRecalcDisplacedThreshold;
	@FXML private Label                   lblResa;
	@FXML private Label                   lblStopway;
	@FXML private Label                   lblBlastProtection;
	@FXML private Label                   lblAngleOfSlope;
	@FXML private Label                   lblStripWidth;
	@FXML private Label                   lblCAndGWidth;
	@FXML private Button                  btnAddObstacle;
	@FXML private Button                  btnRemoveObstacle;

	@FXML private Canvas				  topDownCanvas; //870x345

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
		btnRemoveObstacle.setVisible(false);
		btnRemoveObstacle.setManaged(false);

		GraphicsContext graphicsContext = topDownCanvas.getGraphicsContext2D();
		paintTopDown(graphicsContext);
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
			final Alert alert = new Alert(AlertType.INFORMATION, "No airport selected. Please select an airport.");
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
			if (cmbRunways.getValue().getObstacle() != null)
			{
				final Alert alert = new Alert(AlertType.INFORMATION, "Obstacle already exists. please remove before adding another");
				alert.showAndWait();
			}
			else
			{
				openAddObstacle();
			}
		}
		else
		{
			final Alert alert = new Alert(AlertType.INFORMATION, "No runway selected. Please select an airport.");
			alert.showAndWait();
		}
	}

	//TODO: When obstacle is removed from one logical runway, it should remove it from both logical runways

	//TODO: Change add airport/runway buttons to + and - buttons as per obstacle for consistency and to allow for removal or airports and runways



	@FXML
	private void handleBtnRemoveObstacle()
	{
		if (cmbRunways.getValue() != null)
		{
			if (cmbRunways.getValue().getObstacle() != null)
			{
				cmbRunways.getValue().removeObstacle();
				updateObstacleList();
			}
			else
			{
				final Alert alert = new Alert(AlertType.INFORMATION, "No obstacle to remove");
				alert.showAndWait();
			}
		}
		else
		{
			final Alert alert = new Alert(AlertType.INFORMATION, "No runway selected. Please select an airport.");
			alert.showAndWait();
		}
	}

	/**
	 * Swaps add/remove obstacle button so only 1 shows at a time
	 */
	private void toggleObstacleButton() {
		if(btnRemoveObstacle.isVisible()) {
			btnRemoveObstacle.setVisible(false);
			btnRemoveObstacle.setManaged(false);
			btnAddObstacle.setVisible(true);
			btnAddObstacle.setManaged(true);
		} else if(btnAddObstacle.isVisible()) {
			btnRemoveObstacle.setVisible(true);
			btnRemoveObstacle.setManaged(true);
			btnAddObstacle.setVisible(false);
			btnAddObstacle.setManaged(false);
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
		final String message = "Authors:\nJack Clarke\n" + "Maksim Romanovich\nJames Littlefair\n" + "William Davies\nDaniel Oakey";
		final Alert alert = new Alert(AlertType.INFORMATION, message);
		alert.showAndWait();
	}


	@FXML
	private void handleAirportSelected()
	{
		airportSelected = cmbAirports.getValue() != null;
		cmbRunways.setValue(null);
		cmbRunways.setItems(FXCollections.observableArrayList(cmbAirports.getValue().getRunways()));
		txtObstacles.setText("");

	}


	@FXML
	private void handleRunwaySelected()
	{
		updateOriginalParameters();
		Runway newRunway = null;
		if (cmbRunways.getValue() != null)
		{
			updateObstacleList();
			final Runway currentRunway = cmbRunways.getValue();
			if (currentRunway.getObstacle() != null)
			{
				newRunway = currentRunway.redeclare();
			}
		}
		updateNewParameters(newRunway);
	}


	@FXML
	private void handleObjectSelected()

	{
		final Runway currentRunway = cmbRunways.getValue();
		updateNewParameters(currentRunway);
	}


	private void updateNewParameters(Runway newRunway)
	{
		if (newRunway != null)
		{
			lblRecalcTora.setText(Double.toString(newRunway.getTORA()));
			lblRecalcToda.setText(Double.toString(newRunway.getTODA()));
			lblRecalcAsda.setText(Double.toString(newRunway.getASDA()));
			lblRecalcLda.setText(Double.toString(newRunway.getLDA()));
			lblRecalcDisplacedThreshold.setText(Double.toString(newRunway.getDisplacedThreshold()));
		}
		else
		{
			lblRecalcTora.setText("");
			lblRecalcToda.setText("");
			lblRecalcAsda.setText("");
			lblRecalcLda.setText("");
			lblRecalcDisplacedThreshold.setText("");
		}
	}


	private void updateRunwayDetails()
	{
		lblStopway.setText(Double.toString(Runway.getStopway()));
		lblBlastProtection.setText(Double.toString(Runway.getBlastProtection()));
		lblResa.setText(Double.toString(Runway.getResa()));
	}


	public final void updateObstacleList()
	{
		if (cmbRunways.getValue().getObstacle() != null)
		{
			txtObstacles.setText(cmbRunways.getValue().getObstacle().toString());
		}
		else
		{
			txtObstacles.setText("");
		}
	}


	/**
	 *
	 */
	private void updateOriginalParameters()
	{
		if (cmbRunways.getValue() != null)
		{
			final Runway currentRunway = cmbRunways.getValue();
			lblOrigTora.setText(Double.toString(currentRunway.getTORA()));
			lblOrigToda.setText(Double.toString(currentRunway.getTODA()));
			lblOrigAsda.setText(Double.toString(currentRunway.getASDA()));
			lblOrigLda.setText(Double.toString(currentRunway.getLDA()));
			lblOrigDisplacedThreshold.setText(Double.toString(currentRunway.getDisplacedThreshold()));
		}
		else
		{
			lblOrigTora.setText("");
			lblOrigToda.setText("");
			lblOrigAsda.setText("");
			lblOrigLda.setText("");
			lblOrigDisplacedThreshold.setText("");
		}
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
		mainApp.toggleAddRunway(cmbAirports.getValue().getAirportName());
	}


	/**
	 *
	 */
	@FXML
	private void openAddObstacle()
	{
		mainApp.toggleAddObstacle(cmbAirports.getValue().getAirportName(), cmbRunways.getValue().getAlignment());
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

	/**
	 * Generates the top-down visualisation of the Runway
	 * @param graphicsContext
	 */
	public void paintTopDown(GraphicsContext graphicsContext) {
		double tora = 3902.0; //2986.0;
		double toda = 3902.0; //2986.0;
		double asda = 3902.0; //2986.0;
		double lda = 3595.0; //3346.0;
		double displacedThreshold = 306.0;
		double stripWidth = 150.0;

		graphicsContext.setFill(Color.rgb(77,77,77)); //Runway colour
		graphicsContext.fillRect(100, 122, 620, 100);

		//Draw lines
		graphicsContext.setStroke(Color.WHITE);
		graphicsContext.setLineWidth(5);
		graphicsContext.strokeLine(130, 172, 160, 172);
		graphicsContext.strokeLine(190, 172, 220, 172);
		graphicsContext.strokeLine(250, 172, 280, 172);
		graphicsContext.strokeLine(310, 172, 340, 172);
		graphicsContext.strokeLine(370, 172, 400, 172);
		graphicsContext.strokeLine(430, 172, 460, 172);
		graphicsContext.strokeLine(490, 172, 520, 172);
		graphicsContext.strokeLine(550, 172, 580, 172);
		graphicsContext.strokeLine(610, 172, 640, 172);
		graphicsContext.strokeLine(670, 172, 700, 172);

		//Calculate TORA, TODA, ASDA, LDA
		double pixelRatio = 620.0/tora;
		int toraPixel = 620;
		int todaPixel = (int) Math.round(toda * pixelRatio);
		int asdaPixel = (int) Math.round(asda * pixelRatio);
		int ldaPixel = (int) Math.round(lda * pixelRatio);
		int displacedThresholdPixel = (int) Math.round(displacedThreshold * pixelRatio);
		int stripWidthPixel = 345;

		System.out.println(pixelRatio);

		//draw TORA
		//graphicsContext.setStroke(Color.rgb(255,138,138));
		//graphicsContext.strokeLine(100, 210, 100+toraPixel, 210);
		graphicsContext.setFill(Color.rgb(255,138,138));
		graphicsContext.fillRect(100, 105, toraPixel, 5);

		//draw TODA
		//graphicsContext.setStroke(Color.rgb(255,190,50));
		//graphicsContext.strokeLine(100, 215, 100+todaPixel, 215);
		graphicsContext.setFill(Color.rgb(255,190,50));
		graphicsContext.fillRect(100, 110, todaPixel, 5);

		//draw ASDA
		//graphicsContext.setStroke(Color.rgb(255,240,40));
		//graphicsContext.strokeLine(100, 220, 100+asdaPixel, 220);
		graphicsContext.setFill(Color.rgb(255,240,40));
		graphicsContext.fillRect(100, 115, asdaPixel, 5);

		//draw LDA
		//graphicsContext.setStroke(Color.rgb(180,225,35));
		//graphicsContext.strokeLine(100 + (toraPixel-ldaPixel), 225, 100+toraPixel, 225);
		graphicsContext.setFill(Color.rgb(180,225,35));
		graphicsContext.fillRect(100 + (toraPixel-ldaPixel), 225, ldaPixel, 5);

		//draw Displaced Threshold
		//graphicsContext.setStroke(Color.rgb(150,210,255));
		//graphicsContext.strokeLine(100, 230, 100+displacedThresholdPixel, 230);
		graphicsContext.setFill(Color.rgb(150,210,255));
		graphicsContext.fillRect(100, 230, displacedThresholdPixel, 5);
	}
}

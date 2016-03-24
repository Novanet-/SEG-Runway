package application.controller;

import application.Main;
import application.model.Airport;
import application.model.Obstacle;
import application.model.Runway;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Objects;

public class MainScreenController
{

	private static final double SCALING = 0.6;
	private static final double RUNWAY_START_X_SCALING = 0.2;
	private static final double RUNWAY_START_Y_SCALING = 0.4;
	private static final double RUNWAY_HEIGHT_SCALING  = 0.14;
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
	@FXML private MenuItem				  btnImportAirport;
	@FXML private MenuItem				  btnImportRunway;
	@FXML private MenuItem				  btnImportObstacle;
	@FXML private TextArea                txtCalculations;

	@FXML
	private Canvas cnvTop; //875x345
	@FXML
	private Canvas cnvSide; //875x345

	//TODO: Make program save and restore current state

	// Used for XML handling
	private DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	// Reference to the main application.
	private Main mainApp;
	private ImportController importController = new ImportController();
	private ExportController exportController = new ExportController();


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

		paintVisualisation();
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
				if (btnAddObstacle.isVisible())
				{
					toggleObstacleButton();
				}
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


	@FXML
	private void handleBtnRemoveObstacle()
	{
		final Runway currentRunway = cmbRunways.getValue();
		if (currentRunway != null)
		{
			if (currentRunway.getObstacle() != null)
			{
				String secondaryRunwayID = getSecondaryRunwayID(currentRunway);

				Runway secondaryRunway = null;
				try
				{
					secondaryRunway = getRequestedRunway(secondaryRunwayID);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				currentRunway.removeObstacle();
				if (secondaryRunway != null)
				{
					secondaryRunway.removeObstacle();
				}
				else
				{
					System.out.println("MainScreenController.handleBtnRemoveObstacle");
					System.out.println("secondaryRunway = " + null);
				}
				updateObstacleList();
				updateOriginalParameters();
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


	private String getSecondaryRunwayID(final Runway currentRunway)
	{
		final int primaryAlignment = Integer.parseInt(currentRunway.getAlignment().replaceAll("\\D+", ""));
		final String primaryPosition = currentRunway.getAlignment().replaceAll("\\d+", "");
		return String.format("%02d", Runway.calculateSecondaryAlignment(primaryAlignment)) + Runway.calculateSecondaryPosition(primaryPosition);
	}


	private Runway getRequestedRunway(final String secondaryRunwayID) throws Exception
	{
		for (final Airport currentAirport : mainApp.getAirportList())
		{
			for (final Runway currRunway : currentAirport.getRunways())
			{
				if (Objects.equals(currRunway.getAlignment(), secondaryRunwayID))
				{
					return currRunway;
				}
			}
		}
		throw new Exception("Runway not found");
	}


	/**
	 * Swaps add/remove obstacle button so only 1 shows at a time
	 */
	private void toggleObstacleButton()
	{
		if (btnRemoveObstacle.isVisible())
		{
			btnRemoveObstacle.setVisible(false);
			btnRemoveObstacle.setManaged(false);
			btnAddObstacle.setVisible(true);
			btnAddObstacle.setManaged(true);
		}
		else if (btnAddObstacle.isVisible())
		{
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
		try
		{
			updateRunwayList();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		txtObstacles.setText("");

	}


	public void updateRunwayList() throws Exception
	{
		if (cmbAirports.getValue() != null)
		{
			cmbRunways.setValue(null);
			cmbRunways.setItems(FXCollections.observableArrayList(cmbAirports.getValue().getRunways()));
		}
		else
			throw new Exception("No airport selected");
	}


	@FXML
	private void handleRunwaySelected()
	{
		txtObstacles.setText("");
		updateOriginalParameters();
		Runway newRunway = null;
		updateObstacleList();
		if (cmbRunways.getValue() != null)
		{
			//			updateObstacleList();
			final Runway currentRunway = cmbRunways.getValue();
			if (currentRunway.getObstacle() != null)
			{
				newRunway = currentRunway.redeclare();
				txtCalculations.setText(newRunway.getExplanation());
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
		paintVisualisation();
	}


	private void updateRunwayDetails()
	{
		lblStopway.setText(Double.toString(Runway.getStopway()));
		lblBlastProtection.setText(Double.toString(Runway.getBlastProtection()));
		lblResa.setText(Double.toString(Runway.getResa()));
		lblAngleOfSlope.setText(Double.toString(Runway.getAngleOfSlope()));
		lblStripWidth.setText(Double.toString(Runway.getStripWidth()));
		lblCAndGWidth.setText(Double.toString(Runway.getCagWidth()));
	}


	public final void updateObstacleList()
	{

		if (cmbRunways.getValue() != null)
		{
			final Runway currentRunway = cmbRunways.getValue();
			if (currentRunway.getObstacle() != null)
			{
				txtObstacles.setText(currentRunway.getObstacle().getName());
				final Runway newRunway = currentRunway.redeclare();
				updateNewParameters(newRunway);
				txtCalculations.setText(newRunway.getExplanation());
				paintVisualisation();
			}
			else
			{
				txtObstacles.setText("");
			}

			if (!Objects.equals(txtObstacles.getText(), "") && !btnRemoveObstacle.isVisible())
			{
				toggleObstacleButton();
			}
			else if (Objects.equals(txtObstacles.getText(), "") && !btnAddObstacle.isVisible())
			{
				toggleObstacleButton();
			}
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
			lblRecalcTora.setText(Double.toString(currentRunway.getTORA()));
			lblRecalcToda.setText(Double.toString(currentRunway.getTODA()));
			lblRecalcAsda.setText(Double.toString(currentRunway.getASDA()));
			lblRecalcLda.setText(Double.toString(currentRunway.getLDA()));
			lblRecalcDisplacedThreshold.setText(Double.toString(currentRunway.getDisplacedThreshold()));
		}
		else
		{
			lblOrigTora.setText("");
			lblOrigToda.setText("");
			lblOrigAsda.setText("");
			lblOrigLda.setText("");
			lblOrigDisplacedThreshold.setText("");
			lblRecalcTora.setText("");
			lblRecalcToda.setText("");
			lblRecalcAsda.setText("");
			lblRecalcLda.setText("");
			lblRecalcDisplacedThreshold.setText("");
		}
		paintVisualisation();
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
	 * @param mainApp The main application
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
		airportList.addListener((ListChangeListener<Airport>) change -> updateCmbAirports());
	}


	private void paintVisualisation()
	{
		paintTopDown(cnvTop.getGraphicsContext2D());
		paintSideOn(cnvSide.getGraphicsContext2D());
	}


	/**
	 * Generates the top-down visualisation of the Runway
	 *
	 * @param graphicsContext The graphics context to paint to
	 */
	private void paintTopDown(GraphicsContext graphicsContext)
	{
		graphicsContext.clearRect(0, 0, 870, 345); //Clears canvas for new runway
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.fill();

		drawTopRunwaySurface(graphicsContext);
		drawRunwayStripLines(graphicsContext);
		drawParameterLines(graphicsContext);
		drawObstacle(graphicsContext);
		drawAdditionalComponents(graphicsContext);
	}


	/**
	 * Generates the top-down visualisation of the Runway
	 *
	 * @param graphicsContext The graphics content to paint to
	 */
	private void paintSideOn(GraphicsContext graphicsContext)
	{
		graphicsContext.clearRect(0, 0, 870, 345); //Clears canvas for new runway
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.fill();

		drawSideRunwaySurface(graphicsContext);
		drawParameterLines(graphicsContext);
		drawObstacleSide(graphicsContext);
		drawAdditionalComponents(graphicsContext);
	}


	private void drawObstacle(final GraphicsContext graphicsContext)
	{
		//TODO: Implement obstacle distance from centre line in visualisation
		//TODO: Implement obstacle height in visualisation
		//
		if (!Objects.equals(txtObstacles.getText(), ""))
		{
			Canvas canvas = graphicsContext.getCanvas();
			Obstacle obstacle = cmbRunways.getValue().getObstacle();

			final double pixelRatio = (SCALING * canvas.getWidth()) / Double.parseDouble(lblOrigTora.getText());

			graphicsContext.setFill(Color.rgb(179, 45, 0)); //Background colour
			final double centreAdjustmentX = (obstacle.getDisplacementPosition() < 0) ? -40 : 20;
			final double centreAdjustmentY = 5.0;
			final double runwayStartX = RUNWAY_START_X_SCALING * canvas.getWidth();
			final double runwayStartY = RUNWAY_START_Y_SCALING * canvas.getHeight();
			graphicsContext.fillRect(runwayStartX + (obstacle.getDisplacementPosition() * pixelRatio) + centreAdjustmentX, runwayStartY + centreAdjustmentY, 40, 40);
		}
	}

	private void drawObstacleSide(final GraphicsContext graphicsContext) {
		if (!Objects.equals(txtObstacles.getText(), "")) {
			Canvas canvas = graphicsContext.getCanvas();
			Obstacle obstacle = cmbRunways.getValue().getObstacle();

			final double pixelRatio = (SCALING * canvas.getWidth()) / Double.parseDouble(lblOrigTora.getText());

			graphicsContext.setFill(Color.rgb(179, 45, 0)); //Background colour
			final double centreAdjustmentX = (obstacle.getDisplacementPosition() < 0) ? -40 : 20;
			final double centreAdjustmentY = 5.0;
			final double runwayStartX = RUNWAY_START_X_SCALING * canvas.getWidth();
			final double runwayStartY = canvas.getHeight() / 2 - 40;
			graphicsContext.fillRect(runwayStartX + (obstacle.getDisplacementPosition() * pixelRatio) + centreAdjustmentX, runwayStartY, 40, 40);
		}
	}

	private void drawParameterLines(final GraphicsContext graphicsContext)
	{
		if (!Objects.equals(lblOrigTora.getText(), ""))
		{
			Canvas canvas = graphicsContext.getCanvas();

			Obstacle obstacle;

			final double tora = Objects.equals(lblRecalcTora.getText(), "") ? Double.parseDouble(lblOrigTora.getText()) : Double.parseDouble(lblRecalcTora.getText());
			final double toda = Objects.equals(lblRecalcToda.getText(), "") ? Double.parseDouble(lblOrigToda.getText()) : Double.parseDouble(lblRecalcToda.getText());
			final double asda = Objects.equals(lblRecalcAsda.getText(), "") ? Double.parseDouble(lblOrigAsda.getText()) : Double.parseDouble(lblRecalcAsda.getText());
			final double lda = Objects.equals(lblRecalcLda.getText(), "") ? Double.parseDouble(lblOrigLda.getText()) : Double.parseDouble(lblRecalcLda.getText());
			final double displacedThreshold = Objects.equals(lblRecalcDisplacedThreshold.getText(), "") ?
					Double.parseDouble(lblOrigDisplacedThreshold.getText()) :
					Double.parseDouble(lblRecalcDisplacedThreshold.getText());

			//Calculate TORA, TODA, ASDA, LDA
			final double pixelRatio = (SCALING * canvas.getWidth()) / Double.parseDouble(lblOrigTora.getText());
			final double toraPixel = tora * pixelRatio;
			final double todaPixel = toda * pixelRatio;
			final double asdaPixel = asda * pixelRatio;
			final double ldaPixel = lda * pixelRatio;
			final double displacedThresholdPixel = displacedThreshold * pixelRatio;

			double toraStartPixel = RUNWAY_START_X_SCALING * canvas.getWidth();
			double ldaStartPixel = (RUNWAY_START_X_SCALING * canvas.getWidth()) + displacedThresholdPixel;
			double displacedThresholdStartPixel = RUNWAY_START_X_SCALING * canvas.getWidth();

			if (cmbRunways.getValue().getObstacle() != null)
			{
				obstacle = cmbRunways.getValue().getObstacle();

				if (obstacle.getDisplacementPosition() < (Double.parseDouble(lblOrigTora.getText()) / 2.0)) {
					toraStartPixel = (RUNWAY_START_X_SCALING * canvas.getWidth()) + ((SCALING * canvas.getWidth()) - toraPixel);
					//ldaStartPixel = (RUNWAY_START_X_SCALING * canvas.getWidth()) + displacedThresholdPixel;
					ldaStartPixel = (RUNWAY_START_X_SCALING * canvas.getWidth()) + ((SCALING * canvas.getWidth()) - ldaPixel);
					displacedThresholdStartPixel = RUNWAY_START_X_SCALING * canvas.getWidth();
				} else {
					//displacement threshold stays in same position on runway
					//ldaStartPixel = RUNWAY_START_X_SCALING * canvas.getWidth();
					//displacedThresholdStartPixel = ldaStartPixel + ldaPixel;
				}

			}

			//draw TORA
			graphicsContext.setFill(Color.rgb(255, 138, 138));
			graphicsContext.fillRect(toraStartPixel, 0.55 * canvas.getHeight(), toraPixel, 3);

			//draw TODA
			graphicsContext.setFill(Color.rgb(255, 190, 50));
			graphicsContext.fillRect(toraStartPixel, 0.575 * canvas.getHeight(), todaPixel, 3);

			//draw ASDA
			graphicsContext.setFill(Color.rgb(255, 240, 40));
			graphicsContext.fillRect(toraStartPixel, 0.6 * canvas.getHeight(), asdaPixel, 3);

			//draw LDA
			graphicsContext.setFill(Color.rgb(180, 225, 35));
			graphicsContext.fillRect(ldaStartPixel, 0.625 * canvas.getHeight(), ldaPixel, 3);

			//draw Displaced Threshold
			graphicsContext.setFill(Color.rgb(150, 210, 255));
			graphicsContext.fillRect(displacedThresholdStartPixel, 0.65 * canvas.getHeight(), displacedThresholdPixel, 3);
		}

	}


	private void drawTopRunwaySurface(final GraphicsContext graphicsContext)
	{
		Canvas canvas = graphicsContext.getCanvas();
		graphicsContext.setFill(Color.rgb(0, 51, 0)); //Background colour
		graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphicsContext.setFill(Color.rgb(77, 77, 77)); //Runway colour
		graphicsContext.fillRect(RUNWAY_START_X_SCALING * canvas.getWidth(), RUNWAY_START_Y_SCALING * canvas.getHeight(), (SCALING * canvas.getWidth()),
				RUNWAY_HEIGHT_SCALING * canvas.getHeight());
	}

	private void drawSideRunwaySurface(final GraphicsContext graphicsContext) {
		Canvas canvas = graphicsContext.getCanvas();
		graphicsContext.setFill(Color.rgb(0, 51, 51)); //Background colour
		graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight() / 2);
		graphicsContext.setFill(Color.rgb(0, 51, 0)); //Background colour
		graphicsContext.fillRect(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2);
		graphicsContext.setFill(Color.rgb(77, 77, 77)); //Runway colour
		graphicsContext.fillRect(RUNWAY_START_X_SCALING * canvas.getWidth(), canvas.getHeight() / 2, (SCALING * canvas.getWidth()),
				(RUNWAY_HEIGHT_SCALING * canvas.getHeight()) / 3.5);
	}


	private double getDisplacedThresholdPixel(final GraphicsContext graphicsContext)
	{
		final double displacedThresholdPixel;
		if (!Objects.equals(lblOrigTora.getText(), ""))
		{
			Canvas canvas = graphicsContext.getCanvas();

			final double displacedThreshold = Objects.equals(lblRecalcDisplacedThreshold.getText(), "") ?
					Double.parseDouble(lblOrigDisplacedThreshold.getText()) :
					Double.parseDouble(lblRecalcDisplacedThreshold.getText());

			//Calculate TORA, TODA, ASDA, LDA
			final double pixelRatio = (SCALING * canvas.getWidth()) / Double.parseDouble(lblOrigTora.getText());

			displacedThresholdPixel = displacedThreshold * pixelRatio;
		}
		else
		{
			displacedThresholdPixel = 0;
		}
		return displacedThresholdPixel;
	}


	private void drawRunwayStripLines(final GraphicsContext graphicsContext)
	{//Draw lines
		Canvas canvas = graphicsContext.getCanvas();
		graphicsContext.setStroke(Color.WHITE);
		graphicsContext.setLineWidth(2);

		double lineLength = (SCALING * canvas.getWidth()) / 15;


		double lineStart = (RUNWAY_START_X_SCALING * canvas.getWidth()) + lineLength;
		double centreline = (RUNWAY_START_Y_SCALING * canvas.getHeight()) + 25;


		for (int i = 0; i < 7; i++) {
			graphicsContext.strokeLine(lineStart, centreline, lineStart + lineLength, centreline);
			lineStart = lineStart + 2 * lineLength;
		}
/*
		for (int i = 1; i < (canvas.getWidth() + getDisplacedThresholdPixel(graphicsContext)) / 105; i++)
		{
			double lineStart = firstLineX + (60 * (i - 1));
			double lineEnd = lineStart + 30;

			graphicsContext.strokeLine(lineStart, centreline, lineEnd, centreline);

		}
*/
	}

	/* Draws directional arrow and scale
	 *
	 * @param graphicsContext The graphics content to paint to
	 */
	private void drawAdditionalComponents(final GraphicsContext graphicsContext) {
		Canvas canvas = graphicsContext.getCanvas();
		graphicsContext.setStroke(Color.WHITE);
		graphicsContext.setLineWidth(2);

		graphicsContext.strokeLine(canvas.getWidth() - 60, canvas.getHeight() * 0.1 - 10, canvas.getWidth() - 50, canvas.getHeight() * 0.1);
		graphicsContext.strokeLine(canvas.getWidth() - 350, canvas.getHeight() * 0.1, canvas.getWidth() - 50, canvas.getHeight() * 0.1);
		graphicsContext.strokeLine(canvas.getWidth() - 60, canvas.getHeight() * 0.1 + 10, canvas.getWidth() - 50, canvas.getHeight() * 0.1);

		Font font = new Font(12);
		graphicsContext.setFont(font);
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.fillText("Direction: Landing Towards/Take-off Away", canvas.getWidth() - 330, canvas.getHeight() * 0.1 - 10);

		double scaleLength = 100;
		if (!lblOrigTora.getText().isEmpty())
			scaleLength = (SCALING * canvas.getWidth()) / Double.valueOf(lblOrigTora.getText()) * 500;

		graphicsContext.strokeLine(canvas.getWidth() - 50 - scaleLength, canvas.getHeight() * 0.9, canvas.getWidth() - 50, canvas.getHeight() * 0.9);
		graphicsContext.strokeLine(canvas.getWidth() - 50, canvas.getHeight() * 0.9 - 10, canvas.getWidth() - 50, canvas.getHeight() * 0.9);
		graphicsContext.strokeLine(canvas.getWidth() - 50 - scaleLength, canvas.getHeight() * 0.9 - 10, canvas.getWidth() - 50 - scaleLength, canvas.getHeight() * 0.9);


		graphicsContext.fillText("500m", canvas.getWidth() - scaleLength / 2 - 65, canvas.getHeight() * 0.9 - 10);
	}
	
	
	//TODO: refactor, possibly move to another file
	public void handleBtnImportAirport()
	{
		Airport importedAirport = importController.importAirport(mainApp, dbf);
		if (importedAirport != null) {
			airportList.add(importedAirport);
		} else {
			//TODO: Error message
		}
	}
	
	public void handleBtnImportRunway()
	{
		Runway importedRunway = importController.importRunway(mainApp, dbf);
		if (importedRunway != null) {
			final Airport selected = cmbAirports.getValue();
			selected.addRunway(importedRunway);
			try {
				updateRunwayList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//TODO: Error message
		}
	}


	public void handleBtnImportObstacle()
	{
		Obstacle importedObstacle = importController.importObstacle(mainApp, dbf);
		if (importedObstacle != null) {
			final Airport selected = cmbAirports.getValue();
			final Runway runway = cmbRunways.getValue();
			runway.setObstacle(importedObstacle);
			updateObstacleList();
		} else {
			//TODO: Error message
		}
	}

	public void handleBtnExportAirportOnly() {
		exportController.exportAirport(mainApp, dbf, cmbAirports.getValue(), 2);
	}

	//TODO export airport fully
	public void handleBtnExportAirportRunways() {
		exportController.exportAirport(mainApp, dbf, cmbAirports.getValue(), 1);
	}

	public void handleBtnExportAirport() {
		exportController.exportAirport(mainApp, dbf, cmbAirports.getValue(), 0);
	}

	//TODO: export runway fully

	public void handleBtnExportRunway() {
		exportController.exportRunway(mainApp, dbf, cmbAirports.getValue().getAirportName(), cmbRunways.getValue(), 0);
	}

	public void handleBtnExportRunwayOnly() {
		exportController.exportRunway(mainApp, dbf, cmbAirports.getValue().getAirportName(), cmbRunways.getValue(), 1);
	}

	public void handleBtnExportObstacle() {
		final Runway runway = cmbRunways.getValue();
		exportController.exportObstacle(mainApp, dbf, runway.getObstacle());
	}

	public void handleBtnExportTopDown() {
		exportController.exportImage(mainApp, cnvTop);
	}

	public void handleBtnExportSideOn() {
		exportController.exportImage(mainApp, cnvSide);
	}

	public void handleBtnExportPDF() {
		exportController.exportPDF(mainApp, cnvTop, cnvSide);
	}
}

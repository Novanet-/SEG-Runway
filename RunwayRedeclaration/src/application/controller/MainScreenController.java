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

import java.util.Iterator;
import java.util.Objects;

public class MainScreenController
{

	public static final double SCALING                = 0.575;
	public static final double RUNWAY_START_X_SCALING = 0.175;
	public static final double RUNWAY_START_Y_SCALING = 0.4;
	public static final double RUNWAY_HEIGHT_SCALING  = 0.14;
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
	@FXML private TextArea                txtCalculations;

	@FXML private Canvas cnvTop; //870x345
	@FXML private Canvas cnvSide; //870x345

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

		GraphicsContext graphicsContext = cnvTop.getGraphicsContext2D();
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

	//TODO: Change add airport/runway buttons to + and - buttons as per obstacle for consistency and to allow for removal or airports and runways


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
				else {
					System.out.println("MainScreenController.handleBtnRemoveObstacle");
					System.out.println("secondaryRunway = " + secondaryRunway);
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
		Iterator<Airport> airportList = mainApp.getAirportList().iterator();
		while (airportList.hasNext())
		{
			Airport currentAirport = airportList.next();
			Iterator<Runway> runwayList = currentAirport.getRunways().iterator();
			while (runwayList.hasNext())
			{
				Runway currRunway = runwayList.next();
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


	public void paintVisualisation()
	{
		paintTopDown(cnvTop.getGraphicsContext2D());
		paintSideOn(cnvSide.getGraphicsContext2D());
	}


	/**
	 * Generates the top-down visualisation of the Runway
	 *
	 * @param graphicsContext
	 */
	public void paintTopDown(GraphicsContext graphicsContext)
	{
		graphicsContext.clearRect(0, 0, 870, 345); //Clears canvas for new runway
		graphicsContext.setFill(Color.rgb(255, 255, 255));
		graphicsContext.fill();

		drawRunwaySurface(graphicsContext);
		drawRunwayStripLines(graphicsContext);
		drawParameterLines(graphicsContext);
		drawObstacle(graphicsContext);

	}


	/**
	 * Generates the top-down visualisation of the Runway
	 *
	 * @param graphicsContext
	 */
	public void paintSideOn(GraphicsContext graphicsContext)
	{
		graphicsContext.clearRect(0, 0, 870, 345); //Clears canvas for new runway
		graphicsContext.setFill(Color.rgb(255, 255, 255));
		graphicsContext.fill();

		Canvas canvas = graphicsContext.getCanvas();
		graphicsContext.setFill(Color.rgb(0, 51, 0)); //Background colour
		graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

	}


	private void drawObstacle(final GraphicsContext graphicsContext)
	{
		if (!Objects.equals(txtObstacles.getText(), ""))
		{
			Canvas canvas = graphicsContext.getCanvas();
			Obstacle obstacle = cmbRunways.getValue().getObstacle();

			final double tora = Objects.equals(lblRecalcTora.getText(), "") ? Double.parseDouble(lblOrigTora.getText()) : Double.parseDouble(lblRecalcTora.getText());
			final double pixelRatio = (SCALING * canvas.getWidth()) / Double.parseDouble(lblOrigTora.getText());

			graphicsContext.setFill(Color.rgb(179, 45, 0)); //Background colour
			//			graphicsContext.fillRect((obstacle.getPosition() * pixelRatio) + 20, (RUNWAY_START_Y_SCALING * canvas.getHeight()) + 30, 40, 40);
			final double centreAdjustmentX = (obstacle.getPosition() < 0) ? -40 : 20;
			final double centreAdjustmentY = 30.0;
			final double runwayStartX = RUNWAY_START_X_SCALING * canvas.getWidth();
			final double runwayStartY = RUNWAY_START_Y_SCALING * canvas.getHeight();
			graphicsContext.fillRect(runwayStartX + (obstacle.getPosition() * pixelRatio) + centreAdjustmentX, runwayStartY + centreAdjustmentY, 40, 40);
			//			graphicsContext.fillRect(250, 150, 50, 50);
		}
	}


	private void drawParameterLines(final GraphicsContext graphicsContext)
	{
		if (!Objects.equals(lblOrigTora.getText(), ""))
		{
			Canvas canvas = graphicsContext.getCanvas();

			Obstacle obstacle = null;

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

			if(cmbRunways.getValue().getObstacle() != null) {
				obstacle = cmbRunways.getValue().getObstacle();

				if(obstacle.getPosition() < (Double.parseDouble(lblOrigTora.getText())/2.0)) {
					toraStartPixel = (RUNWAY_START_X_SCALING * canvas.getWidth()) + displacedThresholdPixel;
				}

			}

			//draw TORA
			graphicsContext.setFill(Color.rgb(255, 138, 138));
			graphicsContext.fillRect(toraStartPixel, 0.55 * canvas.getHeight(), toraPixel, 5);

			//draw TODA
			graphicsContext.setFill(Color.rgb(255, 190, 50));
			graphicsContext.fillRect(toraStartPixel, 0.575 * canvas.getHeight(), todaPixel, 5);

			//draw ASDA
			graphicsContext.setFill(Color.rgb(255, 240, 40));
			graphicsContext.fillRect(toraStartPixel, 0.6 * canvas.getHeight(), asdaPixel, 5);

			//draw LDA
			graphicsContext.setFill(Color.rgb(180, 225, 35));
			graphicsContext.fillRect((RUNWAY_START_X_SCALING * canvas.getWidth()) + displacedThresholdPixel, 0.625 * canvas.getHeight(), ldaPixel, 5);

			//draw Displaced Threshold
			graphicsContext.setFill(Color.rgb(150, 210, 255));
			graphicsContext.fillRect(RUNWAY_START_X_SCALING * canvas.getWidth(), 0.65 * canvas.getHeight(), displacedThresholdPixel, 5);
		}

	}

	//	private void drawDebugParameterLines(final GraphicsContext graphicsContext, final double tora, final double toda, final double asda, final double lda,
	//			final double displacedThreshold)
	//	{//Calculate TORA, TODA, ASDA, LDA
	//		final double runwayLength = 0.75 * graphicsContext.getCanvas().getWidth();
	//		double pixelRatio = runwayLength / tora;
	//		int toraPixel = (int) Math.round(runwayLength);
	//		int todaPixel = (int) Math.round(toda * pixelRatio);
	//		int asdaPixel = (int) Math.round(asda * pixelRatio);
	//		int ldaPixel = (int) Math.round(lda * pixelRatio);
	//		int displacedThresholdPixel = (int) Math.round(displacedThreshold * pixelRatio);
	//
	//		System.out.println(pixelRatio);
	//
	//		//draw TORA
	//		graphicsContext.setStroke(Color.rgb(255, 138, 138));
	//		graphicsContext.strokeLine(00, 210, 100 + toraPixel, 210);
	//
	//		//draw TODA
	//		graphicsContext.setStroke(Color.rgb(255, 190, 50));
	//		graphicsContext.strokeLine(100, 215, 100 + todaPixel, 215);
	//
	//		//draw ASDA
	//		graphicsContext.setStroke(Color.rgb(255, 240, 40));
	//		graphicsContext.strokeLine(100, 220, 100 + asdaPixel, 220);
	//
	//		//draw LDA
	//		graphicsContext.setStroke(Color.rgb(180, 225, 35));
	//		graphicsContext.strokeLine(100 + (toraPixel - ldaPixel), 225, 100 - toraPixel, 225);
	//
	//		//draw Displaced Threshold
	//		graphicsContext.setStroke(Color.rgb(150, 210, 255));
	//		graphicsContext.strokeLine(100, 230, 100 + displacedThresholdPixel, 230);
	//	}


	private void drawRunwaySurface(final GraphicsContext graphicsContext)
	{
		double displacedThresholdPixel;

		displacedThresholdPixel = getDisplacedThresholdPixel(graphicsContext);

		Canvas canvas = graphicsContext.getCanvas();
		graphicsContext.setFill(Color.rgb(0, 51, 0)); //Background colour
		graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphicsContext.setFill(Color.rgb(77, 77, 77)); //Runway colour
		//graphicsContext.strokeLine(10, 10, 10, 50);
		graphicsContext
				.fillRect(RUNWAY_START_X_SCALING * canvas.getWidth(), RUNWAY_START_Y_SCALING * canvas.getHeight(), (SCALING * canvas.getWidth()),
						RUNWAY_HEIGHT_SCALING * canvas.getHeight());
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
		graphicsContext.setLineWidth(5);
		//		graphicsContext.strokeLine(0.20 * canvas.getWidth(), 0.4 * canvas.getHeight() + 50, 0.20 * canvas.getWidth() + 30, 0.4 * canvas.getHeight() + 50);ge
		//		graphicsContext.strokeLine(0.20 * canvas.getWidth() + 60, 0.4 * canvas.getHeight() + 50, 220, 0.4 * canvas.getHeight() + 50);
		//		graphicsContext.strokeLine(250, 0.4 * canvas.getHeight() + 50, 280, 0.4 * canvas.getHeight() + 50);
		//		graphicsContext.strokeLine(310, 0.4 * canvas.getHeight() + 50, 340, 0.4 * canvas.getHeight() + 50);
		//		graphicsContext.strokeLine(370, 0.4 * canvas.getHeight() + 50, 400, 0.4 * canvas.getHeight() + 50);
		//		graphicsContext.strokeLine(430, 0.4 * canvas.getHeight() + 50, 460, 0.4 * canvas.getHeight() + 50);
		//		graphicsContext.strokeLine(490, 0.4 * canvas.getHeight() + 50, 520, 0.4 * canvas.getHeight() + 50);
		//		graphicsContext.strokeLine(550, 0.4 * canvas.getHeight() + 50, 580, 0.4 * canvas.getHeight() + 50);
		//		graphicsContext.strokeLine(610, 0.4 * canvas.getHeight() + 50, 640, 0.4 * canvas.getHeight() + 50);
		//		graphicsContext.strokeLine(670, 150, 700, 150);

		double firstLineX = (RUNWAY_START_X_SCALING * canvas.getWidth()) + 30;
		double centreline = (RUNWAY_START_Y_SCALING * canvas.getHeight()) + 50;
		for (int i = 1; i < (canvas.getWidth() + getDisplacedThresholdPixel(graphicsContext)) / 105; i++)
		{
			double lineStart = firstLineX + (60 * (i - 1));
			double lineEnd = lineStart + 30;

			graphicsContext.strokeLine(lineStart, centreline, lineEnd, centreline);

		}
	}
}

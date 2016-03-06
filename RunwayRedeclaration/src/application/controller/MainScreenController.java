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
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

import java.util.Objects;

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

	@FXML private Canvas cnvTop; //870x345

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
			paintTopDown(cnvTop.getGraphicsContext2D());
		}
		else
		{
			lblRecalcTora.setText("");
			lblRecalcToda.setText("");
			lblRecalcAsda.setText("");
			lblRecalcLda.setText("");
			lblRecalcDisplacedThreshold.setText("");
			paintTopDown(cnvTop.getGraphicsContext2D());
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
			paintTopDown(cnvTop.getGraphicsContext2D());
		}
		else
		{
			lblOrigTora.setText("");
			lblOrigToda.setText("");
			lblOrigAsda.setText("");
			lblOrigLda.setText("");
			lblOrigDisplacedThreshold.setText("");
			paintTopDown(cnvTop.getGraphicsContext2D());
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
	 *
	 * @param graphicsContext
	 */
	public void paintTopDown(GraphicsContext graphicsContext)
	{
		double tora = 3902.0; //2986.0;
		double toda = 3902.0; //2986.0;
		double asda = 3902.0; //2986.0;
		double lda = 3595.0; //3346.0;
		double displacedThreshold = 306.0;

		drawRunwaySurface(graphicsContext);
		drawRunwayStripLines(graphicsContext);
		drawParameterLines(graphicsContext);

	}


	private void drawParameterLines(final GraphicsContext graphicsContext)
	{
		if (!Objects.equals(lblOrigTora.getText(), ""))
		{
			final double tora = Objects.equals(lblRecalcTora.getText(), "") ? Double.parseDouble(lblOrigTora.getText()) : Double.parseDouble(lblRecalcTora.getText());
			final double toda = Objects.equals(lblRecalcToda.getText(), "") ? Double.parseDouble(lblOrigToda.getText()) : Double.parseDouble(lblRecalcToda.getText());
			final double asda = Objects.equals(lblRecalcAsda.getText(), "") ? Double.parseDouble(lblOrigAsda.getText()) : Double.parseDouble(lblRecalcAsda.getText());
			final double lda = Objects.equals(lblRecalcLda.getText(), "") ? Double.parseDouble(lblOrigLda.getText()) : Double.parseDouble(lblRecalcLda.getText());
			final double displacedThreshold = Objects.equals(lblRecalcDisplacedThreshold.getText(), "") ?
					Double.parseDouble(lblOrigDisplacedThreshold.getText()) :
					Double.parseDouble(lblRecalcDisplacedThreshold.getText());

			//Calculate TORA, TODA, ASDA, LDA
			final double pixelRatio = 620.0 / tora;
			final double toraPixel = 620.0;
			final double todaPixel = toda * pixelRatio;
			final double asdaPixel = asda * pixelRatio;
			final double ldaPixel = lda * pixelRatio;
			final double displacedThresholdPixel = displacedThreshold * pixelRatio;

			System.out.println(pixelRatio);

			//draw TORA
			//			graphicsContext.setStroke(Color.rgb(255, 138, 138));
			//			graphicsContext.strokeLine(100.0, 210.0, 100.0 + toraPixel, 210);
			graphicsContext.setFill(Color.rgb(255, 138, 138));
			graphicsContext.fillRect(100.0, 210.0, 100.0 + toraPixel, 3);

			//draw TODA
			//			graphicsContext.setStroke(Color.rgb(255, 190, 50));
			//			graphicsContext.strokeLine(100, 215, 100 + todaPixel, 215);
			graphicsContext.setFill(Color.rgb(255, 190, 50));
			graphicsContext.fillRect(100.0, 215.0, 100.0 + todaPixel, 3);

			//draw ASDA
			//			graphicsContext.setStroke(Color.rgb(255, 240, 40));
			//			graphicsContext.strokeLine(100, 220, 100 + asdaPixel, 220);
			graphicsContext.setFill(Color.rgb(255, 240, 40));
			graphicsContext.fillRect(100.0, 220.0, 100.0 + asdaPixel, 3);

			//draw LDA
			//			graphicsContext.setStroke(Color.rgb(180, 225, 35));
			//			graphicsContext.strokeLine(100 + (toraPixel - ldaPixel), 225, 100 + toraPixel, 225);
			graphicsContext.setFill(Color.rgb(180, 225, 35));
			graphicsContext.fillRect(100.0 + displacedThresholdPixel, 225.0, 100.0 + ldaPixel, 3);

			//draw Displaced Threshold
			//			graphicsContext.setStroke(Color.rgb(150, 210, 255));
			//			graphicsContext.strokeLine(100, 230, 100 + displacedThresholdPixel, 230);
			graphicsContext.setFill(Color.rgb(150, 210, 255));
			graphicsContext.fillRect(100.0, 230.0, 100.0 + displacedThresholdPixel, 3);
		}
	}


	private void drawDebugParameterLines(final GraphicsContext graphicsContext, final double tora, final double toda, final double asda, final double lda,
			final double displacedThreshold)
	{//Calculate TORA, TODA, ASDA, LDA
		double pixelRatio = 620.0 / tora;
		int toraPixel = 620;
		int todaPixel = (int) Math.round(toda * pixelRatio);
		int asdaPixel = (int) Math.round(asda * pixelRatio);
		int ldaPixel = (int) Math.round(lda * pixelRatio);
		int displacedThresholdPixel = (int) Math.round(displacedThreshold * pixelRatio);

		System.out.println(pixelRatio);

		//draw TORA
		graphicsContext.setStroke(Color.rgb(255, 138, 138));
		graphicsContext.strokeLine(100, 210, 100 + toraPixel, 210);

		//draw TODA
		graphicsContext.setStroke(Color.rgb(255, 190, 50));
		graphicsContext.strokeLine(100, 215, 100 + todaPixel, 215);

		//draw ASDA
		graphicsContext.setStroke(Color.rgb(255, 240, 40));
		graphicsContext.strokeLine(100, 220, 100 + asdaPixel, 220);

		//draw LDA
		graphicsContext.setStroke(Color.rgb(180, 225, 35));
		graphicsContext.strokeLine(100 + (toraPixel - ldaPixel), 225, 100 + toraPixel, 225);

		//draw Displaced Threshold
		graphicsContext.setStroke(Color.rgb(150, 210, 255));
		graphicsContext.strokeLine(100, 230, 100 + displacedThresholdPixel, 230);
	}


	private void drawRunwaySurface(final GraphicsContext graphicsContext)
	{//graphicsContext.setFill(Color.rgb(214,218,219)); //Background colour
		graphicsContext.setFill(Color.rgb(77, 77, 77)); //Runway colour
		//graphicsContext.strokeLine(10, 10, 10, 50);
		graphicsContext.fillRect(100, 100, 620, 100);
	}


	private void drawRunwayStripLines(final GraphicsContext graphicsContext)
	{//Draw lines
		graphicsContext.setStroke(Color.WHITE);
		graphicsContext.setLineWidth(5);
		graphicsContext.strokeLine(130, 150, 160, 150);
		graphicsContext.strokeLine(190, 150, 220, 150);
		graphicsContext.strokeLine(250, 150, 280, 150);
		graphicsContext.strokeLine(310, 150, 340, 150);
		graphicsContext.strokeLine(370, 150, 400, 150);
		graphicsContext.strokeLine(430, 150, 460, 150);
		graphicsContext.strokeLine(490, 150, 520, 150);
		graphicsContext.strokeLine(550, 150, 580, 150);
		graphicsContext.strokeLine(610, 150, 640, 150);
		graphicsContext.strokeLine(670, 150, 700, 150);
	}
}

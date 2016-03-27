package application.controller;

import application.Main;
import application.model.Airport;
import application.model.Obstacle;
import application.model.Runway;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Objects;
import javafx.fxml.FXML;

//TODO: Remove duplicate code and make the drawing a seperate class, fix this abomination =P

/**
 * Created by Will on 27/03/16.
 */
public class VisualScreenController
{

	private Main                    mainApp;
	private ObservableList<Airport> airportList;
	private MainScreenController    msController;
	private Runway                  selectedRunway;

	@FXML private Canvas cnvRotation;


	public final void drawRotatedRunway()
	{
		GraphicsContext graphicsContext = cnvRotation.getGraphicsContext2D();
		graphicsContext.clearRect(0, 0, 870, 345); //Clears canvas for new runway
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.fill();
		if (selectedRunway != null)
		{
			graphicsContext.getCanvas().setRotate((Double.parseDouble(selectedRunway.getAlignment().replaceAll("\\D+", "")) * 10) - 90);
		}

		drawTopRunwaySurface(graphicsContext);
		drawRunwayStripLines(graphicsContext);
		drawParameterLines(graphicsContext);
		drawObstacle(graphicsContext);
		drawAdditionalComponents(graphicsContext);
	}


	private void drawObstacle(final GraphicsContext graphicsContext)
	{
		//TODO: Implement obstacle distance from centre line in visualisation

		if (!Objects.equals(msController.getTxtObstacles().getText(), ""))
		{
			Canvas canvas = cnvRotation;
			Obstacle obstacle = selectedRunway.getObstacle();

			final double pixelRatio = (MainScreenController.getSCALING() * canvas.getWidth()) / Double.parseDouble(msController.getLblOrigTora().getText());

			graphicsContext.setFill(Color.rgb(179, 45, 0)); //Background colour
			final double centreAdjustmentX = (obstacle.getDisplacementPosition() < 0) ? -20 : 20;
			final double centreAdjustmentY = 5.0;
			final double runwayStartX = MainScreenController.getRunwayStartXScaling() * canvas.getWidth();
			final double runwayStartY = MainScreenController.getRunwayStartYScaling() * canvas.getHeight();
			graphicsContext.fillRect(runwayStartX + (obstacle.getDisplacementPosition() * pixelRatio) + centreAdjustmentX, runwayStartY + centreAdjustmentY, 40, 40);
		}
	}


	private void drawObstacleSide(final GraphicsContext graphicsContext)
	{
		if (!Objects.equals(msController.getTxtObstacles().getText(), ""))
		{
			Canvas canvas = graphicsContext.getCanvas();
			Obstacle obstacle = selectedRunway.getObstacle();

			double obstacleHeight = obstacle.getHeight() * 4;

			final double pixelRatio = (MainScreenController.getSCALING() * canvas.getWidth()) / Double.parseDouble(msController.getLblOrigTora().getText());

			graphicsContext.setFill(Color.rgb(179, 45, 0)); //Background colour
			final double centreAdjustmentX = (obstacle.getDisplacementPosition() < 0) ? -20 : 20;
			final double centreAdjustmentY = 5.0;
			final double runwayStartX = MainScreenController.getRunwayStartXScaling() * canvas.getWidth();
			final double runwayStartY = canvas.getHeight() / 2 - obstacleHeight;
			graphicsContext.fillRect(runwayStartX + (obstacle.getDisplacementPosition() * pixelRatio) + centreAdjustmentX, runwayStartY, 40, obstacleHeight);

			graphicsContext.setStroke(Color.WHITE);
			graphicsContext.setLineWidth(1);

			final double tora = Objects.equals(msController.getLblRecalcTora().getText(), "") ?
					Double.parseDouble(msController.getLblOrigTora().getText()) :
					Double.parseDouble(msController.getLblRecalcTora().getText());
			final double lda = Objects.equals(msController.getLblRecalcLda().getText(), "") ?
					Double.parseDouble(msController.getLblOrigLda().getText()) :
					Double.parseDouble(msController.getLblRecalcLda().getText());

			final double toraPixel = tora * pixelRatio;
			final double ldaPixel = lda * pixelRatio;

			double slopeStartX, slopeStartY, slopeEndX, slopeEndY;
			double obstacleHeightX, obstacleHeightTextX;

			//TODO: Make slope end at correct point
			slopeStartY = runwayStartY;
			slopeEndY = runwayStartY + obstacleHeight;
			if (obstacle.getDisplacementPosition() < (Double.parseDouble(msController.getLblOrigTora().getText()) / 2.0))
			{
				slopeStartX = runwayStartX + (obstacle.getDisplacementPosition() * pixelRatio) + centreAdjustmentX + 40;
				slopeEndX = (MainScreenController.getRunwayStartXScaling() * canvas.getWidth()) + ((MainScreenController.getSCALING() * canvas.getWidth())
						- ldaPixel); // start of lda

				obstacleHeightX = runwayStartX + (obstacle.getDisplacementPosition() * pixelRatio) + centreAdjustmentX - 10;
				obstacleHeightTextX =
						runwayStartX + (obstacle.getDisplacementPosition() * pixelRatio) + centreAdjustmentX - 30 - 8 * String.valueOf(obstacle.getHeight()).length();
			}
			else
			{
				slopeStartX = runwayStartX + (obstacle.getDisplacementPosition() * pixelRatio) + centreAdjustmentX;
				slopeEndX = (MainScreenController.getRunwayStartXScaling() * canvas.getWidth()) + toraPixel; // end of tora

				obstacleHeightX = runwayStartX + (obstacle.getDisplacementPosition() * pixelRatio) + centreAdjustmentX + 50;
				obstacleHeightTextX = runwayStartX + (obstacle.getDisplacementPosition() * pixelRatio) + centreAdjustmentX + 70;
			}

			//Draw slope caused over obstacle
			graphicsContext.strokeLine(slopeStartX, slopeStartY, slopeEndX, slopeEndY);

			//Draw obstacle height
			graphicsContext.strokeLine(obstacleHeightX - 5, runwayStartY + 5, obstacleHeightX, runwayStartY);
			graphicsContext.strokeLine(obstacleHeightX, runwayStartY, obstacleHeightX, runwayStartY + obstacleHeight);
			graphicsContext.strokeLine(obstacleHeightX, runwayStartY, obstacleHeightX + 5, runwayStartY + 5);

			Font font = new Font(12);
			graphicsContext.setFont(font);
			graphicsContext.setFill(Color.WHITE);
			graphicsContext.fillText(obstacle.getHeight() + "m", obstacleHeightTextX, runwayStartY + obstacleHeight / 2);
		}
	}


	private void drawParameterLines(final GraphicsContext graphicsContext)
	{
		if (!Objects.equals(msController.getLblOrigTora().getText(), ""))
		{
			Canvas canvas = graphicsContext.getCanvas();

			Obstacle obstacle;

			final double tora = Objects.equals(msController.getLblRecalcTora().getText(), "") ?
					Double.parseDouble(msController.getLblOrigTora().getText()) :
					Double.parseDouble(msController.getLblRecalcTora().getText());
			final double toda = Objects.equals(msController.getLblRecalcToda().getText(), "") ?
					Double.parseDouble(msController.getLblOrigToda().getText()) :
					Double.parseDouble(msController.getLblRecalcToda().getText());
			final double asda = Objects.equals(msController.getLblRecalcAsda().getText(), "") ?
					Double.parseDouble(msController.getLblOrigAsda().getText()) :
					Double.parseDouble(msController.getLblRecalcToda().getText());
			final double lda = Objects.equals(msController.getLblRecalcLda().getText(), "") ?
					Double.parseDouble(msController.getLblOrigLda().getText()) :
					Double.parseDouble(msController.getLblRecalcLda().getText());
			final double displacedThreshold = Objects.equals(msController.getLblRecalcDisplacedThreshold().getText(), "") ?
					Double.parseDouble(msController.getLblOrigDisplacedThreshold().getText()) :
					Double.parseDouble(msController.getLblRecalcDisplacedThreshold().getText());

			//Calculate TORA, TODA, ASDA, LDA
			final double pixelRatio = (MainScreenController.getSCALING() * canvas.getWidth()) / Double.parseDouble(msController.getLblOrigTora().getText());
			final double toraPixel = tora * pixelRatio;
			final double todaPixel = toda * pixelRatio;
			final double asdaPixel = asda * pixelRatio;
			final double ldaPixel = lda * pixelRatio;
			final double displacedThresholdPixel = displacedThreshold * pixelRatio;

			double toraStartPixel = MainScreenController.getRunwayStartXScaling() * canvas.getWidth();
			double ldaStartPixel = (MainScreenController.getRunwayStartXScaling() * canvas.getWidth()) + displacedThresholdPixel;
			double displacedThresholdStartPixel = MainScreenController.getRunwayStartXScaling() * canvas.getWidth();

			if (selectedRunway.getObstacle() != null)
			{
				obstacle = selectedRunway.getObstacle();

				if (obstacle.getDisplacementPosition() < (Double.parseDouble(msController.getLblOrigTora().getText()) / 2.0))
				{
					toraStartPixel =
							(MainScreenController.getRunwayStartXScaling() * canvas.getWidth()) + ((MainScreenController.getSCALING() * canvas.getWidth()) - toraPixel);
					//ldaStartPixel = (MainScreenController.getRunwayStartXScaling() * canvas.getWidth()) + displacedThresholdPixel;
					ldaStartPixel = (MainScreenController.getRunwayStartXScaling() * canvas.getWidth()) + ((MainScreenController.getSCALING() * canvas.getWidth()) - ldaPixel);
					displacedThresholdStartPixel = MainScreenController.getRunwayStartXScaling() * canvas.getWidth();
				}
				else
				{
					//displacement threshold stays in same position on runway
					//ldaStartPixel = MainScreenController.getRunwayStartXScaling() * canvas.getWidth();
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
		graphicsContext.fillRect(MainScreenController.getRunwayStartXScaling() * canvas.getWidth(), MainScreenController.getRunwayStartYScaling() * canvas.getHeight(),
				(MainScreenController.getSCALING() * canvas.getWidth()), MainScreenController.getRunwayHeightScaling() * canvas.getHeight());
	}


	private void drawSideRunwaySurface(final GraphicsContext graphicsContext)
	{
		Canvas canvas = graphicsContext.getCanvas();
		graphicsContext.setFill(Color.rgb(0, 51, 51)); //Background colour
		graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight() / 2);
		graphicsContext.setFill(Color.rgb(0, 51, 0)); //Background colour
		graphicsContext.fillRect(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2);
		graphicsContext.setFill(Color.rgb(77, 77, 77)); //Runway colour
		graphicsContext
				.fillRect(MainScreenController.getRunwayStartXScaling() * canvas.getWidth(), canvas.getHeight() / 2, (MainScreenController.getSCALING() * canvas.getWidth()),
						(msController.getRunwayHeightScaling() * canvas.getHeight()) / 3.5);
	}


	private double getDisplacedThresholdPixel(final GraphicsContext graphicsContext)
	{
		final double displacedThresholdPixel;
		if (!Objects.equals(msController.getLblOrigTora().getText(), ""))
		{
			Canvas canvas = graphicsContext.getCanvas();

			final double displacedThreshold = Objects.equals(msController.getLblRecalcDisplacedThreshold().getText(), "") ?
					Double.parseDouble(msController.getLblOrigDisplacedThreshold().getText()) :
					Double.parseDouble(msController.getLblRecalcDisplacedThreshold().getText());

			//Calculate TORA, TODA, ASDA, LDA
			final double pixelRatio = (MainScreenController.getSCALING() * canvas.getWidth()) / Double.parseDouble(msController.getLblOrigTora().getText());

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

		double lineLength = (MainScreenController.getSCALING() * canvas.getWidth()) / 15;

		double lineStart = (MainScreenController.getRunwayStartXScaling() * canvas.getWidth()) + lineLength;
		double centreline = (MainScreenController.getRunwayStartYScaling() * canvas.getHeight()) + 25;

		for (int i = 0; i < 7; i++)
		{
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
	private void drawAdditionalComponents(final GraphicsContext graphicsContext)
	{
		Canvas canvas = graphicsContext.getCanvas();
		graphicsContext.setStroke(Color.WHITE);
		graphicsContext.setLineWidth(2);

		graphicsContext.strokeLine(canvas.getWidth() - 60, canvas.getHeight() * 0.1 - 10, canvas.getWidth() - 50, canvas.getHeight() * 0.1);
		graphicsContext.strokeLine(canvas.getWidth() - 350, canvas.getHeight() * 0.1, canvas.getWidth() - 50, canvas.getHeight() * 0.1);
		graphicsContext.strokeLine(canvas.getWidth() - 60, canvas.getHeight() * 0.1 + 10, canvas.getWidth() - 50, canvas.getHeight() * 0.1);

		Font font = new Font(12);
		graphicsContext.setFont(font);
		graphicsContext.setFill(Color.WHITE);

		try
		{
			Obstacle obstacle = selectedRunway.getObstacle();
			if (obstacle.getDisplacementPosition() < (Double.parseDouble(msController.getLblOrigTora().getText()) / 2.0))
			{
				graphicsContext.fillText("Direction: Landing Towards/Take-off Away", canvas.getWidth() - 330, canvas.getHeight() * 0.1 - 10);
			}
			else
			{
				graphicsContext.fillText("Direction: Landing Away/Take-off Over", canvas.getWidth() - 324, canvas.getHeight() * 0.1 - 10);
			}
		}
		catch (NullPointerException e)
		{
			graphicsContext.fillText("Direction: Landing/Take-off", canvas.getWidth() - 287, canvas.getHeight() * 0.1 - 10);
		}

		double scaleLength = 100;
		if (!msController.getLblOrigTora().getText().isEmpty())
			scaleLength = (MainScreenController.getSCALING() * canvas.getWidth()) / Double.valueOf(msController.getLblOrigTora().getText()) * 500;

		graphicsContext.strokeLine(canvas.getWidth() - 50 - scaleLength, canvas.getHeight() * 0.9, canvas.getWidth() - 50, canvas.getHeight() * 0.9);
		graphicsContext.strokeLine(canvas.getWidth() - 50, canvas.getHeight() * 0.9 - 10, canvas.getWidth() - 50, canvas.getHeight() * 0.9);
		graphicsContext.strokeLine(canvas.getWidth() - 50 - scaleLength, canvas.getHeight() * 0.9 - 10, canvas.getWidth() - 50 - scaleLength, canvas.getHeight() * 0.9);

		graphicsContext.fillText("500m", canvas.getWidth() - scaleLength / 2 - 65, canvas.getHeight() * 0.9 - 10);
	}


	public final void updateSelectedAirportRunway(final MainScreenController msController, final Runway selectedRunway)
	{
		this.msController = msController;
		this.selectedRunway = selectedRunway;
	}


	public final void setMainApp(Main mainApp)
	{
		this.mainApp = mainApp;
	}


	public final void linkToSession()
	{
		airportList = mainApp.getAirportList();
	}
}

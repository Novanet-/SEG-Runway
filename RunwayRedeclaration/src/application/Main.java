package application;

import application.controller.*;
import application.model.Airport;
import application.model.Obstacle;
import application.model.Runway;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application
{

	//TODO: readme file, help document

	private static final String PLANE_ICON = "file:resources/planeicon.png";
	private static final String APPLICATION_TITLE = "Runway Redeclaration";
	private static final String MAIN_FXML = "view/MainScreen.fxml";
	private static final String ADD_AIRPORT_FXML = "view/AddAirport.fxml";
	private static final String ADD_RUNWAY_FXML = "view/AddRunway.fxml";
	private static final String ADD_OBSTACLE_FXML = "view/AddObstacle.fxml";
	private static final String VISUAL_SCREEN_FXML = "view/VisualScreen.fxml";
	private static final String UPDATE_AIRPORT_FXML = "view/UpdateAirport.fxml";
	private static final String UPDATE_RUNWAY_FXML = "view/UpdateRunway.fxml";
	private static final String CALCULATIONS_SCREEN_FXML = "view/Calculations.fxml";

	private ObservableList<Airport> airportList;

	private Stage msStage;
	private Stage aaStage;
	private Stage arStage;
	private Stage aoStage;
	private Stage vsStage;
	private Stage uaStage;
	private Stage urStage;
	private Stage csStage;

	private AddAirportController aaController;
	private AddRunwayController arController;
	private AddObstacleController aoController;
	private MainScreenController msController;
	private VisualScreenController vsController;
	private UpdateAirportController uaController;
	private UpdateRunwayController urController;
	private CalculationsScreenController csController;

	/**
	 * @param args Command line arguments
	 */
	public static void main(String[] args)
	{
		launch(args);
	}


	/**
	 * @param primaryStage The starting stage of the program
	 */
	@Override
	public final void start(Stage primaryStage)
	{
		primaryStage.getStyle();

		airportList = FXCollections.observableArrayList(new ArrayList<>());

		final FXMLLoader msLoader = new FXMLLoader();
		msLoader.setLocation(Main.class.getResource(MAIN_FXML));
		AnchorPane msPage = null;
		try
		{
			msPage = msLoader.load();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}

		assert msPage != null;
		Scene mainScene = new Scene(msPage);

		msStage = primaryStage;
		msStage.setScene(mainScene);
		msStage.getIcons().add(new Image(PLANE_ICON));
		msStage.setTitle(APPLICATION_TITLE);
		msStage.setMinWidth(1280.0);
		msStage.setMinHeight(700.0);
		msStage.setResizable(false);

		final FXMLLoader aaLoader = loadAAStage();
		final FXMLLoader arLoader = loadARStage();
		final FXMLLoader aoLoader = loadAOStage();
		final FXMLLoader vsLoader = loadVSStage();
		final FXMLLoader csLoader = loadCSStage();
		final FXMLLoader uaLoader = loadUAStage();
		final FXMLLoader urLoader = loadURStage();

		aaController = aaLoader.getController();
		aaController.setMainApp(this);
		arController = arLoader.getController();
		arController.setMainApp(this);
		aoController = aoLoader.getController();
		aoController.setMainApp(this);
		msController = msLoader.getController();
		msController.setMainApp(this);
		vsController = vsLoader.getController();
		vsController.setMainApp(this);
		uaController = uaLoader.getController();
		uaController.setMainApp(this);
		urController = urLoader.getController();
		urController.setMainApp(this);
		csController = csLoader.getController();
		csController.setMainApp(this);

		aaController.linkToSession();
		arController.linkToSession();
		msController.linkToSession();
		aoController.linkToSession();
		vsController.linkToSession();
		uaController.linkToSession();
		urController.linkToSession();

		msStage.show();
	}


	private FXMLLoader loadCSStage()
	{
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(CALCULATIONS_SCREEN_FXML));
		AnchorPane page = null;
		try
		{
			page = loader.load();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}

		csStage = new Stage();
		assert page != null;
		final Scene scene = new Scene(page);
		csStage.setScene(scene);
		csStage.setTitle("Calculations");
		csStage.getIcons().add(new Image(PLANE_ICON));
		csStage.setMinWidth(300.0);
		csStage.setMinHeight(100.0);

		return loader;
	}


	private FXMLLoader loadAAStage()
	{
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(ADD_AIRPORT_FXML));
		AnchorPane page = null;
		try
		{
			page = loader.load();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}

		aaStage = new Stage();
		assert page != null;
		final Scene scene = new Scene(page);
		aaStage.setScene(scene);
		aaStage.setTitle("Add Airport");
		aaStage.getIcons().add(new Image(PLANE_ICON));
		aaStage.setMinWidth(300.0);
		aaStage.setMinHeight(100.0);

		return loader;
	}


	private FXMLLoader loadARStage()
	{
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(ADD_RUNWAY_FXML));
		AnchorPane page = null;
		try
		{
			page = loader.load();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}

		arStage = new Stage();
		assert page != null;
		final Scene scene = new Scene(page);
		arStage.setScene(scene);
		arStage.setTitle("Add Runway");
		arStage.getIcons().add(new Image(PLANE_ICON));
		arStage.setMinWidth(300.0);
		arStage.setMinHeight(100.0);

		return loader;
	}


	/**
	 * @return The loader for the Add Object stage
	 */
	private FXMLLoader loadAOStage()
	{
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(ADD_OBSTACLE_FXML));
		AnchorPane page = null;

		try
		{
			page = loader.load();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}

		aoStage = new Stage();
		assert page != null;
		final Scene scene = new Scene(page);
		aoStage.setScene(scene);
		aoStage.setTitle("Add Obstacle");
		// TODO: plane icon doesn't show - set path relative to other files
		aoStage.getIcons().add(new Image(PLANE_ICON));
		aoStage.setMinWidth(300.0);
		aoStage.setMinHeight(100.0);

		return loader;
	}


	/**
	 * @return The loader for the Add Object stage
	 */
	private FXMLLoader loadVSStage()
	{
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(VISUAL_SCREEN_FXML));
		AnchorPane page = null;

		try
		{
			page = loader.load();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}

		vsStage = new Stage();
		assert page != null;
		final Scene scene = new Scene(page);
		vsStage.setScene(scene);
		vsStage.setTitle("Visual Screen");
		// TODO: plane icon doesn't show - set path relative to other files
		vsStage.getIcons().add(new Image(PLANE_ICON));
		vsStage.setMinWidth(300.0);
		vsStage.setMinHeight(100.0);

		return loader;
	}


	/**
	 * @return The loader for the Add Object stage
	 */
	private FXMLLoader loadUAStage() {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(UPDATE_AIRPORT_FXML));
		AnchorPane page = null;

		try {
			page = loader.load();
		} catch (final IOException e) {
			e.printStackTrace();
		}

		uaStage = new Stage();
		assert page != null;
		final Scene scene = new Scene(page);
		uaStage.setScene(scene);
		uaStage.setTitle("Update Airport");
		// TODO: plane icon doesn't show - set path relative to other files
		uaStage.getIcons().add(new Image(PLANE_ICON));
		uaStage.setMinWidth(300.0);
		uaStage.setMinHeight(100.0);

		return loader;
	}


	/**
	 * @return The loader for the Add Object stage
	 */
	private FXMLLoader loadURStage() {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(UPDATE_RUNWAY_FXML));
		AnchorPane page = null;

		try {
			page = loader.load();
		} catch (final IOException e) {
			e.printStackTrace();
		}

		urStage = new Stage();
		assert page != null;
		final Scene scene = new Scene(page);
		urStage.setScene(scene);
		urStage.setTitle("Update Runway");
		// TODO: plane icon doesn't show - set path relative to other files
		urStage.getIcons().add(new Image(PLANE_ICON));
		urStage.setMinWidth(300.0);
		urStage.setMinHeight(100.0);

		return loader;
	}


	/**
	 *
	 */
	public final void toggleAddAirport()
	{
		if (aaStage.isShowing())
		{
			aaStage.hide();
		}
		else
		{
			aaStage.show();
		}
	}
	
	public final void toggleShowCalculations()
	{
		if (csStage.isShowing())
		{
			csStage.hide();
		}
		else
		{
			csStage.show();
		}
	}


	/**
	 * @param airportName The airport to add the runway to
	 */
	public final void toggleAddRunway(String airportName)
	{
		if (arStage.isShowing())
		{
			arStage.hide();
			try
			{
				msController.updateRunwayList();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			arController.updateSelectedAirport(airportName);
			arStage.show();
		}
	}


	/**
	 * @param airportName The airport that contains the runway that the obstacle is being added to
	 * @param runwayID    The string alignment+position of the runway that will be the owner of the obstacle
	 */
	public final void toggleAddObstacle(String airportName, String runwayID)
	{
		if (aoStage.isShowing())
		{
			aoStage.hide();
			msController.updateObstacleList();
		}
		else
		{
			aoController.updateSelectedAirportRunway(airportName, runwayID);
			aoStage.show();
		}
	}


	/**
	 * @param airportName The airport that contains the runway that the obstacle is being added to
	 * @param runwayID    The string alignment+position of the runway that will be the owner of the obstacle
	 */
	public final void toggleAddObstacle(String airportName, String runwayID, Obstacle obstacle)
	{
		if (aoStage.isShowing())
		{
			aoStage.hide();
			msController.updateObstacleList();
		}
		else
		{
			aoController.updateSelectedAirportRunway(airportName, runwayID);
			aoController.updateObstacleParameters(obstacle);
			aoStage.show();
		}
	}


	/**
	 * @param airportName The airport that contains the runway that the obstacle is being added to
	 * @param runwayID    The string alignment+position of the runway that will be the owner of the obstacle
	 */
	public final void toggleVisualScreen(String airportName, String runwayID)
	{
		if (vsStage.isShowing())
		{
			vsStage.hide();
		}
		else
		{
			vsController.updateSelectedAirportRunway(msController, msController.getCmbRunways().getValue());
			vsController.drawRotatedRunway();
			vsStage.show();
		}
	}


	/**
	 *
	 */
	public final void toggleUpdateAirport(Airport airport) {
		if (uaStage.isShowing()) {
			uaStage.hide();
		} else {
			uaController.updateSelectedAirport(airport);
			uaStage.show();
		}
	}


	/**
	 * @param airportName The airport to add the runway to
	 */
	public final void toggleUpdateRunway(String airportName, Runway runway) {
		if (urStage.isShowing()) {
			urStage.hide();
			try {
				msController.updateRunwayList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			urController.updateSelectedAirport(airportName);
			urController.updateSelectedRunway(runway);
			urController.updatePrimaries();
			urStage.show();
		}
	}


	public final ObservableList<Airport> getAirportList()
	{
		return airportList;
	}


	@Override
	public final void stop()
	{
		try
		{
			super.stop();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.exit(0);
	}


	public Stage getMsStage()
	{
		return msStage;
	}

}

package application;

import application.model.Airport;
import application.view.AddAirportController;
import application.view.AddObjectController;
import application.view.AddRunwayController;
import application.view.MainScreenController;
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
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application
{

	private ObservableList<Airport> airportList;

	private Stage msStage;
	private Stage aaStage;
	private Stage arStage;
	private Stage aoStage;

	private AddAirportController aaController;
	private AddRunwayController  arController;
	private AddObjectController  aoController;
	private MainScreenController msController;


	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		launch(args);
	}


	/**
	 * @param primaryStage
	 */
	@Override
	public final void start(Stage primaryStage)
	{
		try
		{
			// Load person overview.
			final FXMLLoader msLoader = new FXMLLoader();
			final FXMLLoader aaLoader = loadAAStage();
			final FXMLLoader arLoader = loadARStage();
			assert aaLoader != null;
			aaController = aaLoader.getController();
			aaController.setMainApp(this);

			assert arLoader != null;
			arController = arLoader.getController();
			arController.setMainApp(this);

			msLoader.setLocation(Main.class.getResource("view/MainScreen.fxml"));
			final AnchorPane msPage = msLoader.load();
			msController = msLoader.getController();
			msController.setMainApp(this);

			final Scene scene = new Scene(msPage);
			msStage = primaryStage;
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("file:resources/images/planeicon.png"));
			primaryStage.setTitle("Runway Redeclaration");
			primaryStage.setMinWidth(1000.0);
			primaryStage.setMinHeight(600.0);

			final Collection<Airport> list = new ArrayList<Airport>();
			airportList = FXCollections.observableArrayList(list);

			aaController.linkToSession();
			arController.linkToSession();
			msController.linkToSession();

			primaryStage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (final Exception ex)
		{
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}


	/**
	 * @return
	 */
	private FXMLLoader loadAAStage()
	{
		try
		{// Load person overview.
			final FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/AddAirport.fxml"));
			final AnchorPane page = loader.load();
			aaStage = new Stage();
			final Scene scene = new Scene(page);
			aaStage.setScene(scene);
			aaStage.setTitle("Add Airport");
			aaStage.getIcons().add(new Image("file:resources/images/planeicon.png"));
			aaStage.setMinWidth(300.0);
			aaStage.setMinHeight(100.0);
			return loader;

		}
		catch (final IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * @return
	 */
	private FXMLLoader loadARStage()
	{
		try
		{
			final FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/AddRunway.fxml"));
			final AnchorPane page = loader.load();
			arStage = new Stage();
			final Scene scene = new Scene(page);
			arStage.setScene(scene);
			arStage.setTitle("Add Runway");
			arStage.getIcons().add(new Image("file:resources/images/planeicon.png"));
			arStage.setMinWidth(300.0);
			arStage.setMinHeight(100.0);
			return loader;
		}
		catch (final IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * @return
	 */
	private FXMLLoader loadAOStage()
	{
		try
		{
			final FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/AddObject.fxml"));
			final AnchorPane page = loader.load();
			arStage = new Stage();
			final Scene scene = new Scene(page);
			arStage.setScene(scene);
			arStage.setTitle("Add Object");
			arStage.getIcons().add(new Image("file:resources/images/planeicon.png"));
			arStage.setMinWidth(300.0);
			arStage.setMinHeight(100.0);
			return loader;
		}
		catch (final IOException e)
		{
			e.printStackTrace();
			return null;
		}
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


	/**
	 *
	 */
	public final void toggleAddRunway(String airportName)
	{
		if (arStage.isShowing())
		{
			arStage.hide();
		}
		else
		{
			arController.updateSelectedAirport(airportName);
			arStage.show();
		}
	}


	/**
	 *
	 */
	public final void toggleAddObject(String airportName, String runwayID)
	{
		if (aoStage.isShowing())
		{
			aoStage.hide();
		}
		else
		{
			aoController.updateSelectedAirportRunway(airportName, runwayID);
			aoStage.show();
		}
	}


	/**
	 * @return
	 */
	public final ObservableList<Airport> getAirportList()
	{
		return airportList;
	}
	
	@Override
	public void stop() {
		System.exit(0);
	}

}

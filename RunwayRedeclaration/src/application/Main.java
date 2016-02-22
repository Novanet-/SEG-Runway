package application;

import application.model.Airport;
import application.view.AddAirportController;
import application.view.MainScreenController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

	private ObservableList<Airport> airportList;

	private Stage msStage;
	private Stage aaStage;
	private Stage arStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public final void start(Stage primaryStage) {
		try {
			// Load person overview.
			final FXMLLoader msLoader = new FXMLLoader();
			final FXMLLoader aaLoader = loadAAStage();
			final FXMLLoader arLoader = loadARStage();
			assert aaLoader != null;
			final AddAirportController aaController = aaLoader.getController();
			aaController.setMainApp(this);

			msLoader.setLocation(Main.class.getResource("view/MainScreen.fxml"));
			final AnchorPane msPage = msLoader.load();
			final MainScreenController msController = msLoader.getController();
			msController.setMainApp(this);

			final Scene scene = new Scene(msPage);
			msStage = primaryStage;
			primaryStage.setScene(scene);
			primaryStage.setTitle("Runway Redeclaration");
			primaryStage.setMinWidth(1000.0);
			primaryStage.setMinHeight(600.0);

			final Collection<Airport> list = new ArrayList<Airport>();
			airportList = FXCollections.observableArrayList(list);

			aaController.linkToSession();
			msController.linkToSession();

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (final Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private FXMLLoader loadAAStage() {
		try {// Load person overview.
			final FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/AddAirport.fxml"));
			final AnchorPane page = loader.load();
			aaStage = new Stage();
			final Scene scene = new Scene(page);
			aaStage.setScene(scene);
			aaStage.setTitle("Add Airport");
			aaStage.setMinWidth(300.0);
			aaStage.setMinHeight(100.0);
			return loader;

		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private FXMLLoader loadARStage() {
		try {
			final FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/AddRunway.fxml"));
			final AnchorPane page = loader.load();
			arStage = new Stage();
			final Scene scene = new Scene(page);
			arStage.setScene(scene);
			arStage.setTitle("Add Runway");
			arStage.setMinWidth(300.0);
			arStage.setMinHeight(100.0);
			return loader;
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public final void toggleAddAirport() {
		if (aaStage.isShowing()) {
			aaStage.hide();
		} else {
			aaStage.show();
		}
	}

	public final void toggleAddRunway() {
		if (arStage.isShowing()) {
			arStage.hide();
		} else {
			arStage.show();
		}
	}

	public final ObservableList<Airport> getAirportList() {
		return airportList;
	}
}

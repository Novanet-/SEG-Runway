package application;

import application.view.MainScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application
{

	public static void main(String[] args)
	{
		launch(args);
	}


	@Override public void start(Stage primaryStage)
	{
		try
		{
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainScreen.fxml"));
			AnchorPane page = loader.load();

			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Runway Redeclaration");
			primaryStage.setMinWidth(1000);
			primaryStage.setMinHeight(600);

			// Give the controller access to the main app.
			MainScreenController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();
		}
		catch (Exception ex)
		{
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

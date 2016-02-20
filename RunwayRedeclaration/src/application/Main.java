package application;

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


	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			AnchorPane page = FXMLLoader.load(getClass().getClassLoader().getResource("MainScreen.fxml"));
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.setTitle("FXML is Simple");
			primaryStage.setMinWidth(1000);
			primaryStage.setMinHeight(600);

			primaryStage.show();
		}
		catch (Exception ex)
		{
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

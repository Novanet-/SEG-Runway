package application.view;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Created by jackclarke on 23/02/2016.
 */
public class AddObjectController {
    private Main mainApp;

    @FXML TextField txtObjectName;
    @FXML ComboBox cmbCloserTo;
    @FXML TextField txtObjectHeight;
    @FXML TextField txtObjectDistFromThreshold;
    @FXML TextField txtObjectDistFromCentre;
    @FXML Button btnObjectSubmit;


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize()
    {

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

    public final void linkToSession()
    {
        //airportList = mainApp.getAirportList();
    }
}

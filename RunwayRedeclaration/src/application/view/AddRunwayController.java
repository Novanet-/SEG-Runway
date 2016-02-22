package application.view;

import application.Main;
import application.model.RunwayParameters;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddRunwayController
{

    private Main mainApp;
    @FXML
    private Button btnSubmitAirport;
    @FXML
    private TextField txtTORA;
    @FXML
    private TextField txtTODA;
    @FXML
    private TextField txtASDA;
    @FXML
    private TextField txtLDA;
    @FXML
    private TextField txtDisplacedThreshold;

    /**
     *
     */
    @FXML
    private void handleBtnSubmitRunway()
    {
        try
        {
            double TORA = Double.parseDouble(txtTORA.textProperty().getValue());
            double TODA = Double.parseDouble(txtTODA.textProperty().getValue());
            double ASDA = Double.parseDouble(txtASDA.textProperty().getValue());
            double LDA = Double.parseDouble(txtLDA.textProperty().getValue());
            double displacedThreshold = Double.parseDouble(txtDisplacedThreshold.textProperty().getValue());

            RunwayParameters parameters;
            parameters = new RunwayParameters(new SimpleDoubleProperty(TORA), new SimpleDoubleProperty(TODA), new SimpleDoubleProperty(ASDA), new SimpleDoubleProperty(LDA));

            // TODO: do something with properties
            // TODO: modify runway form so that a specific airport needs to be selected to add a runway to
            // TODO: work out what to do with displaced threshold and the other paramters of RunwayDetails
            // TODO: once the previous three tasks are done, create a new Runway object with the paramters and details objects along with the runway alignment, then add that object to the list of the given airport
        } catch (NumberFormatException e)
        {
            // TODO: deal with invalid input
            e.printStackTrace();
        }

        mainApp.toggleAddRunway();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize()
    {
        //TODO: Make the listener that validates input an inner class or something and apply it to all text fiels

        //Validates the input to numeric input
        txtTODA.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if (newValue.matches("\\d*"))
                {
                    int value = Integer.parseInt(newValue);
                } else
                {
                    txtTODA.setText(oldValue);
                }
            }
        });
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
}


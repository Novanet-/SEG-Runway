package application.view;

import application.Main;
import application.model.Airport;
import application.model.Runway;
import application.model.RunwayParameters;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class AddRunwayController
{

    StringProperty selectedAirport;
    private Main mainApp;
    @FXML
    private Label lblAirportName;
    @FXML
    private Label lblSecondRunwayAlignment;
    @FXML
    private Button btnSubmitRunway;
    @FXML
    private ComboBox cmbRunwayAlignment;
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
    private ObservableList<Airport> airportList;


    /**
     *
     */
    @FXML
    private void handleBtnSubmitRunway()
    {
        try
        {
            int runwayAlignment;

            double TORA = Double.parseDouble(txtTORA.textProperty().getValue());
            double TODA = Double.parseDouble(txtTODA.textProperty().getValue());
            double ASDA = Double.parseDouble(txtASDA.textProperty().getValue());
            double LDA = Double.parseDouble(txtLDA.textProperty().getValue());
            double displacedThreshold = Double.parseDouble(txtDisplacedThreshold.textProperty().getValue());

            RunwayParameters parameters;
            parameters = new RunwayParameters(new SimpleDoubleProperty(TORA), new SimpleDoubleProperty(TODA), new SimpleDoubleProperty(ASDA), new SimpleDoubleProperty(LDA), new SimpleDoubleProperty(displacedThreshold));

            // TODO: do something with properties
            // TODO: modify runway form so that a specific airport needs to be selected to add a runway to
            // TODO: work out what to do with displaced threshold and the other paramters of RunwayDetails
            // TODO: once the previous three tasks are done, create a new Runway object with the paramters and details objects along with the runway alignment, then add that object to the list of the given airport

            Runway runway = new Runway(parameters, null, new SimpleIntegerProperty(0), new SimpleStringProperty(cmbRunwayAlignment.getValue().toString()));

            for (Airport a : airportList)
            {
                if (a.getAirportName().equals(lblAirportName.getText()))
                {
                    a.addRunway(runway);
                    break;
                }
            }

            mainApp.toggleAddRunway(lblAirportName.getText());

        } catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize()
    {
        ArrayList<String> alignments = new ArrayList<String>();
        for (int i = 0; i < 35; i++)
        {
            alignments.add(String.format("%02d", i));
        }

        cmbRunwayAlignment.setItems(FXCollections.observableArrayList(alignments));
        //TODO: Make the listener that validates input an inner class or something and apply it to all text fiels

//        //Validates the input to numeric input
//        txtTODA.textProperty().addListener(new ChangeListener<String>()
//        {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
//            {
//                if (newValue.matches("\\d*"))
//                {
//                    int value = Integer.parseInt(newValue);
//                } else
//                {
//                    txtTODA.setText(oldValue);
//                }
//            }
//        });

        //updateSelectedAirport();
    }

    /**
     *
     */
    @FXML
    private void handleItemSelected()
    {
        if (Integer.parseInt((String) cmbRunwayAlignment.getValue()) > 17)
        {
            lblSecondRunwayAlignment.setText(Integer.toString(Integer.parseInt((String) cmbRunwayAlignment.getValue()) - 18));
        } else
        {
            lblSecondRunwayAlignment.setText(Integer.toString(Integer.parseInt((String) cmbRunwayAlignment.getValue()) + 18));
        }
    }


    public void updateSelectedAirport(String airportName)
    {
        lblAirportName.setText(airportName);
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
        airportList = mainApp.getAirportList();
    }
}


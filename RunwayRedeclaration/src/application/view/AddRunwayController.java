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
	private       Main                    mainApp;
	@FXML private Label                   lblAirportName;
	@FXML private Label                   lblSecondRunwayAlignment;
	@FXML private Button                  btnSubmitRunway;
	@FXML private ComboBox                cmbRunwayAlignment;
	@FXML private ComboBox                cmbRunwayPosition;
	@FXML private TextField               txtPrimaryTORA;
	@FXML private TextField               txtPrimaryTODA;
	@FXML private TextField               txtPrimaryASDA;
	@FXML private TextField               txtPrimaryLDA;
	@FXML private TextField               txtPrimaryDisplacedThreshold;
	@FXML private TextField               txtSecondaryTORA;
	@FXML private TextField               txtSecondaryTODA;
	@FXML private TextField               txtSecondaryASDA;
	@FXML private TextField               txtSecondaryLDA;
	@FXML private TextField               txtSecondaryDisplacedThreshold;
	private       ObservableList<Airport> airportList;

	//TODO: add stuff like strip end and Resa to gui


	/**
	 *
	 */
	@FXML
	private void handleBtnSubmitRunway()
	{
		try
		{
			int runwayAlignment;

			double primaryTORA = Double.parseDouble(txtPrimaryTORA.textProperty().getValue());
			double primaryTODA = Double.parseDouble(txtPrimaryTODA.textProperty().getValue());
			double primaryASDA = Double.parseDouble(txtPrimaryASDA.textProperty().getValue());
			double primaryLDA = Double.parseDouble(txtPrimaryLDA.textProperty().getValue());
			double primaryDisplacedThreshold = Double.parseDouble(txtPrimaryDisplacedThreshold.textProperty().getValue());

			double secondaryTORA = Double.parseDouble(txtSecondaryTORA.textProperty().getValue());
			double secondaryTODA = Double.parseDouble(txtSecondaryTODA.textProperty().getValue());
			double secondaryASDA = Double.parseDouble(txtSecondaryASDA.textProperty().getValue());
			double secondaryLDA = Double.parseDouble(txtSecondaryLDA.textProperty().getValue());
			double secondaryDisplacedThreshold = Double.parseDouble(txtSecondaryDisplacedThreshold.textProperty().getValue());

			RunwayParameters primaryParameters, secondaryParameters;
			primaryParameters = new RunwayParameters(new SimpleDoubleProperty(primaryTORA), new SimpleDoubleProperty(primaryTODA), new SimpleDoubleProperty(primaryASDA),
					new SimpleDoubleProperty(primaryLDA), new SimpleDoubleProperty(primaryDisplacedThreshold));
			secondaryParameters = new RunwayParameters(new SimpleDoubleProperty(secondaryTORA), new SimpleDoubleProperty(secondaryTODA),
					new SimpleDoubleProperty(secondaryASDA), new SimpleDoubleProperty(secondaryLDA), new SimpleDoubleProperty(secondaryDisplacedThreshold));
			
			// TODO: work out what to do with displaced threshold and the other
			// paramters of RunwayDetails
			// TODO: once the previous task is done, create a new Runway
			// object with the paramters and details objects along with the
			// runway alignment, then add that object to the list of the given
			// airport

			Runway primaryRunway = new Runway(primaryParameters, null, new SimpleIntegerProperty(0),
					new SimpleStringProperty(cmbRunwayAlignment.getValue().toString() + cmbRunwayPosition.getValue().toString()));
			Runway secondaryRunway = new Runway(secondaryParameters, null, new SimpleIntegerProperty(1), new SimpleStringProperty(lblSecondRunwayAlignment.getText()));

			for (Airport a : airportList)
			{
				if (a.getAirportName().equals(lblAirportName.getText()))
				{
					a.addRunway(primaryRunway);
					a.addRunway(secondaryRunway);
					break;
				}
			}

			mainApp.toggleAddRunway(lblAirportName.getText());

		}
		catch (NumberFormatException e)
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

		ArrayList<String> positions = new ArrayList<>();
		positions.add("");
		positions.add("L");
		positions.add("C");
		positions.add("R");

		cmbRunwayAlignment.setItems(FXCollections.observableArrayList(alignments));
		cmbRunwayPosition.setItems(FXCollections.observableArrayList(positions));
	}


	/**
	 *
	 */
	@FXML
	private void handleItemSelected()
	{
		updateSecondRunwayAlignmentPosition(calculateSecondPosition(Integer.parseInt((String) cmbRunwayAlignment.getValue())), (String) cmbRunwayPosition.getValue());
	}


	/**
	 *
	 */
	@FXML
	private void handlePositionSelected()
	{
		updateSecondRunwayAlignmentPosition(calculateSecondPosition(Integer.parseInt((String) cmbRunwayAlignment.getValue())), (String) cmbRunwayPosition.getValue());
	}


	private Integer calculateSecondPosition(Integer firstPosition)
	{
		if (firstPosition > 17)
		{
			return firstPosition - 18;
		}
		else
		{
			return firstPosition + 18;
		}
	}


	private void updateSecondRunwayAlignmentPosition(Integer alignment, String position)
	{
		String newPosition = "";
		if (cmbRunwayPosition.getValue() == "L")
		{
			newPosition = "R";
		}
		else if (cmbRunwayPosition.getValue() == "C")
		{
			newPosition = "C";
		}
		else if (cmbRunwayPosition.getValue() == "R")
		{
			newPosition = "L";
		}

		lblSecondRunwayAlignment.setText(Integer.toString(alignment) + newPosition);
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

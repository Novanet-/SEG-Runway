package application.controller;

import application.Main;
import application.model.Airport;
import application.model.Runway;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddRunwayController
{

	private       StringProperty          selectedAirport;
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
	 * Based on the alignment of the first runway entered, calculates the alignment for the logical runway for the given runway in the opposite direction
	 *
	 * @param firstPosition The alignment of the first runway
	 * @return The alignment of the opposite logical runway
	 */
	private static Integer calculateSecondPosition(Integer firstPosition)
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


	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize()
	{
		final List<String> alignments = new ArrayList<String>();
		for (int i = 0; i < 35; i++)
		{
			alignments.add(String.format("%02d", i));
		}

		final List<String> positions = new ArrayList<>();
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
	private void handleBtnSubmitRunway()
	{
		try
		{
			final double primaryTORA = Double.parseDouble(txtPrimaryTORA.textProperty().getValue());
			final double primaryTODA = Double.parseDouble(txtPrimaryTODA.textProperty().getValue());
			final double primaryASDA = Double.parseDouble(txtPrimaryASDA.textProperty().getValue());
			final double primaryLDA = Double.parseDouble(txtPrimaryLDA.textProperty().getValue());
			final double primaryDisplacedThreshold = Double.parseDouble(txtPrimaryDisplacedThreshold.textProperty().getValue());

			final double secondaryTORA = Double.parseDouble(txtSecondaryTORA.textProperty().getValue());
			final double secondaryTODA = Double.parseDouble(txtSecondaryTODA.textProperty().getValue());
			final double secondaryASDA = Double.parseDouble(txtSecondaryASDA.textProperty().getValue());
			final double secondaryLDA = Double.parseDouble(txtSecondaryLDA.textProperty().getValue());
			final double secondaryDisplacedThreshold = Double.parseDouble(txtSecondaryDisplacedThreshold.textProperty().getValue());

			// TODO: work out what to do with displaced threshold and the other
			// paramters of RunwayDetails
			// TODO: once the previous task is done, create a new Runway
			// object with the paramters and details objects along with the
			// runway alignment, then add that object to the list of the given
			// airport

			final String alignment = cmbRunwayAlignment.getValue().toString() + cmbRunwayPosition.getValue().toString();
			final Runway primaryRunway = new Runway(0, alignment, primaryTORA, primaryTODA, primaryASDA, primaryLDA, primaryDisplacedThreshold);
			final Runway secondaryRunway = new Runway(1, lblSecondRunwayAlignment.getText(), secondaryTORA, secondaryTODA, secondaryASDA, secondaryLDA,
					secondaryDisplacedThreshold);

			final Airport selectedAirport = getSelectedAirport();
			selectedAirport.addRunway(primaryRunway);
			selectedAirport.addRunway(secondaryRunway);

			mainApp.toggleAddRunway(lblAirportName.getText());

		}
		catch (final NumberFormatException e)
		{
			e.printStackTrace();
		}
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


	private void updateSecondRunwayAlignmentPosition(Integer alignment, String position)
	{
		String newPosition = "";
		if (Objects.equals(position, "L"))
		{
			newPosition = "R";
		}
		else if (Objects.equals(position, "C"))
		{
			newPosition = "C";
		}
		else if (Objects.equals(position, "R"))
		{
			newPosition = "L";
		}

		lblSecondRunwayAlignment.setText(Integer.toString(alignment) + newPosition);
	}


	public final void updateSelectedAirport(String airportName)
	{
		lblAirportName.setText(airportName);
	}


	private Airport getSelectedAirport()
	{
		for (final Airport a : airportList)
		{
			if (a.getAirportName().equals(lblAirportName.getText()))
			{
				return a;
			}
		}
		return null;
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

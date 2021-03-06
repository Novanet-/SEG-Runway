package application.controller;

import application.Main;
import application.model.Airport;
import application.model.Runway;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import org.controlsfx.control.Notifications;

import java.util.ArrayList;
import java.util.List;

public class AddRunwayController
{

	private       Main                    mainApp;
	@FXML private Label                   lblAirportName;
	@FXML private Label                   lblSecondRunwayAlignment;
	@FXML private Button                  btnSubmitRunway;
	@FXML private ComboBox<String>        cmbRunwayAlignment;
	@FXML private ComboBox<String>        cmbRunwayPosition;
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

	//TODO: modify css to style dialog boxes


	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize()
	{
		final List<String> alignments = new ArrayList<>();
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

		final BooleanBinding needInputsFilled = txtPrimaryTORA.textProperty().isEmpty().or(txtPrimaryTODA.textProperty().isEmpty()).or(txtPrimaryASDA.textProperty().isEmpty())
				.or(txtPrimaryLDA.textProperty().isEmpty()).or(txtPrimaryDisplacedThreshold.textProperty().isEmpty()).or(txtSecondaryTORA.textProperty().isEmpty())
				.or(txtSecondaryTODA.textProperty().isEmpty()).or(txtSecondaryASDA.textProperty().isEmpty()).or(txtSecondaryLDA.textProperty().isEmpty())
				.or(txtSecondaryDisplacedThreshold.textProperty().isEmpty());
		btnSubmitRunway.disableProperty().bind(needInputsFilled);

		// I have added 2 textFields, you can add more...
		final BooleanBinding needAlignmentSelected = cmbRunwayAlignment.valueProperty().isNull();
		cmbRunwayPosition.disableProperty().bind(needAlignmentSelected);
		cmbRunwayAlignment.setValue("00");
		cmbRunwayPosition.setValue("");
	}

	//TODO: Add not-null/data type validation to input/submission


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

			// TODO: Add all runways properties (incl. strip width) as a user declared property of runways

			final String alignment = cmbRunwayAlignment.getValue() + cmbRunwayPosition.getValue();
			final Runway primaryRunway = new Runway(0, alignment, primaryTORA, primaryTODA, primaryASDA, primaryLDA, primaryDisplacedThreshold);
			final Runway secondaryRunway = new Runway(1, lblSecondRunwayAlignment.getText(), secondaryTORA, secondaryTODA, secondaryASDA, secondaryLDA,
					secondaryDisplacedThreshold);

			final Airport selectedAirport = getSelectedAirport();
			assert selectedAirport != null;
			selectedAirport.addRunway(primaryRunway);
			selectedAirport.addRunway(secondaryRunway);

			Notifications.create().title("Runway added").text("Runway " + primaryRunway.getAlignment() + "and " + secondaryRunway.getAlignment() + " added to system.")
					.showWarning();

			//Clears textboxes after runways added
			txtPrimaryTORA.setText("");
			txtPrimaryTODA.setText("");
			txtPrimaryASDA.setText("");
			txtPrimaryLDA.setText("");
			txtPrimaryDisplacedThreshold.setText("");
			txtSecondaryTORA.setText("");
			txtSecondaryTODA.setText("");
			txtSecondaryASDA.setText("");
			txtSecondaryLDA.setText("");
			txtSecondaryDisplacedThreshold.setText("");

			mainApp.toggleAddRunway(lblAirportName.getText());

		}
		catch (final NumberFormatException e)
		{
			final Alert alert = new Alert(AlertType.ERROR, "Please enter only numerical values.");
			alert.showAndWait();
		}
	}


	/**
	 *
	 */
	@FXML
	private void handleItemSelected()
	{
		updateSecondRunway(Runway.calculateSecondaryAlignment(Integer.parseInt(cmbRunwayAlignment.getValue())), cmbRunwayPosition.getValue());
	}


	/**
	 *
	 */
	@FXML
	private void handlePositionSelected()
	{
		updateSecondRunway(Runway.calculateSecondaryAlignment(Integer.parseInt(cmbRunwayAlignment.getValue())), cmbRunwayPosition.getValue());
	}


	private void updateSecondRunway(int alignment, String position)
	{
		String newPosition = "";

		if (!position.isEmpty())
		{
			switch (position)
			{
				case "L":
					newPosition = "R";
					break;
				case "C":
					newPosition = "C";
					break;
				case "R":
					newPosition = "L";
			}
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
	 * @param mainApp The main application
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

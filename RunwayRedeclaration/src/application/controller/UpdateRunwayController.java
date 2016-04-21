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

public class UpdateRunwayController
{

	Runway selectedRunway;
	private       Main                    mainApp;
	@FXML private Label                   lblAirportName;
	@FXML private Button                  btnSubmitRunway;
	@FXML private ComboBox<String>        cmbRunwayAlignment;
	@FXML private ComboBox<String>        cmbRunwayPosition;
	@FXML private TextField               txtPrimaryTORA;
	@FXML private TextField               txtPrimaryTODA;
	@FXML private TextField               txtPrimaryASDA;
	@FXML private TextField               txtPrimaryLDA;
	@FXML private TextField               txtPrimaryDisplacedThreshold;
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
				.or(txtPrimaryLDA.textProperty().isEmpty()).or(txtPrimaryDisplacedThreshold.textProperty().isEmpty());
		btnSubmitRunway.disableProperty().bind(needInputsFilled);

		// I have added 2 textFields, you can add more...
		final BooleanBinding needAlignmentSelected = cmbRunwayAlignment.valueProperty().isNull();
		cmbRunwayPosition.disableProperty().bind(needAlignmentSelected);
		cmbRunwayAlignment.setValue("00");
		cmbRunwayPosition.setValue("");

		txtPrimaryTORA.setText(Double.toString(selectedRunway.getTORA()));
		txtPrimaryTODA.setText(Double.toString(selectedRunway.getTODA()));
		txtPrimaryASDA.setText(Double.toString(selectedRunway.getASDA()));
		txtPrimaryLDA.setText(Double.toString(selectedRunway.getLDA()));
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

			// TODO: Add all runways properties (incl. strip width) as a user declared property of runways

			final String alignment = cmbRunwayAlignment.getValue() + cmbRunwayPosition.getValue();
			final Runway primaryRunway = new Runway(0, alignment, primaryTORA, primaryTODA, primaryASDA, primaryLDA, primaryDisplacedThreshold);

			final Airport selectedAirport = getSelectedAirport();
			assert selectedAirport != null;
			selectedAirport.updateRunway(selectedRunway.getAlignment(), primaryRunway);

			Notifications.create().title("Runway added").text("Runway " + primaryRunway.getAlignment() + " added to system.").showWarning();

			//Clears textboxes after runways added
			txtPrimaryTORA.setText("");
			txtPrimaryTODA.setText("");
			txtPrimaryASDA.setText("");
			txtPrimaryLDA.setText("");
			txtPrimaryDisplacedThreshold.setText("");

			mainApp.toggleUpdateRunway(lblAirportName.getText(), selectedRunway);

		}
		catch (final NumberFormatException e)
		{
			final Alert alert = new Alert(AlertType.ERROR, "Please enter only numerical values.");
			alert.showAndWait();
		}
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


	public void updateSelectedRunway(final Runway runway)
	{
		selectedRunway = runway;
	}
}

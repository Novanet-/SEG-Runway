package application.controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class CalculationsScreenController
{
	private       Main                    mainApp;
	@FXML
	private TextArea txtCalculations;

	public void setMainApp(Main main)
	{
		mainApp = main;
	}

	public void updateCalculations(String explanation) {
		txtCalculations.setText(explanation);
	}

	public void updatetempCalculations(String airportName, String runway) {
		String calculationText = "";
		calculationText += "Recalculations for " + airportName + " runway " + runway;

		txtCalculations.setText(calculationText);
	}

}

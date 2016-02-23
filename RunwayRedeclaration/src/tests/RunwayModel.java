package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import application.model.Runway;
import application.model.RunwayDetails;
import application.model.RunwayParameters;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RunwayModel {
	@Test
	public void testRunwayProperties() {
		// Exact values don't matter, just want to ensure
		// they are stored and returned

		DoubleProperty TORA = new SimpleDoubleProperty(20.0);
		DoubleProperty TODA = new SimpleDoubleProperty(20.0);
		DoubleProperty ASDA = new SimpleDoubleProperty(20.0);
		DoubleProperty LDA = new SimpleDoubleProperty(20.0);
		DoubleProperty displacedThreshold = new SimpleDoubleProperty(20.0);
		RunwayParameters rP = new RunwayParameters(TORA, TODA, ASDA, LDA, displacedThreshold);

		IntegerProperty displacedThresholdI = new SimpleIntegerProperty(20);
		IntegerProperty clearway = new SimpleIntegerProperty(20);
		IntegerProperty stopway = new SimpleIntegerProperty(20);
		IntegerProperty resa = new SimpleIntegerProperty(20);
		IntegerProperty stripEnd = new SimpleIntegerProperty(20);
		IntegerProperty blastProtection = new SimpleIntegerProperty(20);
		IntegerProperty runwayStrip = new SimpleIntegerProperty(20);
		RunwayDetails rD = new RunwayDetails(displacedThresholdI, clearway, stopway, resa, stripEnd, blastProtection,
				runwayStrip);

		int runwayID = 14;
		String alignment = "L";
		Runway r = new Runway(rP, rD, new SimpleIntegerProperty(runwayID), new SimpleStringProperty(alignment));

		assertEquals("Should return parameter", runwayID, r.getRunwayID());
		assertEquals("Should return parameter", alignment, r.getAlignment());
		assertEquals("Should return paramter", rP, r.getRunwayParameters());
	}
}

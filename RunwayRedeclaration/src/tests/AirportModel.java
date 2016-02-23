package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import application.model.Airport;
import application.model.Runway;
import application.model.RunwayDetails;
import application.model.RunwayParameters;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AirportModel {

	@Test
	public void testAirportProperties() {
		Airport a = new Airport(new SimpleIntegerProperty(42), new SimpleStringProperty("Brussels"));

		assertEquals("Airport ID should be 42", 42, a.getAirportID());
		assertEquals("Airport name should be Brussels", "Brussels", a.getAirportName());
		assertEquals("Airport string representation correct", "42 - Brussels", a.toString());
	}

	@Test
	public void testAirportAddRunway() {
		Airport a = new Airport(new SimpleIntegerProperty(20), new SimpleStringProperty("JFK International"));
		DoubleProperty dP = new SimpleDoubleProperty(0.0);
		IntegerProperty iP = new SimpleIntegerProperty(0);
		RunwayParameters p = new RunwayParameters(dP, dP, dP, dP, dP);
		RunwayDetails d = new RunwayDetails(iP, iP, iP, iP, iP, iP, iP);
		Runway r = new Runway(p, d, new SimpleIntegerProperty(9), new SimpleStringProperty("L"));

		a.addRunway(r);
		assertEquals("Should get runway back", r, a.getRunways().get(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAirportIDNegative() {
		new Airport(new SimpleIntegerProperty(-1), new SimpleStringProperty("Test"));
	}

	@Test
	public void testAirportIDBoundaries() {
		Airport a = null;
		a = new Airport(new SimpleIntegerProperty(Integer.MAX_VALUE), new SimpleStringProperty("Test"));
		assertEquals("Should allow all positive integers", Integer.MAX_VALUE, a.getAirportID());
		a = new Airport(new SimpleIntegerProperty(0), new SimpleStringProperty("Test"));
		assertEquals("Should allow zero", 0, a.getAirportID());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAirportNameBoundaries() {
		new Airport(new SimpleIntegerProperty(10), new SimpleStringProperty(""));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAirportNullName() {
		new Airport(new SimpleIntegerProperty(10), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAirportNullID() {
		new Airport(null, new SimpleStringProperty("Test"));
	}
}

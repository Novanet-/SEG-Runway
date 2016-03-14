package tests;

import application.model.Airport;
import application.model.Runway;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AirportModel
{

	@Test
	public void testAirportProperties()
	{
		Airport a = new Airport(42, "Brussels");

		assertEquals("Airport ID should be 42", 42, a.getAirportID());
		assertEquals("Airport name should be Brussels", "Brussels", a.getAirportName());
		assertEquals("Airport string representation correct", "42 - Brussels", a.toString());
	}


	@Test
	public void testAirportAddRunway()
	{
		Airport a = new Airport(20, "JFK International");
		Runway r = new Runway(0, "C", 0.0, 0.0, 0.0, 0.0, 0.0);
		a.addRunway(r);
		assertEquals("Should get runway back", r, a.getRunways().get(a.getRunways().size() - 1));
	}


	@Test(expected = IllegalArgumentException.class)
	public void testAirportIDNegative()
	{
		new Airport(-1, "Test");
	}


	@Test
	public void testAirportIDBoundaries()
	{
		Airport a;
		a = new Airport(Integer.MAX_VALUE, "Test");
		assertEquals("Should allow all positive integers", Integer.MAX_VALUE, a.getAirportID());
		a = new Airport(0, "Test");
		assertEquals("Should allow zero", 0, a.getAirportID());
	}


	@Test(expected = IllegalArgumentException.class)
	public void testAirportNameBoundaries()
	{
		new Airport(10, "");
	}


	@Test(expected = IllegalArgumentException.class)
	public void testAirportNullName()
	{
		new Airport(10, null);
	}
}

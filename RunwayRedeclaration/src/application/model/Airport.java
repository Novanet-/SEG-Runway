package application.model;

import java.util.ArrayList;

public class Airport
{

	private int airportID;
	private String airportName;
	private ArrayList<Runway> runways;

	public int getAirportID()
	{
		return airportID;
	}

	public String getAirportName()
	{
		return airportName;
	}

	public ArrayList<Runway> getRunways()
	{
		return runways;
	}

	public void addRunway(Runway runway)
	{
		this.runways.add(runway);
	}

}

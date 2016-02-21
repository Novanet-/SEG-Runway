package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Airport
{

	private final IntegerProperty        airportID;
	private final StringProperty         airportName;
	private       ObservableList<Runway> runways;


	public Airport(final IntegerProperty airportID, final StringProperty airportName, final ObservableList<Runway> runways)
	{
		this.airportID = airportID;
		this.airportName = airportName;
		this.runways = runways;
	}


	public int getAirportID()
	{
		return airportID.getValue();
	}


	public String getAirportName()
	{
		return airportName.getValueSafe();
	}


	public ObservableList<Runway> getRunways()
	{
		return runways;
	}


	public void addRunway(Runway runway)
	{
		this.runways.add(runway);
	}

}

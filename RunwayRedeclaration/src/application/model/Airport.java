package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Airport
{

	private IntegerProperty        airportID;
	private StringProperty         airportName;
	private ObservableList<Runway> runways;


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

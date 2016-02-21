package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Airport
{

	private final IntegerProperty        airportID;
	private final StringProperty         airportName;
	private       ObservableList<Runway> runways;


	public Airport(final IntegerProperty airportID, final StringProperty airportName)
	{
		this.airportID = airportID;
		this.airportName = airportName;
		ArrayList<Runway> list = new ArrayList<Runway>();
		runways = FXCollections.observableArrayList(list);
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

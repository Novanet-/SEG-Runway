package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Airport
{

	private final IntegerProperty        airportID;
	private final StringProperty         airportName;
	private final ObservableList<Runway> runways;


	public Airport(final IntegerProperty airportID, final StringProperty airportName)
	{
		this.airportID = airportID;
		this.airportName = airportName;
		final Collection<Runway> list = new ArrayList<Runway>();
		runways = FXCollections.observableArrayList(list);
	}


	public final int getAirportID()
	{
		return airportID.getValue();
	}


	public final String getAirportName()
	{
		return airportName.getValueSafe();
	}


	public final List<Runway> getRunways()
	{
		return Collections.unmodifiableList(runways);
	}


	public final void addRunway(Runway runway)
	{
		runways.add(runway);
	}


	@Override
	public final String toString()
	{
		return airportID.getValue() + " - " + airportName.getValue();
	}

}

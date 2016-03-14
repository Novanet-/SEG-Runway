package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Airport
{

	private final int                    airportID;
	private final String                 airportName;
	private final ObservableList<Runway> runways;


	/**
	 * @param airportID   Unique numerical identifier for the airport
	 * @param airportName String identifier for the airport
	 */
	public Airport(final int airportID, final String airportName)
	{
		if (airportName == null || airportID < 0 || "".equals(airportName))
		{
			throw new IllegalArgumentException();
		}

		this.airportID = airportID;
		this.airportName = airportName;

		final Collection<Runway> list = new ArrayList<>();
		runways = FXCollections.observableArrayList(list);
		//createTestRunway(this);
	}


	@SuppressWarnings("unused")
	private static void createTestRunway(final Airport airport)
	{
		final double primaryTORA = 3884.0;
		final double primaryTODA = 3962.0;
		final double primaryASDA = 3884.0;
		final double primaryLDA = 3884.0;
		final double primaryDisplacedThreshold = 0.0;

		final double secondaryTORA = 3902.0;
		final double secondaryTODA = 3902.0;
		final double secondaryASDA = 3902.0;
		final double secondaryLDA = 3595.0;
		final double secondaryDisplacedThreshold = 306.0;

		final Runway primaryRunway = new Runway(0, 27 + "R", primaryTORA, primaryTODA, primaryASDA, primaryLDA, primaryDisplacedThreshold);
		final Runway secondaryRunway = new Runway(1, "09L", secondaryTORA, secondaryTODA, secondaryASDA, secondaryLDA, secondaryDisplacedThreshold);
		airport.addRunway(primaryRunway);
		airport.addRunway(secondaryRunway);

	}


	/**
	 * @return Numerical airport id
	 */
	public int getAirportID()
	{
		return airportID;
	}


	/**
	 * @return String airport name
	 */
	public final String getAirportName()
	{
		return airportName;
	}


	/**
	 * @return A list of runways controlled by the airport
	 */
	public final List<Runway> getRunways()
	{
		return Collections.unmodifiableList(runways);
	}


	/**
	 * @param runway The runway to be added to the airport
	 */
	public final void addRunway(Runway runway)
	{
		runways.add(runway);
	}


	/**
	 * @return A string representation of the airport
	 */
	@Override
	public final String toString()
	{
		return airportID + " - " + airportName;
	}


	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof Airport))
			return false;

		Airport o = (Airport) other;
		return getAirportID() == o.getAirportID() &&
				getAirportName().equals(o.getAirportName()) &&
				getRunways().equals(o.getRunways());
	}

}
